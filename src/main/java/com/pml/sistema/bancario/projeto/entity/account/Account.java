package com.pml.sistema.bancario.projeto.entity.account;

import com.pml.sistema.bancario.projeto.entity.account.enums.AccountType;
import com.pml.sistema.bancario.projeto.entity.account.exceptions.InvalidDocumentException;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import static com.pml.sistema.bancario.projeto.service.AccountService.gerarContaAleatorio;
import static com.pml.sistema.bancario.projeto.service.AccountService.gerarScoreAleatorio;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private AccountType accountType;
    private String documentNumber;
    private Integer score;
    private Integer numeroConta;
    private Integer agencia;
    private String person;
    private Integer age;
    private String address;
    private Float chequeEspecial;
    private Float cartaoCredito;



    public Account(AccountDTO accountDTO, Integer agencia, String person) throws InvalidDocumentException {
        if (accountDTO == null || accountDTO.getDocumentNumber() == null) {
            throw new InvalidDocumentException("O objeto AccountDTO ou o número do documento não pode ser nulo.");
        }
        if (person == null || person.isEmpty()) {
            throw new InvalidDocumentException("O campo 'person' não pode ser nulo ou vazio.");
        }

        String docNumber = accountDTO.getDocumentNumber();

        if (docNumber.length() == 11) {
            this.accountType = AccountType.CPF_CONTA_PESSOA_FISICA;
        } else if (docNumber.length() == 14) {
            this.accountType = AccountType.CNPJ_CONTA_PESSOA_JURIDICA;
        } else {
            throw new InvalidDocumentException("Número de documento inválido. Deve ter 11 dígitos para PF ou 14 dígitos para PJ.");
        }

        this.score = gerarScoreAleatorio();

        if (score == 0 || score == 1) {
            this.chequeEspecial = 0F;
        }
        if (score >= 2 && score <= 5) {
            this.chequeEspecial = 1000F;
        }
        if (score >= 6 && score <= 8) {
            this.chequeEspecial = 2000F;
        }
        if (score == 9 ) {
            this.chequeEspecial = 5000F;
        }

        if (score == 0 || score == 1) {
            this.cartaoCredito = 0F;
        }
        if (score >= 2 && score <= 5) {
            this.cartaoCredito = 200F;
        }
        if (score >= 6 && score <= 8) {
            this.cartaoCredito = 2000F;
        }
        if (score == 9 ) {
            this.cartaoCredito = 15000F;
        }

        this.documentNumber = docNumber;
        this.numeroConta = Integer.valueOf(gerarContaAleatorio());
        this.agencia = agencia;
        this.person = person;
        this.age = accountDTO.getAge();
        this.address = accountDTO.getAddress();
        this.chequeEspecial = getChequeEspecial();
        this.cartaoCredito = getCartaoCredito();

    }

}
