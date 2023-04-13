package top.jezer.service.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.jezer.domain.Consumer;
import top.jezer.mapper.ConsumerMapper;
import top.jezer.service.ConsumerService;

import java.util.List;

@Service
public class ConsumerServiceImpl implements ConsumerService {
    @Autowired
    private ConsumerMapper consumerMapper;
    //<editor-fold desc="增">

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
    //</editor-fold>

}
