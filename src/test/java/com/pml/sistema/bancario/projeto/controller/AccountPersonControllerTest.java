package com.pml.sistema.bancario.projeto.controller;

import com.pml.sistema.bancario.projeto.entity.account.AccountPerson;
import com.pml.sistema.bancario.projeto.entity.account.AccountPersonDTO;
import com.pml.sistema.bancario.projeto.entity.account.exceptions.InvalidDocumentException;
import com.pml.sistema.bancario.projeto.repositories.AccountRepository;
import com.pml.sistema.bancario.projeto.service.AccountPersonService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.security.auth.login.AccountNotFoundException;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
class AccountPersonControllerTest {


    @Autowired
    MockMvc mockMvc;

    @Mock
    private AccountPersonService service;

    @InjectMocks
    private AccountPersonController controller;

    @Autowired
    private AccountRepository repository;


    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void createAccountSucess() throws Exception {
        AccountPersonDTO accountDTO = new AccountPersonDTO();

        accountDTO.setDocumentNumber("123456789");
        accountDTO.setNumeroConta(12345);
        accountDTO.setAgencia(678);
        accountDTO.setPerson("João Silva");
        accountDTO.setAge(30);
        accountDTO.setAddress("Rua A, 123");
        accountDTO.setChequeEspecial(1000.0f);
        accountDTO.setCartaoCredito(500.0f);

        AccountPerson account = new AccountPerson();

        account.setDocumentNumber("123456789");
        account.setNumeroConta(12345);
        account.setAgencia(678);
        account.setPerson("João Silva");
        account.setAge(30);
        account.setAddress("Rua A, 123");
        account.setChequeEspecial(1000.0f);
        account.setCartaoCredito(500.0f);
        account.setId(1L);

        when(service.createAccount(any(AccountPersonDTO.class))).thenReturn(account);

        mockMvc.perform(post("/account")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(accountDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.documentNumber").value("123456789"))
                .andExpect(jsonPath("$.numeroConta").value(12345))
                .andExpect(jsonPath("$.agencia").value(678))
                .andExpect(jsonPath("$.person").value("João Silva"))
                .andExpect(jsonPath("$.age").value(30))
                .andExpect(jsonPath("$.address").value("Rua A, 123"))
                .andExpect(jsonPath("$.chequeEspecial").value(1000.0))
                .andExpect(jsonPath("$.cartaoCredito").value(500.0))
                .andExpect(jsonPath("$.id").value(1L));

    }

    @Test
    void invalidDcoument_BadRequest() throws Exception {

        AccountPersonDTO invalidAccountDTO = new AccountPersonDTO();
        when(service.createAccount(any(AccountPersonDTO.class))).thenThrow(new InvalidDocumentException("Documento não encontrado"));

        mockMvc.perform(post("/account")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"document\": \"\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("Documento não encontrado")));

    }

    @Test
    void invalidAccount_BadRequest() throws Exception {

        AccountPersonDTO invalidAccount = new AccountPersonDTO();
        invalidAccount.setDocumentNumber("");

        when(service.createAccount(any(AccountPersonDTO.class)

        )).thenThrow(new InvalidDocumentException("Documento não encontrado"));

        mockMvc.perform(post("/account")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidAccount)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("Número de documento inválido: Documento não encontrado")));
    }

    @Test
    void updateAccount() throws Exception {
        String accountId = "1";
        AccountPersonDTO accountDTO = new AccountPersonDTO();
        accountDTO.setDocumentNumber("123456789");
        accountDTO.setPerson("Maria");

        AccountPerson updateAccount = new AccountPerson();
        when(service.updateAccount(any(Long.class), any(AccountPersonDTO.class))).thenReturn(updateAccount);

        mockMvc.perform(put("/account/{id}", accountId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(accountDTO)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

    }

    @Test
    void updateNotFound() throws Exception {
        String accountId = "1";
        AccountPersonDTO accountDTO = new AccountPersonDTO();
        accountDTO.setDocumentNumber("123456789");
        accountDTO.setPerson("Maria");

        when(service.updateAccount(any(Long.class), any(AccountPersonDTO.class))).thenThrow(new AccountNotFoundException());

        mockMvc.perform(put("/account/{id}", accountId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(accountDTO)))
                .andExpect(status().isNotFound());

    }

    @Test
    void updateBadRequest() throws Exception {
        String accountId = "1";
        AccountPersonDTO accountDTO = new AccountPersonDTO();

        mockMvc.perform(put("/account/{id}", accountId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(accountDTO)))
                .andExpect(status().isBadRequest());

    }


    @Test
    void findById() throws Exception {
        String accountId = "1";
        AccountPerson account = new AccountPerson();
        account.setId(1L);
        account.setDocumentNumber("123456789");
        account.setPerson("Maria");

        when(service.findById(any(Long.class))).thenReturn(account);

        mockMvc.perform(get("/account/{id}", accountId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.documentNumber").value("123456789"))
                .andExpect(jsonPath("$.person").value("Maria"));

    }

    @Test
    void notFindById() throws Exception {
        String accountId = "999";

        when(service.findById(any(Long.class))).thenThrow(new AccountNotFoundException("Conta não encontrada"));

        mockMvc.perform(get("/account/{id}", accountId))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.error").value("Conta não encontrada."));

    }

    @Test
    void badRequestByid() throws Exception {
        String invalidAccountId = "abc";

        mockMvc.perform(get("/account/{id}", invalidAccountId))
                .andExpect(status().isBadRequest());

    }


    @Test
    void delete() throws Exception {

        String accountId = "1";
        doNothing().when(service).deleteAccount(any(Long.class));

        mockMvc.perform(MockMvcRequestBuilders.delete("/account/{id}", accountId))
                .andExpect(status().isNoContent());

    }

    @Test
    void notDelete() throws Exception {
        String accountId = "999";
        doThrow(new AccountNotFoundException()).when(service).deleteAccount(Long.valueOf(accountId));

        mockMvc.perform(MockMvcRequestBuilders.delete("/account/{id}", accountId))
                .andExpect(status().isNotFound());

    }

    @Test
    void badRequestDelete() throws Exception {
        String invalidAccountId = "abc";

        mockMvc.perform(MockMvcRequestBuilders.delete("/account/{id}", invalidAccountId))
                .andExpect(status().isBadRequest());

    }

    @Test
    void getAll() {
    }
}