package com.paymybuddy.paymybuddy.dto;

public class PersonFullDto {


    private int personFullDtoId;


    private String firstName;


    private String lastName;


    private String email;


    private String password;


    private String iban;


    private double amountAvailable;

    public int getPersonFullDtoId() {
        return personFullDtoId;
    }

    public void setPersonFullDtoId(int personFullDtoId) {
        this.personFullDtoId = personFullDtoId;
    }

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public double getAmountAvailable() {
        return amountAvailable;
    }

    public void setAmountAvailable(double amountAvailable) {
        this.amountAvailable = amountAvailable;
    }
}