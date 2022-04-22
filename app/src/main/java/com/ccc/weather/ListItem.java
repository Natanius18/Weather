package com.ccc.weather;


import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.RetryPolicy;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.request.RequestOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class ListItem extends AppCompatActivity {

    ListView listView;
    ListAdapter adapter;
    ProgressDialog loading;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_item);

        listView = findViewById(R.id.lv_items);
        Bundle arguments = getIntent().getExtras();
        String city = arguments.getString("city");
        setTitle(city);

        getItems(city);
    }

    private void getItems(String city) {

        String url = "https://api.openweathermap.org/data/2.5/forecast?q=" + city +
                "&lang=ru&units=metric&appid=3d822b9dce4e57f12b9b3400d480a358";
        loading = ProgressDialog.show(this, "Загрузка", "Составляю прогноз", false, true);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                this::parseItems,
                error -> {}
        );

        int socketTimeOut = 50000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeOut, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);

        stringRequest.setRetryPolicy(policy);

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(stringRequest);

    }

    private void parseItems(String jsonResponse) {
        ArrayList<HashMap<String, String>> list = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(jsonResponse);
            JSONArray array = jsonObject.getJSONArray("list");

            String city = jsonObject.getJSONObject("city").getString("name");
            String country = jsonObject.getJSONObject("city").getString("country");

            setTitle(city + ", " + country);

            for (int i = 0; i < array.length(); i++) {
                JSONObject jo = array.getJSONObject(i);

                int temp = (int) jo.getJSONObject("main").getDouble("temp");
                int possibility = (int) (jo.getDouble("pop") * 100);
                String windSpeed = String.valueOf(jo.getJSONObject("wind").getLong("speed"));
                Date date = new Date(jo.getInt("dt") * 1000L);
                String time = new SimpleDateFormat("HH:mm").format(date);
                String day = new SimpleDateFormat("E").format(date);
                String description = jo.getJSONArray("weather").getJSONObject(0).getString("description");
                String icon = jo.getJSONArray("weather").getJSONObject(0).getString("icon");



                HashMap<String, String> item = new HashMap<>();
                item.put("name", city);
                item.put("temp", "Температура: " + temp + " °C");
                item.put("possibility", "Вероятность осадков: " + possibility + "%");
                item.put("country", country);
                item.put("wind", "Ветер: " + windSpeed + " м/с");
                item.put("time", time);
                item.put("day", day);
                item.put("description", description);
                item.put("icon", "https://openweathermap.org/img/wn/"+icon+"@2x.png");

                list.add(item);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        adapter = new MySimpleAdapter(this, list, R.layout.list_item_row,
                new String[]{"description","day","time", "temp", "possibility", "wind", "icon"},
                new int[]{R.id.description, R.id.day, R.id.time, R.id.temp, R.id.probability, R.id.wind, R.id.icon});

        listView.setAdapter(adapter);
        loading.dismiss();

    }
}
