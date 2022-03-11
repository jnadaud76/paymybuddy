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
    @Column(name = "DATE")
    private LocalDateTime date;
    @ManyToOne
    @JoinColumn (name = "RECIPIEND")
    private Person recipiend;
    @ManyToOne
    @JoinColumn(name = "SENDER")
    private Person sender;
    @Column(name = "AMOUNT")
    private int amount;
    @Column(name = "DESCRIPTION")
    private String description;
    @Column(name = "FEE_AMOUNT")
    private double feeAmount;



}
