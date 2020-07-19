package com.example.movieapp.viewModel;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.movieapp.R;
import com.example.movieapp.model.MovieResults;
import com.example.movieapp.repository.MovieRepository;
import com.example.movieapp.view.MainActivity;


public class MainActivityViewModel extends AndroidViewModel {
    public static String LANGUAGE="en-us";


    private   static  String apikey;
    private MovieRepository movieRepository;
    private LiveData<MovieResults> movieResultsLiveData;
    private Context context;

    public MainActivityViewModel(@NonNull Application application) {
        super(application);
        movieRepository=new MovieRepository();
        apikey=application.getResources().getString(R.string.apikey);
        this.context=application.getApplicationContext();

        this.movieResultsLiveData=movieRepository.getMovieResults(context,MainActivity.getCATEGORY(),apikey,LANGUAGE,MainActivity.getPAGE());

    }

    public LiveData<MovieResults> getMovieResultsLiveData() {
        this.movieResultsLiveData=movieRepository.getMovieResults(context,MainActivity.getCATEGORY(),apikey,LANGUAGE,MainActivity.getPAGE());
        return movieResultsLiveData;
    }

}
