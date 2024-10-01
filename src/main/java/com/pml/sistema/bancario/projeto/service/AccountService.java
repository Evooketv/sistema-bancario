package com.pml.sistema.bancario.projeto.service;

import com.pml.sistema.bancario.projeto.entity.account.Account;
import com.pml.sistema.bancario.projeto.entity.account.AccountDTO;
import com.pml.sistema.bancario.projeto.entity.account.exceptions.InvalidDocumentException;
import com.pml.sistema.bancario.projeto.repositories.AccountRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.security.auth.login.AccountNotFoundException;
import java.util.List;
import java.util.Random;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Service
public class AccountService {

    @Value("${banco.agencia}")
    private Integer agencia;
    private String person;

    @Autowired
    private AccountRepository repository;

    public Account createAccount(AccountDTO accountDTO) throws InvalidDocumentException {
        String person = accountDTO.getPerson();
        Account account = new Account(accountDTO, agencia, person);
        return this.repository.save(account);
    }

    public static int gerarScoreAleatorio() {
        Random random = new Random();
        return random.nextInt(10);
    }


    public static String gerarContaAleatorio() {
        Random random = new Random();
        int numero = random.nextInt(1000000);
        return String.format("%06d", numero);
    }


    public List<Account> getAll() {
        return this.repository.findAll();
    }

    public Account findById(String id) throws AccountNotFoundException {
        Account account = this.repository.findById(Long.valueOf(id))
                .orElseThrow(AccountNotFoundException::new);
        return account;
    }

    public void deleteAccount(String id) throws AccountNotFoundException {
        Account account = this.repository.findById(Long.valueOf(id))
                .orElseThrow(AccountNotFoundException::new);
        this.repository.delete(account);
    }

    public Account updateAccount(String id, AccountDTO accountDTO) throws AccountNotFoundException, InvalidDocumentException {
        Account existingAccount = this.repository.findById(Long.valueOf(id))
                .orElseThrow(AccountNotFoundException::new);

        if (accountDTO.getDocumentNumber() != null) {
            existingAccount.setDocumentNumber(accountDTO.getDocumentNumber());
        }
        if (accountDTO.getPerson() != null) {
            existingAccount.setPerson(accountDTO.getPerson());
        }
        if (accountDTO.getAge() != null) {
            existingAccount.setAge(accountDTO.getAge());
        }
        if (accountDTO.getAddress() != null) {
            existingAccount.setAddress(accountDTO.getAddress());
        }

        return this.repository.save(existingAccount);
    }
}



