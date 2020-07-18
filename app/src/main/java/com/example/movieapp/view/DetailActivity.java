package com.example.movieapp.view;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.bumptech.glide.Glide;
import com.example.movieapp.R;
import com.example.movieapp.adapter.MovieAdapter;
import com.example.movieapp.adapter.ReviewAdapter;
import com.example.movieapp.model.FavouriteModel;
import com.example.movieapp.model.MovieResults;
import com.example.movieapp.model.MovieReview;
import com.example.movieapp.model.Trailer;
import com.example.movieapp.viewModel.DetailActivityViewModel;
import com.example.movieapp.viewModel.FavouriteViewModel;
import com.example.movieapp.viewModel.MainActivityViewModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity implements ReviewAdapter.ListItemClickListener1 {


    @BindView(R.id.thumbnail)
    ImageView thumbnail;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.yt)
    Button yt;
    @BindView(R.id.fav)
    Button fav;
    @BindView(R.id.description)
    TextView description;
    @BindView(R.id.releaseDate)
    TextView releaseDate;
    @BindView(R.id.rating)
    TextView rating;
    @BindView(R.id.reviewlist)
    RecyclerView recyclerView;


    MovieResults.ResultsBean object;
    private static int movieID;

    ReviewAdapter adapter;
    List<MovieReview.ResultsBean> listofReview;
    List<Trailer.ResultsBean> listofTrailer;
    DetailActivityViewModel detailActivityViewModel;
    private static String ytid;
    int size;
    public static final String EXTRA_REPLY = "com.example.android.wordlistsql.REPLY";


    public static int getMovieID() {
        return movieID;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        object = intent.getParcelableExtra("DATA");
        movieID=object.getId();
        Log.e(DetailActivity.class.getSimpleName(),"movie id: "+movieID);
        Toast.makeText(this, "movie id: "+movieID, Toast.LENGTH_SHORT).show();
        initialization();
        getData();
        populate();




    }

    private void getData() {

        detailActivityViewModel.getMovieReviewLiveData().observe(this, new Observer<MovieReview>() {
            @Override
            public void onChanged(MovieReview movieReview) {
                if(movieReview!=null){
                    List<MovieReview.ResultsBean> listofreview1 = movieReview.getResults();
                    listofReview.addAll(listofreview1);
                    adapter.notifyDataSetChanged();
                    Toast.makeText(DetailActivity.this, "GOT Review Data", Toast.LENGTH_SHORT).show();
                }

            }
        });

        detailActivityViewModel.getTrailerLiveData().observe(this, new Observer<Trailer>() {
            @Override
            public void onChanged(Trailer trailer) {
                if (trailer != null) {
                    List<Trailer.ResultsBean> listoftrailer1=trailer.getResults();
                    listofTrailer.addAll(listoftrailer1);
                    ytid=listoftrailer1.get(0).getKey();

                    Log.e(DetailActivity.class.getSimpleName(),listoftrailer1.get(0).getKey());


                }

            }
        });


    }

    private void initialization() {

        listofReview=new ArrayList<>();
        listofTrailer=new ArrayList<>();
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);

        adapter=new ReviewAdapter(DetailActivity.this,listofReview,this);
        recyclerView.setAdapter(adapter);

        detailActivityViewModel= ViewModelProviders.of(this).get(DetailActivityViewModel.class);
    }

    public void populate(){
        String url = "http://image.tmdb.org/t/p/w500/" + object.getPoster_path();
        Glide.with(this).load(url).into(thumbnail);
        title.setText(object.getOriginal_title());
        description.setText(object.getOverview());
        releaseDate.setText(object.getRelease_date());
        rating.setText(Float.toString(object.getVote_average()));
         size=listofTrailer.size();
        if(size!=0) {
            Trailer.ResultsBean obj = listofTrailer.get(size - 1);
            ytid = obj.getId();
        }
    }

    public void fhd(View view) {

            String url = "https://www.youtube.com/watch?v=" + ytid;
            Intent yt_play = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            Intent chooser = Intent.createChooser(yt_play, "Open With");
            chooser.putExtra("VIDEO ID", ytid);
            startActivity(chooser);


    }


    public void fav(View view) {
        Intent replyIntent = new Intent();

        String word = object.getTitle();
        FavouriteModel obj1 =new FavouriteModel(word);
        detailActivityViewModel.insert(obj1);
        Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();



    }

    @Override
    public void onListItemClick(int clickedIndex) {
        Toast.makeText(this, "Clicked on", Toast.LENGTH_SHORT).show();
    }
}