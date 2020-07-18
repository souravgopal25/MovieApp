package com.example.movieapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movieapp.R;
import com.example.movieapp.model.FavouriteModel;


import java.util.ArrayList;
import java.util.List;

public class FavouriteAdapter extends RecyclerView.Adapter<FavouriteAdapter.FavouriteViewHolder> {

    private final LayoutInflater mInflater;
    private List<FavouriteModel> favouriteModels; // Cached copy of words

    public FavouriteAdapter(Context context) {
        favouriteModels=new ArrayList<>();
        mInflater=LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public FavouriteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.recyclerview_item, parent, false);
        return new FavouriteViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull FavouriteViewHolder holder, int position) {
        if (favouriteModels.size()!=0) {
            FavouriteModel current = favouriteModels.get(position);
            holder.wordItemView.setText(current.getMoviename());
        } else {
            // Covers the case of data not being ready yet.
            holder.wordItemView.setText("No Word");
        }


    }
    public void setMovie(List<FavouriteModel> words){
        favouriteModels.addAll(words);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (favouriteModels != null)
            return favouriteModels.size();
        else return 0;
    }

    class FavouriteViewHolder extends RecyclerView.ViewHolder {
        private final TextView wordItemView;

        private FavouriteViewHolder(View itemView) {
            super(itemView);
            wordItemView = itemView.findViewById(R.id.textview);
        }
    }
}
