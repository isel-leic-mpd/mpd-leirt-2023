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
import static isel.mpd.queries.lazy2.StreamIterable.range;


public class OpenWeatherService {
	private  OpenWeatherWebApi api;

	public OpenWeatherService( OpenWeatherWebApi api) {
		this.api = api;
	}


	/**
	 * Sa~apresentadas duas formas de garantir que
	 * o acesso à api apenas acontece quando for
	 * consumido o Iterable retornado
	 *
	 * @param placeName
	 * @return
	 */
	public StreamIterable<Location> search(String placeName) {

		return   range(1,1)
				 .flatMap( n -> api.search(placeName))
				 .map(this::dtoToLocation);


		/*
		return  from( () -> api.search(placeName).iterator())
					.map(this::dtoToLocation);

		*/

	}


	// corrijam e completem os dois métodso abaixo
	// para terem a mesma semântica lazy do método anterior

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
		return  new Location(dto.getName(),
			dto.getCountry(),
			dto.getLat(),
			dto.getLon(),
			l -> forecastAt(l)
		);

	}

	private  WeatherInfo dtoToWeatherInfo(ForecastHourlyDto dto) {
		return new WeatherInfo(
			dto.observationDateTime(),
			dto.temp(),
			dto.description(),
			dto.humidity(),
			dto.feelsLike());
	}

	// utilizem neste método a mesma técnica usada no Location
	// para que a obtenção das abservações de temperatura do dia só ocorra
	// quando for chamado o método temperatures de DayInfo
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
