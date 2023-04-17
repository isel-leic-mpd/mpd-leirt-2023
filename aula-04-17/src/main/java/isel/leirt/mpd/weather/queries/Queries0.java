package isel.leirt.mpd.weather.queries;

import isel.leirt.mpd.weather.dto.WeatherInfo;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

public class Queries0 {

    public static Iterable<WeatherInfo> get_sunny_locals(
        List<WeatherInfo> wiSeq) {

        List<WeatherInfo> result = new ArrayList<>();

        for(var wi : wiSeq) {
            if (wi.description().contains("sky"))
                result.add(wi);
        }
        return result;
    }

    public static Iterable<WeatherInfo> get_rainy_locals(
        List<WeatherInfo> wiSeq) {


        List<WeatherInfo> result = new ArrayList<>();

        for(var wi : wiSeq) {
            if (wi.description().contains("rain"))
                result.add(wi);
        }
        return result;
    }

    public static Iterable<WeatherInfo> getLisbonWeatherFromList(Iterable<WeatherInfo> seq) {
         // TODO
        return null;
    }

    public Iterable<Double> getTemperaturesInInterval(
                    Iterable<WeatherInfo> seq, double min, double max) {
        List<Double> temps = new ArrayList<>();

        for(var wi : seq) {
            if (wi.temp() >= min && wi.temp() <= max)
                temps.add(wi.temp());
        }
        return temps;
    }

}
