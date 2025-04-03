package com.example.demo;


import com.example.demo.api.PostTransactionRequest;
import com.example.demo.client.PrefixClient;

import com.example.demo.resource.BankResource;
import com.example.demo.service.TransactionService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@SpringBootTest
 class IntegrationTest {

  @MockBean
  private TransactionService transactionService;

  @Autowired
  private BankResource bankResource;

  @Autowired
  private PrefixClient prefixClient;


  @BeforeEach
  void setUp() {

    doNothing().when(transactionService).createTransaction(anyLong(), any(PostTransactionRequest.class));
  }


  @Test
  void testAddTransactionWithPositiveAmount() {

    PostTransactionRequest request = new PostTransactionRequest();
    request.setAmount(BigDecimal.valueOf(100));


    ResponseEntity<Void> response = bankResource.addTransaction(1L, request);


    assert(response.getStatusCode().is2xxSuccessful());


    verify(transactionService, times(1)).createTransaction(anyLong(), any(PostTransactionRequest.class));
  }




}
