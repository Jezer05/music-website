package top.jezer.service.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.jezer.controller.dto.LoginDTO;
import top.jezer.domain.Admin;
import top.jezer.mapper.AdminMapper;
import top.jezer.service.AdminService;
import top.jezer.utils.TokenUtils;

@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    private AdminMapper adminMapper;
    @Override
    public LoginDTO login(LoginDTO loginDTO) {
        LambdaQueryWrapper<Admin> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Admin::getName, loginDTO.getUsername()).eq(Admin::getPassword, loginDTO.getPassword());
        Admin admin = adminMapper.selectOne(wrapper);
        // 未查到用户返回null
        if (null == admin) return null;
        loginDTO.setId(admin.getId());
        loginDTO.setToken(TokenUtils.sign(loginDTO));
        return loginDTO;
    }
}
