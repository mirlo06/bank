package com.zmerli.bank.domain.operation;

import com.zmerli.bank.domain.entity.Account;
import com.zmerli.bank.domain.entity.Client;
import com.zmerli.bank.domain.entity.Operation;
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
public class OperationTests {

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
    public void should_save_operation() {
        Double operationValue = 50d;
        Account account = new Account(1001, 50d, client);
        Operation expectedOperation = new Operation("deposit", operationValue, account);


        when(operationService.saveOperation(account, operationValue))
                .thenReturn(expectedOperation);

        Operation insertedOperation = operationManager.saveOperation(account, operationValue);

        Assert.assertEquals(insertedOperation, expectedOperation);
    }
}
