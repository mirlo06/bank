package com.zmerli.bank.exposition;

import com.zmerli.bank.domain.entity.Account;
import com.zmerli.bank.domain.entity.Client;
import com.zmerli.bank.domain.entity.Operation;
import com.zmerli.bank.domain.manager.AccountManager;
import com.zmerli.bank.domain.manager.OperationManager;
import com.zmerli.bank.exposition.dto.v1.operation.OperationRequestV1;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {ExpositionApplication.class})
@AutoConfigureMockMvc
public class ExpositionApplicationTests {

    @MockBean
    private OperationManager operationManager;

    @MockBean
    private AccountManager accountManager;

    @Autowired
    private MockMvc mockMvc;

    private Client client = new Client();

    private static final Integer ACCOUNT_NUMBER = 1001;
    private static final Integer BAD_ACCOUNT_NUMBER = 888;
    private static final String DEPOSIT_OPERATION = "deposit";
    private static final String WITHDRAWAL_OPERATION = "withdrawal";


    @Test
    public void should_save_operation() throws Exception {
        Account account = new Account(ACCOUNT_NUMBER, 50d, client);
        OperationRequestV1 newOperationRequest = OperationRequestV1.builder().operationAmount(50d).accountNumber(ACCOUNT_NUMBER).build();

        Operation operation = new Operation(DEPOSIT_OPERATION, 50d, account);

        when(accountManager.getAccount(ACCOUNT_NUMBER))
                .thenReturn(account);

        when(operationManager.saveOperation(account, newOperationRequest.getOperationAmount()))
                .thenReturn(operation);

        String json = "{\"accountNumber\":" + ACCOUNT_NUMBER + ",\"operationAmount\":50}";
        this.mockMvc.perform(post("/v1/operations")
                        .content(json)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    public void should_not_save_operation_with_invalid_arguments() throws Exception {
        String json = "{\"accountNumber\":\"" + ACCOUNT_NUMBER + "\"}";
        this.mockMvc.perform(post("/v1/operations")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void should_not_execute_withdrawal_operation_with_insufficient_fund() throws Exception {
        Account account = new Account(ACCOUNT_NUMBER, 20d, client);
        OperationRequestV1 newOperationRequest = OperationRequestV1.builder().operationAmount(-50d).accountNumber(ACCOUNT_NUMBER).build();
        Operation operation = new Operation(WITHDRAWAL_OPERATION, -50d, account);

        when(accountManager.getAccount(ACCOUNT_NUMBER))
                .thenReturn(account);
        when(operationManager.saveOperation(account, newOperationRequest.getOperationAmount()))
                .thenReturn(operation);

        String json = "{\"accountNumber\":\"" + ACCOUNT_NUMBER + "\",\"operationAmount\":-50}";
        this.mockMvc.perform(post("/v1/operations")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void should_get_operations_history_for_account() throws Exception {
        Account account = new Account(ACCOUNT_NUMBER, 20d, client);
        Operation operation = new Operation(DEPOSIT_OPERATION, 100d, account);
        List<Operation> operationHistory = new ArrayList<>();
        operationHistory.add(operation);

        when(accountManager.getAccount(ACCOUNT_NUMBER))
                .thenReturn(account);
        when(operationManager.getAllOperationByAccount(account, 0, 10))
                .thenReturn(operationHistory);

        this.mockMvc.perform(get("/v1/operations")
                        .param("accountNumber", String.valueOf(ACCOUNT_NUMBER))
                ).andDo(print())
                .andExpect(jsonPath("$[*].operationType").value(DEPOSIT_OPERATION))
                .andExpect(jsonPath("$[*].operationValue").value(100d))
                .andExpect(status().isOk());
    }


    @Test
    public void should_return_not_found_when_getting_history_for_an_unknown_account() throws Exception {
        this.mockMvc.perform(get("/v1/operations")
                        .param("accountNumber", String.valueOf(BAD_ACCOUNT_NUMBER))
                ).andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void should_return_operation_for_a_known_account() throws Exception {

        Account account = new Account(ACCOUNT_NUMBER, 20d, client);
        Operation operation = new Operation(DEPOSIT_OPERATION, 100d, account);
        when(operationManager.getOperation(1L))
                .thenReturn(operation);

        this.mockMvc.perform(get("/v1/operations/{operationId}/", 1L)

                ).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.operationValue").value(100d));
    }
}
