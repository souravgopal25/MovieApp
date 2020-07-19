package com.example.movieapp.room;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.movieapp.model.FavouriteModel;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


@Database(entities = {FavouriteModel.class},version = 2,exportSchema = false)
public abstract class FavRoomDatabase extends RoomDatabase {
    public abstract FavDAO favDAO();
    private static volatile FavRoomDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS=4;

    public static final ExecutorService databaseWriteExecutor= Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static FavRoomDatabase getDatabase(final Context context){
        if (INSTANCE==null){
            synchronized (FavRoomDatabase.class){
                if (INSTANCE == null) {
                    INSTANCE= Room.databaseBuilder(context.getApplicationContext(),
                            FavRoomDatabase.class,"movie_database")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);

        }
    };
}
