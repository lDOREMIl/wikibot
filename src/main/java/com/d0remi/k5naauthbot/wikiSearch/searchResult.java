package com.d0remi.k5naauthbot.wikiSearch;

public class searchResult {
    private String title;
    private String urlStr;
    private String description;

    public searchResult(String title, String urlStr, String description) {
        this.title = title;
        this.urlStr = urlStr;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public String getUrlStr() {
        return urlStr;
    }

    public String getDescription() {
        return description;
    }
}