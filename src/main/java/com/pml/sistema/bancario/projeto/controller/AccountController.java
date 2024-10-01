package com.pml.sistema.bancario.projeto.controller;

import com.pml.sistema.bancario.projeto.entity.account.Account;
import com.pml.sistema.bancario.projeto.entity.account.AccountDTO;
import com.pml.sistema.bancario.projeto.entity.account.exceptions.InvalidDocumentException;
import com.pml.sistema.bancario.projeto.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.login.AccountNotFoundException;
import java.util.List;


@RestController
@RequestMapping("/account")
@CrossOrigin(origins = "*")

public class AccountController {

    @Autowired
    private AccountService service;

    @PostMapping
    public ResponseEntity<Account> create(@RequestBody AccountDTO accountDTO) {
        if (accountDTO == null || accountDTO.getDocumentNumber() == null || accountDTO.getPerson() == null) {
            return ResponseEntity.badRequest().body(null);
        }
        try {
            Account newAccount = this.service.createAccount(accountDTO);
            return ResponseEntity.ok().body(newAccount);
        } catch (InvalidDocumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Account> update(@PathVariable("id") String id, @RequestBody AccountDTO accountDTO) {
        if (accountDTO == null || accountDTO.getDocumentNumber() == null || accountDTO.getPerson() == null) {
            return ResponseEntity.badRequest().body(null);
        }
        try {
            Account updatedAccount = this.service.updateAccount(id, accountDTO);
            return ResponseEntity.ok(updatedAccount);
        } catch (AccountNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (InvalidDocumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping
    public ResponseEntity<List<Account>> getAll() {
        List<Account> accounts = this.service.getAll();
        return ResponseEntity.ok().body(accounts);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable("id") String id) {
        try {
            Account account = this.service.findById(id);
            return ResponseEntity.ok(account);
        } catch (AccountNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Conta não encontrada.");
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") String id) {
        try {
            this.service.deleteAccount(id);
            return ResponseEntity.ok("Conta deletada com sucesso!");
        } catch (AccountNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Conta não encontrada.");
        }
    }


    public class TestController {
        @GetMapping
        public ResponseEntity<String> test() {
            return ResponseEntity.ok("CORS is working!");
        }
    }
}

