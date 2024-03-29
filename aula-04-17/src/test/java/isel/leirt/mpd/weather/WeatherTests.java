package isel.leirt.mpd.weather;

import isel.leirt.mpd.weather.dto.*;
import isel.leirt.mpd.weather.queries.Queries0;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

import static java.util.stream.Collectors.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class WeatherTests {

    private final static double LISBON_LAT  =  38.7071;
    private final static double LISBON_LONG = -9.1359;

    @Test
    public void get_weather_at_lisbon_now() {
        OpenWeatherWebApi webApi = new OpenWeatherWebApi();
        WeatherInfo winfo = webApi.weatherAt(LISBON_LAT, LISBON_LONG );
        System.out.println(winfo);
    }

    @Test
    public void portugal_locations_weather_now() {
        double lati = 36.945, longi = -9.522;
        double latf = 42.149, longf = -6.187;
        OpenWeatherWebApi webApi = new OpenWeatherWebApi();
        List<WeatherInfo> cities = webApi.weatherAtArea(longi, lati,  longf, latf);
        Assertions.assertTrue(cities.size() > 0);

        System.out.println("found " + cities.size() + " cities.");
        for(WeatherInfo wi: cities)
            System.out.println(wi);
    }

    @Test
    public void get_sunny_locals_in_portugal_now() {
        double lati = 36.945, longi = -9.522;
        double latf = 42.149, longf = -6.187;
        OpenWeatherWebApi webApi = new OpenWeatherWebApi();

        List<WeatherInfo> result = webApi.weatherAtArea(longi, lati, longf, latf);

        Iterable<WeatherInfo> sunnyLocals = Queries0.get_sunny_locals(result);

        int count = 0;
        for(var e : sunnyLocals) count++;

        System.out.println("now there are " + count + " sunny locals");

        for(var sl : sunnyLocals)
            System.out.println(sl);

    }




}
