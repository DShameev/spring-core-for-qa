package com.acme.banking.dbo.ooad.service;

import com.acme.banking.dbo.ooad.dal.AccountRepository;
import com.acme.banking.dbo.ooad.domain.Account;

public class TransferService {

    private AccountRepository repository;

    public TransferService(AccountRepository repository) {
        this.repository = repository;
    }

    public void transfer(long from, long to, double amount) {
        Account withdrawn=repository.findById(from);
        Account received=repository.findById(to);
        withdrawn.withdraw(amount);
        received.deposit(amount);
        repository.save(withdrawn);
        repository.save(received);
    }
}
