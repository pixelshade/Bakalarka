package shade.pixel.gpsoclient;

import java.io.Serializable;

/**
 * Created by pixelshade on 27.3.2014.
 */
public class Reward implements Serializable {
    private int id;
    private String code;
    private String name;
    private Item item;
    private int itemAmount;
    private Attribute attribute;
    private int attributeAmount;

    public Reward() {
    }

    ;

    public Reward(int id, String code, String name, Item item, int itemAmount, Attribute attribute, int attributeAmount) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.item = item;
        this.itemAmount = itemAmount;
        this.attribute = attribute;
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


    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public int getItemAmount() {
        return itemAmount;
    }

    public void setItemAmount(int itemAmount) {
        this.itemAmount = itemAmount;
    }

    public Attribute getAttribute() {
        return attribute;
    }

    public void setAttribute(Attribute attribute) {
        this.attribute = attribute;
    }

    public int getAttributeAmount() {
        return attributeAmount;
    }

    public void setAttributeAmount(int attributeAmount) {
        this.attributeAmount = attributeAmount;
    }
}

