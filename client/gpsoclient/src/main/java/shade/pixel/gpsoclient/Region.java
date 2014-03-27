package shade.pixel.gpsoclient;

import java.io.Serializable;

/**
 * Created by pixelshade on 2.3.2014.
 */
public class Region implements Serializable{
    public static final String KEY_REGION_ID = "id";
    public static final String KEY_REGION_NAME = "name";
    public static final String KEY_REGION_INFO = "info";
    public static final String KEY_REGION_IMAGE = "image";
    public static final String KEY_REGION_LAT_START = "lat_start";
    public static final String KEY_REGION_LON_START = "lon_start";
    public static final String KEY_REGION_LAT_END = "lat_end";
    public static final String KEY_REGION_LON_END = "lon_end";

    private int id;
    private String name;
    private String info;
    private String image;
    private int lat_start;
    private int lon_start;
    private int lat_end;
    private int lon_end;

    public Region(int id,
                  String name,
                  String info,
                  String image,
                  int lat_start,
                  int lon_start,
                  int lat_end,
                  int lon_end) {
        this.id = id;
        this.name = name;
        this.info = info;
        this.image = image;
        this.lat_start = lat_start;
        this.lon_start = lon_start;
        this.lat_end = lat_end;
        this.lon_end = lon_end;
    }


    public int getLon_end() {
        return lon_end;
    }

    public void setLon_end(int lon_end) {
        this.lon_end = lon_end;
    }

    public int getLat_end() {
        return lat_end;
    }

    public void setLat_end(int lat_end) {
        this.lat_end = lat_end;
    }

    public int getLon_start() {
        return lon_start;
    }

    public void setLon_start(int lon_start) {
        this.lon_start = lon_start;
    }

    public int getLat_start() {
        return lat_start;
    }

    public void setLat_start(int lat_start) {
        this.lat_start = lat_start;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String toString(){
        return getName();
    }

}
