package shade.pixel.gpsoclient;

import java.io.Serializable;

/**
 * Created by pixelshade on 27.3.2014.
 */
public class Reward implements Serializable{
    private int id;
    private String code;
    private String name;
    private String itemName;
    private String itemInfo;
    private String itemImage;
    private int itemId;
    private int itemAmount;
    private String attributeName;
    private String attributeInfo;
    private String attributeImage;
    private int attributeId;
    private int attributeAmount;

    public Reward(){};

    public Reward(int id, String code, String name, String itemName, String itemInfo, String itemImage, int itemId, int itemAmount, String attributeName, String attributeInfo, String attributeImage, int attributeId, int attributeAmount) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.itemName = itemName;
        this.itemInfo = itemInfo;
        this.itemImage = itemImage;
        this.itemId = itemId;
        this.itemAmount = itemAmount;
        this.attributeName = attributeName;
        this.attributeInfo = attributeInfo;
        this.attributeImage = attributeImage;
        this.attributeId = attributeId;
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

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getAttributeName() {
        return attributeName;
    }

    public void setAttributeName(String attributeName) {
        this.attributeName = attributeName;
    }

    public String getItemInfo() {
        return itemInfo;
    }

    public void setItemInfo(String itemInfo) {
        this.itemInfo = itemInfo;
    }

    public String getAttributeInfo() {
        return attributeInfo;
    }

    public void setAttributeInfo(String attributeInfo) {
        this.attributeInfo = attributeInfo;
    }
}
