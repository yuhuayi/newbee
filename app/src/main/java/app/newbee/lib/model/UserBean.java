package app.newbee.lib.model;

import java.io.Serializable;

public class UserBean implements Serializable {
    private String uid;

    private String nickname;

    private String email;

    private String mobile;

    private String qq;

    private String password;

    private String avatar;

    private String sex;

    private String birthday;

    private String province;

    private String city;

    private String level;

    private String money;

    private String status;

    private String recommend;

    private String android_tokey;

    private String ios_tokey;

    private String reg_time;
    private String name;
    private String wechat;
    private String vip_level;

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUid() {
        return this.uid;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getNickname() {
        return this.nickname;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return this.email;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getMobile() {
        return this.mobile;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getQq() {
        return this.qq;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return this.password;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getAvatar() {
        return this.avatar;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getSex() {
        return this.sex;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getBirthday() {
        return this.birthday;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getProvince() {
        return this.province;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCity() {
        return this.city;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getLevel() {
        return this.level;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getMoney() {
        return this.money;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return this.status;
    }

    public void setRecommend(String recommend) {
        this.recommend = recommend;
    }

    public String getRecommend() {
        return this.recommend;
    }

    public void setAndroid_tokey(String android_tokey) {
        this.android_tokey = android_tokey;
    }

    public String getAndroid_tokey() {
        return this.android_tokey;
    }

    public void setIos_tokey(String ios_tokey) {
        this.ios_tokey = ios_tokey;
    }

    public String getIos_tokey() {
        return this.ios_tokey;
    }

    public void setReg_time(String reg_time) {
        this.reg_time = reg_time;
    }

    public String getReg_time() {
        return this.reg_time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWechat() {
        return wechat;
    }

    public void setWechat(String wechat) {
        this.wechat = wechat;
    }

    public String getVip_level() {
        return vip_level;
    }

    public void setVip_level(String vip_level) {
        this.vip_level = vip_level;
    }

    @Override
    public String toString() {
        return "UserBean{" +
                "uid='" + uid + '\'' +
                ", nickname='" + nickname + '\'' +
                ", email='" + email + '\'' +
                ", mobile='" + mobile + '\'' +
                ", qq='" + qq + '\'' +
                ", password='" + password + '\'' +
                ", avatar='" + avatar + '\'' +
                ", sex='" + sex + '\'' +
                ", birthday='" + birthday + '\'' +
                ", province='" + province + '\'' +
                ", city='" + city + '\'' +
                ", level='" + level + '\'' +
                ", money='" + money + '\'' +
                ", status='" + status + '\'' +
                ", recommend='" + recommend + '\'' +
                ", android_tokey='" + android_tokey + '\'' +
                ", ios_tokey='" + ios_tokey + '\'' +
                ", reg_time='" + reg_time + '\'' +
                ", name='" + name + '\'' +
                ", wechat='" + wechat + '\'' +
                ", vip_level='" + vip_level + '\'' +
                '}';
    }
}
