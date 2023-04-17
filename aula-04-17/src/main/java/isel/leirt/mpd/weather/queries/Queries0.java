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

        // TODO
        return null;
    }

    public static Iterable<WeatherInfo> get_rainy_locals(
        List<WeatherInfo> wiSeq) {
        // TODO
        return null;
    }

    public static Iterable<WeatherInfo> getLisbonWeatherFromList(Iterable<WeatherInfo> seq) {
         // TODO
        return null;
    }

    public Iterable<Double> getTemperaturesInInterval(
                    Iterable<WeatherInfo> seq, double min, double max) {
        List<Double> temps = new ArrayList<>();
        // TODO
        return temps;
    }

}
