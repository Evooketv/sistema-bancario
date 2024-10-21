package com.pml.sistema.bancario.projeto.controller;
import com.pml.sistema.bancario.projeto.entity.account.AccountBank;
import com.pml.sistema.bancario.projeto.entity.account.AccountBankDTO;
import com.pml.sistema.bancario.projeto.entity.account.AccountCreditCard;
import com.pml.sistema.bancario.projeto.entity.account.exceptions.InvalidDocumentException;
import com.pml.sistema.bancario.projeto.service.AccountBankService;
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
@RequestMapping("/bank")
@CrossOrigin(origins = "*")

public class AccountBankController {

    @Autowired
    private AccountBankService service;

    @PostMapping
    public ResponseEntity<?> createAccount(@Valid @RequestBody AccountBankDTO accountDTO) {
        try {
            AccountBank newAccount = this.service.createAccount(accountDTO, accountDTO.getDocumentNumber());
            return ResponseEntity.status(HttpStatus.CREATED).body(newAccount);
        } catch (InvalidDocumentException e) {
            return ResponseEntity.badRequest().body("Número de documento inválido: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao criar a conta: " + e.getMessage());
        }
    }


    @PutMapping("/{id}")
    public ResponseEntity<AccountBank> update(@PathVariable("id") String id, @RequestBody AccountBankDTO accountDTO) {
        if (accountDTO == null || accountDTO.getAgencia() == null) {
            return ResponseEntity.badRequest().body(null);
        }
        try {
            AccountBank updatedAccount = this.service.updateAccount(Long.valueOf(id), accountDTO);
            return ResponseEntity.ok(updatedAccount);
        } catch (AccountNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (InvalidDocumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping
    public ResponseEntity<List<AccountBank>> getAll() {
        List<AccountBank> accounts = this.service.getAll();
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
            AccountBank account = this.service.findById(Long.valueOf(id));
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


