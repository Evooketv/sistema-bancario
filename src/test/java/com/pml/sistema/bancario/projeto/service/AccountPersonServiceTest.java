
import com.pml.sistema.bancario.projeto.entity.account.AccountPerson;
import com.pml.sistema.bancario.projeto.entity.account.AccountPersonDTO;
import com.pml.sistema.bancario.projeto.entity.account.enums.AccountType;
import com.pml.sistema.bancario.projeto.entity.account.exceptions.InvalidDocumentException;
import com.pml.sistema.bancario.projeto.repositories.AccountPersonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import javax.security.auth.login.AccountNotFoundException;
import java.util.*;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


//@ExtendWith(MockitoExtension.class)
//class AccountPersonServiceTest {

   // @Mock
   // AccountPersonRepository repository;

  //  @Autowired
  //  @InjectMocks
   // AccountPersonService service;

  //  @BeforeEach
   // void setUp() {
    //    MockitoAnnotations.openMocks(this);
  //  }

  //  @Test
  //  void createAccount() throws InvalidDocumentException {

      //  AccountPersonDTO accountDTO = new AccountPersonDTO();
     //   accountDTO.setDocumentNumber("123456789");
      //  accountDTO.setNumeroConta(12345);
      //  accountDTO.setAgencia(678);
      //  accountDTO.setPerson("João Silva");
       // accountDTO.setAge(30);
       // accountDTO.setAddress("Rua A, 123");
      //  accountDTO.setChequeEspecial(1000.0f);
      //  accountDTO.setCartaoCredito(500.0f);

     //   AccountPerson savedAccount = new AccountPerson();
       // savedAccount.setId(1L);
      //  savedAccount.setDocumentNumber(accountDTO.getDocumentNumber());

      //  when(repository.save(any(AccountPerson.class))).thenReturn(savedAccount);

      //  AccountPerson createdAccount = service.createAccount(accountDTO);

       // verify(repository, times(1)).save(any(AccountPerson.class));

        //assertNotNull(createdAccount);
      //  assertEquals(savedAccount.getId(), createdAccount.getId());
       // assertEquals(accountDTO.getDocumentNumber(), createdAccount.getDocumentNumber());

    //}

   // @Test
   // void gerarScoreAleatorio() {
       // AccountPersonService mockAccount = new AccountPersonService();
       // int score = AccountPersonService.gerarScoreAleatorio();

      //  assertTrue(score >= 0 && score <= 9, "O score deve estar entre 0 e 9.");

   // }

   // @Test
   // void gerarContaAleatorio() {
       // AccountPersonService mockAccount = new AccountPersonService();
       // int numeroConta = Integer.parseInt(AccountPersonService.gerarContaAleatorio());


       // assertTrue(numeroConta >= 0 && numeroConta < 1000000, "O número da conta deve estar entre 0 e 999.999.");


    //}

   // @Test
    //void getAll() {
       // List<AccountPerson> mockAccounts = new ArrayList<>();
       // mockAccounts.add(new AccountPerson(1L, AccountType.CPF_CONTA_PESSOA_FISICA,
              //  "123456789",
             //   null,
              //  12345,
              //  678,
              //  "João Silva",
              //  30,
              //  "Rua A, 123",
              //  1000.0f,
              //  500.0f));

       // when(repository.findAll()).thenReturn(mockAccounts);

     //   List<AccountPerson> accounts = service.getAll();

       // assertEquals(1, accounts.size());
       // verify(repository, times(1)).findAll();

       // when(repository.findAll()).thenReturn(Collections.emptyList());
       // accounts = service.getAll();
      //  assertTrue(accounts.isEmpty());
       // verify(repository, times(2)).findAll();

   // }

   // @Test
   // void findById() throws AccountNotFoundException {
    //    Long id = 1L;

     //   AccountPerson mockAccount = new AccountPerson();
      //  mockAccount.setId(id);
      //  mockAccount.setDocumentNumber("123456789");

     //   when(repository.findById(id)).thenReturn(java.util.Optional.of(mockAccount));

     //   AccountPerson account = service.findById(id);

      //  verify(repository, times(1)).findById(id);

       // assertNotNull(account);
       // assertEquals(mockAccount.getId(), account.getId());

   // }

   // @Test
  //  void notFindById() throws AccountNotFoundException {
     //   Long id = 1L;

     //   AccountPersonService accountService = new AccountPersonService();

      //  when(repository.findById(id)).thenReturn(java.util.Optional.empty());

      //  AccountNotFoundException exception = assertThrows(
           //     AccountNotFoundException.class,
            //    () -> service.findById(id),
            //    "Conta não encontrada: " + id
      //  );
       // assertFalse(repository.findById(id).isPresent());
   // }


   // @Test
   // void deleteAccount() throws AccountNotFoundException {

     //   Long id = 1L;
      //  AccountPerson mockAccount = new AccountPerson();
      //  mockAccount.setId(id);

     //   when(repository.findById(id)).thenReturn(java.util.Optional.of(mockAccount));

      //  service.deleteAccount(id);

      //  verify(repository, times(1)).deleteById(id);

  //  }

  //  @Test
   // void notFindDeleteAccount() throws AccountNotFoundException {
     //   Long id = 1L;

     //   AccountPersonService accountService = new AccountPersonService();

      //  when(repository.findById(id)).thenReturn(java.util.Optional.empty());

       // AccountNotFoundException exception = assertThrows(
       //         AccountNotFoundException.class,
       //         () -> service.deleteAccount(id),
           //     "Conta não encontrada: " + id
       // );
       // verify(repository, never()).deleteById(id);
   // }

   // @Test
   // void updateAccount() throws AccountNotFoundException, InvalidDocumentException {

      //  Long id = 1L;

      //  AccountPersonDTO accountDTO = new AccountPersonDTO();
      //  AccountPerson mockAccount = new AccountPerson();

      //  mockAccount.setId(id);
      //  when(repository.findById(id)).thenReturn(Optional.of(mockAccount));

      //  service.updateAccount(id, accountDTO);

       // verify(repository, times(1)).findById(id);
       // verify(repository, times(1)).save(any(AccountPerson.class));


   // }

   // @Test
  //  void notUpdateAccount() throws AccountNotFoundException {
      //  Long id = 1L;
      //  AccountPersonDTO accountDTO = new AccountPersonDTO();

      //  when(repository.findById(id)).thenReturn(java.util.Optional.empty());

      //  AccountNotFoundException exception = assertThrows(
         //       AccountNotFoundException.class,
            //    () -> service.updateAccount(id, accountDTO)

      //  );
       // assertEquals("Conta não encontrada: " + id, exception.getMessage());

       // verify(repository, never()).save(any());

   // }


   // @Test
  //  void getAgencia() {
  //  }

   // @Test
  //  void getPerson() {
  //  }

  //  @Test
  //  void getRepository() {
  //  }

   // @Test
  //  void setAgencia() {
  //  }

  //  @Test
  //  void setPerson() {
   // }

   // @Test
  //  void setRepository() {
//}
//}