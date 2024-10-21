package com.pml.sistema.bancario.projeto.service;

import com.pml.sistema.bancario.projeto.entity.account.*;
import com.pml.sistema.bancario.projeto.entity.account.exceptions.InvalidDocumentException;
import com.pml.sistema.bancario.projeto.repositories.AccountPersonRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;
import javax.security.auth.login.AccountNotFoundException;
import java.util.List;
import static com.pml.sistema.bancario.projeto.service.AccountBankService.gerarContaAleatorio;
import static com.pml.sistema.bancario.projeto.service.AccountBankService.gerarScoreAleatorio;



@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Service
public class AccountPersonService {

    @Value("${banco.agencia}")
    private Integer agencia;

    @Autowired
    private AccountPersonRepository repository;

    @Autowired
    private AccountBankService accountBankService;

    public AccountPerson createAccount(AccountPersonDTO accountDTO) throws InvalidDocumentException {
        AccountPerson accountPerson = new AccountPerson(accountDTO, accountDTO.getPerson());
        AccountBank accountBank = new AccountBank();
        AccountCreditCard accountCreditCard = new AccountCreditCard();
        AccountSpecialCheck accountSpecialCheck = new AccountSpecialCheck();
        accountBank.setNumeroConta(gerarContaAleatorio());
        accountBank.setAgencia(agencia);
        accountBank.setScore(gerarScoreAleatorio());
        accountBank.setAccountCreditCard(accountCreditCard);
        accountBank.setAccountSpecialCheck(accountSpecialCheck);
        accountPerson.setBankAccount(accountBank);
        return repository.save(accountPerson);

    }

    public List<AccountPerson> getAll() {
        return this.repository.findAll();
    }

    public AccountPerson findById(Long id) throws AccountNotFoundException {
        return this.repository.findById(id)
                .orElseThrow(() -> new AccountNotFoundException("Conta não encontrada: " + id));
    }

    public void deleteAccount(Long id) throws AccountNotFoundException {
        AccountPerson account = this.repository.findById(id)
                .orElseThrow(() -> new AccountNotFoundException("Conta não encontrada: " + id));
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



