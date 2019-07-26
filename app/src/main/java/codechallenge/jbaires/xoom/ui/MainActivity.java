package codechallenge.jbaires.xoom.ui;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import codechallenge.jbaires.xoom.R;
import codechallenge.jbaires.xoom.adapters.CountryAdapter;
import codechallenge.jbaires.xoom.models.Country;
import codechallenge.jbaires.xoom.request.CountryRequest;
import codechallenge.jbaires.xoom.utils.ConfigHelper;
import codechallenge.jbaires.xoom.utils.NetworkUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    String ApiBaseUrl;
    public List<Country> countries;

    private RecyclerView recyclerViewCountries;
    private CountryAdapter countryAdapter;
    Boolean isScrolling = false;
    int currentItems, totalItems = 0, scrollOutItems;
    LinearLayoutManager layoutManager;
    Integer itemsPerPage;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        if(!ConfigHelper.checkReadPhoneState(getApplicationContext())){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_PHONE_STATE},1);
        }



        setContentView(R.layout.activity_main);
        this.countries = new ArrayList<Country>();
        layoutManager = new LinearLayoutManager(getApplicationContext());
        this.ApiBaseUrl = ConfigHelper.getConfigValue(getApplicationContext(), "api_base_url");
        this.itemsPerPage = Integer.parseInt(ConfigHelper.getConfigValue(getApplicationContext(),"items_per_page"));
        progressBar = (ProgressBar) findViewById(R.id.progress);
        recyclerViewCountries = (RecyclerView) findViewById(R.id.recycler_view_country_list);
        recyclerViewCountries.setLayoutManager(layoutManager);
        countryAdapter = new CountryAdapter(countries,getApplicationContext());
        recyclerViewCountries.setAdapter(countryAdapter);
        getCountries();
        recyclerViewCountries.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if(newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL){
                    isScrolling = true;
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                currentItems = layoutManager.getChildCount();
                totalItems = layoutManager.getItemCount();
                scrollOutItems = layoutManager.findFirstVisibleItemPosition();
                if(isScrolling && ( (currentItems + scrollOutItems) == totalItems) ){
                    isScrolling = false;
                    getCountries();
                }
            }
        });
    }


    public void getCountries(){
        progressBar.setVisibility(View.VISIBLE);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(this.ApiBaseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        CountryRequest countryRequest = retrofit.create(CountryRequest.class);
        String uid = ConfigHelper.getUUID(getApplicationContext());
        Integer page = (int) (Math.ceil((double) totalItems / itemsPerPage)) + 1  ;

        Call<List<Country>> call = countryRequest.getAll(page,itemsPerPage, uid);

        call.enqueue(new Callback<List<Country>>() {
            @Override
            public void onResponse(Call<List<Country>> call, Response<List<Country>> response) {
                if(response.body() != null){
                    for (Country singleCountry : response.body()) {
                        countries.add(singleCountry);
                    };
                    countryAdapter.notifyDataSetChanged();
                    progressBar.setVisibility(View.GONE);
                }


            }

            @Override
            public void onFailure(Call<List<Country>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "An error happened while getting countries", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        if(!NetworkUtils.haveNetworkConnection(getApplication())){
            Intent noInternetAccesIntent = new Intent(MainActivity.this, CheckInternetAccess.class);
            startActivity(noInternetAccesIntent);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1:
                if(grantResults[0]==PackageManager.PERMISSION_GRANTED) {
                } else {
                    ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_PHONE_STATE},1);
                }
        }
    }
}
