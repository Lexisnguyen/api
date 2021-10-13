package com.lexisnguyen.api;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiService {

    @GET("article/18dthc5_nhom11")
    Call<ArticleList> getArticles();

    @POST("article")
    Call<Article> postArticle(@Body Article article);

    @PUT("article/{id}")
    Call<Article> putArticle(@Path("id") String id, @Body Article article);

    @DELETE("article/{id}")
    Call<Article> deleteArticle(@Path("id") String id);
}