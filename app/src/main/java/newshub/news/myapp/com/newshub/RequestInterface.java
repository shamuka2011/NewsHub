package newshub.news.myapp.com.newshub;

import java.util.List;


import newshub.news.myapp.com.model.EntertainmentResponse;
import newshub.news.myapp.com.model.GeneralModel;
import newshub.news.myapp.com.model.GeneralResponse;
import newshub.news.myapp.com.model.ScienceResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Sweety on 07-02-2019.
 */

public interface RequestInterface {

    String BASE_URL = "https://newsapi.org/v2/";

    @GET("top-headlines")
    Call<ResponseModel> getLatestNews(@Query("country") String source ,@Query("apiKey") String apiKey);

    @GET("top-headlines")
    Call<ResponseModel> getTechnicalNews(@Query("category") String category, @Query("apiKey") String apiKey );

    @GET("top-headlines")
    Call<ResponseModel> getSportsNews(@Query("category") String category, @Query("apiKey") String apiKey );

    @GET("top-headlines")
    Call<ResponseModel> getHealthNews(@Query("category") String category, @Query("apiKey") String apiKey );

    @GET("top-headlines")
    Call<ResponseModel> getEntertainmentNews(@Query("category") String category, @Query("apiKey") String apiKey );

    @GET("sources")
    Call<GeneralResponse> getGeneralNews(@Query("apiKey") String apiKey );

    @GET("top-headlines")
    Call<ResponseModel> getScienceNews(@Query("category") String category, @Query("apiKey") String apiKey );

    @GET("top-headlines")
    Call<ResponseModel> getBusinessNews(@Query("category") String category, @Query("apiKey") String apiKey );







}
