package isel.leirt.mpd.weather2;

import isel.leirt.mpd.weather2.dto.ForecastHourlyDto;
import isel.leirt.mpd.weather2.dto.ForecastInfoDto;
import isel.leirt.mpd.weather2.dto.LocationDto;
import isel.leirt.mpd.weather2.model.DayInfo;
import isel.leirt.mpd.weather2.model.Location;
import isel.leirt.mpd.weather2.model.WeatherInfo;

import java.time.LocalDate;

import isel.mpd.queries.lazy2.StreamIterable;

import static isel.mpd.queries.lazy2.StreamIterable.from;


public class OpenWeatherService {
	private  OpenWeatherWebApi api;

	public OpenWeatherService( OpenWeatherWebApi api) {
		this.api = api;
	}


	public StreamIterable<Location> search(String placeName) {
		return  from(api.search(placeName))
					//.map(dto -> dtoToLocation(dto));
					.map(this::dtoToLocation);

	}


	private StreamIterable<DayInfo> forecastAt(Location loc) {
		return
			from(api.forecastWeatherAt(loc.getLatitude(), loc.getLongitude()))
				.map(dto -> dtoToDayInfo(dto, loc));

	}

	public StreamIterable<WeatherInfo> weatherDetail(Double lat,
											   Double lon,
											   LocalDate day) {
		// TODO
		return null;
	}

	public StreamIterable<WeatherInfo> weatherDetail(Location loc, DayInfo di) {
		return weatherDetail(loc.getLatitude(), loc.getLongitude(), di.getDate());
	}

	private  Location dtoToLocation(LocationDto dto) {
		var loc =  new Location(dto.getName(),
			dto.getCountry(),
			dto.getLat(),
			dto.getLon()
		);
		loc.setForecast(() -> forecastAt(loc));
		return loc;
	}

	private  WeatherInfo dtoToWeatherInfo(ForecastHourlyDto dto) {
		return new WeatherInfo(
			dto.observationDateTime(),
			dto.temp(),
			dto.description(),
			dto.humidity(),
			dto.feelsLike());
	}

	public DayInfo dtoToDayInfo(ForecastInfoDto dto, Location loc) {
		return new DayInfo(
			dto.obsDate(),
			dto.maxTemp(),
			dto.minTemp(),
			dto.sunRise(),
			dto.sunSet(),
			dto.moonRise(),
			dto.moonSet(),
			dto.moonPhase(),
			dto.getDescription()
		);
	}
}
