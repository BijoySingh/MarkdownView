package com.bijoykochar.markdownview.markdown;

/**
 * The Markdown Rule
 * Created by bijoy on 10/12/15.
 */
public class MarkdownRule {

    public enum RuleType {
        STARTS_WITH,
        FULL_LINE,
        MULTI_LINE,
        RANGE
    }

    public String indicator;
    public RuleType ruleType;
    public Markdown.Type markType;

    public MarkdownRule(RuleType ruleType, String indicator, Markdown.Type markType) {
        this.ruleType = ruleType;
        this.indicator = indicator;
        this.markType = markType;
    }

}
