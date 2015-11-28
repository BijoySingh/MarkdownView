package com.bijoykochar.markdownview.markdown;

import com.bijoykochar.markdownview.markdown.Markdown.Type;
import com.bijoykochar.markdownview.markdown.MarkdownRule.RuleType;

import java.util.ArrayList;
import java.util.List;

/**
 * The configuration file for setting the various connections
 * Created by bijoy on 10/12/15.
 */
public class MarkdownConfig {
    private List<MarkdownRule> configs = new ArrayList<>();

    /**
     * Returns the markdown configuration instance
     *
     * @return the instance of the markdown configurations
     */
    public static MarkdownConfig getInstance() {
        return new MarkdownConfig();
    }

    /**
     * Modify an item of the Configurations. This will not allow for adding new configurations
     *
     * @param newRule the new rule
     * @return the instance of the markdown configuration
     */
    public MarkdownConfig modify(MarkdownRule newRule) {
        for (int ruleIndex = 0; ruleIndex != configs.size(); ruleIndex++) {
            MarkdownRule rule = configs.get(ruleIndex);
            if (rule.markType.equals(newRule.markType)) {
                configs.set(ruleIndex, newRule);
                break;
            }
        }
        return this;
    }

    /**
     * Returns the length of the delimiter of the rule
     *
     * @param type the type of the markdown
     * @return the length
     */
    public Integer getOptionLength(Type type) {
        for (MarkdownRule rule : configs) {
            if (rule.markType.equals(type)) {
                return rule.indicator.length();
            }
        }

        return 0;
    }

    /**
     * Get all the rules for a rule type
     *
     * @param ruleType the rule type
     * @return the list of rules
     */
    public List<MarkdownRule> getMarkdownRules(MarkdownRule.RuleType ruleType) {
        List<MarkdownRule> rules = new ArrayList<>();
        for (MarkdownRule rule : configs) {
            if (rule.ruleType.equals(ruleType)) {
                rules.add(rule);
            }
        }
        return rules;
    }

    /**
     * The private constructor
     */
    private MarkdownConfig() {
        configs.add(new MarkdownRule(RuleType.RANGE, "\\*\\*", Type.BOLD));
        configs.add(new MarkdownRule(RuleType.MULTI_LINE, "```", Type.CODE));
        configs.add(new MarkdownRule(RuleType.FULL_LINE, "=", Type.HEADING));
        configs.add(new MarkdownRule(RuleType.RANGE, "\\*", Type.ITALICS));
        configs.add(new MarkdownRule(RuleType.STARTS_WITH, "- ", Type.ORDERED_LIST_ITEM));
        configs.add(new MarkdownRule(RuleType.RANGE, "`", Type.MONOSPACE));
        configs.add(new MarkdownRule(RuleType.RANGE, "~~", Type.STRIKE_THROUGH));
        configs.add(new MarkdownRule(RuleType.FULL_LINE, "-", Type.SUB_HEADING));
        configs.add(new MarkdownRule(RuleType.STARTS_WITH, "## ", Type.SUB_SUB_HEADING));
        configs.add(new MarkdownRule(RuleType.RANGE, "#", Type.UNDERLINE));
        configs.add(new MarkdownRule(RuleType.STARTS_WITH, "* ", Type.UNORDERED_LIST_ITEM));
    }
}
