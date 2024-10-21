package com.pml.sistema.bancario.projeto.entity.account;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccountSpecialCheckDTO {

    private Float chequeEspecial;
    private Integer score;
}
