package com.example.shopping_mall.config;

import com.example.shopping_mall.Service.AuthServiceImpl;
import com.example.shopping_mall.filter.LoginVerifyCodeFilter;
import com.example.shopping_mall.mapper.UserMapper;
import com.example.shopping_mall.repo.RedisTokenRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import javax.annotation.Resource;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Resource
    AuthServiceImpl service;

    @Resource
    RedisTokenRepository repository;

    @Resource
    LoginVerifyCodeFilter verifyCodeFilter;

    @Resource
    UserMapper userMapper;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        /*http.addFilterBefore(verifyCodeFilter, UsernamePasswordAuthenticationFilter.class);*/
        http
                .authorizeRequests()   //首先需要配置哪些请求会被拦截，哪些请求必须具有什么角色才能访问
                .antMatchers("/api/auth/**").permitAll()
                .antMatchers("/api/**").permitAll()
                .and()
                .formLogin()
                .loginPage("/api/auth/access-deny")//默认的登录页路径
                .loginProcessingUrl("/api/auth/login")//处理登录请求的接口
                /*.successHandler(this::onAuthenticationSuccess)*/
                .successForwardUrl("/api/auth/login-success")
                .failureForwardUrl("/api/auth/login-failure")
                .and()
                .logout()
                .logoutUrl("/api/auth/logout")
                .logoutSuccessUrl("/api/auth/logout-success")
                // 关闭CSRF
                .and()
                .csrf()
                .disable()
                .rememberMe()   //开启记住我功能
                .rememberMeParameter("remember")  //登陆请求表单中需要携带的参数，如果携带，那么本次登陆会被记住
                .tokenRepository(repository)  //这里使用的是直接在内存中保存的TokenRepository实现
                //TokenRepository有很多种实现，InMemoryTokenRepositoryImpl直接基于Map实现的，缺点就是占内存、服务器重启后记住我功能将失效
                //后面我们还会讲解如何使用数据库来持久化保存Token信息
                /*.tokenValiditySeconds(60*2)  //Token的有效时间（秒）默认为14天;单位 秒*/
        ;
        //开启跨域访问
        /*http.cors().disable();*/
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(service)
                .passwordEncoder(new BCryptPasswordEncoder());
        //这里使用SpringSecurity提供的BCryptPasswordEncoder
    }
    @Bean
    public CorsFilter corsFilter(){
        //创建CorsConfiguration对象后添加配置
        CorsConfiguration config = new CorsConfiguration();
        //设置放行哪些原始域，这里直接设置为所有
        config.addAllowedOriginPattern("*");
        //你可以单独设置放行哪些原始域 config.addAllowedOrigin("http://localhost:2222");
        //放行哪些原始请求头部信息
        config.addAllowedHeader("*");
        //放行哪些请求方式，*代表所有
        config.addAllowedMethod("*");
        //是否允许发送Cookie，必须要开启，因为我们的JSESSIONID需要在Cookie中携带
        config.setAllowCredentials(true);
        //映射路径
        UrlBasedCorsConfigurationSource corsConfigurationSource = new UrlBasedCorsConfigurationSource();
        corsConfigurationSource.registerCorsConfiguration("/**", config);
        //返回CorsFilter
        return new CorsFilter(corsConfigurationSource);
    }

    /*public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        ShoppingUser user = userMapper.getPasswordByUsername(authentication.getName());//验证信息
        if (user.getRole().equals("user")){
            httpServletResponse.sendRedirect("/api/user/index");//接口路径
        }
        if (user.getRole().equals("shop")){
            httpServletResponse.sendRedirect("/api/shop/index");//接口路径
        }
        if (user.getRole().equals("admin")){
            httpServletResponse.sendRedirect("/api/admin/index");//接口路径
        }
    }*/
}
