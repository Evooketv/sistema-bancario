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
@Table(name = "account_credit_card")
public class AccountCreditCard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "account_bank_id", referencedColumnName = "id")
    private AccountBank accountBank;

    private Float cartCredit;

    public AccountCreditCard(AccountBank accountBank) {
        this.accountBank = accountBank;
        limitCard(accountBank.getScore());
    }

    public float limitCard(Integer score) {
        if (score == 0 || score == 1) {
            this.cartCredit = 0F;
        } else if (score >= 2 && score <= 5) {
            this.cartCredit = 200F;
        } else if (score >= 6 && score <= 8) {
            this.cartCredit = 2000F;
        } else if (score == 9) {
            this.cartCredit = 15000F;
        }
        return 0;
    }

}
