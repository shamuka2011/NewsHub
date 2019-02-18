package newshub.news.myapp.com.newshub;

import java.util.List;

import newshub.news.myapp.com.model.BusinessResponse;
import newshub.news.myapp.com.model.EntertainmentResponse;
import newshub.news.myapp.com.model.GeneralModel;
import newshub.news.myapp.com.model.GeneralResponse;
import newshub.news.myapp.com.model.ScienceResponse;
import newshub.news.myapp.com.model.SportsResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Sweety on 07-02-2019.
 */

public interface RequestInterface {

    String BASE_URL = "https://newsapi.org/v2/";

    @GET("top-headlines")
    Call<ResponseModel> getLatestNews(@Query("country") String source, @Query("apiKey") String apiKey);

    @GET("sources")
    Call<TechResponse> getTechnicalNews(@Query("category") String category, @Query("apiKey") String apiKey );

    @GET("sources")
    Call<SportsResponse> getSportsNews(@Query("category") String category, @Query("apiKey") String apiKey );

    @GET("sources")
    Call<EntertainmentResponse> getEntertainmentNews(@Query("category") String category, @Query("apiKey") String apiKey );

    @GET("sources")
    Call<GeneralResponse> getGeneralNews(@Query("apiKey") String apiKey );

    @GET("sources")
    Call<ScienceResponse> getScienceNews(@Query("category") String category, @Query("apiKey") String apiKey );

    @GET("sources")
    Call<BusinessResponse> getBusinessNews(@Query("category") String category, @Query("apiKey") String apiKey );







}
