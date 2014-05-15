package shade.pixel.gpsoclient;

import java.io.Serializable;

/**
 * Created by pixelshade on 15.5.2014.
 */
public class MSGContainer implements Serializable {
    int type = -1;
    int id = -1;
    Item item = null;

    public MSGContainer(int type) {
        this.type = type;
    }

    public MSGContainer(int type, Item item) {
        this.type = type;
        this.item = item;
    }

    public MSGContainer(int type, int id) {
        this.type = type;
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public int getId() {
        return id;
    }

    public Item getItem() {
        return item;
    }
}
