package com.lexisnguyen.api;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private Button button;
    private MaterialToolbar toolbar;
    private ListView listView;

    private OkHttpClient okHttpClient;
    public static Retrofit retrofit;
    public static ApiService apiService;

    private ArrayList<Article> articles;
    private ArticleAdapter adapter;

    private String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // GUI
        button = findViewById(R.id.button);
        toolbar = findViewById(R.id.toolbar);
        listView = findViewById(R.id.listView);

        // Data
        // - API data
        okHttpClient = new OkHttpClient.Builder()
                .build();
        retrofit = new Retrofit.Builder()
                .baseUrl("https://android-article.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
        apiService = retrofit.create(ApiService.class);
        // - Local data
        articles = new ArrayList<>();
        // - ListView
        adapter = new ArticleAdapter(this, R.layout.layout_article_item, articles);
        listView.setAdapter(adapter);

        // Update data
        button.setOnClickListener(this::action_post);
        getArticles();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        getArticles();
    }

    private void getArticles() {
        Toast.makeText(MainActivity.this, "Getting articles...", Toast.LENGTH_SHORT).show();
        Call<ArticleList> call = apiService.getArticles();

        call.enqueue(new Callback<ArticleList>() {
            @Override
            public void onResponse(@NonNull Call<ArticleList> call, @NonNull Response<ArticleList> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(MainActivity.this, "Get failed", Toast.LENGTH_SHORT).show();
                    Log.e(TAG, "onResponse: failed with code " + response.code());
                    Log.e(TAG, "onResponse: " + call.request());
                    return;
                }

                if (response.body() == null) {
                    Toast.makeText(MainActivity.this, "Data is null", Toast.LENGTH_SHORT).show();
                    Log.e(TAG, "onResponse: Data is null");
                    return;
                }
                List<Article> newArticles = response.body().getArticles();
                articles.clear();
                articles.addAll(newArticles);
                adapter.notifyDataSetChanged();
                Log.i(TAG, "onResponse: GET done " + response.body());
            }

            @Override
            public void onFailure(@NonNull Call<ArticleList> call, @NonNull Throwable t) {
                Toast.makeText(MainActivity.this, "Load failed", Toast.LENGTH_SHORT).show();
                Log.e(TAG, "onFailure: failed with message " + t.getMessage());
            }
        });
    }

    private void action_post(View view) {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.layout_add);
        TextView editTextTitle = dialog.findViewById(R.id.editTextTitle),
                editTextContent = dialog.findViewById(R.id.editTextContent),
                editTextImage = dialog.findViewById(R.id.editTextImage);
        Button action_cancel = dialog.findViewById(R.id.action_cancel),
                action_post = dialog.findViewById(R.id.action_post);
        dialog.setCancelable(false);

        action_cancel.setOnClickListener((v) -> dialog.dismiss());
        action_post.setOnClickListener((v) -> {
            Toast.makeText(MainActivity.this, "Posting article...", Toast.LENGTH_SHORT).show();
            Article article = new Article(
                    editTextTitle.getText().toString(),
                    editTextContent.getText().toString(),
                    editTextImage.getText().toString(),
                    "18dthc5_nhom11");
            Call<Article> call = apiService.postArticle(article);
            call.enqueue(new Callback<Article>() {
                @Override
                public void onResponse(@NonNull Call<Article> call, @NonNull Response<Article> response) {
                    if (!response.isSuccessful()) {
                        Toast.makeText(MainActivity.this, "Post failed", Toast.LENGTH_SHORT).show();
                        Log.e(TAG, "onResponse: failed with code " + response.code());
                        Log.e(TAG, "onResponse: " + call.request());
                        return;
                    }

                    if (response.body() == null) {
                        Toast.makeText(MainActivity.this, "Data is null", Toast.LENGTH_SHORT).show();
                        Log.e(TAG, "onResponse: Data is null");
                        return;
                    }

                    articles.add(response.body());
                    adapter.notifyDataSetChanged();
                    Log.i(TAG, "onResponse: POST done " + response.body());
                }

                @Override
                public void onFailure(@NonNull Call<Article> call, @NonNull Throwable t) {
                    Toast.makeText(MainActivity.this, "Post failed", Toast.LENGTH_SHORT).show();
                    Log.e(TAG, "onFailure: failed with message " + t.getMessage());
                }
            });
            dialog.dismiss();
        });

        dialog.show();
    }
}