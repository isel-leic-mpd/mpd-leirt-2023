package isel.leirt.mpd.weather2.model;

import isel.mpd.queries.lazy2.StreamIterable;

import java.util.function.Function;
import java.util.function.Supplier;

public class Location {

	private String name;
	private String country;
	private double latitude;
	private double longitude;

	Function<Location, StreamIterable<DayInfo>> forecast;

	public Location(String name,
	                String country,
	                double latitude,
	                double longitude,
					Function<Location, StreamIterable<DayInfo>> forecast
					) {
		this.name = name;
		this.country = country;

		this.latitude = latitude;
		this.longitude = longitude;
		this.forecast = forecast;
	}

	// acessors
	public String getName()         { return name; }
	public String getCountry()      { return country; }
	public double getLatitude()     { return latitude; }
	public double getLongitude()    { return longitude; }

	public StreamIterable<DayInfo> forecast()  {
		return forecast.apply(this);
	}

	@Override
	public String toString() {
		return "{"
			+ name
			+ ", country=" + getCountry()
			+ ", latitude=" + getLatitude()
			+ ", longitude=" + getLongitude()
			+ "}";
	}

}
