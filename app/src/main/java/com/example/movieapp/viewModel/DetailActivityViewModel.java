package com.example.movieapp.viewModel;

import android.app.Application;
import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.example.movieapp.R;
import com.example.movieapp.model.FavouriteModel;
import com.example.movieapp.model.MovieResults;
import com.example.movieapp.model.MovieReview;
import com.example.movieapp.model.Trailer;
import com.example.movieapp.repository.MovieDetailsRepository;
import com.example.movieapp.repository.MovieRepository;
import com.example.movieapp.view.DetailActivity;
import com.example.movieapp.view.MainActivity;

public class DetailActivityViewModel extends AndroidViewModel {
    public static String LANGUAGE="en-us";


    private   static  String apikey;
    private static int page=1;
    private MovieDetailsRepository movieDetailsRepository;
    private LiveData<MovieReview> movieReviewLiveData;
    private LiveData<Trailer> trailerLiveData;
    private Context context;


    public DetailActivityViewModel(@NonNull Application application) {
        super(application);
        this.context=application.getApplicationContext();
        apikey=application.getResources().getString(R.string.apikey);
        movieDetailsRepository=new MovieDetailsRepository(application);
        this.movieReviewLiveData=movieDetailsRepository.getMovieReview(context, DetailActivity.getMovieID(),apikey,LANGUAGE,page);
        this.trailerLiveData=movieDetailsRepository.getMovieTrailer(context,DetailActivity.getMovieID(),apikey,LANGUAGE);

    }

    public LiveData<MovieReview> getMovieReviewLiveData() {

        this.movieReviewLiveData=movieDetailsRepository.getMovieReview(context, DetailActivity.getMovieID(),apikey,LANGUAGE,page);
        return movieReviewLiveData;
    }

    public LiveData<Trailer> getTrailerLiveData() {
        this.trailerLiveData=movieDetailsRepository.getMovieTrailer(context,DetailActivity.getMovieID(),apikey,LANGUAGE);

        return trailerLiveData;
    }
    public void insert(FavouriteModel favouriteModel){
        movieDetailsRepository.insert(favouriteModel);
    }
}
