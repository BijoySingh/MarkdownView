package com.bijoykochar.markdownview.markdown;

import com.bijoykochar.markdownview.markdown.MarkdownRule.RuleType;

import java.util.ArrayList;
import java.util.List;

/**
 * The rules for the markdown
 * Created by bijoy on 10/11/15.
 */
public class MarkdownRuleSet {

    private MarkdownConfig configuration;
    private FullLineRuleSet fullLineRuleSet;
    private MultiLineRuleSet multiLineRuleSet;
    private RangeRuleSet rangeRuleSet;
    private StartWithRuleSet startWithRuleSet;

    public static MarkdownRuleSet getInstance(MarkdownConfig configuration) {
        return new MarkdownRuleSet(configuration);
    }

    public Markdown.Type getMultiLineType(String line) {
        return multiLineRuleSet.getType(line);
    }

    public Markdown.Type getFullLineType(String line) {
        return fullLineRuleSet.getType(line);
    }

    public Markdown.Type getStartsWithType(String line) {
        return startWithRuleSet.getType(line);
    }

    public MarkdownItem getRangeItem(String line) {
        return rangeRuleSet.createTree(line);
    }

    private abstract class RuleSet {
        List<MarkdownRule> rules;

        public RuleSet() {
        }

        public RuleSet(List<MarkdownRule> rules) {
            this.rules = rules;
        }

        public boolean checkType(String line, MarkdownRule rule) {
            return false;
        }

        public Markdown.Type getType(String line) {
            for (MarkdownRule rule : rules) {
                if (checkType(line, rule)) {
                    return rule.markType;
                }
            }
            return Markdown.Type.NONE;
        }
    }

    private final class StartWithRuleSet extends RuleSet {

        public StartWithRuleSet(List<MarkdownRule> rules) {
            super(rules);
        }

        @Override
        public boolean checkType(String line, MarkdownRule rule) {
            String reference = rule.indicator;
            return line.trim().startsWith(reference);
        }

        public Integer getOptionLength(Markdown.Type type) {
            return configuration.getOptionLength(type);
        }

    }

    private final class FullLineRuleSet extends RuleSet {

        public FullLineRuleSet(List<MarkdownRule> rules) {
            super(rules);
        }

        @Override
        public boolean checkType(String line, MarkdownRule rule) {
            String reference = rule.indicator;

            int index = 0;
            for (; index < line.length(); index += reference.length()) {
                String subString = line.substring(index, index + reference.length());
                if (!reference.contentEquals(subString)) {
                    return false;
                }
            }

            return line.length() == index;
        }
    }

    private final class RangeRuleSet extends RuleSet {

        public RangeRuleSet(List<MarkdownRule> rules) {
            super(rules);
        }

        public MarkdownItem createTree(String line) {
            MarkdownItem item = new MarkdownItem(line);
            for (MarkdownRule rule : rules) {
                item = splitByType(line, rule);
                if (!item.isTypeless()) {
                    break;
                }
            }
            return item;
        }

        private MarkdownItem splitByType(String line, MarkdownRule rule) {
            String reference = rule.indicator;

            String[] subStrings = line.split(reference);
            if (subStrings.length == 1) {
                return new MarkdownItem(line);
            }

            List<MarkdownItem> items = new ArrayList<>();
            for (int index = 0; index < subStrings.length; index++) {
                String subString = subStrings[index];
                MarkdownItem markdownItem = createTree(subString);

                if (index % 2 == 1) {
                    List<MarkdownItem> lines = new ArrayList<>();
                    lines.add(markdownItem);
                    markdownItem = new MarkdownItem(lines, rule.markType);
                }

                items.add(markdownItem);
            }

            return new MarkdownItem(items, Markdown.Type.NONE, line);
        }

    }

    private final class MultiLineRuleSet extends RuleSet {

        public MultiLineRuleSet(List<MarkdownRule> rules) {
            super(rules);
        }

        @Override
        public boolean checkType(String line, MarkdownRule rule) {
            String reference = rule.indicator;
            return line.contentEquals(reference);
        }

    }

    private MarkdownRuleSet(MarkdownConfig config) {
        this.configuration = config;
        this.fullLineRuleSet = new FullLineRuleSet(config.getMarkdownRules(RuleType.FULL_LINE));
        this.multiLineRuleSet = new MultiLineRuleSet(config.getMarkdownRules(RuleType.MULTI_LINE));
        this.rangeRuleSet = new RangeRuleSet(config.getMarkdownRules(RuleType.RANGE));
        this.startWithRuleSet = new StartWithRuleSet(config.getMarkdownRules(RuleType.STARTS_WITH));
    }

}
