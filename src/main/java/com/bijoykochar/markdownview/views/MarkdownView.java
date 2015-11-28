package com.bijoykochar.markdownview.views;

import android.annotation.TargetApi;
import android.content.Context;
import android.util.AttributeSet;
import android.webkit.WebView;

import com.bijoykochar.markdownview.markdown.MarkdownParser;

/**
 * Creates a markdown view with a custom markdown parser if needed
 * Created by bijoy on 10/12/15.
 */
public class MarkdownView extends WebView {

    private MarkdownParser parser;

    public MarkdownView(Context context) {
        super(context);
    }

    public MarkdownView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MarkdownView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(21)
    public MarkdownView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    /**
     * Set a custom parser to the MarkdownView
     *
     * @param parser The markdown parser you wish to load
     */
    public void setParser(MarkdownParser parser) {
        this.parser = parser;
    }

    /**
     * Set the text in the markdown view
     *
     * @param text The text you wish to write in the markdown view
     */
    public void setText(String text) {
        if (parser == null) {
            parser = MarkdownParser.getInstance();
        }

        String html = parser.convertToHtml(text);
        loadDataWithBaseURL(null, html, "text/html", "utf-8", null);
    }
}
