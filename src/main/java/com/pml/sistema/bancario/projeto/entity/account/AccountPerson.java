package com.pml.sistema.bancario.projeto.entity.account;
import com.pml.sistema.bancario.projeto.entity.account.exceptions.InvalidDocumentException;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static com.pml.sistema.bancario.projeto.service.AccountBankService.gerarScoreAleatorio;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "account_person")
public class AccountPerson {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String documentNumber;
    private String person;
    private Integer age;
    private String address;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "account_bank_id", referencedColumnName = "id")
    private AccountBank bankAccount;

    public AccountPerson(AccountPersonDTO accountDTO, String person) throws InvalidDocumentException {
        if (accountDTO == null) {
            throw new IllegalArgumentException("O objeto AccountDTO não pode ser nulo.");
        }
        if (person == null || person.isEmpty()) {
            throw new IllegalArgumentException("O campo 'person' não pode ser nulo ou vazio.");
        }

        String docNumber = accountDTO.getDocumentNumber();

        this.documentNumber = docNumber;
        this.person = person;
        this.age = accountDTO.getAge();
        this.address = accountDTO.getAddress();

        Integer score = gerarScoreAleatorio();

        this.bankAccount = new AccountBank(1234, docNumber, score); // Atribui o score gerado

    }
}

