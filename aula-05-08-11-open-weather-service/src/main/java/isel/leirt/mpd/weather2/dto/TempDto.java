package isel.leirt.mpd.weather2.dto;

public class TempDto {
    public final double min;
    public final double max;

    public TempDto(double min, double max) {
        this.min = min;
        this.max = max;
    }
}