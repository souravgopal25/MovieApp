package com.example.movieapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.movieapp.model.MovieResults;

public class DetailActivity extends AppCompatActivity {

    ImageView thumbnail;
    TextView titles,descriptions,ratings,releaseDates;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Intent intent=getIntent();
        MovieResults.ResultsBean object=intent.getParcelableExtra("DATA");
        thumbnail=findViewById(R.id.thumbnail);
        titles=findViewById(R.id.title);
        descriptions=findViewById(R.id.description);
        ratings=findViewById(R.id.rating);
        releaseDates=findViewById(R.id.releaseDate);
        String url="http://image.tmdb.org/t/p/w500/"+object.getPoster_path();
        Glide.with(this).load(url).into(thumbnail);
        titles.setText(object.getTitle());
        descriptions.setText(object.getOverview());
        ratings.setText(Float.toString(object.getVote_average()));
        releaseDates.setText(object.getRelease_date());




    }
}