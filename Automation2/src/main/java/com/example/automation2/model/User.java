package com.example.automation2.model;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/*** Created: 8/15/2021 --- 2:03 AM --- ALPHANULL ***/

@Component @Entity
public class User {

    public enum Gender {Male, Female, Other}

    @Transient private UUID uuid;
    @Id @Column(name = "uuid", length = 51, unique = true)
    private String uuidText;
    @Column(name = "username", length = 33)
    private String userName;
    @Column(name = "useremail", length = 33)
    private String userEmail;
    private Integer userAge;
    @Transient private Gender userGender;
    @Transient private Boolean isActive;
    @Column(name = "isactive", length = 1)
    private Integer isActiveInt;
    @Column(name = "usergender", length = 9)
    private String userGenderText;



    public User() { }
    public User(@NonNull UUID uuid, String userName, String userEmail,
                Integer userAge, @NonNull Gender userGender, Boolean isActive) {
        this.uuid = uuid;
        this.userName = userName;
        this.userEmail = userEmail;
        this.userAge = userAge;
        this.userGender = userGender;
        this.isActive = isActive;
        this.userGenderText = userGender.toString();
        this.uuidText = uuid.toString();
        this.isActiveInt = (isActive) ? 1 : 0;
    }
    public User(@NonNull String uuid, String userName, String userEmail,
                Integer userAge, @NonNull String userGender, Integer isActive) {
        this.uuidText = uuid;
        this.userName = userName;
        this.userEmail = userEmail;
        this.userAge = userAge;
        this.userGenderText = userGender;
        this.isActiveInt = isActive;
        this.userGender = Gender.valueOf(userGender);
        this.uuid = UUID.fromString(uuid);
        this.isActive = isActive == 1;
    }

    @NonNull
    public static List<String> getGenderList() {
        List<String> list = new ArrayList<>();
        for(Gender gender : Gender.values()) list.add(gender.toString());
        return list;
    }

    @NonNull
    public static List<Integer> getActiveList() {
        List<Integer> list = new ArrayList<>();
        list.add(0);list.add(1);
        return list;
    }

    public UUID getUuid() {
        if(uuid == null) return UUID.randomUUID();
        return uuid;
    }
    public void setUuid(@NonNull UUID uuid) {
        this.uuid = uuid;
        this.uuidText = uuid.toString();
    }

    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }

    public String getUserEmail() { return userEmail; }
    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public Integer getUserAge() { return userAge; }
    public void setUserAge(Integer userAge) {
        this.userAge = userAge;
    }

    public Gender getUserGender() { return userGender; }
    public void setUserGender(@NonNull Gender userGender) {
        this.userGender = userGender;
        this.userGenderText = userGender.toString();
    }

    public Boolean getActive() { return isActive; }
    public void setActive(Boolean active) {
        isActive = active;
        this.isActiveInt = (isActive) ? 1 : 0;
    }

    public String getUuidText() { return uuidText; }
    public void setUuidText(String uuidText) {
        this.uuidText = uuidText;
        this.uuid = UUID.fromString(uuidText);
    }

    public String getUserGenderText() { return userGenderText; }
    public void setUserGenderText(String userGenderText) {
        this.userGenderText = userGenderText;
        this.userGender = Gender.valueOf(userGenderText);
    }

    public Integer getIsActiveInt() { return isActiveInt; }
    public void setIsActiveInt(Integer isActiveInt) {
        this.isActiveInt = isActiveInt;
        this.isActive = isActiveInt == 1;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return uuid.equals(user.uuid) && Objects.equals(userName, user.userName)
                && Objects.equals(userEmail, user.userEmail) && Objects.equals(userAge, user.userAge)
                && userGender == user.userGender && Objects.equals(isActive, user.isActive);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, userName, userEmail, userAge, userGender, isActive);
    }

    @Override
    public String toString() {
        return "User{" +
                "uuid=" + uuid +
                ", userName='" + userName + '\'' +
                ", userEmail='" + userEmail + '\'' +
                ", userAge=" + userAge +
                ", userGender=" + userGender +
                ", isActive=" + isActive +
                '}';
    }
}


