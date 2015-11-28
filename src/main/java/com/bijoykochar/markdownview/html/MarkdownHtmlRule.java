package com.bijoykochar.markdownview.html;

import com.bijoykochar.markdownview.markdown.Markdown;

/**
 * The Markdown HTML Rules
 * Created by bijoy on 10/12/15.
 */
public class MarkdownHtmlRule {

    public String htmlTag;
    public Markdown.Type markType;

    /**
     * The constructor
     *
     * @param htmlTag  the HTML Tag for the rule
     * @param markType the markdown type
     */
    public MarkdownHtmlRule(String htmlTag, Markdown.Type markType) {
        this.markType = markType;
        this.htmlTag = htmlTag;
    }

    public String getStartTag() {
        return "<" + htmlTag + ">";
    }

    public String getEndTag() {
        return "</" + htmlTag + ">";
    }
}
