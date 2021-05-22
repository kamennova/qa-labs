package models;

public class Route {
    public Long id;
    public String polyline;
    public Double lat;
    public Double lng;

    Route(String polyline, Double lat, Double lng){
        this.polyline = polyline;
        this.lat = lat;
        this.lng = lng;
    }
}
