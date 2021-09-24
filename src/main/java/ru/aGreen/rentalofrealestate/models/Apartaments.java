package ru.aGreen.rentalofrealestate.models;

import javax.persistence.*;

@Entity
public class Apartaments {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private double price;
    private double totalArea; //площадь
    private int floor; //этаж
    private int bedsNumber; //спальные места
    private String parking; //стоянка
    private String description;
    private String address;
    private String bathroom;
    private String photo;
    @OneToOne
    private District district;
    @OneToOne
    private HousingType housingType;
    @OneToOne
    private HousingClass housingClass;

    public Apartaments() {
    }

    public Apartaments(String title, double price, double totalArea, int floor, int bedsNumber, String parking, String description, String address, String bathroom, String photo, District district, HousingType housingType, HousingClass housingClass) {
        this.title = title;
        this.price = price;
        this.totalArea = totalArea;
        this.floor = floor;
        this.bedsNumber = bedsNumber;
        this.parking = parking;
        this.description = description;
        this.address = address;
        this.bathroom = bathroom;
        this.photo = photo;
        this.district = district;
        this.housingType = housingType;
        this.housingClass = housingClass;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getTotalArea() {
        return totalArea;
    }

    public void setTotalArea(double totalArea) {
        this.totalArea = totalArea;
    }

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    public int getBedsNumber() {
        return bedsNumber;
    }

    public void setBedsNumber(int bedsNumber) {
        this.bedsNumber = bedsNumber;
    }

    public String getParking() {
        return parking;
    }

    public void setParking(String parking) {
        this.parking = parking;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBathroom() {
        return bathroom;
    }

    public void setBathroom(String bathroom) {
        this.bathroom = bathroom;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public District getDistrict() {
        return district;
    }

    public void setDistrict(District district) {
        this.district = district;
    }

    public HousingType getHousingType() {
        return housingType;
    }

    public void setHousingType(HousingType housingType) {
        this.housingType = housingType;
    }

    public HousingClass getHousingClass() {
        return housingClass;
    }

    public void setHousingClass(HousingClass housingClass) {
        this.housingClass = housingClass;
    }
}
