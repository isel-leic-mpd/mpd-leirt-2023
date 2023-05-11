package isel.leirt.mpd.weather2.model;

import isel.mpd.queries.lazy2.StreamIterable;

import java.util.function.Supplier;

public class Location {

	private String name;
	private String country;
	private double latitude;
	private double longitude;

	Supplier<StreamIterable<DayInfo>> forecast;

	public Location(String name,
	                String country,
	                double latitude,
	                double longitude) {
		this.name = name;
		this.country = country;

		this.latitude = latitude;
		this.longitude = longitude;
	}

	// acessors
	public String getName()         { return name; }
	public String getCountry()      { return country; }
	public double getLatitude()     { return latitude; }
	public double getLongitude()    { return longitude; }

	public void setForecast(Supplier<StreamIterable<DayInfo>> forecast) {
		this.forecast = forecast;
	}

	public StreamIterable<DayInfo> forecast()  {
		return forecast.get();
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
