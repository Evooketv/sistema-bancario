package com.pml.sistema.bancario.projeto.repositories;
import com.pml.sistema.bancario.projeto.entity.account.AccountSpecialCheck;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountSpecialCheckRepository extends JpaRepository<AccountSpecialCheck, Long>{

}
