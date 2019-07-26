package codechallenge.jbaires.xoom.adapters;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import codechallenge.jbaires.xoom.R;
import codechallenge.jbaires.xoom.models.Country;
import codechallenge.jbaires.xoom.request.CountryRequest;
import codechallenge.jbaires.xoom.utils.ConfigHelper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CountryAdapter extends RecyclerView.Adapter<CountryAdapter.ViewHolderCountry> {

    private List<Country> countriesList;
    private Context context;



    public CountryAdapter() {
    }

    public CountryAdapter(List<Country> countriesList, Context context) {
        this.countriesList = countriesList;
        this.context = context;
    }

    @NonNull
    @Override
    public CountryAdapter.ViewHolderCountry onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.country_item_list, null, false);
        return new ViewHolderCountry(view);
    }

    public void Add(Country country){
        this.countriesList.add(country);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderCountry viewHolderCountry, final int position) {
        String flagImgUrl = "https://www.countryflags.io/" + countriesList.get(position).getCode() +  "/flat/64.png";
        viewHolderCountry.countryName.setText(countriesList.get(position).getName());
        viewHolderCountry.countryFlag.setImageResource(R.drawable.loading24);
        final int thispos = position;
        Integer flag = getDrawableFlag(countriesList.get(position).getCode());
        viewHolderCountry.countryFlag.setImageResource(flag);

        if(countriesList.get(position).isFavoriteCountry()){
            viewHolderCountry.favIcon.setImageResource(R.drawable.star32);
        }else {
            viewHolderCountry.favIcon.setImageResource(R.drawable.star_black32);
        }

        viewHolderCountry.favIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("TESTING CLICK", "CLICKED CUNTRY CODE : " + countriesList.get(thispos).getCode());
                ImageView imv = (ImageView) v.findViewById(R.id.fav_icon_item);
                String uid = ConfigHelper.getUUID(context);
                if(countriesList.get(thispos).isFavoriteCountry()){
                    countriesList.get(thispos).setAsFavoriteCountry(false);
                    imv.setImageResource(R.drawable.star_black32);
                    removeFavorite(countriesList.get(thispos).getId().toString(),uid);
                } else {
                    countriesList.get(thispos).setAsFavoriteCountry(true);
                    imv.setImageResource(R.drawable.star32);
                    addToFavorite(countriesList.get(thispos).getId().toString(),uid);
                }


            }
        });
    }

    @Override
    public int getItemCount() {
        if(this.countriesList == null){
            return 0;
        } else {
            return this.countriesList.size();
        }

    }

    public class ViewHolderCountry  extends RecyclerView.ViewHolder {

        ImageView countryFlag;
        ImageView favIcon;
        TextView countryName;

        public ViewHolderCountry(@NonNull View itemView) {
            super(itemView);
            countryFlag = (ImageView) itemView.findViewById(R.id.imageViewCountryFlag);
            countryName = (TextView) itemView.findViewById(R.id.countryNameLbl);
            favIcon = (ImageView) itemView.findViewById(R.id.fav_icon_item);
        }
    }



    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }



    }

    public void addToFavorite(String countryid, String uid){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Objects.requireNonNull(ConfigHelper.getConfigValue(this.context, "api_base_url")))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        CountryRequest countryRequest = retrofit.create(CountryRequest.class);
        Call<HashMap<String,String>> call = countryRequest.addFavorite(countryid, uid);
        call.enqueue(new Callback<HashMap<String, String>>() {
            @Override
            public void onResponse(Call<HashMap<String, String>> call, Response<HashMap<String, String>> response) {
                Toast.makeText(context, "Country added to favorites!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<HashMap<String, String>> call, Throwable t) {
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void removeFavorite(String countryid, String uid){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Objects.requireNonNull(ConfigHelper.getConfigValue(this.context, "api_base_url")))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        CountryRequest countryRequest = retrofit.create(CountryRequest.class);
        Call<HashMap<String,String>> call = countryRequest.removeFavorite(countryid, uid);
        call.enqueue(new Callback<HashMap<String, String>>() {
            @Override
            public void onResponse(Call<HashMap<String, String>> call, Response<HashMap<String, String>> response) {
                //List<HashMap<String,String>> result = response.body();
                Toast.makeText(context,"Country removed from favorites!" , Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<HashMap<String, String>> call, Throwable t) {
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public Integer getDrawableFlag(String countryCode){
        Resources resources = context.getResources();
        String flagName = "contryflag_" + countryCode.toLowerCase() ;
        Integer identifier = resources.getIdentifier(flagName, "drawable",
                context.getPackageName());
        Log.i("FLAG", "IDENTIFIER : " + identifier);
        return identifier;
    }

    public List<Country> getCountriesList() {
        return countriesList;
    }

    public void setCountriesList(List<Country> countriesList) {
        this.countriesList = countriesList;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }
}
