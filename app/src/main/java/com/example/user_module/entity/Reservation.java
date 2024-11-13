package com.example.user_module.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Reservation {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private int accommodationId;
    private int userId;
    private String startDate;
    private String endDate;
    private boolean hasBreakfast;
    private boolean hasTransfer;
    private String firstName; // Added field for first name
    private String lastName; // Added field for last name
    private String email; // Added field for email
    private String phone; // Added field for phone
    private String occupantFirstName; // New field for occupant's first name
    private String occupantLastName; // New field for occupant's last name
    private String message; // New field for message
    private String title; // New field for title
    private boolean lateArrival; // New field for late arrival
    private boolean sideBySide; // New field for side by side
    private boolean kingBed; // New field for king bed
    private boolean paymentOnline; // New field for payment option
    private boolean payment_agency;
    private boolean termsAccepted; // New field for terms acceptance


    private boolean mr ;
    private boolean  mm;
    private boolean mlle;
    // Getters


    public boolean isPaymentAgency() {
        return payment_agency;
    }

    public void setPaymentAgency(boolean payment_agency) {
        this.payment_agency = payment_agency;
    }

    public boolean isMr() {
        return mr;
    }

    public void setMr(boolean mr) {
        this.mr = mr;
    }

    public boolean isMm() {
        return mm;
    }

    public void setMm(boolean mm) {
        this.mm = mm;
    }

    public boolean isMlle() {
        return mlle;
    }

    public void setMlle(boolean mlle) {
        this.mlle = mlle;
    }
    public int getId() {
        return id;
    }

    public int getAccommodationId() {
        return accommodationId;
    }

    public int getUserId() {
        return userId;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public boolean hasBreakfast() {
        return hasBreakfast;
    }

    public boolean hasTransfer() {
        return hasTransfer;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getOccupantFirstName() {
        return occupantFirstName;
    }

    public String getOccupantLastName() {
        return occupantLastName;
    }

    public String getMessage() {
        return message;
    }

    public String getTitle() {
        return title;
    }

    public boolean isLateArrival() {
        return lateArrival;
    }

    public boolean isSideBySide() {
        return sideBySide;
    }

    public boolean isKingBed() {
        return kingBed;
    }

    public boolean isPaymentOnline() {
        return paymentOnline;
    }
    public boolean isPayment_agency() {
        return payment_agency;
    }

    public boolean isTermsAccepted() {
        return termsAccepted;
    }
    // New fields
    private String price;
    private String roomDescription;
    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setAccommodationId(int accommodationId) {
        this.accommodationId = accommodationId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public void setHasBreakfast(boolean hasBreakfast) {
        this.hasBreakfast = hasBreakfast;
    }

    public void setHasTransfer(boolean hasTransfer) {
        this.hasTransfer = hasTransfer;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setOccupantFirstName(String occupantFirstName) {
        this.occupantFirstName = occupantFirstName;
    }

    public void setOccupantLastName(String occupantLastName) {
        this.occupantLastName = occupantLastName;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setLateArrival(boolean lateArrival) {
        this.lateArrival = lateArrival;
    }

    public void setSideBySide(boolean sideBySide) {
        this.sideBySide = sideBySide;
    }

    public void setKingBed(boolean kingBed) {
        this.kingBed = kingBed;
    }

    public void setPaymentOnline(boolean paymentOnline) {
        this.paymentOnline = paymentOnline;
    }

    public void setTermsAccepted(boolean termsAccepted) {
        this.termsAccepted = termsAccepted;
    }
    // Getters and setters for new fields
    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getRoomDescription() {
        return roomDescription;
    }

    public void setRoomDescription(String roomDescription) {
        this.roomDescription = roomDescription;
    }
    // toString method
    @Override
    public String toString() {
        return "Reservation{" +
                "id=" + id +
                ", accommodationId=" + accommodationId +
                ", userId=" + userId +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", hasBreakfast=" + hasBreakfast +
                ", hasTransfer=" + hasTransfer +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", occupantFirstName='" + occupantFirstName + '\'' +
                ", occupantLastName='" + occupantLastName + '\'' +
                ", message='" + message + '\'' +
                ", title='" + title + '\'' +
                ", lateArrival=" + lateArrival +
                ", sideBySide=" + sideBySide +
                ", kingBed=" + kingBed +
                ", paymentOnline=" + paymentOnline +
                ", termsAccepted=" + termsAccepted +
                ", price=" + price +
                ", roomDescription='" + roomDescription + '\'' +
                '}';
    }


    // Constructor with all fields
    public Reservation(String firstName, String lastName, String email, String phone,
                       String occupantFirstName, String occupantLastName, String message,
                       boolean lateArrival, boolean sideBySide, boolean kingBed,
                       boolean paymentOnline, boolean termsAccepted, boolean payment_agency,
                       boolean mr, boolean mm, boolean mlle, String price, String roomDescription) {

        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.occupantFirstName = occupantFirstName;
        this.occupantLastName = occupantLastName;
        this.message = message;
        this.lateArrival = lateArrival;
        this.sideBySide = sideBySide;
        this.kingBed = kingBed;
        this.paymentOnline = paymentOnline;
        this.termsAccepted = termsAccepted;
        this.payment_agency = payment_agency;
        this.mr = mr;
        this.mm = mm;
        this.mlle = mlle;

        // Initialize the new fields
        this.price = price;
        this.roomDescription = roomDescription;
    }

}
