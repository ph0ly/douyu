package model;

/**
 * @author ph0ly
 * @time 2016-09-16
 */
public class Room {

    private String id;
    private String name;
    private String cateId;
    private String cateName;
    private Integer status;
    private String ownerName;
    private Integer online;
    private String avatar;
    private Integer fansCount;
    private String ownerWeight;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCateId() {
        return cateId;
    }

    public void setCateId(String cateId) {
        this.cateId = cateId;
    }

    public String getCateName() {
        return cateName;
    }

    public void setCateName(String cateName) {
        this.cateName = cateName;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public Integer getOnline() {
        return online;
    }

    public void setOnline(Integer online) {
        this.online = online;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Integer getFansCount() {
        return fansCount;
    }

    public void setFansCount(Integer fansCount) {
        this.fansCount = fansCount;
    }

    public String getOwnerWeight() {
        return ownerWeight;
    }

    public void setOwnerWeight(String ownerWeight) {
        this.ownerWeight = ownerWeight;
    }
}
