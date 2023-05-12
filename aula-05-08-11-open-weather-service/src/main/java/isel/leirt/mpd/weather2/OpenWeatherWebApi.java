package isel.leirt.mpd.weather2;

import com.google.gson.Gson;


import isel.leirt.mpd.weather2.dto.*;
import isel.leirt.mpd.weather2.exceptions.WeatherApiException;
import isel.leirt.mpd.weather2.requests.FileRequest;
import isel.leirt.mpd.weather2.requests.HttpRequest;
import isel.leirt.mpd.weather2.requests.Request;
import isel.leirt.mpd.weather2.utils.TimeUtils;


import java.io.*;
import java.net.URL;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class OpenWeatherWebApi {
    private static final String API_KEY = getApiKeyFromResources();

    private static final String WEATHER_HOST =
            "http://api.openweathermap.org/";

    private static final String GEO_SERVICE =
        "geo/1.0/";

    private static final String WEATHER_SERVICE =
            "http://api.openweathermap.org/data/2.5/";

    private static final String WEATHER_AT_TEMPLATE =
        WEATHER_SERVICE + "weather?lat=%f&lon=%f&units=metric&appid=" + API_KEY;


    private static final String FORECAST_WEATHER_TEMPLATE =
        WEATHER_SERVICE
            + "onecall?lat=%.3f&lon=%.3f&exclude=hourly,minutely,current&units=metric&appid="
            + API_KEY;

    private static final String FORECAST_HOURLY_TEMPLATE =
        WEATHER_SERVICE
            + "onecall?lat=%.3f&lon=%.3f&exclude=minutely,current&units=metric&appid="
            + API_KEY;

    private static final String AIR_POLLUTION_AT_TEMPLATE =
        WEATHER_SERVICE + "air_pollution?lat=%f&lon=%f&appid=" + API_KEY;

    private static final String AIR_POLLUTION_HISTORY_TEMPLATE =
        WEATHER_SERVICE
            +  "air_pollution/history?lat=%f&lon=%f&start=%d&end=%d&appid="
            + API_KEY;

    private static final String LOCATION_SEARCH_TEMPLATE =
        WEATHER_HOST
            + GEO_SERVICE + "direct?q=%s&limit=10&lang=pt&appid=" + API_KEY;

    Request req;
    protected final Gson gson;

    /**
     * Retrieve API-KEY from resources
     * @return
     */
    private static String getApiKeyFromResources() {
        try {
            URL keyFile =
                    ClassLoader.getSystemResource("openweatherapi-app-key.txt");
            try (BufferedReader reader =
                         new BufferedReader(new InputStreamReader(keyFile.openStream()))) {
                return reader.readLine();
            }

        }
        catch(IOException e) {
            throw new IllegalStateException(
                    "YOU MUST GET a KEY from  openweatherapi.com and place it in src/main/resources/openweatherapi-app-key2.txt");
        }
    }


    /**
     * Get WeatherInfo's from a local coordinates given a date interval
     * @param lat
     * @param lon
     * @return
     */
    public WeatherInfoDto weatherAt(double lat, double lon) {
        String path = String.format(WEATHER_AT_TEMPLATE, lat, lon);

        Reader reader = req.get(path);
        WeatherInfoDto winfo =
            gson.fromJson(reader, WeatherInfoDto.class);
        System.out.println("class is " + winfo.getClass().getName() +
                            "winfo = " + winfo.toString());
        return winfo;
    }

    public void saveWeatherAt(double lat, double lon) {
        String path = String.format(WEATHER_AT_TEMPLATE, lat, lon);
        Reader reader = req.get(path);

        FileRequest.saveOn(path, reader);
    }

    /**
     * Get current air pollution metrics from a local coordinates
     * @param lat
     * @param lon
     * @return
     */
    public PollutionInfoDto airPollutionAt(double lat, double lon) {
        String path = String.format(AIR_POLLUTION_AT_TEMPLATE, lat, lon);

        PollutionInfoQueryDto pi =
            gson.fromJson(req.get(path), PollutionInfoQueryDto.class);
        if (pi.list == null || pi.list.length != 1)
            throw new WeatherApiException("response list must have one element");
        return pi.list[0];
    }

    public void saveAirPollutionAt(double lat, double lon) {
        String path = String.format(AIR_POLLUTION_AT_TEMPLATE, lat, lon);
        FileRequest.saveOn(path, req.get(path));
    }

    /**
     * Get WeatherInfo's forecast for a local coordinates
     * @param lat
     * @param lon
     * @return
     */
    public List<ForecastInfoDto> forecastWeatherAt0(double lat, double lon) {
        String path =  String.format(FORECAST_WEATHER_TEMPLATE, lat, lon);
        ForecastWeatherInfoQueryDto finfo =
                gson.fromJson(req.get(path), ForecastWeatherInfoQueryDto.class);
        return Arrays.asList(finfo.getForecastInfo());
    }

    /**
     * Get WeatherInfo's forecast for a local coordinates
     * @param lat
     * @param lon
     * @return
     */
    public List<ForecastInfoDto> forecastWeatherAt(double lat, double lon) {
        String path = String.format(FORECAST_WEATHER_TEMPLATE, lat, lon);

        Reader reader = req.get(path);

        ForecastWeatherInfoQueryDto finfo =
            gson.fromJson(reader, ForecastWeatherInfoQueryDto.class);
        return Arrays.asList(finfo.getForecastInfo());

    }

    public List<ForecastHourlyDto> forecastDetailAt(double lat, double lon) {
        String path =  String.format(FORECAST_HOURLY_TEMPLATE, lat, lon);

        ForecastWeatherInfoQueryDto winfo =
            gson.fromJson(req.get(path), ForecastWeatherInfoQueryDto.class);
        return Arrays.asList(winfo.getHourlyInfo());

    }

    /**
     * Get local info given the name of the local
     * @param location
     * @return
     */
    public List<LocationDto> search(String location) {
        String path =  String.format(LOCATION_SEARCH_TEMPLATE, location);
        LocationDto[] search = gson.fromJson(req.get(path), LocationDto[].class);
        return Arrays.asList(search);
    }

    public List<PollutionInfoDto> pollutionHistoryAt(
            double lati, double longi, LocalDate start, LocalDate end) {

        String path = String.format(AIR_POLLUTION_HISTORY_TEMPLATE,
                            lati, longi, TimeUtils.toUnixTime(start), TimeUtils.toUnixTime(end));

        PollutionInfoQueryDto winfo =
                gson.fromJson(req.get(path), PollutionInfoQueryDto.class);
        return Arrays.asList(winfo.list);

    }

    // the instance created receive the data source used to
    // get the weather resources
    public OpenWeatherWebApi(Request req) {
        gson = new Gson();
        this.req = req;
    }

    public OpenWeatherWebApi( ) {
        gson = new Gson();
        this.req = new HttpRequest();
    }


}
