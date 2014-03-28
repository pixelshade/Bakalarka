package shade.pixel.gpsoclient;

import java.io.Serializable;

/**
 * Created by pixelshade on 27.3.2014.
 */
public class Reward implements Serializable{
    private int id;
    private String code;
    private String name;
    private String itemImage;
    private String attributeImage;
    private int itemId;
    private int attributeId;
    private int itemAmount;
    private int attributeAmount;

    public Reward(int id, String code, String name, int itemId, int itemAmount, String itemImage, int attributeId, int attributeAmount, String attributeImage) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.itemId = itemId;
        this.attributeId = attributeId;
        this.itemImage = itemImage;
        this.attributeImage = attributeImage;
        this.itemAmount = itemAmount;
        this.attributeAmount = attributeAmount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getItemImage() {
        return itemImage;
    }

    public void setItemImage(String itemImage) {
        this.itemImage = itemImage;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public int getAttributeId() {
        return attributeId;
    }

    public void setAttributeId(int attributeId) {
        this.attributeId = attributeId;
    }

    public int getItemAmount() {
        return itemAmount;
    }

    public void setItemAmount(int itemAmount) {
        this.itemAmount = itemAmount;
    }

    public int getAttributeAmount() {
        return attributeAmount;
    }

    public void setAttributeAmount(int attributeAmount) {
        this.attributeAmount = attributeAmount;
    }

    public String getAttributeImage() {
        return attributeImage;
    }

    public void setAttributeImage(String attributeImage) {
        this.attributeImage = attributeImage;
    }

}
