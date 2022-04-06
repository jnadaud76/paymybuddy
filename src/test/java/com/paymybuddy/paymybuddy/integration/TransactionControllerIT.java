package com.paymybuddy.paymybuddy.integration;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.paymybuddy.paymybuddy.dto.TransactionFullDto;


import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser
class TransactionControllerIT {

    private static final ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    private MockMvc mockMvc;

    @Test
    void TestGetTransactions() throws Exception {
        mockMvc.perform(get("/api/transactions"))
                .andExpect(status().isOk());
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3})
    void TestGetTransactionById(int ints) throws Exception {
             mockMvc.perform(get("/api/transaction")
                        .queryParam("transactionId", String.valueOf(ints)))
                .andExpect(status().isOk());
    }

    @Test
    void TestGetTransactionBySender() throws Exception {
              mockMvc.perform(get("/api/transactions/sender")
                        .queryParam("senderId", "1"))
                .andExpect(status().isOk());
    }

    @ParameterizedTest
    @ValueSource(ints = {4, 32, 72})
    void TestGetTransactionBySenderWithBadArguments(int ints) throws Exception {

        mockMvc.perform(get("/api/transactions/sender")
                        .queryParam("senderId", String.valueOf(ints)))
                .andExpect(status().isNotFound());
    }




    @ParameterizedTest
    @ValueSource(ints= {18, 24, 39})
    void TestGetTransactionByIdWithBad(int ints) throws Exception {

        mockMvc.perform(get("/api/transaction")
                        .queryParam("transactionId", String.valueOf(ints)))
                .andExpect(status().isNotFound());
    }

    @Test
    void TestCreateTransaction() throws Exception {
        TransactionFullDto transactionFullDto = new TransactionFullDto();
        transactionFullDto.setSender(1);
        transactionFullDto.setRecipient(2);
        transactionFullDto.setDescription("Transaction5");
        transactionFullDto.setAmount(100);
        String transactionAsString = objectMapper.writeValueAsString(transactionFullDto);

        mockMvc.perform(post("/api/transaction")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(transactionAsString))
                .andExpect(status().isCreated());
    }


    @RepeatedTest(5)
    void TestCreateTransactionFiveTimes() throws Exception {
        TransactionFullDto transactionFullDto = new TransactionFullDto();
        transactionFullDto.setSender(1);
        transactionFullDto.setRecipient(2);
        transactionFullDto.setDescription("Transaction5");
        transactionFullDto.setAmount(100);
        String transactionAsString = objectMapper.writeValueAsString(transactionFullDto);


        mockMvc.perform(post("/api/transaction")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(transactionAsString))
                .andExpect(status().isCreated());
    }

    @ParameterizedTest
    @CsvSource(value={"1|89","2|31","58|327"}, delimiter='|')
    void TestCreateTransactionWithBadArgument(int a, int b) throws Exception {
        TransactionFullDto transactionFullDto = new TransactionFullDto();
        transactionFullDto.setSender(a);
        transactionFullDto.setRecipient(b);
        transactionFullDto.setDescription("Transaction5");
        transactionFullDto.setAmount(100);
        String transactionAsString = objectMapper.writeValueAsString(transactionFullDto);

        mockMvc.perform(post("/api/transaction")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(transactionAsString))
                        .andExpect(status().isBadRequest());
    }

    @Test
    void TestDeleteTransaction() throws Exception {

        mockMvc.perform(delete("/api/transaction")
                        .queryParam("transactionId", "1"))
                .andExpect(status().isOk());
    }

    @ParameterizedTest
    @ValueSource(ints = {24, 32, 99})
    void TestDeleteTransactionWithBadId(int ints) throws Exception {

        mockMvc.perform(delete("/api/transaction")
                        .queryParam("transactionId", String.valueOf(ints)))
                .andExpect(status().isBadRequest());

    }
}
