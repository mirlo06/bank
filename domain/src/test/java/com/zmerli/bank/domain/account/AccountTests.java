package com.zmerli.bank.domain.account;

import com.zmerli.bank.domain.entity.Account;
import com.zmerli.bank.domain.entity.Client;
import com.zmerli.bank.domain.manager.AccountManager;
import com.zmerli.bank.domain.manager.OperationManager;
import com.zmerli.bank.domain.service.AccountService;
import com.zmerli.bank.domain.service.OperationService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AccountTests {


    @Autowired
    private OperationManager operationManager;

    @MockBean
    private OperationService operationService;


    @Autowired
    private AccountManager accountManager;

    @MockBean
    private AccountService accountService;


    private static Integer ACCOUNT_NUMBER = 123456;
    private Client client = new Client();

    @Test
    public void should_get_account_equals_by_account_number() throws Exception {
        Account account = new Account(ACCOUNT_NUMBER, 50d, client);
        when(accountService.getAccount(ACCOUNT_NUMBER))
                .thenReturn(account);
        Assert.assertEquals(accountManager.getAccount(ACCOUNT_NUMBER), account);
    }

    @Test
    public void should_return_null_where_account_not_found_when_trying_to_get_account_position_for_unknown_account_number() {
        when(accountService.getAccount(ACCOUNT_NUMBER))
                .thenReturn(null);
        Assert.assertNull(accountManager.getAccount(ACCOUNT_NUMBER));
    }

}
