package top.jezer.service;

import org.springframework.transaction.annotation.Transactional;
import top.jezer.domain.Consumer;

import java.util.List;

@Transactional
public interface ConsumerService {
    // 获得所有用户信息
    List<Consumer> getAllConsumer();
    // 删除用户
    boolean deleteConsumer(Integer id);
    boolean deleteConsumers(List<Integer> ids);
}
