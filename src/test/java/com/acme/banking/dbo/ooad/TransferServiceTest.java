package com.acme.banking.dbo.ooad;

import com.acme.banking.dbo.ooad.dal.AccountRepository;
import com.acme.banking.dbo.ooad.domain.Account;
import com.acme.banking.dbo.ooad.domain.SavingAccount;
import com.acme.banking.dbo.ooad.service.TransferService;
import org.junit.Test;

import static org.mockito.Mockito.*;

public class TransferServiceTest {
    @Test
    public void shouldUpdateAccountsStateWhenTransfer() {
        //region Given
        AccountRepository repo = mock(AccountRepository.class);

        TransferService sut = new TransferService(repo);
        Account fromAccount = mock(Account.class);
        Account toAccount = mock(Account.class);
        when(repo.findById(0L)).thenReturn(fromAccount);
        when(repo.findById(1L)).thenReturn(toAccount);
        //endregion

        //region When
        sut.transfer(0, 1, 100.);
        //endregion

        //region Then
        verify(fromAccount).withdraw(100.);
        verify(toAccount).deposit(100);
        verify(repo).save(fromAccount);
        verify(repo).save(toAccount);
        //endregion
    }
}
