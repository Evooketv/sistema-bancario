package com.pml.sistema.bancario.projeto.controller;
import com.pml.sistema.bancario.projeto.entity.account.AccountCreditCard;
import com.pml.sistema.bancario.projeto.entity.account.AccountCreditCardDTO;
import com.pml.sistema.bancario.projeto.entity.account.exceptions.InvalidDocumentException;
import com.pml.sistema.bancario.projeto.service.AccountCreditCardService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.login.AccountNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ResponseBody
@RestController
@RequestMapping("/credit")
@CrossOrigin(origins = "*")

public class AccountCreditCardController {

    @Autowired
    private AccountCreditCardService service;

    @PostMapping
    public ResponseEntity<?> createAccount(@Valid @RequestBody AccountCreditCardDTO accountDTO) {
        try {
            AccountCreditCard newAccount = this.service.createAccount(accountDTO);

            return ResponseEntity.status(HttpStatus.CREATED).body(newAccount);

        } catch (InvalidDocumentException e) {

            return ResponseEntity.badRequest().body("Número de documento inválido: " + e.getMessage());

        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao criar a conta: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<AccountCreditCard> update(@PathVariable("id") String id, @RequestBody AccountCreditCardDTO accountDTO) {
        if (accountDTO == null || accountDTO.getCartaoCredito() == null) {
            return ResponseEntity.badRequest().body(null);
        }
        try {
            AccountCreditCard updatedAccount = this.service.updateAccount(Long.valueOf(id), accountDTO);
            return ResponseEntity.ok(updatedAccount);
        } catch (AccountNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (InvalidDocumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping
    public ResponseEntity<List<AccountCreditCard>> getAll() {
        List<AccountCreditCard> accounts = this.service.getAll();
        return ResponseEntity.ok().body(accounts);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable("id") String id) {
        if (!id.matches("\\d+")) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "ID inválido.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
        try {
            AccountCreditCard account = this.service.findById(Long.valueOf(id));
            return ResponseEntity.ok(account);
        } catch (AccountNotFoundException e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Conta não encontrada.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") String id) {
        try {
            this.service.deleteAccount(Long.valueOf(id));
            return ResponseEntity.noContent().build();
        } catch (AccountNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/test")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("CORS is working!");
    }
}


