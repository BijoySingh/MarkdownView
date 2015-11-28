package com.bijoykochar.markdownview.markdown;

import com.bijoykochar.markdownview.html.MarkdownCSSConfig;
import com.bijoykochar.markdownview.html.MarkdownHtmlConfig;
import com.bijoykochar.markdownview.html.MarkdownToHtml;

import java.util.ArrayList;
import java.util.List;

/**
 * Parses the markdown string to a list of markdown items for each line.
 * Created by bijoy on 10/11/15.
 */
public class MarkdownParser {

    MarkdownRuleSet ruleSet;
    MarkdownConfig config;
    MarkdownHtmlConfig htmlConfig;
    MarkdownCSSConfig cssConfig;
    MarkdownToHtml markdownToHtml;

    public List<MarkdownItem> getMarkdownItems(String text) {
        String[] lines = getLines(text);
        return getMarkdownItems(lines);
    }

    public String convertToHtml(String text) {
        List<MarkdownItem> items = getMarkdownItems(text);
        return markdownToHtml.getHTMLString(items);
    }

    public static MarkdownParser getInstance() {
        return new MarkdownParser(MarkdownConfig.getInstance(),
                MarkdownHtmlConfig.getInstance(),
                MarkdownCSSConfig.getInstance());
    }

    public static MarkdownParser getInstance(MarkdownConfig config) {
        return new MarkdownParser(config,
                MarkdownHtmlConfig.getInstance(),
                MarkdownCSSConfig.getInstance());
    }

    public static MarkdownParser getInstance(MarkdownHtmlConfig htmlConfig) {
        return new MarkdownParser(MarkdownConfig.getInstance(),
                htmlConfig,
                MarkdownCSSConfig.getInstance());
    }

    public static MarkdownParser getInstance(MarkdownCSSConfig cssConfig) {
        return new MarkdownParser(MarkdownConfig.getInstance(),
                MarkdownHtmlConfig.getInstance(),
                cssConfig);
    }

    public static MarkdownParser getInstance(MarkdownConfig config,
                                             MarkdownHtmlConfig htmlConfig) {
        return new MarkdownParser(config,
                htmlConfig,
                MarkdownCSSConfig.getInstance());
    }

    public static MarkdownParser getInstance(MarkdownConfig config,
                                             MarkdownCSSConfig cssConfig) {
        return new MarkdownParser(config,
                MarkdownHtmlConfig.getInstance(),
                cssConfig);
    }

    public static MarkdownParser getInstance(MarkdownCSSConfig cssConfig,
                                             MarkdownHtmlConfig htmlConfig) {
        return new MarkdownParser(MarkdownConfig.getInstance(),
                htmlConfig,
                cssConfig);
    }

    public static MarkdownParser getInstance(MarkdownConfig config,
                                             MarkdownHtmlConfig htmlConfig,
                                             MarkdownCSSConfig cssConfig) {
        return new MarkdownParser(config,
                htmlConfig,
                cssConfig);
    }

    /**
     * Splits the markdown text into lines based on \n.
     *
     * @param text the markdown text to be split.
     * @return the text split on lines.
     */
    private String[] getLines(String text) {
        text = text.replace("\n\n", "  \n");
        return text.split("\n");
    }

    /**
     * Gets the MarkdownItem for a line.
     *
     * @param line the line to parse.
     * @return the markdown line object line.
     */
    private MarkdownItem getMarkdownItem(String line) {
        Markdown.Type multilineType = ruleSet.getMultiLineType(line);
        Markdown.Type startWithType = ruleSet.getStartsWithType(line);
        Markdown.Type fullLineType = ruleSet.getFullLineType(line);

        if (!multilineType.equals(Markdown.Type.NONE)) {
            return new MarkdownItem(multilineType);
        } else if (!fullLineType.equals(Markdown.Type.NONE)) {
            return new MarkdownItem(fullLineType);
        } else {
            Integer prefixLength = config.getOptionLength(startWithType);
            String shortenedLine = line.replaceAll("^\\s+", "").substring(prefixLength);
            MarkdownItem markdownItem = ruleSet.getRangeItem(line);

            if (startWithType.equals(Markdown.Type.NONE)) {
                return markdownItem;
            } else {
                List<MarkdownItem> items = new ArrayList<>();
                items.add(markdownItem);
                return new MarkdownItem(items, startWithType, line);
            }
        }
    }

