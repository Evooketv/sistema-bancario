package com.pml.sistema.bancario.projeto.service;

import com.pml.sistema.bancario.projeto.entity.account.AccountPerson;
import com.pml.sistema.bancario.projeto.entity.account.AccountPersonDTO;
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
public class AccountPersonService {

    @Value("${banco.agencia}")
    private Integer agencia;
    private String person;

    @Autowired
    private AccountRepository repository;

    public AccountPerson createAccount(AccountPersonDTO accountDTO) throws InvalidDocumentException {
        AccountPerson account = new AccountPerson(accountDTO, accountDTO.getAgencia(), accountDTO.getPerson());
        return repository.save(account);
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


    public List<AccountPerson> getAll() {
        return this.repository.findAll();
    }

    public AccountPerson findById(Long id) throws AccountNotFoundException {
        AccountPerson account = this.repository.findById(id)
                .orElseThrow(() -> new AccountNotFoundException("Conta não encontrada: " + id));
        return account;
    }

    public void deleteAccount(Long id) throws AccountNotFoundException {
        AccountPerson account = this.repository.findById(id)
                .orElseThrow(() -> new AccountNotFoundException("Account not found"));
        repository.deleteById(id);
    }

    public AccountPerson updateAccount(Long id, AccountPersonDTO accountDTO) throws AccountNotFoundException, InvalidDocumentException {
        if (id == null) {
            throw new InvalidDocumentException("ID deve ser um número válido.");
        }

        AccountPerson existingAccount = this.repository.findById(id)
                .orElseThrow(() -> new AccountNotFoundException("Conta não encontrada: " + id));


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



