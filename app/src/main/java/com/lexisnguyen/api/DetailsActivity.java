package com.lexisnguyen.api;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.appbar.MaterialToolbar;
import com.squareup.picasso.Picasso;

import okhttp3.OkHttpClient;
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
        if (item.getTitle().equals("Edit")) {
            apiService.putArticle(article.get_id());
        } else if (item.getTitle().equals("Delete")) {
            apiService.deleteArticle(article.get_id());
        }
        return true;
    }
}