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


@Database(entities = {FavouriteModel.class},version = 1,exportSchema = false)
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
/*
            // If you want to keep data through app restarts,
            // comment out the following block
            databaseWriteExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    // Populate the database in the background.
                    // If you want to start with more words, just add them.
                    FavDAO dao = INSTANCE.favDAO();

                    FavouriteModel object=new FavouriteModel("OSCAR");
                    dao.insert(object);

                }
            });*/
        }
    };
}
