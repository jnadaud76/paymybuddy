package com.paymybuddy.paymybuddy.model;

import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@DynamicUpdate
@Table(name = "transaction")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private int transactionId;

    @Column(name = "DATE", nullable = false)
    private LocalDateTime date;

    @ManyToOne
    @JoinColumn (name = "recipient", referencedColumnName = "ID", nullable = false)
    private Person recipient;

    @ManyToOne
    @JoinColumn(name = "sender", referencedColumnName = "ID", nullable = false)
    private Person sender;

    @Column(name = "AMOUNT", nullable = false)
    private int amount;

    @Column(name = "DESCRIPTION" , length=100)
    private String description;

    @Column(name = "FEE_AMOUNT", nullable = false, precision = 5, scale=2)
    private double feeAmount;

    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public Person getRecipient() {
        return recipient;
    }


    public void setRecipient(Person recipient) {
        this.recipient = recipient;
    }

    public Person getSender() {
        return sender;
    }

    public void setSender(Person sender) {
        this.sender = sender;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getFeeAmount(double feeAmount) {
        return feeAmount;
    }

    public void setFeeAmount(double feeAmount) {
        this.feeAmount = feeAmount;
    }
}
