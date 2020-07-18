package com.example.movieapp.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.movieapp.model.FavouriteModel;
import com.example.movieapp.room.FavDAO;
import com.example.movieapp.room.FavRoomDatabase;

import java.util.List;

public class FavouriteRepository {
    private FavDAO favDAO;
    private LiveData<List<FavouriteModel>> mAllMovie;

    public FavouriteRepository(Application application) {
        FavRoomDatabase db = FavRoomDatabase.getDatabase(application);
        favDAO = db.favDAO();
        mAllMovie = favDAO.getAlphabetizedWords();
    }
    public LiveData<List<FavouriteModel>> getAllMovie() {
        return mAllMovie;
    }

    public void insert(final FavouriteModel movie) {
        FavRoomDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                favDAO.insert(movie);
            }
        });

    }
}
