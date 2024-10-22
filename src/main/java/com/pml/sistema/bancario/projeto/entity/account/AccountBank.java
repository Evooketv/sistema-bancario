package com.pml.sistema.bancario.projeto.entity.account;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Id;
import com.pml.sistema.bancario.projeto.entity.account.enums.AccountType;
import com.pml.sistema.bancario.projeto.entity.account.exceptions.InvalidDocumentException;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import static com.pml.sistema.bancario.projeto.service.AccountBankService.gerarContaAleatorio;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity

@Table(name = "account_bank")

public class AccountBank {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String numeroConta;

    @Column(nullable = false)
    private Integer agencia;

    @Column(nullable = false)
    private Integer score;


    @Enumerated(EnumType.STRING)
    private AccountType accountType;

    @JsonIgnore
    @OneToOne(mappedBy = "bankAccount", cascade = CascadeType.ALL)
    private AccountPerson accountPerson;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "account_credit_card_id", referencedColumnName = "id")
    private AccountCreditCard accountCreditCard;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "account_special_check_id", referencedColumnName = "id")
    private AccountSpecialCheck accountSpecialCheck;

    public void setAccountType(String docNumber) throws InvalidDocumentException {
        if (docNumber == null || (docNumber.length() != 11 && docNumber.length() != 14)) {
            throw new InvalidDocumentException("Número de documento inválido. Deve ter 11 (CPF) ou 14 (CNPJ) dígitos.");
        }

        if (docNumber.length() == 11) {
            this.accountType = AccountType.CPF_CONTA_PESSOA_FISICA;
        } else if (docNumber.length() == 14) {
            this.accountType = AccountType.CNPJ_CONTA_PESSOA_JURIDICA;
        }
    }


    public AccountBank(Integer agencia, String documentNumber, Integer score) throws InvalidDocumentException {
        if (agencia == null) {
            throw new IllegalArgumentException("O campo 'agencia' não pode ser nulo.");
        }

        this.agencia = agencia;
        this.numeroConta = gerarContaAleatorio();
        this.score = score;

        this.accountCreditCard = new AccountCreditCard(this);
        this.accountSpecialCheck = new AccountSpecialCheck(this);

        setAccountType(documentNumber);

        this.accountCreditCard.limitCard(score);
        this.accountSpecialCheck.setChequeEspecial(this.accountSpecialCheck.limitCheck(score));
    }


    public Integer getScore() {
        return score;

    }

}



