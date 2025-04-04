package com.example.demo.service;

import com.example.demo.api.PostTransactionRequest;
import com.example.demo.domain.BankAccount;
import com.example.demo.exception.InternalTransactionException;
import com.example.demo.repository.BankAccountRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class TransactionServiceImpl implements TransactionService {
    private final BankAccountRepository bankAccountRepository;

    public TransactionServiceImpl(BankAccountRepository bankAccountRepository) {
        this.bankAccountRepository = bankAccountRepository;
    }

    @Override
    public void createTransaction(Long accountId, PostTransactionRequest request){
        if (request.getAmount().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Transaction amount must be positive");
        }

        BankAccount account = bankAccountRepository.findById(accountId)
                .orElseThrow(() -> new IllegalArgumentException("Bank account not found"));


        account.setBalance(account.getBalance().add(request.getAmount()));
        bankAccountRepository.save(account);


        if (account.getBalance().compareTo(BigDecimal.valueOf(200)) < 0) {
            throw new InternalTransactionException("Balance below 200");
        }


        bankAccountRepository.save(account);
    }

}
