package com.example.movieapp.repository;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.movieapp.model.MovieResults;
import com.example.movieapp.networking.ApiInterface;
import com.example.movieapp.networking.RetrofitRequest;
import com.example.movieapp.utils.NetworkCheck;
import com.example.movieapp.view.MainActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieRepository {
    private static final String TAG = MovieRepository.class.getSimpleName();
    private ApiInterface apiInterface;


    public MovieRepository() {
        apiInterface = RetrofitRequest.getRetrofitInstance().create(ApiInterface.class);
    }
    public LiveData<MovieResults> getMovieResults(Context context,String category, String apikey, String language, int page){
        final MutableLiveData<MovieResults> data =new MutableLiveData<>();
        if (NetworkCheck.connected(context)!=false){

        apiInterface.getlistOfMovies(category,apikey,language,page)
                .enqueue(new Callback<MovieResults>() {
                    @Override
                    public void onResponse(Call<MovieResults> call, Response<MovieResults> response) {
                        data.setValue(response.body());
//                        Log.d(TAG,"TOTAL RESULTS "+response.body().getTotal_results());
                    }

                    @Override
                    public void onFailure(Call<MovieResults> call, Throwable t) {
                        data.setValue(null);
                        Log.e(TAG,"FAILED" +t.getMessage());

                    }
                });





        return data;
    }else {
            Toast.makeText(context, "NO INTERNET CONNECTION", Toast.LENGTH_SHORT).show();
        }
        data.setValue(null);
        return data;
    }

}