    /**
     * Converts a string array into a list of markdown items which can be rendered
     *
     * @param lines the lines to be converted
     * @return the list of markdown items
     */
    private List<MarkdownItem> getMarkdownItems(String[] lines) {

        Boolean codeStarted = false;
        Boolean orderedStarted = false;
        Boolean unorderedStarted = false;

        List<MarkdownItem> items = new ArrayList<>();
        List<MarkdownItem> subItems = new ArrayList<>();

        for (String line : lines) {
            MarkdownItem item = getMarkdownItem(line);

            if (codeStarted) {
                if (item.type.equals(Markdown.Type.CODE)) {
                    codeStarted = false;
                    items.add(new MarkdownItem(subItems, Markdown.Type.CODE));
                    continue;
                } else {
                    item = new MarkdownItem(Markdown.Type.NONE, line);
                    subItems.add(item);
                    continue;
                }
            } else if (item.type.equals(Markdown.Type.CODE)) {
                codeStarted = true;
                subItems = new ArrayList<>();
                continue;
            }

            if (orderedStarted) {
                if (!item.type.equals(Markdown.Type.ORDERED_LIST_ITEM)) {
                    orderedStarted = false;
                    items.add(new MarkdownItem(subItems, Markdown.Type.ORDERED_LIST));
                } else {
                    subItems.add(item);
                    continue;
                }
            }

            if (unorderedStarted) {
                if (!item.type.equals(Markdown.Type.UNORDERED_LIST_ITEM)) {
                    unorderedStarted = false;
                    items.add(new MarkdownItem(subItems, Markdown.Type.UNORDERED_LIST));
                } else {
                    subItems.add(item);
                    continue;
                }
            }

            if (item.type.equals(Markdown.Type.ORDERED_LIST_ITEM)) {
                orderedStarted = true;
                subItems = new ArrayList<>();
                subItems.add(item);
                continue;
            } else if (item.type.equals(Markdown.Type.UNORDERED_LIST_ITEM)) {
                unorderedStarted = true;
                subItems = new ArrayList<>();
                subItems.add(item);
                continue;
            }

            if (item.type.equals(Markdown.Type.HEADING)) {
                if (!items.isEmpty() && items.get(items.size() - 1).type.equals(Markdown.Type.NONE)) {
                    List<MarkdownItem> tItems = new ArrayList<>();
                    MarkdownItem tItem = items.get(items.size() - 1);
                    tItems.add(tItem);
                    items.set(items.size() - 1,
                            new MarkdownItem(tItems, Markdown.Type.HEADING, line));
                    continue;
                } else {
                    items.add(new MarkdownItem(Markdown.Type.NONE, line));
                    continue;
                }
            } else if (item.type.equals(Markdown.Type.SUB_HEADING)) {
                if (!items.isEmpty() && items.get(items.size() - 1).type.equals(Markdown.Type.NONE)) {
                    List<MarkdownItem> tItems = new ArrayList<>();
                    MarkdownItem tItem = items.get(items.size() - 1);
                    tItems.add(tItem);
                    items.set(items.size() - 1,
                            new MarkdownItem(tItems, Markdown.Type.SUB_HEADING, line));
                    continue;
                } else {
                    items.add(new MarkdownItem(Markdown.Type.NONE, line));
                    continue;
                }
            }

            if (line.endsWith("  ")) {
                items.add(item);
                items.add(new MarkdownItem(Markdown.Type.NEW_LINE));
            } else if (!line.endsWith(" ")) {
                item.line = item.line + " ";
            }

            items.add(item);
        }

        if (codeStarted) {
            items.add(new MarkdownItem(subItems, Markdown.Type.CODE));
        } else if (orderedStarted) {
            items.add(new MarkdownItem(subItems, Markdown.Type.ORDERED_LIST));
        } else if (unorderedStarted) {
            items.add(new MarkdownItem(subItems, Markdown.Type.UNORDERED_LIST));
        }
        return items;
    }

    private MarkdownParser(MarkdownConfig configuration, MarkdownHtmlConfig htmlConfig, MarkdownCSSConfig cssConfig) {
        this.ruleSet = MarkdownRuleSet.getInstance(configuration);
        this.config = configuration;
        this.htmlConfig = htmlConfig;
        this.cssConfig = cssConfig;
        this.markdownToHtml = MarkdownToHtml.getInstance(htmlConfig, cssConfig);
    }
}
