package com.lexisnguyen.api;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ArticleList {
    @SerializedName("data")
    private List<Article> articles;

    public List<Article> getArticles() {
        return articles;
    }
}