package com.example.movieapp.view;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.example.movieapp.R;
import com.example.movieapp.model.FavouriteModel;
import com.example.movieapp.utils.NetworkCheck;
import com.example.movieapp.viewModel.FavDetailViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FavDetail extends AppCompatActivity {

    @BindView(R.id.thumbnail)
    ImageView thumbnail;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.description)
    TextView description;
    @BindView(R.id.rating)
    TextView rating;
    @BindView(R.id.releaseDate)
    TextView releaseDate;

    FavouriteModel favouriteModel;
    String ytkey;
    private FavDetailViewModel favDetailViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fav_detail);
        ButterKnife.bind(this);
        Intent intent=getIntent();
        favouriteModel=intent.getParcelableExtra("OBJECT");
        favDetailViewModel=new ViewModelProvider(this).get(FavDetailViewModel.class);
        populate();

    }

    private void populate() {
        Glide.with(this).load(favouriteModel.getUrl()).into(thumbnail);
        title.setText(favouriteModel.getMoviename());
        description.setText(favouriteModel.getDescription());
        rating.setText(Float.toString(favouriteModel.getRating()));
        releaseDate.setText(favouriteModel.getrDate());
        ytkey=favouriteModel.getYtid();

    }

    public void fhd(View view) {
        if (NetworkCheck.connected(this)!=false){
            String url = "https://www.youtube.com/watch?v=" + ytkey;
            Intent yt_play = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            Intent chooser = Intent.createChooser(yt_play, "Open With");
            chooser.putExtra("VIDEO ID", ytkey);
            startActivity(chooser);

        }
        else {
            Toast.makeText(this, "No Interent Connection", Toast.LENGTH_SHORT).show();
        }
    }

    public void rfav(View view) {
        favDetailViewModel.remove(favouriteModel);
        Toast.makeText(this, "Removed From Favourite", Toast.LENGTH_SHORT).show();
        finish();


    }
}