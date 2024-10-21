package com.pml.sistema.bancario.projeto.entity.account;

import com.pml.sistema.bancario.projeto.entity.account.enums.AccountType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccountBankDTO {

    private Long id;
    private String numeroConta;
    private Integer agencia;
    private Integer score;
    private String documentNumber;

    @NotNull
    @Size(min = 11, max = 14, message = "O número do documento deve ter entre 11 e 14 dígitos.")

    private AccountType accountType;

    private AccountCreditCard accountCreditCard;

    private AccountSpecialCheck accountSpecialCheck;
}


