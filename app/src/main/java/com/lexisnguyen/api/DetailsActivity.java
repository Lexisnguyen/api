package com.lexisnguyen.api;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
        updateArticle(article);

        // Toolbar
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_details, menu);
        return super.onCreateOptionsMenu(menu);
    }

    private void updateArticle(Article article) {
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
        if (item.getItemId() == R.id.action_edit) {
            action_put();
        } else if (item.getItemId() == R.id.action_delete) {
            action_delete();
        } else if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return true;
    }

    // DELETE
    private void action_delete() {
        Toast.makeText(DetailsActivity.this, "Deleting article...", Toast.LENGTH_SHORT).show();
        Call<Article> call = apiService.deleteArticle(article.get_id());
        call.enqueue(new Callback<Article>() {
            @Override
            public void onResponse(@NonNull Call<Article> call, @NonNull Response<Article> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(DetailsActivity.this, "Delete data failed", Toast.LENGTH_SHORT).show();
                    Log.e(TAG, "onResponse: failed with code " + response.code());
                    Log.e(TAG, "onResponse: " + call.request());
                    return;
                }

                if (response.body() == null) {
                    Toast.makeText(DetailsActivity.this, "Data is null", Toast.LENGTH_SHORT).show();
                    Log.e(TAG, "onResponse: Data is null");
                    return;
                }

                Log.i(TAG, "onResponse: DELETE done");
                onBackPressed();
            }

            @Override
            public void onFailure(@NonNull Call<Article> call, @NonNull Throwable t) {
                Toast.makeText(DetailsActivity.this, "Delete data failed", Toast.LENGTH_SHORT).show();
                Log.e(TAG, "onFailure: failed with message " + t.getMessage());
            }
        });
    }

    // PUT
    private void action_put() {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.layout_edit);
        TextView editTextTitle = dialog.findViewById(R.id.editTextTitle),
                editTextContent = dialog.findViewById(R.id.editTextContent),
                editTextImage = dialog.findViewById(R.id.editTextImage);
        Button action_cancel = dialog.findViewById(R.id.action_cancel),
                action_update = dialog.findViewById(R.id.action_update);
        dialog.setCancelable(false);
        editTextTitle.setText(article.getTitle());
        editTextContent.setText(article.getContent());
        editTextImage.setText(article.getImageUrl());

        action_cancel.setOnClickListener((v) -> dialog.dismiss());
        action_update.setOnClickListener((v) -> {
            Toast.makeText(DetailsActivity.this, "Updating article...", Toast.LENGTH_SHORT).show();
            Article article = DetailsActivity.this.article;
            article.setTitle(editTextTitle.getText().toString());
            article.setContent(editTextContent.getText().toString());
            article.setImageUrl(editTextImage.getText().toString());
            Call<Article> call = apiService.putArticle(article.get_id(), article);
            call.enqueue(new Callback<Article>() {
                @Override
                public void onResponse(@NonNull Call<Article> call, @NonNull Response<Article> response) {
                    if (!response.isSuccessful()) {
                        Toast.makeText(DetailsActivity.this, "Put failed", Toast.LENGTH_SHORT).show();
                        Log.e(TAG, "onResponse: failed with code " + response.code());
                        Log.e(TAG, "onResponse: " + call.request());
                        return;
                    }

                    if (response.body() == null) {
                        Toast.makeText(DetailsActivity.this, "Data is null", Toast.LENGTH_SHORT).show();
                        Log.e(TAG, "onResponse: Data is null");
                        return;
                    }

                    updateArticle(article);
                    Log.i(TAG, "onResponse: PUT done " + response.body());
                }

                @Override
                public void onFailure(@NonNull Call<Article> call, @NonNull Throwable t) {
                    Toast.makeText(DetailsActivity.this, "Put failed", Toast.LENGTH_SHORT).show();
                    Log.e(TAG, "onFailure: failed with message " + t.getMessage());
                }
            });
            dialog.dismiss();
        });

        dialog.show();
    }
}