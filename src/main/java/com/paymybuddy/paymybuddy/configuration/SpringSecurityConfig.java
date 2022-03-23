package com.paymybuddy.paymybuddy.configuration;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;


@Configuration
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

  /*@Bean
    public MyUserDetailsService userDetailsService() {
      return new MyUserDetailsService();
  }*/
  @Autowired
  private DataSource dataSource;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
     auth.jdbcAuthentication()
             .dataSource(dataSource)
             .usersByUsernameQuery("select email,password,enable from person where email = ?")
             .passwordEncoder(new BCryptPasswordEncoder())
             .authoritiesByUsernameQuery("select email, 'ROLE_USER' from person where email=?");


    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
                .authorizeRequests()
                .antMatchers("/login").permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic();
       /* http.cors().and().authorizeRequests()
                .antMatchers("/index.html", "/", "/home", "/login").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                //.loginProcessingUrl("http://localhost:4200/")
                .loginPage("/login")
                .usernameParameter("email")
                .passwordParameter("password")
                .permitAll()*/
        ;

        http.authorizeHttpRequests()
                .antMatchers("/person")
                        .permitAll()
                .and().httpBasic();;
       http.csrf()
                .ignoringAntMatchers("/person/**");



    }


}


