package com.pml.sistema.bancario.projeto.entity.account.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum AccountType {
        CPF_CONTA_PESSOA_FISICA("CPF Conta Pessoa Física"),
        CNPJ_CONTA_PESSOA_JURIDICA("CNPJ Conta Pessoa Jurídica");

        private final String description;

        AccountType(String description) {
                this.description = description;
        }

        @JsonValue
        public String getDescription() {
                return description;
        }

        @Override
        public String toString() {
                return  description ;
        }
}
