package com.pml.sistema.bancario.projeto.entity.account;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccountCreditCardDTO {

    private Float cartaoCredito;
    private Integer accountBankId;
}
