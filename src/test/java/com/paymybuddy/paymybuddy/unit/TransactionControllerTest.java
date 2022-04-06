package com.paymybuddy.paymybuddy.unit;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.paymybuddy.paymybuddy.controller.PersonController;
import com.paymybuddy.paymybuddy.controller.TransactionController;
import com.paymybuddy.paymybuddy.dto.PersonFullDto;
import com.paymybuddy.paymybuddy.dto.TransactionFullDto;
import com.paymybuddy.paymybuddy.dto.TransactionLightDto;
import com.paymybuddy.paymybuddy.service.IPersonService;
import com.paymybuddy.paymybuddy.service.ITransactionService;
import com.paymybuddy.paymybuddy.service.MyUserDetailsService;
import com.paymybuddy.paymybuddy.util.IConversion;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashSet;
import java.util.Set;

@WithMockUser
@WebMvcTest(controllers = TransactionController.class)
public class TransactionControllerTest {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private MyUserDetailsService userDetailsService;
    @MockBean
    private ITransactionService transactionService;
    /*@MockBean
    private IConversion conversionService;*/

    @Test
    void TestGetTransactions() throws Exception {
        mockMvc.perform(get("/api/transactions"))
                .andExpect(status().isOk());
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3})
    void TestGetTransactionById(int ints) throws Exception {
        TransactionFullDto transactionFullDto = new TransactionFullDto();
        transactionFullDto.setId(ints);
        when(transactionService.getTransactionById(ints)).thenReturn(transactionFullDto);
        mockMvc.perform(get("/api/transaction")
                        .queryParam("transactionId", String.valueOf(ints)))
                              .andExpect(status().isOk());
    }

   @Test
    void TestGetTransactionBySender() throws Exception {
        Set<TransactionLightDto> transactionLightDtoSet = new HashSet<>();
        TransactionLightDto transactionLightDto = new TransactionLightDto();
        transactionLightDto.setId(1);
        transactionLightDtoSet.add(transactionLightDto);
        when(transactionService.getTransactionsBySender(1)).thenReturn(transactionLightDtoSet);
        mockMvc.perform(get("/api/transactions/sender")
                        .queryParam("senderId", "1"))
                .andExpect(status().isOk());
    }

    @ParameterizedTest
    @ValueSource(ints = {4, 32, 72})
    void TestGetTransactionBySenderWithBadArguments(int ints) throws Exception {
        Set<TransactionLightDto> transactionLightDtoSet = new HashSet<>();
        when(transactionService.getTransactionsBySender(1)).thenReturn(transactionLightDtoSet);
        mockMvc.perform(get("/api/transactions/sender")
                        .queryParam("senderId", String.valueOf(ints)))
                .andExpect(status().isNotFound());
    }




    @ParameterizedTest
    @ValueSource(ints= {18, 24, 39})
    void TestGetTransactionByIdWithBad(int ints) throws Exception {
        TransactionFullDto transactionFullDto = new TransactionFullDto();
        transactionFullDto.setId(ints);
        when(transactionService.getTransactionById(ints)).thenReturn(null);
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
        when(transactionService.addTransaction(transactionFullDto)).thenReturn(transactionFullDto);

        mockMvc.perform(post("/api/transaction")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(transactionAsString))
                .andExpect(status().isCreated());
    }

    @Test
    @RepeatedTest(5)
    void TestCreateTransactionFiveTimes() throws Exception {
        TransactionFullDto transactionFullDto = new TransactionFullDto();
        transactionFullDto.setSender(1);
        transactionFullDto.setRecipient(2);
        transactionFullDto.setDescription("Transaction5");
        transactionFullDto.setAmount(100);
        String transactionAsString = objectMapper.writeValueAsString(transactionFullDto);
        when(transactionService.addTransaction(transactionFullDto)).thenReturn(transactionFullDto);

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
        //String transactionAsString = objectMapper.writeValueAsString(transactionFullDto);
        doThrow(new IllegalArgumentException()).when(transactionService).addTransaction(transactionFullDto);

        mockMvc.perform(post("/api/transaction")
                        .contentType(MediaType.APPLICATION_JSON))
                       // .content(transactionAsString))
                .andExpect(status().isBadRequest());
    }

    @Test
    void TestDeleteTransaction() throws Exception {
        transactionService.deleteTransactionById(1);
        mockMvc.perform(delete("/api/transaction")
                        .queryParam("transactionId", "1"))
                .andExpect(status().isOk());
    }

    @ParameterizedTest
    @ValueSource(ints = {24, 32, 99})
    void TestDeleteTransactionWithBadId(int ints) throws Exception {
        doThrow(new IllegalArgumentException()).when(transactionService).deleteTransactionById(ints);
        mockMvc.perform(delete("/api/transaction")
                        .queryParam("transactionId", String.valueOf(ints)))
                .andExpect(status().isBadRequest());

    }



}
