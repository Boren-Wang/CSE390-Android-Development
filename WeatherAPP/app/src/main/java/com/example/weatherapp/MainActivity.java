package com.example.weatherapp;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText editText = (EditText) findViewById(R.id.editText);
        final TextView cityName = (TextView) findViewById(R.id.cityName);
        final TextView temperature = (TextView) findViewById(R.id.temperature);
        final TextView condition = (TextView) findViewById(R.id.condition);
        final TextView sunrise = (TextView) findViewById(R.id.sunriseTime);
        final TextView sunset = (TextView) findViewById(R.id.sunsetTime);
        final ImageView image = (ImageView) findViewById(R.id.imageView);
        final Button button = (Button) findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(MainActivity.this, "Clicked!", Toast.LENGTH_SHORT).show();
                String city = editText.getText().toString().toLowerCase().trim().replace(" ", "+");
                String url = "https://api.openweathermap.org/data/2.5/weather?q="+city+"&appid=26e0df627f538fff710ca347649fc92c&units=imperial";
                JsonObjectRequest jor = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try{
                            JSONObject main = response.getJSONObject("main");
                            // cityName
                            String city = response.getString("name");
                            cityName.setText(city);
                            // temperature
                            String temp = String.valueOf(main.getDouble("temp"));
                            temperature.setText(temp+" °F");
                            // condition
                            JSONObject weather = response.getJSONArray("weather").getJSONObject(0);
                            String description = weather.getString("description");
                            String icon = weather.getString("icon");
                            String conditionImageUrl = "https://openweathermap.org/img/wn/"+icon+"@2x.png";
                            condition.setText(description);
                            Picasso.get().load(conditionImageUrl).into(image);
                            // sunrise & sunset time
                            JSONObject sys = response.getJSONObject("sys");
                            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.US);

                            Long sunriseTime = Long.parseLong(sys.getString("sunrise"))*1000;
                            Long sunsetTime = Long.parseLong(sys.getString("sunset"))*1000;

                            sunrise.setText(sdf.format(sunriseTime)+" (EDT)");
                            sunset.setText(sdf.format(sunsetTime)+" (EDT)");

                            cityName.setVisibility(View.VISIBLE);
                            temperature.setVisibility(View.VISIBLE);
                            condition.setVisibility(View.VISIBLE);
                            sunrise.setVisibility(View.VISIBLE);
                            sunset.setVisibility(View.VISIBLE);
                            image.setVisibility(View.VISIBLE);
                        } catch(Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, "Invalid city name! Please try again.", Toast.LENGTH_SHORT).show();
                    }
                });
                RequestQueue rq = Volley.newRequestQueue(MainActivity.this);
                rq.add(jor);
            }
        });

    }
}
