package com.example.jsonparsingbyretrofit;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface DataService {

    //http://api.openweathermap.org/data/2.5/weather?q=Dhaka&appid=a81105056ce68fa9f6c63c5a42b08ba1&units=metric

    @GET("data/2.5/weather")
    Call<DailyForecast> getDailyForecast(@Query("q") String city, @Query("appid") String apikey, @Query("units") String units);
}
