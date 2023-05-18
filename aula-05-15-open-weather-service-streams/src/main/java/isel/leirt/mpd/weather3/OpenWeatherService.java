package isel.leirt.mpd.weather3;

import isel.leirt.mpd.weather3.dto.ForecastHourlyDto;
import isel.leirt.mpd.weather3.dto.ForecastInfoDto;
import isel.leirt.mpd.weather3.dto.LocationDto;
import isel.leirt.mpd.weather3.model.DayInfo;
import isel.leirt.mpd.weather3.model.Location;
import isel.leirt.mpd.weather3.model.WeatherInfo;

import java.time.LocalDate;
import java.util.function.Supplier;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import isel.mpd.queries.lazy2.StreamIterable;

import static isel.mpd.queries.lazy2.StreamIterable.from;
import static isel.mpd.queries.lazy2.StreamIterable.range;


public class OpenWeatherService {
	private  OpenWeatherWebApi api;

	public OpenWeatherService( OpenWeatherWebApi api) {
		this.api = api;
	}


	/**
	 * São apresentadas duas formas de garantir que
	 * o acesso à api apenas acontece quando for
	 * consumido o Iterable retornado
	 *
	 * @param placeName
	 * @return
	 */
	public Supplier<Stream<Location>> search0(String placeName) {
 		return () -> api.search(placeName)
					  .stream()
			   		  .map(this::dtoToLocation);
	}

	Stream<Location> search(String placeName) {
		return Stream.of(1)
			.flatMap(__-> api.search(placeName).stream())
			.map(this::dtoToLocation);
	}

	// corrijam e completem os dois métodso abaixo
	// para terem a mesma semântica lazy do método anterior

	private Stream<DayInfo> forecastAt(Location loc) {
		// TODO
		return null;
	}

	private Stream<WeatherInfo> weatherDetail(Double lat,
                                                      Double lon,
                                                      LocalDate day) {
		// TODO
		return null;
	}

	public Stream<WeatherInfo> weatherDetail(Location loc, DayInfo di) {
		return weatherDetail(loc.getLatitude(), loc.getLongitude(), di.getDate());
	}

	private  Location dtoToLocation(LocationDto dto) {
		return  new Location(dto.getName(),
			dto.getCountry(),
			dto.getLat(),
			dto.getLon(),
			this::forecastAt
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
			dto.getDescription(),
			di -> weatherDetail(loc, di)
		);
	}
}
