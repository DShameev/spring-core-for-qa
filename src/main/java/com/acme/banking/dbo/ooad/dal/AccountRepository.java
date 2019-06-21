package com.acme.banking.dbo.ooad.dal;

import com.acme.banking.dbo.ooad.domain.Account;

public interface AccountRepository {
    Account findById(long id);
    void save(Account account);
}
