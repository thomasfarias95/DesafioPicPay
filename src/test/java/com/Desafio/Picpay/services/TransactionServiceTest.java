package com.Desafio.Picpay.services;


import com.Desafio.Picpay.domain.user.User;
import com.Desafio.Picpay.domain.user.UserType;
import com.Desafio.Picpay.dtos.TransactionDTO;
import com.Desafio.Picpay.repositories.TransactionRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.stubbing.OngoingStubbing;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class TransactionServiceTest {

    @Mock
    private UserService userService;

    @Mock
    private TransactionRepository repository;

    @Mock
    private AuthorizationService authService;

    @Mock
    private  NotificationService notificationService;

    @Autowired
    @InjectMocks
    private TransactionService transactionService;

    @BeforeEach
    void setup(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    @DisplayName("Should create transaction successfully when everything is ok")
    void createTransactionCase1()throws Exception {
        User sender = new User(1L,"thomas","farias","10915958495", new BigDecimal(60), "thomasfarias@hotmail.com","21049511", UserType.COMMON);
        User receiver = new User(2L, "Anderson", "Silva", "15859395385",new BigDecimal(550),"thomasfarias@gmail.com","21049511", UserType.MERCHANT);

         when(userService.findUserById(1L)).thenReturn(sender);
         when(userService.findUserById(2L)).thenReturn(receiver);

        when(authService.authorizeTransaction(any(), any())).thenReturn(true);

        TransactionDTO request = new TransactionDTO(new BigDecimal(10), 1L,2l);
        transactionService.createTransaction(request);

        verify(repository, times(1)).save(any());

        sender.setBalance(new BigDecimal(0));
        verify(userService, times(1)).saveUser(sender);


        receiver.setBalance(new BigDecimal(20));
        verify(userService,times(1)).saveUser(receiver);

        verify(notificationService, times(1)).sendNotification(sender, "transação realizada com sucesso");
        verify(notificationService, times(1)).sendNotification(receiver, "transação recebida com sucesso");

    }

    @Test
    @DisplayName("Should throw Exception when transaction is not allowed")
    void createTransactionCase2() throws Exception {
        User sender = new User(1L,"thomas","farias","10915958495", new BigDecimal(60), "thomasfarias@hotmail.com","21049511", UserType.COMMON);
        User receiver = new User(2L, "Anderson", "Silva", "15859395385",new BigDecimal(550),"thomasfarias@gmail.com","21049511", UserType.MERCHANT);

        when(userService.findUserById(1L)).thenReturn(sender);
        when(userService.findUserById(2L)).thenReturn(receiver);

        when(authService.authorizeTransaction(any(), any())).thenReturn(false);
        
        Exception thrown = Assertions.assertThrows(Exception.class, () ->{
            TransactionDTO request = new TransactionDTO(new BigDecimal(10),1L, 2L);
            transactionService.createTransaction(request);


        });

        Assertions.assertEquals("Transação não autorizada", thrown.getMessage());
    }
}