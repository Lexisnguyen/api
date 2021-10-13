package com.lexisnguyen.api;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ArticleList {
    @SerializedName("data")
    private List<Article> articles;

    public List<Article> getArticles() {
        return articles;
    }

    @NonNull
    @Override
    public String toString() {
        StringBuilder str = new StringBuilder("ArticleList{articles[" + articles.size() + "]=");
        for (Article article : articles) {
            str.append(article.toString()).append("\n");
        }
        str.append("}");
        return str.toString();
    }
}