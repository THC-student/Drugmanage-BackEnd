package com.example.drugmanage;

import com.example.pojo.User;
import com.example.utils.JwtUtils;
import com.example.utils.SHA256Utils;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DrugManageApplicationTests {
    @Autowired
    private SHA256Utils sha256Utils;
    @Autowired
    private JwtUtils jwtUtils;
    @Test
    void contextLoads() {
        System.out.println(sha256Utils.SHE256hash("222222"));

    }
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Test
    public void sendMessage() {
        String queueName = "exceptionSuggestQueue";
        String message = "hello, spring amqp!";
        rabbitTemplate.convertAndSend(queueName, message);
    }

}
