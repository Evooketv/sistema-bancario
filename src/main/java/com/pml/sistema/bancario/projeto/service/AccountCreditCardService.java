package com.pml.sistema.bancario.projeto.service;

import com.pml.sistema.bancario.projeto.entity.account.*;
import com.pml.sistema.bancario.projeto.entity.account.exceptions.InvalidDocumentException;
import com.pml.sistema.bancario.projeto.repositories.AccountBankRepository;
import com.pml.sistema.bancario.projeto.repositories.AccountCreditCardRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.security.auth.login.AccountNotFoundException;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Service
public class AccountCreditCardService {

    @Autowired
    private AccountCreditCardRepository repository;

    @Autowired
    private AccountBankRepository accountBankRepository;

    public AccountCreditCard createAccount(AccountCreditCardDTO accountDTO) throws InvalidDocumentException {
        if (accountDTO == null) {
            throw new IllegalArgumentException("accountDTO não pode ser nulo");
        }

        AccountBank accountBank = accountBankRepository.findById(Long.valueOf(accountDTO.getAccountBankId()))
                .orElseThrow(() -> new IllegalArgumentException("Conta bancária não encontrada"));


        AccountCreditCard creditCard = new AccountCreditCard(accountBank);
        creditCard.setCartCredit(limitCard(accountBank.getScore()));

        return repository.save(creditCard);
    }

    public List<AccountCreditCard> getAll() {
        return this.repository.findAll();
    }

    public AccountCreditCard findById(Long id) throws AccountNotFoundException {
        return this.repository.findById(id)
                .orElseThrow(() -> new AccountNotFoundException("Conta não encontrada: " + id));
    }

    public void deleteAccount(Long id) throws AccountNotFoundException {
        AccountCreditCard account = this.repository.findById(id)
                .orElseThrow(() -> new AccountNotFoundException("Conta não encontrada"));
        repository.deleteById(id);
    }

    public AccountCreditCard updateAccount(Long id, AccountCreditCardDTO accountDTO) throws AccountNotFoundException, InvalidDocumentException {
        if (id == null) {
            throw new InvalidDocumentException("ID deve ser um número válido.");
        }

        AccountCreditCard existingAccount = this.repository.findById(id)
                .orElseThrow(() -> new AccountNotFoundException("Conta não encontrada: " + id));


        return this.repository.save(existingAccount);
    }

    private Float limitCard(Integer score) {
        if (score == 0 || score == 1) {
            return 0F;
        }
        if (score >= 2 && score <= 5) {
            return 200F;
        }
        if (score >= 6 && score <= 8) {
            return 2000F;
        }
        if (score == 9) {
            return 15000F;
        }
        return 0F;
    }
}


