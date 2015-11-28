package com.bijoykochar.markdownview.html;

import com.bijoykochar.markdownview.markdown.Markdown;

import java.util.HashMap;
import java.util.Map;

/**
 * The HTML Configurations
 * Created by bijoy on 10/12/15.
 */
public class MarkdownHtmlConfig {
    /**
     * Configurations
     */
    private Map<Markdown.Type, MarkdownHtmlRule> configs = new HashMap<>();

    /**
     * Returns an instance of the HTML Configuration
     *
     * @return Instance
     */
    public static MarkdownHtmlConfig getInstance() {
        return new MarkdownHtmlConfig();
    }

    /**
     * Modifies the configuration
     *
     * @param newRule The rule to change
     * @return
     */
    public MarkdownHtmlConfig modify(MarkdownHtmlRule newRule) {
        configs.put(newRule.markType, newRule);
        return this;
    }

    /**
     * Returns the configurations
     *
     * @return the configurations
     */
    public Map<Markdown.Type, MarkdownHtmlRule> getConfigs() {
        return configs;
    }

    /**
     * The private constructor
     */
    private MarkdownHtmlConfig() {
        configs.put(Markdown.Type.BOLD, new MarkdownHtmlRule("b", Markdown.Type.BOLD));
        configs.put(Markdown.Type.CODE, new MarkdownHtmlRule("pre", Markdown.Type.CODE));
        configs.put(Markdown.Type.HEADING, new MarkdownHtmlRule("h1", Markdown.Type.HEADING));
        configs.put(Markdown.Type.ITALICS, new MarkdownHtmlRule("i", Markdown.Type.ITALICS));
        configs.put(Markdown.Type.ORDERED_LIST_ITEM, new MarkdownHtmlRule("li", Markdown.Type.ORDERED_LIST_ITEM));
        configs.put(Markdown.Type.MONOSPACE, new MarkdownHtmlRule("code", Markdown.Type.MONOSPACE));
        configs.put(Markdown.Type.STRIKE_THROUGH, new MarkdownHtmlRule("s", Markdown.Type.STRIKE_THROUGH));
        configs.put(Markdown.Type.SUB_HEADING, new MarkdownHtmlRule("h2", Markdown.Type.SUB_HEADING));
        configs.put(Markdown.Type.SUB_SUB_HEADING, new MarkdownHtmlRule("h3", Markdown.Type.SUB_SUB_HEADING));
        configs.put(Markdown.Type.UNDERLINE, new MarkdownHtmlRule("u", Markdown.Type.UNDERLINE));
        configs.put(Markdown.Type.UNORDERED_LIST_ITEM, new MarkdownHtmlRule("li", Markdown.Type.UNORDERED_LIST_ITEM));
    }
}
