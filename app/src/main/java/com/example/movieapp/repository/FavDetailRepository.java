package com.example.movieapp.repository;

import android.app.Application;

import com.example.movieapp.model.FavouriteModel;
import com.example.movieapp.room.FavDAO;
import com.example.movieapp.room.FavRoomDatabase;

public class FavDetailRepository {
    private FavDAO favDAO;

    public FavDetailRepository (Application application) {
        FavRoomDatabase db = FavRoomDatabase.getDatabase(application);
        favDAO = db.favDAO();

    }

    public void remove(final FavouriteModel movie) {
        FavRoomDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                favDAO.delete(movie);
            }
        });

    }

}
