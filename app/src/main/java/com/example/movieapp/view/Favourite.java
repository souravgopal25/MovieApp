package com.example.movieapp.view;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.movieapp.R;
import com.example.movieapp.adapter.FavouriteAdapter;
import com.example.movieapp.model.FavouriteModel;
import com.example.movieapp.viewModel.FavouriteViewModel;

import java.util.ArrayList;
import java.util.List;

public class Favourite extends AppCompatActivity implements FavouriteAdapter.ListItemClickListener{
    private FavouriteViewModel favouriteViewModel;
    public static final int NEW_WORD_ACTIVITY_REQUEST_CODE = 1;
    List<FavouriteModel> favouriteModels1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite);
        favouriteModels1=new ArrayList<>();;
        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        final FavouriteAdapter adapter = new FavouriteAdapter(this,this::onListItemClick);
        recyclerView.setAdapter(adapter);
        StaggeredGridLayoutManager staggeredGridLayoutManager=new StaggeredGridLayoutManager(calculateNoOfColumns(this),LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);

        favouriteViewModel=new ViewModelProvider(this).get(FavouriteViewModel.class);

        favouriteViewModel.getAllMovies().observe(this, new Observer<List<FavouriteModel>>() {
            @Override
            public void onChanged(List<FavouriteModel> favouriteModels) {

                adapter.clearView();
                adapter.setMovie(favouriteModels);
                adapter.notifyDataSetChanged();
                favouriteModels1.addAll(favouriteModels);


            }
        });

    }
    public static int calculateNoOfColumns(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        int scalingFactor = 200;
        int noOfColumns = (int) (dpWidth / scalingFactor);
        if(noOfColumns < 2)
            noOfColumns = 2;
        return noOfColumns;
    }


    @Override
    public void onListItemClick(int clickedIndex) {
        Intent intent=new Intent(Favourite.this,FavDetail.class);
        intent.putExtra("OBJECT",favouriteModels1.get(clickedIndex));
        startActivity(intent);


    }
}