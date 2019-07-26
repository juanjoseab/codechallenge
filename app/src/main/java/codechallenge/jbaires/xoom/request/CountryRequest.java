package codechallenge.jbaires.xoom.request;

import java.util.HashMap;
import java.util.List;

import codechallenge.jbaires.xoom.models.Country;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface CountryRequest {
    @GET("countries/{page}/{pageSize}/{uid}")
    Call<List<Country>> getAll( @Path("page") Integer page, @Path("pageSize") Integer pageSize, @Path("uid") String uid );

    @POST("favorite/{countryid}/{uid}")
    Call<HashMap<String,String>> addFavorite(@Path("countryid") String countryid, @Path("uid") String uid);

    @DELETE("favorite/{countryid}/{uid}")
    Call<HashMap<String,String>> removeFavorite(@Path("countryid") String countryid, @Path("uid") String uid);
}
