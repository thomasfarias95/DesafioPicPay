package com.Desafio.Picpay.repositories;

import com.Desafio.Picpay.domain.user.User;
import com.Desafio.Picpay.domain.user.UserType;
import com.Desafio.Picpay.dtos.UserDTO;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;
import java.math.BigDecimal;



@DataJpaTest
@ActiveProfiles("test")
class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;


    @Autowired
    EntityManager entityManager;

    @Test
    @DisplayName("Should get User syccessfully from DB")
    void findUserByDocumentCase1() {
        String document = "10915855498";
        UserDTO data = new UserDTO("thomas","Farias", document, new BigDecimal(10), "thomasfarias@gmail.com", "95112104", UserType.COMMON);
        this.createUser(data);

        Optional<User> result = this.userRepository.findUserByDocument(document);

        assertThat(result.isPresent()).isTrue();
    }

    @Test
    @DisplayName("Should  not get User  from DB when user not exists")
    void findUserByDocumentCase2() {
        String document = "10915855498";

        Optional<User> result = this.userRepository.findUserByDocument(document);

        assertThat(result.isEmpty()).isTrue();
    }

    private User createUser(UserDTO data){
        User newUser = new User(data);
        this.entityManager.persist(newUser);
        return newUser;
    }
}