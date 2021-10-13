package com.lexisnguyen.api;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.squareup.picasso.Picasso;

import java.util.List;

public class ArticleAdapter extends ArrayAdapter<Article> {
    private Context context;
    private int layoutId;
    private List<Article> articles;

    public ArticleAdapter(@NonNull Context context, int layoutId, @NonNull List<Article> articles) {
        super(context, layoutId, articles);
        this.context = context;
        this.layoutId = layoutId;
        this.articles = articles;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // Get elements
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View view = inflater.inflate(layoutId, null);
        RelativeLayout articleLayout = view.findViewById(R.id.articleLayout);
        ImageView imageView = view.findViewById(R.id.imageView);
        TextView textView = view.findViewById(R.id.textView);
        TextView textViewContent = view.findViewById(R.id.textViewContent);

        // Change elements' content
        Article article = articles.get(position);
        Picasso.get().load(article.getImageUrl())
                .into(imageView);
        textView.setText(article.getTitle());
        textViewContent.setText(article.getContent());

        // Click events
        articleLayout.setOnClickListener((v) -> onClick(v, article));

        return(view);
    }

    private void onClick(View view, Article article) {
        Intent intent = new Intent(context, DetailsActivity.class);
        intent.putExtra("article", article);
        context.startActivity(intent);
    }
}