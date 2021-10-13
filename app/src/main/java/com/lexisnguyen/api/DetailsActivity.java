package com.lexisnguyen.api;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;
import com.squareup.picasso.Picasso;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.lexisnguyen.api.MainActivity.apiService;

public class DetailsActivity extends AppCompatActivity {
    private MaterialToolbar toolbar;
    private TextView textViewGroup;
    private ImageView imageView;
    private TextView textViewDate;
    private TextView textView;

    private Article article;

    private String TAG = "DetailsActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        // GUI
        toolbar = findViewById(R.id.toolbar);
        textViewGroup = findViewById(R.id.textViewGroup);
        imageView = findViewById(R.id.imageView);
        textViewDate = findViewById(R.id.textViewDate);
        textView = findViewById(R.id.textView);

        // Data
        Intent intent = getIntent();
        article = (Article) intent.getSerializableExtra("article");
        toolbar.setTitle(article.getTitle());
        textViewGroup.setText(article.getGroupName());
        Picasso.get().load(article.getImageUrl())
                .into(imageView);
        if (article.getUpdateAt() == null) {
            textViewDate.setVisibility(View.GONE);
        } else {
            textViewDate.setText(String.format("Last update at %s", article.getUpdateAt()));
        }
        textView.setText(article.getContent());
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);
        Call<Article> call;
        if (item.getItemId() == R.id.action_edit) {
            call = apiService.putArticle(article.get_id());
        } else if (item.getItemId() == R.id.action_delete) {
            call = apiService.deleteArticle(article.get_id());
        } else {
            return false;
        }
        call.enqueue(new Callback<Article>() {
            @Override
            public void onResponse(@NonNull Call<Article> call, @NonNull Response<Article> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(DetailsActivity.this, "Update/Delete data failed", Toast.LENGTH_SHORT).show();
                    Log.e(TAG, "onResponse: failed with code " + response.code());
                    return;
                }

                if (response.body() == null) {
                    Toast.makeText(DetailsActivity.this, "Data is null", Toast.LENGTH_SHORT).show();
                    Log.e(TAG, "onResponse: Data is null");
                    return;
                }

                if (item.getItemId() == R.id.action_edit) {

                }
            }

            @Override
            public void onFailure(@NonNull Call<Article> call, @NonNull Throwable t) {
                Toast.makeText(DetailsActivity.this, "Update/Delete data failed", Toast.LENGTH_SHORT).show();
                Log.e(TAG, "onResponse: failed with message " + t.getMessage());
            }
        });

        return true;
    }
}