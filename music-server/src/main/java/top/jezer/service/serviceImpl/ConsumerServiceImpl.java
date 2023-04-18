package top.jezer.service.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.jezer.controller.dto.LoginDTO;
import top.jezer.domain.Consumer;
import top.jezer.mapper.ConsumerMapper;
import top.jezer.service.ConsumerService;
import top.jezer.utils.TokenUtils;

import java.util.List;

@Service
public class ConsumerServiceImpl implements ConsumerService {
    @Autowired
    private ConsumerMapper consumerMapper;
    //<editor-fold desc="增">
    @Override
    public boolean addConsumer(Consumer consumer) {
        return consumerMapper.insert(consumer) > 0;
    }
    //</editor-fold>

    //<editor-fold desc="删">

    //</editor-fold>

    //<editor-fold desc="改">

    //</editor-fold>

    //<editor-fold desc="查">
    @Override
    public List<Consumer> getAllConsumer() {
        return consumerMapper.selectList(null);
    }

    @Override
    public boolean deleteConsumer(Integer id) {
        return consumerMapper.deleteById(id) > 0;
    }

    @Override
    public boolean deleteConsumers(List<Integer> ids) {
        return consumerMapper.deleteBatchIds(ids) > 0;
    }

    @Override
    public LoginDTO login(LoginDTO loginDTO) {
        LambdaQueryWrapper<Consumer> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Consumer::getUsername, loginDTO.getUsername()).eq(Consumer::getPassword, loginDTO.getPassword());
        Consumer consumer = consumerMapper.selectOne(wrapper);
        if (consumer == null) return null;
        loginDTO.setId(consumer.getId());
        loginDTO.setAvatar(consumer.getAvatar());
        loginDTO.setToken(TokenUtils.sign(loginDTO));
        return loginDTO;
    }

    @Override
    public Consumer getConsumerByNameEq(String name) {
        LambdaQueryWrapper<Consumer> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Consumer::getUsername, name);
        return consumerMapper.selectOne(wrapper);
    }
    //</editor-fold>

}
