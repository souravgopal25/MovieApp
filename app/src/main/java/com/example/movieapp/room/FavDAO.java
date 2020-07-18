package com.example.movieapp.room;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.movieapp.model.FavouriteModel;

import java.util.List;

@Dao
public interface FavDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(FavouriteModel word);

    @Delete
    void delete(FavouriteModel word);

    @Query("DELETE FROM movie_table")
    void deleteAll();

    @Query("SELECT * from movie_table ORDER BY movie ASC")
    LiveData<List<FavouriteModel>> getAlphabetizedWords();

}
