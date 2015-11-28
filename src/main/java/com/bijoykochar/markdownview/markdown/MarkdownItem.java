package com.bijoykochar.markdownview.markdown;

import java.util.ArrayList;
import java.util.List;

/**
 * The markdown for a sentence.
 * Created by bijoy on 10/11/15.
 */
public class MarkdownItem {
    public String line;
    public List<MarkdownItem> items = new ArrayList<>();
    public Markdown.Type type;

    public MarkdownItem(List<MarkdownItem> items, Markdown.Type type, String line) {
        this.items = items;
        this.type = type;
        this.line = line;
    }

    public MarkdownItem(List<MarkdownItem> items, Markdown.Type type) {
        this.items = items;
        this.type = type;
    }

    public MarkdownItem(Markdown.Type type, String line) {
        this.type = type;
        this.line = line;
    }

    public MarkdownItem(String line) {
        this.type = Markdown.Type.NONE;
        this.line = line;
    }

    public MarkdownItem(Markdown.Type type) {
        this.type = type;
    }

    public boolean isTypeless() {
        return type.equals(Markdown.Type.NONE) && items.isEmpty();
    }
}
