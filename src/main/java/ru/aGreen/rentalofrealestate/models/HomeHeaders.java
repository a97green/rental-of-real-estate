package ru.aGreen.rentalofrealestate.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class HomeHeaders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String subtitle;
    private String descriptions;
    private String portfolioSubtitle;
    private String portfolioDescriptions;
    private String advantageDescriptions;
    private String address;
    private String email;
    private String phoneNumber;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getDescriptions() {
        return descriptions;
    }

    public void setDescriptions(String descriptions) {
        this.descriptions = descriptions;
    }

    public String getPortfolioSubtitle() {
        return portfolioSubtitle;
    }

    public void setPortfolioSubtitle(String portfolioSubtitle) {
        this.portfolioSubtitle = portfolioSubtitle;
    }

    public String getPortfolioDescriptions() {
        return portfolioDescriptions;
    }

    public void setPortfolioDescriptions(String portfolioDescriptions) {
        this.portfolioDescriptions = portfolioDescriptions;
    }

    public String getAdvantageDescriptions() {
        return advantageDescriptions;
    }

    public void setAdvantageDescriptions(String advantageDescriptions) {
        this.advantageDescriptions = advantageDescriptions;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
