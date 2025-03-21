package com.example.demo;

import com.example.demo.api.PostTransactionRequest;
import com.example.demo.client.PrefixClient;
import com.example.demo.resource.BankResource;
import com.example.demo.service.TransactionService;
import com.example.demo.client.PrefixClient.Prefix; // Импортируем класс Prefix, если его еще нет
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
public class IntegrationTest {

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

  @Test
  void testAddTransactionWithNegativeAmount() {

    PostTransactionRequest request = new PostTransactionRequest();
    request.setAmount(BigDecimal.valueOf(-100));

    ResponseEntity<Void> response = bankResource.addTransaction(1L, request);


    assert(response.getStatusCode().is4xxClientError());
  }


  @Test
  void testPrefixClient() {

    Prefix prefix = new Prefix();
    prefix.setPrefix("0300");
    ResponseEntity<Prefix> responseEntity = ResponseEntity.ok(prefix);

    when(prefixClient.getPrefix()).thenReturn(responseEntity);


    String prefixValue = prefixClient.getPrefix().getBody().getPrefix();


    assert(prefixValue.equals("0300"));
  }
}
