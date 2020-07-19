package com.example.movieapp.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.movieapp.model.FavouriteModel;
import com.example.movieapp.repository.FavDetailRepository;

public class FavDetailViewModel extends AndroidViewModel {

    private FavDetailRepository favDetailRepository;
    public FavDetailViewModel(@NonNull Application application) {
        super(application);
        favDetailRepository=new FavDetailRepository(application);
    }
    public void remove(FavouriteModel favouriteModel){
        favDetailRepository.remove(favouriteModel);
    }




}
