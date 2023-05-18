package isel.leirt.mpd.weather3;

import isel.leirt.mpd.weather3.OpenWeatherService;
import isel.leirt.mpd.weather3.OpenWeatherWebApi;
import isel.leirt.mpd.weather3.model.DayInfo;
import isel.leirt.mpd.weather3.model.Location;
import isel.leirt.mpd.weather3.model.WeatherInfo;
import isel.leirt.mpd.weather3.requests.HttpRequest;
import isel.leirt.mpd.weather3.requests.Request;
import isel.mpd.queries.lazy2.StreamIterable;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class WeatherServiceTests {

	@Test
	public void get_locations_named_lisbon() {
		Request httpReq = new HttpRequest();
		int[] count = { 0 };

		Request counterReq = path -> {
			count[0]++;

			return httpReq.get(path);
		};


		OpenWeatherService service =
			new OpenWeatherService(
				new OpenWeatherWebApi(counterReq)
			);

		assertEquals(0, count[0]);
		var locationsSupplier =
			service.search("Lisboa");

		assertEquals(0, count[0]);

		var locations = locationsSupplier.get();
		locations.forEach( System.out::println);
		assertEquals(1, count[0]);
	}


	@Test
	public void getForecastForLisbonTest() {

		OpenWeatherService service =
			new OpenWeatherService(
				new OpenWeatherWebApi( ));

		Stream<DayInfo> forecastWeather =
				service.search("Lisboa").get()
				.filter(l -> l.getCountry().equals("PT"))
				.flatMap(l -> l.forecast());


		List<DayInfo>  forecastList = forecastWeather.toList();

		long nDays = forecastList.size();
		assertEquals(8, nDays);

		System.out.println("DayInfo list size: " + nDays);
		forecastList.forEach(System.out::println);

	}

	@Test
	public void getForecastDetailForTomorrowAtLisbonTest() {
		OpenWeatherService service =
			new OpenWeatherService(new OpenWeatherWebApi());


		// TODO
		List<WeatherInfo> tomorrowTemps =
				service.search("Lisboa").get()
					   .filter(l -> l.getCountry().equals("PT"))
					   .flatMap(l -> l.forecast())
					   .filter(di -> di.getDate().equals(LocalDate.now().plusDays(1)))
					   .flatMap(di -> di.temperatures())

					   .collect(Collectors.toList());


		System.out.println("WeatherInfo list size: " + tomorrowTemps.size());
		tomorrowTemps.forEach(System.out::println);
	}
}
