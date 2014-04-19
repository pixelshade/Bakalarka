package shade.pixel.gpsoclient;

import java.io.Serializable;

/**
 * Created by pixelshade on 2.3.2014.
 */
public class Quest implements Serializable{
    public static final String KEY_QUEST_ID = "id";
    public static final String KEY_QUEST_CODE = "code";
    public static final String KEY_QUEST_NAME = "name";
    public static final String KEY_QUEST_INFO = "info";
    public static final String KEY_QUEST_IMAGE = "image";
    public static final String KEY_QUEST_REWARD_ID = "reward_id";
    public static final String KEY_QUEST_AUTOSTART = "autostart";
    public static final String KEY_QUEST_REGION_ID = "region_id";
    public static final String KEY_QUEST_REQUIRED_QUEST_ID = "required_completed_quest_id";
    public static final String KEY_QUEST_DURATION = "duration";
    public static final String KEY_QUEST_REQUIREMENT_TYPE = "completion_requirement_type";
    public static final String KEY_QUEST_REQUIREMENT = "completion_requirement";

    public static final int UNDEFINED_INT_VALUE = -1;

    private int id;
    private String code;
    private String name;
    private String info;
    private String image;
    private int rewardId;
    private boolean autostart;
    private int regionId;
    private int requiredQuestId;
    private int duration;
    private int requirementType;
    private String requirement;
    // optional
    private boolean active = false;
    private boolean completed = false;

    public Quest(int id, String code, String name, String info, String image, int rewardId, boolean autostart, int regionId, int requiredQuestId, int duration, int requirementType, String requirement, boolean active, boolean completed) {
        this(id, code, name, info, image, rewardId, autostart, regionId, requiredQuestId, duration, requirementType, requirement);
        this.active = active;
        this.completed = completed;
    }

    public Quest(int id, String code, String name, String info, String image, int rewardId, boolean autostart, int regionId, int requiredQuestId, int duration, int requirementType, String requirement) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.info = info;
        this.image = image;
        this.rewardId = rewardId;
        this.autostart = autostart;
        this.regionId = regionId;
        this.requiredQuestId = requiredQuestId;
        this.duration = duration;
        this.requirementType = requirementType;
        this.requirement = requirement;
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

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getRewardId() {
        return rewardId;
    }

    public void setRewardId(int rewardId) {
        this.rewardId = rewardId;
    }

    public boolean isAutostart() {
        return autostart;
    }

    public void setAutostart(boolean autostart) {
        this.autostart = autostart;
    }

    public int getRegionId() {
        return regionId;
    }

    public void setRegionId(int regionId) {
        this.regionId = regionId;
    }

    public int getRequiredQuestId() {
        return requiredQuestId;
    }

    public void setRequiredQuestId(int requiredQuestId) {
        this.requiredQuestId = requiredQuestId;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getRequirementType() {
        return requirementType;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public String getRequirementTypeText() {
        switch (requirementType) {
            case 0:
                return "Input an Answer";

            case 1:
                return "Item in inventory";

            case 2:
                return  "Completed Quest";

            case 3:
                return  "Having value of Attribute";

            case 4:
                return  "Be in specific region";

            default:
                return "Uknown type";
        }
    }

    public void setRequirementType(int requirementType) {
        this.requirementType = requirementType;
    }

    public String getRequirement() {
        return requirement;
    }

    public void setRequirement(String requirement) {
        this.requirement = requirement;
    }

    @Override
    public String toString() {
        return getName();
    }

}
