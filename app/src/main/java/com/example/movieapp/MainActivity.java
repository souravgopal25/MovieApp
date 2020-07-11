package com.example.movieapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.movieapp.adapter.MovieAdapter;
import com.example.movieapp.model.MovieResults;
import com.example.movieapp.networking.ApiInterface;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.Retrofit.Builder;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, MovieAdapter.ListItemClickListener {





    Spinner categories,page;
    Button search;
    RecyclerView recyclerView;
    String[] category={"popular","top_rated","upcoming"};
    Integer[] pages;
    String[] pg;
    public static int maxPage=10;
    public static String LANGUAGE="en-us";
    public static final String BASE_URL="https://api.themoviedb.org";
    public static String CATEGORY="popular";
    public static int PAGE=1;
    List<MovieResults.ResultsBean> listofMovies1;
    MovieAdapter movieAdapter;
    public static final String TAG=MainActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        categories=findViewById(R.id.category);
        page=findViewById(R.id.page);
        recyclerView=findViewById(R.id.recycler);
        search=findViewById(R.id.search);

        listofMovies1=new ArrayList<>();
        StaggeredGridLayoutManager staggeredGridLayoutManager=new StaggeredGridLayoutManager(2,LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);

        populateDropdown();
        retrofit();

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, CATEGORY+" !!!"+PAGE, Toast.LENGTH_SHORT).show();
                listofMovies1.clear();
                movieAdapter.notifyDataSetChanged();
                retrofit();
            }
        });







    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

        switch (adapterView.getId()){
            case R.id.category:CATEGORY=category[i];
/*
           Toast.makeText(this, category[i], Toast.LENGTH_SHORT).show();
                                //TODO*/
                                break;

            case R.id.page:
                PAGE=pages[i];
               /*Toast.makeText(this, pages[i].toString(), Toast.LENGTH_SHORT).show();
                //TODO*/
                break;
        }




    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
    void retrofit(){
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiInterface myinterface=retrofit.create(ApiInterface.class);
        String APIKEY=getResources().getString(R.string.apikey);

        Call<MovieResults> call =myinterface.listOfMovies(CATEGORY,APIKEY,LANGUAGE,PAGE);

        call.enqueue(new Callback<MovieResults>() {
            @Override
            public void onResponse(Call<MovieResults> call, Response<MovieResults> response) {
                try {
                    MovieResults results = response.body();
                   // maxPage = results.getTotal_pages();
                     listofMovies1 = results.getResults();
                    // movieAdapter.notifyDataSetChanged();
                    Log.e(TAG, "No of Item" + listofMovies1.size());
                    Toast.makeText(MainActivity.this, "MOVIE" + listofMovies1.get(0).getTitle(), Toast.LENGTH_SHORT).show();
                    movieAdapter=new MovieAdapter(MainActivity.this, listofMovies1, MainActivity.this::onListItemClick);
                    recyclerView.setAdapter(movieAdapter);


                }catch (Exception e){
                    System.out.println(e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<MovieResults> call, Throwable t) {
                Log.e(TAG,t.getMessage());

            }
        });
    }
    void recyclerView(){



    }
    void populateDropdown(){
        pages= new Integer[20];
        for(int i =1;i<=20;i++){
            pages[i-1]=i;
            /*pg[i-1]=Integer.toString(i);*/
        }
        categories.setOnItemSelectedListener(this);
        page.setOnItemSelectedListener(this);
        ArrayAdapter a1=new ArrayAdapter(this,android.R.layout.simple_spinner_item,category);
        ArrayAdapter<Integer> a2=new ArrayAdapter<Integer>(this,android.R.layout.simple_spinner_item,pages);
       /* ArrayAdapter a2=new ArrayAdapter(this,android.R.layout.simple_spinner_item,pg);*/
        a1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        a2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categories.setAdapter(a1);
        page.setAdapter(a2);


    }

    @Override
    public void onListItemClick(int clickedIndex) {
        Toast.makeText(this, "ITEM CLICKED"+clickedIndex, Toast.LENGTH_SHORT).show();

    }
}