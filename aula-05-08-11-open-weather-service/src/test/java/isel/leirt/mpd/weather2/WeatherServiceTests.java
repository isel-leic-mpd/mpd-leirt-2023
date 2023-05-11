package isel.leirt.mpd.weather2;

import isel.leirt.mpd.weather2.model.DayInfo;
import isel.leirt.mpd.weather2.model.Location;
import isel.leirt.mpd.weather2.model.WeatherInfo;
import isel.leirt.mpd.weather2.requests.HttpRequest;
import isel.leirt.mpd.weather2.requests.Request;
import isel.mpd.queries.lazy2.StreamIterable;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.List;


public class WeatherServiceTests {

	@Test
	public void get_locations_named_lisbon() {
		Request httpReq = new HttpRequest();

		OpenWeatherService service =
			new OpenWeatherService(
				new OpenWeatherWebApi()
			);

		StreamIterable<Location> locations =
			service.search("Lisbon");
	}


	@Test
	public void getForecastForLisbonTest() {

		OpenWeatherService service =
			new OpenWeatherService(
				new OpenWeatherWebApi( ));

		// TODO
		StreamIterable<DayInfo> forecastWeather = null;


		List<DayInfo>  forecastList = forecastWeather.toList();

		long nDays = forecastList.size();
		assertEquals(8, nDays);

		System.out.println("DayInfo list size: " + nDays);
		forecastList.forEach(System.out::println);

	}

	@Test
	public void getForecastDetailForTomorrowAtLisbonTest() {
		OpenWeatherService serv =
			new OpenWeatherService(new OpenWeatherWebApi());


		// TODO
		StreamIterable<WeatherInfo> tomorrowTemps = null;

		System.out.println("WeatherInfo list size: " + tomorrowTemps.count());
		tomorrowTemps.forEach(System.out::println);
	}
}
