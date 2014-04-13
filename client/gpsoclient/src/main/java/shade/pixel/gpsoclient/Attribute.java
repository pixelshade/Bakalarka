package shade.pixel.gpsoclient;

import android.util.Log;

import java.io.Serializable;

/**
 * Created by pixelshade on 28.3.2014.
 */
public class Attribute implements Serializable {
    private final String TAG = "Attribute";
    public static final String KEY_ATTRIBUTE_ID = "id";
    public static final String KEY_ATTRIBUTE_NAME = "name";
    public static final String KEY_ATTRIBUTE_INFO = "info";
    public static final String KEY_ATTRIBUTE_IMAGE = "image";

    private int id;
    private String name;
    private String info;
    private String image;


    public Attribute(int id,
                     String name,
                     String info,
                     String image
    ) {
        this.id = id;
        this.name = name;
        this.info = info;
        this.image = image;
        Log.d(TAG,"was created:"+ this.name+","+this.info+","+this.image);

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
