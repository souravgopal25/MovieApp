package com.example.movieapp.repository;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.movieapp.model.FavouriteModel;
import com.example.movieapp.model.MovieReview;
import com.example.movieapp.model.Trailer;
import com.example.movieapp.networking.ApiInterface;
import com.example.movieapp.networking.RetrofitRequest;
import com.example.movieapp.room.FavDAO;
import com.example.movieapp.room.FavRoomDatabase;
import com.example.movieapp.utils.NetworkCheck;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieDetailsRepository {

    private static final String TAG = MovieRepository.class.getSimpleName();
    private ApiInterface apiInterface;
    private FavDAO favDAO;
    private LiveData<List<FavouriteModel>> mAllMovie;


    public MovieDetailsRepository(Application application) {
        apiInterface = RetrofitRequest.getRetrofitInstance().create(ApiInterface.class);
        FavRoomDatabase db = FavRoomDatabase.getDatabase(application);
        favDAO = db.favDAO();
        mAllMovie = favDAO.getAlphabetizedWords();
    }

    public LiveData<MovieReview> getMovieReview(Context context,int movieId,String apikey,String language,int page){
        final MutableLiveData<MovieReview> data =new MutableLiveData<>();
        if (NetworkCheck.connected(context)!=false){
            apiInterface.getReviewCall(movieId,apikey,language,page)
                    .enqueue(new Callback<MovieReview>() {
                        @Override
                        public void onResponse(Call<MovieReview> call, Response<MovieReview> response) {
                            data.setValue(response.body());
                            Log.d(TAG,"SUCCESS"+response.body().getTotal_results());
                        }

                        @Override
                        public void onFailure(Call<MovieReview> call, Throwable t) {
                            data.setValue(null);
                            Log.e(TAG,"FAILED" +t.getMessage());

                        }
                    });

        }else{
            Toast.makeText(context, "NO INTERNET CONNECTION", Toast.LENGTH_SHORT).show();
            data.setValue(null);
        }
        return data;
    }
    public LiveData<Trailer> getMovieTrailer(Context context,int movieId,String apikey,String language){
        final MutableLiveData<Trailer> data1=new MutableLiveData<>();
        if (NetworkCheck.connected(context) != false) {
            apiInterface.getTrailerCall(movieId,apikey,language).enqueue(new Callback<Trailer>() {
                @Override
                public void onResponse(Call<Trailer> call, Response<Trailer> response) {
                    data1.setValue(response.body());
                    Log.d(TAG,"SUCCESS"+response.body().getResults().size());
                }

                @Override
                public void onFailure(Call<Trailer> call, Throwable t) {
                    data1.setValue(null);
                    Log.e(TAG,"FAILED" +t.getMessage());

                }
            });


        }else {
            Toast.makeText(context, "NO INTERNET CONNECTION", Toast.LENGTH_SHORT).show();
            data1.setValue(null);
        }
        return data1;
    }
    public LiveData<List<FavouriteModel>> getAllMovie() {
        return mAllMovie;
    }
    public void insert(final FavouriteModel movie) {
        FavRoomDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                favDAO.insert(movie);
            }
        });

    }
}
