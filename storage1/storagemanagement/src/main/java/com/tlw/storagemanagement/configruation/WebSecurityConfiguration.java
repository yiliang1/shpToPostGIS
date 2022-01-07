//package com.tlw.storagemanagement.configruation;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.AuthenticationProvider;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//
//@Configuration
//@EnableWebSecurity
//public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {
//
//    @Autowired
//    private UserDetailsService userDetailsService;
//    @Autowired
//    private AuthenticationProvider securityProvider;
//
//    @Override
//    protected UserDetailsService userDetailsService() {
//        //自定义用户信息类
//        return this.userDetailsService;
//    }
//
////    @Override
////    protected void configure(HttpSecurity http) throws Exception {
////        http.authorizeRequests()
////                .antMatchers("/orders/**").hasRole("USER")    //用户权限
////                .antMatchers("/users/**").hasRole("ADMIN")    //管理员权限
////                .antMatchers("/login").permitAll()
////                .anyRequest().authenticated()
////                .and()
////                .formLogin()
////                .loginPage("/login")    //跳转登录页面的控制器，该地址要保证和表单提交的地址一致！
////                .permitAll()
////                .and()
////                .logout()
////                .permitAll()
////                .and()
////                .csrf().disable();        //暂时禁用CSRF，否则无法提交表单
////    }
//
////    //可以使用传统的控制器去映射，也可以使用新增的映射关系去完成，上面是通过增加控制器去映射的，在homeController中
////    //新增的映射关系在WebMVCConfiguration中的addViewControllers
////    @Override
////    protected void configure(HttpSecurity http) throws Exception {
////        http.authorizeRequests()
////                //限定”/user/** ”请求赋予角色ROLE_USER 或者ROLE_ADMIN
////                .antMatchers("/user/**").hasAnyRole("USER","ADMIN")
////                //限定”admin”下所有请求权限赋予角色ROLE_ADMIN
////                .antMatchers("/admin/**").hasAuthority("ROLE_ADMIN")
////                //其他路径允许签名后访问
////                .anyRequest().authenticated()
////                //对于没有配置权限的其他请求允许匿名访问
////                .and ().anonymous ()
////                //http基础认证
////                .and().httpBasic()
////                //设置登录页和默认的跳转路径
////                .and ().formLogin().loginPage("/login/page")
////                .defaultSuccessUrl ("/admin/welcomel")
////                 //登出页面和默认跳转路径
////                .and().logout().logoutUrl("/工口gout/page")
////                .logoutSuccessUrl("/welcome");
////    }
//
////    /**
////     * 方式一  重写该方法，添加自定义用户,基于内存,
////     * */
////    @Override
////    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
////        // 密码编码器
////        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder () ;
////        auth.inMemoryAuthentication()
////                .passwordEncoder(passwordEncoder)
////                .withUser("admin").password(passwordEncoder.encode("admin")).roles("ADMIN")
////                .and()
////                .withUser("terry").password(passwordEncoder.encode("terry")).roles("USER")
////                .and()
////                .withUser("larry").password(passwordEncoder.encode("larry")).roles("USER");
////    }
//
//
////    /**
////     * 方式二， 基于AuthenticationProvider
////     * @param auth
////     * @throws Exception
////     */
////    @Override
////    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
////        //自定义AuthenticationProvider
////        auth.authenticationProvider(securityProvider);
////    }
//
//    /**
//     * 方式三 基于userDetailsService
//     * @param auth
//     * @throws Exception
//     */
//    @Override
//    protected void configure (AuthenticationManagerBuilder auth) throws Exception {
//        //密码编码器
//        PasswordEncoder passwordEncoder =new BCryptPasswordEncoder();
//        //设置用户密码服务和密码编码器
//        auth.userDetailsService(userDetailsService)
//                .passwordEncoder(passwordEncoder) ;
//    }
//
//}
