package com.mmall.service.impl;
import com.mmall.pojo.User;
import com.mmall.common.ServerResponse;
import com.mmall.dao.UserMapper;
import com.mmall.service.IUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("iUserService")
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public ServerResponse<User> login(String username, String password) {
        int reslutCount = userMapper.checkUsername(username);
        if (reslutCount == 0){
            return ServerResponse.createByErrorMessage("该用户不存在");
        }

//        todo password get md5

        User user = userMapper.selectLogin(username, password);
        if (user == null){
            return ServerResponse.createByErrorMessage("密码错误");
        }

        /**
         * 处理到这里都没有返回值说明登录成功了，将密码置为空然后返回给前端
         */

        user.setPassword(StringUtils.EMPTY);
        return ServerResponse.createBySuccess("登录成功", user);

    }
}
