package com.bijoykochar.markdownview.html;

import com.bijoykochar.markdownview.markdown.Markdown;

import java.util.HashMap;
import java.util.Map;

/**
 * The CSS Configurations
 * Created by bijoy on 11/28/15.
 */
public class MarkdownCSSConfig {
    /**
     * Configurations
     */
    private Map<Markdown.Type, Map<String, String>> configs = new HashMap<>();

    /**
     * Returns an instance of the HTML Configuration
     *
     * @return Instance
     */
    public static MarkdownCSSConfig getInstance() {
        return new MarkdownCSSConfig();
    }

    /**
     * Modifies the configuration
     *
     * @param newRule The rule to change
     * @return
     */
    public MarkdownCSSConfig modify(Markdown.Type rule, String cssTag, String cssValue) {
        if (configs.containsKey(rule)) {
            Map<String, String> configuration = configs.get(rule);
            configuration.put(cssTag, cssValue);
            configs.put(rule, configuration);
        }
        return this;
    }

    /**
     * Returns the configurations
     *
     * @return the configurations
     */
    public Map<Markdown.Type, Map<String, String>> getConfigs() {
        return configs;
    }

    /**
     * The private constructor
     */
    private MarkdownCSSConfig() {
        configs.put(Markdown.Type.BOLD, new HashMap<String, String>());
        configs.put(Markdown.Type.CODE, new HashMap<String, String>());
        configs.put(Markdown.Type.HEADING, new HashMap<String, String>());
        configs.put(Markdown.Type.ITALICS, new HashMap<String, String>());
        configs.put(Markdown.Type.ORDERED_LIST_ITEM, new HashMap<String, String>());
        configs.put(Markdown.Type.MONOSPACE, new HashMap<String, String>());
        configs.put(Markdown.Type.STRIKE_THROUGH, new HashMap<String, String>());
        configs.put(Markdown.Type.SUB_HEADING, new HashMap<String, String>());
        configs.put(Markdown.Type.SUB_SUB_HEADING, new HashMap<String, String>());
        configs.put(Markdown.Type.UNDERLINE, new HashMap<String, String>());
        configs.put(Markdown.Type.UNORDERED_LIST_ITEM, new HashMap<String, String>());
    }
}
