package com.example.movieapp.view;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.movieapp.R;
import com.example.movieapp.adapter.FavouriteAdapter;
import com.example.movieapp.model.FavouriteModel;
import com.example.movieapp.viewModel.FavouriteViewModel;

import java.util.List;

public class Favourite extends AppCompatActivity {
    private FavouriteViewModel favouriteViewModel;
    public static final int NEW_WORD_ACTIVITY_REQUEST_CODE = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite);

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        final FavouriteAdapter adapter = new FavouriteAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        favouriteViewModel=new ViewModelProvider(this).get(FavouriteViewModel.class);

        favouriteViewModel.getAllMovies().observe(this, new Observer<List<FavouriteModel>>() {
            @Override
            public void onChanged(List<FavouriteModel> favouriteModels) {
                adapter.setMovie(favouriteModels);

            }
        });

    }


}