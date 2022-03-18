package com.paymybuddy.paymybuddy.dto;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PersonConnectionDto that = (PersonConnectionDto) o;
        return personConnectionDtoId == that.personConnectionDtoId && Double.compare(that.amountAvailable, amountAvailable) == 0 && firstName.equals(that.firstName) && lastName.equals(that.lastName) && email.equals(that.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(personConnectionDtoId, firstName, lastName, email, amountAvailable);
    }
}
