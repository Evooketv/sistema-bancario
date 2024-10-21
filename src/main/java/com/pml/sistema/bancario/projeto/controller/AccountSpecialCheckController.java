package com.pml.sistema.bancario.projeto.controller;

import com.pml.sistema.bancario.projeto.entity.account.AccountSpecialCheck;
import com.pml.sistema.bancario.projeto.entity.account.AccountSpecialCheckDTO;
import com.pml.sistema.bancario.projeto.entity.account.exceptions.InvalidDocumentException;
import com.pml.sistema.bancario.projeto.service.AccountSpecialCheckService;
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
@RequestMapping("/check")
@CrossOrigin(origins = "*")

public class AccountSpecialCheckController {

    @Autowired
    private AccountSpecialCheckService service;

    @PostMapping
    public ResponseEntity<?> createAccount(@Valid @RequestBody AccountSpecialCheckDTO accountDTO) {
        try {
            AccountSpecialCheck newAccount = this.service.createAccount(accountDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(newAccount);
        } catch (InvalidDocumentException e) {
            return ResponseEntity.badRequest().body("Número de documento inválido: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao criar a conta: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<AccountSpecialCheck> update(@PathVariable("id") String id, @RequestBody AccountSpecialCheckDTO accountDTO) {
        if (accountDTO == null || accountDTO.getChequeEspecial() == null ) {
            return ResponseEntity.badRequest().body(null);
        }
        try {
            AccountSpecialCheck updatedAccount = this.service.updateAccount(Long.valueOf(id), accountDTO);
            return ResponseEntity.ok(updatedAccount);
        } catch (AccountNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (InvalidDocumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping
    public ResponseEntity<List<AccountSpecialCheck>> getAll() {
        List<AccountSpecialCheck> accounts = this.service.getAll();
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
            AccountSpecialCheck account = this.service.findById(Long.valueOf(id));
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


