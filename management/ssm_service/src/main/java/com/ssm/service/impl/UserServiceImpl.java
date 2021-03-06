package com.ssm.service.impl;

import com.ssm.dao.IUserDao;
import com.ssm.domain.Role;
import com.ssm.domain.UserInfo;
import com.ssm.service.IUserService;
import com.ssm.utils.BCryptPasswordEncoderUtils;
import org.springframework.beans.SimpleTypeConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service("userService")
@Transactional
public class UserServiceImpl implements IUserService {
    @Autowired
    private IUserDao userDao;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserInfo userInfo=null;
        try {
             userInfo = userDao.findByUsername(username);
        } catch (Exception e) {
            e.printStackTrace();
        }
       // User user=new User(userInfo.getUsername(),"{noop}"+userInfo.getPassword(),getAuthority());
        User user=new User(userInfo.getUsername(),userInfo.getPassword(),userInfo.getStatus() == 0?false:true,true,true,true,getAuthority(userInfo.getRoles()));
        return user;
    }
    public List<SimpleGrantedAuthority> getAuthority(List<Role> roles){
        List<SimpleGrantedAuthority> list =new ArrayList<>();
        for (Role role : roles) {
            list.add(new SimpleGrantedAuthority("ROLE_"+role.getRoleName()));
        }
        return list;
    }

    @Override
    public List<UserInfo> findAll() throws Exception {
        return userDao.findAll();
    }

    @Override
    public void save(UserInfo userInfo) throws Exception {
        //加密密码
        userInfo.setPassword(BCryptPasswordEncoderUtils.encodePassword(userInfo.getPassword()));
        userDao.save(userInfo);
    }

    @Override
    public UserInfo findById(String id) throws Exception {
        return userDao.findById(id);
    }

    @Override
    public List<Role> findOtherRoles(String userId) throws Exception {
        return userDao.findOtherRole(userId);
    }

    @Override
    public void addRoleToUSer(String userId, String roleId) throws Exception {
        userDao.addRoleToUser(userId,roleId);
    }
}
