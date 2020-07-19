package com.example.movieapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.movieapp.R;
import com.example.movieapp.model.FavouriteModel;

import java.util.ArrayList;
import java.util.List;

public class FavouriteAdapter extends RecyclerView.Adapter<FavouriteAdapter.FavouriteViewHolder> {

    private final LayoutInflater mInflater;
    private List<FavouriteModel> favouriteModels; // Cached copy of words
    final private ListItemClickListener mOnClickListener;
    Context context;

    public FavouriteAdapter(Context context,ListItemClickListener mOnClickListener) {
        favouriteModels=new ArrayList<>();
        this.context=context;
        mInflater=LayoutInflater.from(context);
        this.mOnClickListener=mOnClickListener;
    }

    @NonNull
    @Override
    public FavouriteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.post_item, parent, false);
        return new FavouriteViewHolder(itemView);

    }
    public void clearView(){
        favouriteModels.clear();
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull FavouriteViewHolder holder, int position) {
        if (favouriteModels.size()!=0) {
            FavouriteModel current = favouriteModels.get(position);
            Glide.with(context).load(current.getUrl()).into(holder.imageView);
        } else {
            // Covers the case of data not being ready yet.
            Toast.makeText(context, "No Favourite Movie Saved", Toast.LENGTH_SHORT).show();
        }


    }
    public void setMovie(List<FavouriteModel> words){
        favouriteModels.addAll(words);
        notifyDataSetChanged();
    }
    public interface ListItemClickListener {
        void onListItemClick(int clickedIndex);
    }

    @Override
    public int getItemCount() {
        if (favouriteModels != null)
            return favouriteModels.size();
        else return 0;
    }

    class FavouriteViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imageView;

        private FavouriteViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.postImage);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int clickedPosition=getAdapterPosition();
            mOnClickListener.onListItemClick(clickedPosition);
        }
    }
}
