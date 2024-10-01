package com.pml.sistema.bancario.projeto.entity.account;

import com.pml.sistema.bancario.projeto.entity.account.enums.AccountType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class AccountDTO {

    private AccountType accountType;
    private String documentNumber;
    private Integer numeroConta;
    private Integer agencia;
    private String person;
    private Integer age;
    private String address;
    private Float chequeEspecial;
    private Float cartaoCredito;

}
