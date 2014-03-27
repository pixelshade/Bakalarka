package shade.pixel.gpsoclient;

import java.io.Serializable;

/**
 * Created by pixelshade on 27.3.2014.
 */
public class Reward implements Serializable{
    public static final String KEY_QUEST_ID = "id";
    public static final String KEY_QUEST_CODE = "code";
    public static final String KEY_QUEST_NAME = "name";
    public static final String KEY_QUEST_INFO = "info";
    public static final String KEY_QUEST_IMAGE = "image";
    public static final String KEY_QUEST_REWARD_ID = "reward_id";
    public static final String KEY_QUEST_AUTOSTART = "autostart";
    public static final String KEY_QUEST_REGION_ID = "region_id";
    public static final String KEY_QUEST_REQUIRED_QUEST_ID = "required_completed_quest_id";
    public static final String KEY_QUEST_DURATION = "attributeAmount";
    public static final String KEY_QUEST_REQUIREMENT_TYPE = "completion_requirement_type";
    public static final String KEY_QUEST_REQUIREMENT = "completion_requirement";

    public static final int UNDEFINED_INT_VALUE = -1;

    private int id;
    private String code;
    private String name;
    private String rewardImage;
    private String itemImage;
    private String attributeImage;
    private int itemId;
    private int attributeId;
    private int itemAmount;
    private int attributeAmount;

    public Reward(int id, String code, String name, String rewardImage, int itemId, int itemAmount, String itemImage, int attributeId, int attributeAmount, String attributeImage) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.itemId = itemId;
        this.attributeId = attributeId;
        this.itemImage = itemImage;
        this.attributeImage = attributeImage;
        this.itemAmount = itemAmount;
        this.attributeAmount = attributeAmount;
        this.rewardImage = rewardImage;
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

    public String getRewardImage() {
        return rewardImage;
    }

    public void setRewardImage(String rewardImage) {
        this.rewardImage = rewardImage;
    }
}
