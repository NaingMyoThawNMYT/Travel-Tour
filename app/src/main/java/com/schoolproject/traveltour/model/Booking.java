package com.schoolproject.traveltour.model;

import java.io.Serializable;
import java.util.Map;

public class Booking implements Serializable {
    private String id;
    private String bookingDate;
    private String tourId;
    private String tourCountry;
    private String tourType;
    private String packageName;
    private String packagePrice;
    private String username;
    private String passportNo;
    private String phone;
    private String email;
    private String address;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(String bookingDate) {
        this.bookingDate = bookingDate;
    }

    public String getTourId() {
        return tourId;
    }

    public void setTourId(String tourId) {
        this.tourId = tourId;
    }

    public String getTourCountry() {
        return tourCountry;
    }

    public void setTourCountry(String tourCountry) {
        this.tourCountry = tourCountry;
    }

    public String getTourType() {
        return tourType;
    }

    public void setTourType(String tourType) {
        this.tourType = tourType;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getPackagePrice() {
        return packagePrice;
    }

    public void setPackagePrice(String packagePrice) {
        this.packagePrice = packagePrice;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassportNo() {
        return passportNo;
    }

    public void setPassportNo(String passportNo) {
        this.passportNo = passportNo;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void parse(Map<String, Object> map) {
        id = (String) map.get("id");
        bookingDate = (String) map.get("bookingDate");
        tourId = (String) map.get("tourId");
        tourCountry = (String) map.get("tourCountry");
        tourType = (String) map.get("tourType");
        packageName = (String) map.get("packageName");
        packagePrice = (String) map.get("packagePrice");
        username = (String) map.get("username");
        passportNo = (String) map.get("passportNo");
        phone = (String) map.get("phone");
        email = (String) map.get("email");
        address = (String) map.get("address");
    }
}
