package com.pml.sistema.bancario.projeto.service;

import com.pml.sistema.bancario.projeto.entity.account.*;
import com.pml.sistema.bancario.projeto.entity.account.exceptions.InvalidDocumentException;
import com.pml.sistema.bancario.projeto.repositories.AccountSpecialCheckRepository;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.security.auth.login.AccountNotFoundException;
import java.util.List;

import static com.pml.sistema.bancario.projeto.service.AccountBankService.gerarScoreAleatorio;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Service
public class AccountSpecialCheckService {

    private Integer score;

    @Autowired
    private AccountSpecialCheckRepository repository;


    public List<AccountSpecialCheck> getAll() {
        return this.repository.findAll();
    }

    public AccountSpecialCheck findById(Long id) throws AccountNotFoundException {
        AccountSpecialCheck account = this.repository.findById(id)
                .orElseThrow(() -> new AccountNotFoundException("Conta não encontrada: " + id));
        return account;
    }

    public void deleteAccount(Long id) throws AccountNotFoundException {
        AccountSpecialCheck account = this.repository.findById(id)
                .orElseThrow(() -> new AccountNotFoundException("Account not found"));
        repository.deleteById(id);
    }

    public AccountSpecialCheck createAccount(@Valid AccountSpecialCheckDTO accountDTO) throws InvalidDocumentException {
        Integer score = gerarScoreAleatorio();
        AccountBank accountBank = new AccountBank();
        accountBank.setScore(score);

        AccountSpecialCheck specialCheck = new AccountSpecialCheck();
        specialCheck.setAccountBank(accountBank); // Associar o accountBank ao cheque especial

        return repository.save(specialCheck);  // Salvar a conta de cheque especial
    }

    public AccountSpecialCheck updateAccount(Long id, AccountSpecialCheckDTO accountDTO) throws AccountNotFoundException, InvalidDocumentException {
        if (id == null) {
            throw new InvalidDocumentException("ID deve ser um número válido.");
        }

        AccountSpecialCheck existingAccount = this.repository.findById(id)
                .orElseThrow(() -> new AccountNotFoundException("Conta não encontrada: " + id));

        AccountSpecialCheckDTO accountSpecialCheckDTO = new AccountSpecialCheckDTO();

        if (accountDTO.getChequeEspecial() != null) {
            existingAccount.setChequeEspecial(accountDTO.getChequeEspecial());
        }

        if (accountDTO.getScore() != null) {
            existingAccount.getAccountBank().setScore(accountDTO.getScore());
        }

        return this.repository.save(existingAccount);

    }

}
