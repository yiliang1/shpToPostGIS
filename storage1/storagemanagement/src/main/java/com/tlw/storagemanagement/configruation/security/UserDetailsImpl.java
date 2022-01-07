//package com.tlw.storagemanagement.configruation.security;
//
//import com.tlw.storagemanagement.entity.User;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//
//import java.util.Collection;
//
///**
// * 自定义用户身份信息
// * */
//public class UserDetailsImpl implements UserDetails {
//    // 用户信息
//    private User user;
//    // 用户角色
//    private Collection<? extends GrantedAuthority> authorities;
//
//    public UserDetailsImpl(User user, Collection<? extends GrantedAuthority> authorities) {
//        super();
//        this.user = user;
//        this.authorities = authorities;
//    }
//
//    /**
//     *
//     */
//    private static final long serialVersionUID = 1L;
//
//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//
//        return authorities;
//    }
//
//    @Override
//    public String getPassword() {
//        return this.user.getPassWord();
//    }
//
//    @Override
//    public String getUsername() {
//        return this.user.getUserName();
//    }
//
//    @Override
//    public boolean isAccountNonExpired() {
//        return this.user.getState().equals(User.STATE_ACCOUNTEXPIRED);
//    }
//
//    @Override
//    public boolean isAccountNonLocked() {
//        return this.user.getState().equals(User.STATE_LOCK);
//    }
//
//    @Override
//    public boolean isCredentialsNonExpired() {
//        return this.user.getState().equals(User.STATE_TOKENEXPIRED);
//    }
//
//    @Override
//    public boolean isEnabled() {
//        return this.user.getState().equals(User.STATE_NORMAL);
//    }
//
//}
