package shade.pixel.gpsoclient;

/**
 * Created by pixelshade on 13.3.2014.
 */
public class Item {
    public static final String KEY_REGION_ID = "id";
    public static final String KEY_REGION_NAME = "name";
    public static final String KEY_REGION_INFO = "info";
    public static final String KEY_REGION_IMAGE = "image";


    private int id;
    private String name;
    private String info;
    private String image;


    public Item(int id,
                String name,
                String info,
                String image
                ) {
        this.id = id;
        this.name = name;
        this.info = info;
        this.image = image;

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

    public String toString() {
        return getName();
    }


}
