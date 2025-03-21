package com.example.demo.resource;

import com.example.demo.api.BankAccountResponse;
import com.example.demo.api.PostTransactionRequest;
import com.example.demo.service.BankAccountService;
import com.example.demo.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping("/accounts")
@RequiredArgsConstructor
public class BankResource {

  private final BankAccountService bankAccountService;
  private final TransactionService transactionService;

  @GetMapping
  public ResponseEntity<Page<BankAccountResponse>> getLowBalanceAccounts(Pageable pageable) {
    return ResponseEntity.ok(bankAccountService.findAll(pageable));
  }


  @PostMapping("/{id}/transaction")
  public ResponseEntity<Void> addTransaction(@PathVariable Long id, @RequestBody PostTransactionRequest request) {
    if (request.getAmount() == null || request.getAmount().compareTo(BigDecimal.ZERO) <= 0){
      return ResponseEntity.badRequest().build(); //HTTP 400
    }

    transactionService.createTransaction(id, request);
    return ResponseEntity.accepted().build();
  }


  @PostMapping("/{id}/transaction")
  public ResponseEntity<Void> applyForLoan(@PathVariable Long id) {
    bankAccountService.applyForLoan(id);
    return ResponseEntity.accepted().build();
  }

}
