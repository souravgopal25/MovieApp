package com.example.movieapp.model;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "movie_table")
public class FavouriteModel {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name="movie")
    String moviename;

    public FavouriteModel(String moviename) {
        this.moviename = moviename;
    }

    public void setMoviename(String moviename) {
        this.moviename = moviename;
    }

    public String getMoviename() {
        return moviename;
    }


}
