package com.Desafio.Picpay.domain.user;

import com.Desafio.Picpay.dtos.UserDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.math.BigDecimal;


@Entity(name = "users")
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")

public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    @Column(unique = true)
    private String document;
    @Column(unique = true)
    private String email;
    private String password;
    private BigDecimal balance;

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    @Enumerated(EnumType.STRING)
    private UserType userType;

    public UserType getUserType() {
        return userType;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public String getEmail() {
        return email;
    }



    public  User(UserDTO user){

        this.firstName = user.firstName();
        this.lastName = user.lastName();
        this.document = user.document();
        this.balance = user.balance();
        this.email = user.email();
        this.password = user.password();
        this.userType = user.userType();

    }
}
