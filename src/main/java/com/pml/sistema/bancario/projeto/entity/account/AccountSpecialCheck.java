package com.pml.sistema.bancario.projeto.entity.account;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "account_special_check")
public class AccountSpecialCheck {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "account_bank_id", referencedColumnName = "id")
    private AccountBank accountBank;

    private Float chequeEspecial;

    public AccountSpecialCheck(AccountBank accountBank) {
        this.accountBank = accountBank;
        this.chequeEspecial = limitCheck(accountBank.getScore());
    }

    public Float limitCheck(Integer score) {
        if (score == 0 || score == 1) {
            return 0F;
        } else if (score >= 2 && score <= 5) {
            return 1000F;
        } else if (score >= 6 && score <= 8) {
            return 2000F;
        } else if (score == 9) {
            return 5000F;
        }
        return 0F;
    }
}
