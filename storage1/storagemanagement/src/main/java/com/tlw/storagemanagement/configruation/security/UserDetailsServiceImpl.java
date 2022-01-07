//package com.tlw.storagemanagement.configruation.security;
//
//import com.tlw.storagemanagement.entity.User;
//import com.tlw.storagemanagement.entity.UserRole;
//import com.tlw.storagemanagement.mapper.UserMapper;
//import com.tlw.storagemanagement.mapper.UserRoleMapper;
//import lombok.SneakyThrows;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.ArrayList;
//import java.util.Collection;
//import java.util.List;
//
//@Service
//public class UserDetailsServiceImpl implements UserDetailsService {
//    @Autowired
//    private UserMapper userMapper;
//    @Autowired
//    private UserRoleMapper userRoleMapper;
//
////    /**
////     * 方式二
////     * @param name
////     * @return
////     * @throws UsernameNotFoundException
////     */
////    @Override
////    public UserDetails loadUserByUsername(String name)
////            throws UsernameNotFoundException {
////        UserDetails userDetails = null;
////        try {
////            User user = userMapper.findByUsername(name);
////            if(user != null) {
////                List<UserRole> urs = userRoleMapper.findByUserId(user.getId());
////                Collection<GrantedAuthority> authorities = new ArrayList<>();
////                for(UserRole ur : urs) {
////                    String roleName = ur.getRole().getName();
////                    SimpleGrantedAuthority grant = new SimpleGrantedAuthority(roleName);
////                    authorities.add(grant);
////                }
////                //封装自定义UserDetails类
////                userDetails = new UserDetailsImpl(user, authorities);
////            } else {
////                throw new UsernameNotFoundException("该用户不存在！");
////            }
////        } catch (Exception e) {
////            e.printStackTrace();
////        }
////        return userDetails;
////    }
//
//    /**
//     * 方式三
//     *
//     * @param name
//     * @return
//     * @throws UsernameNotFoundException
//     */
//    @Override
//    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
//        //获取数据库用户信息
//        User user = userMapper.findByUsername(name);
//        //获取数据库角色信息
//        List<UserRole> urs = null;
//        try {
//            urs = userRoleMapper.findByUserId(user.getId());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return changeToUser(user, urs);
//    }
//
//    private UserDetails changeToUser(User dbUser, List<UserRole> roleList) {
//        UserDetails userDetails = null;
//        //权限列表
//        List<GrantedAuthority> authorityList = new ArrayList<>();
//        //赋予查询到的角色
//        for (UserRole role : roleList) {
//            String roleName = role.getRole().getName();
//            GrantedAuthority authority = new SimpleGrantedAuthority(roleName);
//            authorityList.add(authority);
//            //创建UserDetails 对象，设置用户名、密码和权限
//            PasswordEncoder passwordEncoder = new BCryptPasswordEncoder() ;
//            userDetails = new org.springframework.security.core.userdetails.User(dbUser.getUserName(), passwordEncoder.encode(dbUser.getPassWord()), authorityList);
//        }
//        return userDetails;
//    }
//}
