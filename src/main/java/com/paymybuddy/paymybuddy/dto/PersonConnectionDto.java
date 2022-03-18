package com.paymybuddy.paymybuddy.dto;

public class PersonConnectionDto {

    private int personConnectionDtoId;


    private String firstName;


    private String lastName;


    private String email;


    private double amountAvailable;

    public int getPersonConnectionDtoId() {
        return personConnectionDtoId;
    }

    public void setPersonConnectionDtoId(int personConnectionDtoId) {
        this.personConnectionDtoId = personConnectionDtoId;
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

    public double getAmountAvailable() {
        return amountAvailable;
    }

    public void setAmountAvailable(double amountAvailable) {
        this.amountAvailable = amountAvailable;
    }
}
