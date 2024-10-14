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
public class AccountBankDTO {

    private AccountType accountType;
    private Integer numeroConta;
    private Integer agencia;


}
