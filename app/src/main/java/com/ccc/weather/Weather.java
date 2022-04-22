package com.ccc.weather;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class Weather {
    private final String city;
    private final String country;
    private final double temp;
    private final int pressure;
    private final int windSpeed;
    private final Date date;
    private final SimpleDateFormat formatter = new SimpleDateFormat("dd MMM yyyy HH:mm");

    public Weather(HashMap<String, String> weather) {
        this.city = weather.get("name");
        this.temp = Double.parseDouble(weather.get("temp"));
        this.pressure = Integer.parseInt(weather.get("pressure"));
        this.country = weather.get("country");
        this.windSpeed = Integer.parseInt(weather.get("speed"));
        this.date = new Date(Long.parseLong(weather.get("time")) * 1000);
    }

    public void GetInfo(){
        System.out.println(
                formatter.format(date) +
                "\nCity: " + city + ", " + country + ";\n" +
                        "Temperature: " + temp + "°C;\n" +
                        "Pressure: " + pressure + " millibars;\n" +
                        "Wind speed: " + windSpeed + " km/h."
        );
    }
}
