package isel.leirt.mpd.weather2;

import isel.leirt.mpd.weather2.dto.*;
import isel.leirt.mpd.weather2.requests.FileRequest;
import isel.leirt.mpd.weather2.requests.HttpRequest;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class WeatherTests {

    private final static double LISBON_LAT  =  38.7071;
    private final static double LISBON_LONG = -9.1359;

    @Test
    public void get_weather_at_lisbon_now() {
        OpenWeatherWebApi webApi = new OpenWeatherWebApi(new FileRequest());
        WeatherInfoDto winfo = webApi.weatherAt(LISBON_LAT, LISBON_LONG );
        String wiStr = winfo.toString();
        System.out.println(wiStr);
    }

    @Test
    public void save_get_weather_at_lisbon_now() {
        OpenWeatherWebApi webApi = new OpenWeatherWebApi(new HttpRequest());
        webApi.saveWeatherAt(LISBON_LAT, LISBON_LONG);
    }

    @Test
    public void get_air_pollution_in_lisbon_now() {
        OpenWeatherWebApi webApi = new OpenWeatherWebApi();

        PollutionInfoDto pi = webApi.airPollutionAt(
            LISBON_LAT, LISBON_LONG);


        System.out.println(pi);
    }

    @Test
    public void save_air_pollution_in_lisbon_now() {
        OpenWeatherWebApi webApi = new OpenWeatherWebApi();

        webApi.saveAirPollutionAt(LISBON_LAT, LISBON_LONG);
    }

    @Test
    public void get_air_pollution_history_by_period() {
        OpenWeatherWebApi webApi = new OpenWeatherWebApi();
        LocalDate start = LocalDate.of(2022, 03, 03);
        LocalDate end = LocalDate.of(2022, 04, 03);
        List<PollutionInfoDto> pinfo =
                webApi.pollutionHistoryAt(LISBON_LAT, LISBON_LONG,start,end);
        System.out.println(pinfo.size());
        for(PollutionInfoDto pi : pinfo) {
            System.out.println(pi);
        }
        assertEquals(745, pinfo.size() );
    }

    @Test
    public void get_location_info_by_name() {
        OpenWeatherWebApi webApi = new OpenWeatherWebApi();
        String localName = "Lisboa";

        List<LocationDto> locations = webApi.search(localName);
        for(var loc : locations)
            System.out.println(loc);
        assertEquals(5, locations.size());
    }

    @Test
    public void getForecastWeatherForLisbonTest() {
        OpenWeatherWebApi webApi = new OpenWeatherWebApi();
        List<LocationDto> locs = webApi.search("Lisbon");
        assertTrue(locs.size() > 0);
        LocationDto loc = locs.get(0);
        List<ForecastInfoDto> winfo =
            webApi.forecastWeatherAt(loc.getLat(), loc.getLon());

        for(ForecastInfoDto wif : winfo) {
            System.out.println(wif);
        }

        assertEquals(8, winfo.size());
    }

    @Test
    public void getForecastDetailForLisbonTest() {
        OpenWeatherWebApi webApi = new OpenWeatherWebApi();
        List<LocationDto> locs = webApi.search("Lisbon");
        assertTrue(locs.size() > 0);
        LocationDto loc = locs.get(0);
        List<ForecastHourlyDto> winfo = webApi.forecastDetailAt(loc.getLat(), loc.getLon());

        System.out.println("WeatherInfo list size: " + winfo.size());
        for(ForecastHourlyDto fwi : winfo) {
            System.out.println(fwi);
        }

        assertEquals(2*24, winfo.size());
    }

}
