package com.Desafio.Picpay.services;


import com.Desafio.Picpay.domain.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.Map;

@Service
public class AuthorizationService {

    @Autowired
    private RestTemplate restTemplate;

    public boolean authorizeTransaction(User sender, BigDecimal value){
        ResponseEntity<Map> authotizationResponse= restTemplate.getForEntity("https://util.devi.tools/api/v2/authorize", Map.class );
        if(authotizationResponse.getStatusCode() == HttpStatus.OK ){
            String message = (String) authotizationResponse.getBody().get("message");
            return "Autorizado".equalsIgnoreCase(message);
        }else return false;
    }
}
