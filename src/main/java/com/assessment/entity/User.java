package com.assessment.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="Users")
public class User {

    @Id
    private Long id;
    private String firstName;
    private String lastName;
    private Integer age;
    private String gender;
    private String email;
    private String phone;
    private String username;
    private String password;
    private String birthDate;
    private String image;
    private String bloodGroup;
    private Double height;
    private Double weight;
    private String eyeColor;
    @Embedded
    private Hair hair;
    private String ip;
    @Embedded
    @AttributeOverride(name = "address", column = @Column(name = "user_address"))
    @AttributeOverride(name = "city", column = @Column(name = "user_city"))
    @AttributeOverride(name = "postalCode", column = @Column(name = "user_postal_code"))
    @AttributeOverride(name = "state", column = @Column(name = "user_state"))
    @AttributeOverride(name = "coordinates.lat", column = @Column(name = "user_lat"))
    @AttributeOverride(name = "coordinates.lng", column = @Column(name = "user_lng"))
    @AttributeOverride(name = "country", column = @Column(name = "user_country"))
    private Address address;
    private String macAddress;
    private String university;
    @Embedded
    private Bank bank;
    @Embedded
    private Company company;
    private String ein;
    private String ssn;
    private String userAgent;
    @Embedded
    private Crypto crypto;
    private String role;

    @Getter
    @Setter
    @Embeddable
    public static class Hair {
        private String color;
        private String type;
    }

    @Getter
    @Setter
    @Embeddable
    public static class Address {
        @Column(name = "user_address", insertable=false, updatable=false)
        private String address;
        @Column(name = "user_city", insertable=false, updatable=false)
        private String city;
        @Embedded
        private Coordinates coordinates;
        @Column(name = "user_postal_code", insertable=false, updatable=false)
        private String postalCode;
        @Column(name = "user_state", insertable=false, updatable=false)
        private String state;
        @Column(name = "user_country", insertable=false, updatable=false)
        private String country;
    }

    @Getter
    @Setter
    @Embeddable
    public static class Coordinates {
        @Column(name = "user_lat", insertable=false, updatable=false)
        private Double lat;
        @Column(name = "user_lng", insertable=false, updatable=false)
        private Double lng;
    }

    @Getter
    @Setter
    @Embeddable
    public static class Bank {
        private String cardExpire;
        private String cardNumber;
        private String cardType;
        private String currency;
        private String iban;
    }

    @Getter
    @Setter
    @Embeddable
    public static class Company {
        private String department;
        private String name;
        private String title;
        @Embedded
        private Address address;
    }

    @Getter
    @Setter
    @Embeddable
    public static class Crypto {
        private String coin;
        private String wallet;
        private String network;
    }
}
