package com.example.movieapp.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.movieapp.model.FavouriteModel;
import com.example.movieapp.repository.FavouriteRepository;

import java.util.List;

public class FavouriteViewModel extends AndroidViewModel {
    private FavouriteRepository favouriteRepository;
    private LiveData<List<FavouriteModel>> mallMovies;
    public FavouriteViewModel(@NonNull Application application) {
        super(application);
        favouriteRepository=new FavouriteRepository(application);
        mallMovies=favouriteRepository.getAllMovie();
    }
    public LiveData<List<FavouriteModel>> getAllMovies(){
        return mallMovies;
    }
    public void insert(FavouriteModel favouriteModel){
        favouriteRepository.insert(favouriteModel);
    }
}
