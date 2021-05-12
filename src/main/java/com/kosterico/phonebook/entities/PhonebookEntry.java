package com.kosterico.phonebook.entities;

import javax.persistence.*;
import java.util.Map;
import java.util.Objects;

@Entity
public class PhonebookEntry implements Comparable {

    private @Id
    @GeneratedValue
    Long id;

    private Long userId;

    private String phoneNumber;

    public PhonebookEntry(Long id, String phoneNumber, Long userId) {
        this.id = id;
        this.userId = userId;
        this.phoneNumber = phoneNumber;
    }

    public PhonebookEntry(String phoneNumber, Long userId) {
        this.phoneNumber = phoneNumber;
        this.userId = userId;
    }

    public PhonebookEntry() {
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Phonebook{" +
                "id=" + id +
                ", userId=" + userId +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PhonebookEntry that = (PhonebookEntry) o;

        return id.equals(that.id)
                && userId.equals(that.userId)
                && phoneNumber.equals(that.phoneNumber);
    }

    @Override
    public int compareTo(Object o) {
        PhonebookEntry entry = (PhonebookEntry) o;

        return (int) Math.signum(id - entry.getId());
    }
}
