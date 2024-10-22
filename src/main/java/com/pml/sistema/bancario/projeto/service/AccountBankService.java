package com.pml.sistema.bancario.projeto.service;

import com.pml.sistema.bancario.projeto.entity.account.AccountBank;
import com.pml.sistema.bancario.projeto.entity.account.AccountBankDTO;
import com.pml.sistema.bancario.projeto.entity.account.AccountCreditCard;
import com.pml.sistema.bancario.projeto.entity.account.AccountSpecialCheck;
import com.pml.sistema.bancario.projeto.entity.account.enums.AccountType;
import com.pml.sistema.bancario.projeto.entity.account.exceptions.InvalidDocumentException;
import com.pml.sistema.bancario.projeto.repositories.AccountBankRepository;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
public class AccountBankService {

    @Value("${banco.agencia}")
    private String agencia;

    @Autowired
    private AccountBankRepository repository;

    @Enumerated(EnumType.STRING)
    private AccountType accountType;

    public AccountBank createAccount(AccountBankDTO accountDTO, String docNumber) throws InvalidDocumentException {
        if (accountDTO == null) {
            throw new IllegalArgumentException("accountDTO não pode ser nulo");
        }

        Integer score = gerarScoreAleatorio();

        AccountBank accountBank = new AccountBank(1234, docNumber, score);
        accountBank.setAccountType(docNumber);

        AccountCreditCard accountCreditCard = new AccountCreditCard(accountBank);
        accountCreditCard.limitCard(score);
        accountBank.setAccountCreditCard(accountCreditCard);

        AccountSpecialCheck accountSpecialCheck = new AccountSpecialCheck(accountBank);
        accountSpecialCheck.limitCheck(score);
        accountBank.setAccountSpecialCheck(accountSpecialCheck);


        return repository.save(accountBank);
    }

    public static String gerarContaAleatorio() {
        Random random = new Random();
        int numero = random.nextInt(1000000);
        return String.format("%06d", numero);
    }

    public static int gerarScoreAleatorio() {
        Random random = new Random();
        return random.nextInt(10);
    }

    public List<AccountBank> getAll() {
        return this.repository.findAll();
    }

    public AccountBank findById(Long id) throws AccountNotFoundException {
        AccountBank account = this.repository.findById(id)
                .orElseThrow(() -> new AccountNotFoundException("Conta não encontrada: " + id));
        return account;
    }

    public void deleteAccount(Long id) throws AccountNotFoundException {
        AccountBank account = this.repository.findById(id)
                .orElseThrow(() -> new AccountNotFoundException("Account not found"));
        repository.deleteById(id);
    }

    public AccountBank updateAccount(Long id, AccountBankDTO accountDTO) throws AccountNotFoundException, InvalidDocumentException {
        if (id == null) {
            throw new InvalidDocumentException("ID deve ser um número válido.");
        }

        AccountBank existingAccount = this.repository.findById(id)
                .orElseThrow(() -> new AccountNotFoundException("Conta não encontrada: " + id));

        if (accountDTO.getAgencia() != null) {
            existingAccount.setAgencia(accountDTO.getAgencia());
        }


        return this.repository.save(existingAccount);
    }

}
