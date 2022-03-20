package com.paymybuddy.paymybuddy.dto;

public class TransactionFullDto {

    private int transactionFullDtoId;


    private int recipient;


    private int sender;


    private int amount;


    private String description;


   public int getTransactionFullDtoId() {
        return transactionFullDtoId;
    }

    public void setTransactionFullDtoId(int transactionFullDtoId) {
        this.transactionFullDtoId = transactionFullDtoId;
    }

    public int getRecipient() {
        return recipient;
    }

    public void setRecipient(int recipient) {
        this.recipient = recipient;
    }

    public int getSender() {
        return sender;
    }

    public void setSender(int sender) {
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


}
