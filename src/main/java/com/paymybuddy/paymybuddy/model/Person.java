package com.paymybuddy.paymybuddy.model;



import org.hibernate.annotations.DynamicUpdate;

import java.util.HashSet;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.transaction.Transactional;

@Entity
@DynamicUpdate
@Transactional
@Table(name = "person")
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private int personId;

    @Column(name = "FIRSTNAME", nullable = false, length=100)
    private String firstName;

    @Column(name = "LASTNAME", nullable = false, length=100)
    private String lastName;

    @Column(name = "EMAIL", unique = true, nullable = false, length=100)
    private String email;

    @Column(name = "PASSWORD", nullable = false, length=100)
    private String password;

    @Column(name = "IBAN", nullable = false, length=34)
    private String iban;

    @Column(name = "AMOUNT_AVAILABLE",nullable = false, precision = 8, scale=2)
    private double amountAvailable;

    @OneToMany(
            mappedBy = "sender",
            cascade = CascadeType.PERSIST
                            )
    private Set<Transaction> transactions = new HashSet<>();

    /*@OneToMany(
            mappedBy = "recipient",
            cascade = CascadeType.PERSIST
    )
    private Set<Transaction> transactionsRecipient = new HashSet<>();*/

    @ManyToMany(
          fetch = FetchType.LAZY,

          cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
                    }
    )
    @JoinTable(
            name = "person_connection",
            joinColumns = @JoinColumn(name = "PERSON_ID", referencedColumnName = "ID", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "CONNECTION_ID", referencedColumnName = "ID", nullable = false)
    )
    private Set<Person> connections = new HashSet<>();

    @ManyToMany(
            fetch = FetchType.LAZY,

            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            }
    )
    @JoinTable(
            name = "person_connection",
            joinColumns = @JoinColumn(name = "CONNECTION_ID", referencedColumnName = "ID", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "PERSON_ID", referencedColumnName = "ID", nullable = false)
    )
    private Set<Person> connectionsOf = new HashSet<>();

   /* public Person(){

    }

    public Person(int personId, String firstName, String lastName, String email, String password, String iban, double amountAvailable) {
        this.personId = personId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.iban = iban;
        this.amountAvailable = amountAvailable;
    }

    public Person(int personId, String firstName, String lastName, String email,
                  String password, String iban, double amountAvailable,
                  Set<Person> connections) {
        this.personId = personId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.iban = iban;
        this.amountAvailable = amountAvailable;
        this.connections = connections;
    }*/

    public int getPersonId() {
        return personId;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
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

    public Set<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(Set<Transaction> transactions) {
        this.transactions = transactions;
    }

    public Set<Person> getConnections() {
        return connections;
    }

    public void setConnections(Set<Person> connections) {
        this.connections = connections;
    }

    public Set<Person> getConnectionsOf() {
        return connectionsOf;
    }

    public void setConnectionsOf(Set<Person> connectionsOf) {
        this.connectionsOf = connectionsOf;
    }
}
