package com.bijoykochar.markdownview.html;

import java.util.HashMap;
import java.util.Map;

/**
 * Simple utility with some basic CSS elements
 * Created by bijoy on 11/29/15.
 */
public class CssUtility {
    Map<String, String> cssValues;

    public void setViewHeight(Integer height) {
        cssValues.put("height", height.toString());
    }

    public void setViewWidth(Integer width) {
        cssValues.put("width", width.toString());
    }

    public void setFontSize(Integer size) {
        cssValues.put("font-size", size.toString());
    }

    public void setFontFamily(String family) {
        cssValues.put("font-family", family);
    }

    public void setColor(String color) {
        cssValues.put("color", color);
    }

    public void setColor(Integer r, Integer g, Integer b) {
        cssValues.put("color", "rgb(" + r + "," + g + "," + b + ")");
    }

    public void setColor(Integer r, Integer g, Integer b, Integer a) {
        cssValues.put("color", "rgba(" + r + "," + g + "," + b + "," + a + ")");
    }

    public void setCssStyle(String style, Integer value) {
        cssValues.put(style, value.toString());
    }

    public void setCssStyle(String style, String value) {
        cssValues.put(style, value);
    }

    public Map<String, String> getCssValues() {
        return cssValues;
    }

    public static CssUtility getInstance() {
        return new CssUtility();
    }

    private CssUtility() {
        cssValues = new HashMap<>();
    }

}
