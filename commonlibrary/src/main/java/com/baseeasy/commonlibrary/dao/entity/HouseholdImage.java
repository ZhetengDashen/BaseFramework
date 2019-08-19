package com.baseeasy.commonlibrary.dao.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

/**
 * @author：MagicLon
 * @date：2019/8/13 013
 * email：1348149485@qq.com
 * detail：
 */
@Entity
public class HouseholdImage {
    @Id
    String id;
    String family_id;
    String member_id;
    String ff_id;
    String state;
    String ismember;
    String path;
    String timestamp;
    @Generated(hash = 1492469443)
    public HouseholdImage(String id, String family_id, String member_id,
                          String ff_id, String state, String ismember, String path,
                          String timestamp) {
        this.id = id;
        this.family_id = family_id;
        this.member_id = member_id;
        this.ff_id = ff_id;
        this.state = state;
        this.ismember = ismember;
        this.path = path;
        this.timestamp = timestamp;
    }
    @Generated(hash = 973612418)
    public HouseholdImage() {
    }
    public String getId() {
        return this.id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getFamily_id() {
        return this.family_id;
    }
    public void setFamily_id(String family_id) {
        this.family_id = family_id;
    }
    public String getMember_id() {
        return this.member_id;
    }
    public void setMember_id(String member_id) {
        this.member_id = member_id;
    }
    public String getFf_id() {
        return this.ff_id;
    }
    public void setFf_id(String ff_id) {
        this.ff_id = ff_id;
    }
    public String getState() {
        return this.state;
    }
    public void setState(String state) {
        this.state = state;
    }
    public String getIsmember() {
        return this.ismember;
    }
    public void setIsmember(String ismember) {
        this.ismember = ismember;
    }
    public String getPath() {
        return this.path;
    }
    public void setPath(String path) {
        this.path = path;
    }
    public String getTimestamp() {
        return this.timestamp;
    }
    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
   
}
