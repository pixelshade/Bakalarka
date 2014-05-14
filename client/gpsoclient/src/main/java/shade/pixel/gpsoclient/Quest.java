package shade.pixel.gpsoclient;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by pixelshade on 2.3.2014.
 */
public class Quest implements Serializable {
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
    public static final String KEY_QUEST_TIME_ACCEPTED = "time_accepted";
    public static final String KEY_QUEST_COMPLETED = "completed";

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
    private long duration;
    private QuestRequirement requirementType;
    private String requirement;
    // optional, used with active user quest
    private boolean active;
    private boolean completed;
    private Date timeAccepted;

    public Quest(){
    }

    public Quest(int id, String code, String name, String info, String image, int rewardId, boolean autostart, int regionId, int requiredQuestId, long duration, int requirementType, String requirement, boolean active, boolean completed, Date timeAccepted) {
        this(id, code, name, info, image, rewardId, autostart, regionId, requiredQuestId, duration, requirementType, requirement);
        this.active = active;
        this.completed = completed;
        this.timeAccepted = timeAccepted;
    }

    public Quest(int id, String code, String name, String info, String image, int rewardId, boolean autostart, int regionId, int requiredQuestId, long duration, int requirementType, String requirement) {
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
        switch (requirementType) {
            case 0:
                this.requirementType = QuestRequirement.input_answer;
                break;
            case 1:
                this.requirementType = QuestRequirement.have_item_value;
                break;
            case 2:
                this.requirementType = QuestRequirement.complete_quest;
                break;
            case 3:
                this.requirementType = QuestRequirement.have_attribute_value;
                break;
            case 4:
                this.requirementType = QuestRequirement.be_in_region;
                break;
        }
        this.requirement = requirement;
        this.active = false;
        this.completed = false;
    }

   public void acceptNow(){
       this.setActive(true);
       this.setTimeAccepted(new Date());
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

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public QuestRequirement getRequirementType() {
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

    public void setRequirementType(QuestRequirement requirementType) {
        this.requirementType = requirementType;
    }

    public String getRequirement() {
        return requirement;
    }

    public void setRequirement(String requirement) {
        this.requirement = requirement;
    }


    public Date getTimeAccepted() {
        return timeAccepted;
    }

    public void setTimeAccepted(Date timeAccepted) {
        this.timeAccepted = timeAccepted;
    }

    @Override
    public String toString() {
        return getName();
    }

    public enum QuestRequirement {
        input_answer(0), have_item_value(1), complete_quest(2), have_attribute_value(3), be_in_region(4);
        private int value;

        private QuestRequirement(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        public int toInt() {
            return value;
        }

        public String toString() {
            switch (value) {
                case 0:
                    return "Input an Answer";

                case 1:
                    return "Item in inventory";

                case 2:
                    return "Completed Quest";

                case 3:
                    return "Having value of Attribute";

                case 4:
                    return "Be in specific region";

                default:
                    return "Uknown type";
            }
        }
    }

    ;


}
