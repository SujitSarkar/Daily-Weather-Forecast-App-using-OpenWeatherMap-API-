package com.example.jsonparsingbyretrofit;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.DateFormat;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    EditText searchText;
    ImageView searchImage;
    TextView dateTV,locationTV,tempTV2,feelTV2,windTV2,pressureTV2,humidityTV2,downTV,upTV,sunriseTV2,sunsetTV2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        searchText = findViewById(R.id.searchText);
        searchImage = findViewById(R.id.searchImage);

        dateTV = findViewById(R.id.dateTV);
        locationTV = findViewById(R.id.locationTV);
        tempTV2 = findViewById(R.id.tempTV2);
        feelTV2 = findViewById(R.id.feelTV2);
        windTV2 = findViewById(R.id.windTV2);
        pressureTV2 = findViewById(R.id.pressureTV2);
        humidityTV2 = findViewById(R.id.humidityTV2);
        downTV = findViewById(R.id.downTV);
        upTV = findViewById(R.id.upTV);
        sunriseTV2 = findViewById(R.id.sunriseTV2);
        sunsetTV2 = findViewById(R.id.sunsetTV2);



        searchImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String city = searchText.getText().toString();

                //handler creation for Retrofit instance
                Retrofit retrofit = RetrofitClientInstance.getRetrofitInstance();
                DataService dataService = retrofit.create(DataService.class);
                Call<DailyForecast> call = dataService.getDailyForecast(city, "a81105056ce68fa9f6c63c5a42b08ba1", "metric");

                call.enqueue(new Callback<DailyForecast>() {
                    @Override
                    public void onResponse(Call<DailyForecast> call, Response<DailyForecast> response) {

                        if (response.isSuccessful()){
                            DailyForecast dailyForecast = response.body();

                            Date date = new Date((long)(dailyForecast.getDt())*1000);
                            String dates= DateFormat.getDateTimeInstance().format(date);

                            Date sun1 = new Date((long)(dailyForecast.getSys().getSunrise())*1000);
                            String sunrise = DateFormat.getTimeInstance().format(sun1);

                            Date sun2 = new Date((long)(dailyForecast.getSys().getSunset())*1000);
                            String sunset = DateFormat.getTimeInstance().format(sun2);

                            dateTV.setText(dates);
                            locationTV.setText(dailyForecast.getName().toString());
                            tempTV2.setText(dailyForecast.getMain().getTemp().toString()+"° C");
                            feelTV2.setText(dailyForecast.getMain().getFeelsLike().toString()+"°");
                            windTV2.setText(dailyForecast.getWind().getSpeed().toString()+" Km/h");
                            pressureTV2.setText(dailyForecast.getMain().getPressure().toString()+" mb");
                            humidityTV2.setText(dailyForecast.getMain().getHumidity().toString()+"%");
                            downTV.setText(dailyForecast.getMain().getTempMin().toString()+"°");
                            upTV.setText(dailyForecast.getMain().getTempMax().toString()+"°");
                            sunriseTV2.setText(sunrise);
                            sunsetTV2.setText(sunset);
                        }
                        else {
                            Toast.makeText(MainActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onFailure(Call<DailyForecast> call, Throwable t) {

                        Toast.makeText(MainActivity.this, "Failure", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });
    }
}