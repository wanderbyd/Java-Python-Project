//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//
//@Configuration
//@EnableWebSecurity
//public class SecurityConfig extends WebSecurityConfigurerAdapter {
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.authorizeRequests()
//            .antMatchers("/homebook").authenticated()
//            .antMatchers("/homebook/**").access("hasRole('ROLE_USER') and #username == authentication.principal.username")
//            .anyRequest().permitAll()
//            .and()
//            .formLogin()
//            .loginPage("/login")
//            .defaultSuccessURL("/homebook", true)
//            .permitAll()
//            .and()
//            .logout()
//            .logoutUrl("/logout")
//            .logoutSuccessUrl("/login")
//            .permitAll();
//    }
//}





//package com.example.jpatest.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//
//@Configuration
//@EnableWebSecurity
//public class SecurityConfig extends WebSecurityConfigurerAdapter {
//
//	@Bean
//	public PasswordEncoder passwordEncoder() {
//		return new BCryptPasswordEncoder();
//	}
//
//	@Override
//	protected void configure(HttpSecurity http) throws Exception {
//		http.authorizeRequests().antMatchers("/homebook").authenticated() // /homebook 요청은 인증된 사용자에게만 허용
//				.anyRequest().permitAll() // 그 외 요청은 모두 허용
//				.and().formLogin().loginPage("/login") // 로그인 페이지 URL 지정
//				.defaultSuccessURL("/homebook", true) // 로그인 성공 후 이동할 페이지
//				.permitAll().and().logout().logoutUrl("/logout") // 로그아웃 URL 지정
//				.logoutSuccessUrl("/login") // 로그아웃 후 이동할 페이지
//				.permitAll();
//	}
//
//	// 사용자 정보를 가져오는 데 사용될 UserDetailsService 빈을 등록
//	@Bean
//	public UserDetailsService userDetailsService() {
//		return new CustomUserDetailsService();
//	}
//}
