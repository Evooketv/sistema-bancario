package com.pml.sistema.bancario.projeto.repositories;
import com.pml.sistema.bancario.projeto.entity.account.AccountPerson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountPersonRepository extends JpaRepository<AccountPerson, Long> {

}