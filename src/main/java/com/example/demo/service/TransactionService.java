package com.example.demo.service;

import com.example.demo.api.PostTransactionRequest;

public interface TransactionService {



  void createTransaction(Long account, PostTransactionRequest request);
}
