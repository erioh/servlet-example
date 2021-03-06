package com.sdemenkov.http.servlet.example.server.entity;

import com.sdemenkov.http.servlet.example.server.dao.mapper.TimestampToLocalDateTime;

import java.time.LocalDateTime;
import java.util.Objects;

public class User {
    private Integer id;
    private String firstName;
    private String lastName;
    private Integer age;
    private String gender;
    @TimestampToLocalDateTime
    private LocalDateTime registeredAt;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDateTime getRegisteredAt() {
        return registeredAt;
    }

    public void setRegisteredAt(LocalDateTime registeredAt) {
        this.registeredAt = registeredAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) &&
                Objects.equals(firstName, user.firstName) &&
                Objects.equals(lastName, user.lastName) &&
                Objects.equals(age, user.age) &&
                Objects.equals(gender, user.gender) &&
                Objects.equals(registeredAt, user.registeredAt);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, firstName, lastName, age, gender, registeredAt);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("User{");
        sb.append("id=").append(id);
        sb.append(", firstName='").append(firstName).append('\'');
        sb.append(", lastName='").append(lastName).append('\'');
        sb.append(", age=").append(age);
        sb.append(", gender='").append(gender).append('\'');
        sb.append(", registeredAt=").append(registeredAt);
        sb.append('}');
        return sb.toString();
    }
}
