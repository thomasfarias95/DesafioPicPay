package com.Desafio.Picpay.services;


import com.Desafio.Picpay.domain.user.User;
import com.Desafio.Picpay.domain.user.UserType;
import com.Desafio.Picpay.dtos.UserDTO;
import com.Desafio.Picpay.repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.math.BigDecimal;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;


    public void validateTransaction(User sender, BigDecimal amount)throws Exception{
        if (sender.getUserType() == UserType.MERCHANT){
            throw new Exception("Usuário do tipo Logista não está autorizado a realizar transações");
        }
        if (sender.getBalance().compareTo(amount) <0){
            throw new Exception("Saldo insuficiente");
        }
    }

    public  User findUserById(Long id) throws Exception{
        return this.repository.findUserById(id).orElseThrow(() -> new Exception("Usuario não encontrado"));
    }

    public User createUser(@RequestBody UserDTO data){
        User newUser = new User(data);
        this.saveUser(newUser);
        return newUser;
    }
    public List<User> getAllUsers(){
       return this.repository.findAll();

    }

    public  void saveUser(User user){
        this.repository.save(user);
    }
}
