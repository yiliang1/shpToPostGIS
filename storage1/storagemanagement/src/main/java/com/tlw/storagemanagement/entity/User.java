package com.tlw.storagemanagement.entity;

public class User {
    public static final  Integer STATE_ACCOUNTEXPIRED = 1;
    public static final  Integer STATE_LOCK = 1;
    public static final  Integer STATE_TOKENEXPIRED = 1;
    public static final  Integer STATE_NORMAL = 1;

    private Integer id;
    private String userName;
    private String passWord;
    private String realName;
    private Integer state;
    private String gender;

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    private String birth;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }
    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", passWord='" + passWord + '\'' +
                ", realName='" + realName + '\'' +
                ", state=" + state +
                ", gender='" + gender + '\'' +
                ", birth='" + birth + '\'' +
                '}';
    }
}
