package com.pml.sistema.bancario.projeto.repositories;
import com.pml.sistema.bancario.projeto.entity.account.AccountBank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountBankRepository extends JpaRepository<AccountBank, Long> {

}
