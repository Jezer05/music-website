package top.jezer.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import top.jezer.service.serviceImpl.ConsumerServiceImpl;

import java.util.ArrayList;
import java.util.List;


@SpringBootTest
@Transactional
class ConsumerServiceTest {
    @Autowired
    private ConsumerServiceImpl consumerService;

    @Test
    void getAllConsumer() {
        System.out.println(consumerService.getAllConsumer().size());
    }

    @Test
    void deleteConsumer() {
        consumerService.deleteConsumer(1);
    }

    @Test
    void deleteConsumers() {
        List<Integer> ids = new ArrayList<>();
        ids.add(1);
        ids.add(2);
        consumerService.deleteConsumers(ids);
    }
}