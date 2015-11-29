package com.bijoykochar.markdownview.html;

import com.bijoykochar.markdownview.markdown.Markdown;
import com.bijoykochar.markdownview.markdown.MarkdownItem;

import java.util.List;
import java.util.Map;

/**
 * THe class to convert the markdown items to HTML code
 * Created by bijoy on 10/12/15.
 */
public class MarkdownToHtml {

    private MarkdownHtmlConfig htmlConfig;
    private MarkdownCSSConfig cssConfig;

    /**
     * Gets the instance of the Markdown to HTML
     *
     * @param htmlConfig the HTML configuration
     * @param cssConfig  The CSS configuration
     * @return the instance of Markdown To HTML
     */
    public static MarkdownToHtml getInstance(MarkdownHtmlConfig htmlConfig,
                                             MarkdownCSSConfig cssConfig) {
        return new MarkdownToHtml(htmlConfig, cssConfig);
    }

    /**
     * Gets the HTML for the markdown
     *
     * @param markdownItems the items
     * @return the HTML string
     */
    public String getHTMLString(List<MarkdownItem> markdownItems) {
        String html = "<html>";
        html += getCSSString();
        html += "<body>";
        html += convertMarkdownItems(markdownItems);
        html += "</body>";
        html += "</html>";

        return html;
    }

    /**
     * Recursively converts the markdown items into HTML
     *
     * @param markdownItems
     * @return
     */
    private String convertMarkdownItems(List<MarkdownItem> markdownItems) {
        String response = "";
        for (MarkdownItem item : markdownItems) {
            response += convertMarkdownItem(item) + "\n";
        }
        return response;
    }

    /**
     * Converts a markdown item to HTML
     *
     * @param item the markdown item
     * @return the HTML string
     */
    private String convertMarkdownItem(MarkdownItem item) {
        if (item.isTypeless()) {
            return item.line;
        } else if (item.type.equals(Markdown.Type.NEW_LINE)) {
            return "<br/>";
        } else if (item.type.equals(Markdown.Type.ORDERED_LIST)) {
            return "<ol>" + convertMarkdownItems(item.items) + "</ol>";
        } else if (item.type.equals(Markdown.Type.UNORDERED_LIST)) {
            return "<ul>" + convertMarkdownItems(item.items) + "</ul>";
        } else if (item.type.equals(Markdown.Type.NONE)) {
            return convertMarkdownItems(item.items);
        } else if (htmlConfig.getConfigs().containsKey(item.type)) {
            MarkdownHtmlRule rule = htmlConfig.getConfigs().get(item.type);
            return rule.getStartTag() + convertMarkdownItems(item.items) + rule.getEndTag();
        }
        return "";
    }

    /**
     * Returns the CSS String
     *
     * @return the CSS String
     */
    private String getCSSString() {
        Map<Markdown.Type, Map<String, String>> cssConfigurations = cssConfig.getConfigs();
        Map<Markdown.Type, MarkdownHtmlRule> htmlConfigurations = htmlConfig.getConfigs();

        String css = "<head>\n" +
                "<meta charset=\"utf-8\">\n" +
                "<style type=\"text/css\">\n";
        for (Map.Entry<Markdown.Type, Map<String, String>> entry : cssConfigurations.entrySet()) {
            if (entry.getValue().isEmpty()) {
                continue;
            }

            if (entry.getKey().equals(Markdown.Type.NONE)) {
                css += "* {";
            } else {
                css += htmlConfigurations.get(entry.getKey()).htmlTag + " {";
            }

            for (Map.Entry<String, String> cssEntry : entry.getValue().entrySet()) {
                css += "\n" + cssEntry.getKey() + " : " + cssEntry.getValue() + ";";
            }

            css += "\n}\n";
        }
        css += "</style></head>\n";
        return css;
    }


    /**
     * Private constructor
     *
     * @param htmlConfig The HTML configuration
     * @param cssConfig  The CSS configuration
     */
    private MarkdownToHtml(MarkdownHtmlConfig htmlConfig, MarkdownCSSConfig cssConfig) {
        this.htmlConfig = htmlConfig;
        this.cssConfig = cssConfig;
    }

}
