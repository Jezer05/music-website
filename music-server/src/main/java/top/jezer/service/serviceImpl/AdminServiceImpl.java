package top.jezer.service.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.jezer.common.ErrorResp;
import top.jezer.controller.dto.AdminDTO;
import top.jezer.domain.Admin;
import top.jezer.exception.BusinessException;
import top.jezer.mapper.AdminMapper;
import top.jezer.service.AdminService;
import top.jezer.utils.TokenUtils;

@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    private AdminMapper adminMapper;
    @Override
    public AdminDTO login(AdminDTO adminDTO) {
        LambdaQueryWrapper<Admin> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Admin::getName, adminDTO.getUsername()).eq(Admin::getPassword, adminDTO.getPassword());
        Admin admin = adminMapper.selectOne(wrapper);
        // 未查到用户返回null
        if (null == admin) return null;
        adminDTO.setToken(TokenUtils.sign(admin));
        return adminDTO;
    }
}
