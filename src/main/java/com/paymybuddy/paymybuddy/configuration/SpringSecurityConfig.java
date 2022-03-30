package com.paymybuddy.paymybuddy.configuration;


import com.paymybuddy.paymybuddy.service.MyUserDetailsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
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

  @Autowired
    private MyUserDetailsService userDetailsService;

 /* @Autowired
  private DataSource dataSource;*/

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //auth.authenticationProvider(authenticationProvider());
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    /* auth.jdbcAuthentication()
             .dataSource(dataSource)
             .usersByUsernameQuery("select email,password,enable from person where email = ?")
             .passwordEncoder(new BCryptPasswordEncoder())
             .authoritiesByUsernameQuery("select email, 'ROLE_USER' from person where email=?");*/


    }
    /*@Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider
                = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }*/

    @Override
    public void configure(HttpSecurity http) throws Exception {
        //http.cors().and().csrf().disable()
        http.cors().disable();
        http.csrf().disable()
                .authorizeRequests()
                //.antMatchers("/registration", "/person**", "/transactions**", "transactions/**").permitAll()
                .antMatchers("/**").permitAll()
                //.fullyAuthenticated()
                //.and()
                //.httpBasic()
                //.antMatchers("/login", "/person**", "/registration", "/email").permitAll()
                //.anyRequest()
                //.authenticated()
                //.and().formLogin()
                //.loginPage("/login")
                //.and()
                //.httpBasic()
                //.loginProcessingUrl("http://localhost:4200/dashboard")
                //.usernameParameter("email")
                //.passwordParameter("password")
                //.permitAll()
                //.and()
                //.httpBasic()

                //.loginPage("/login")
                //.permitAll()
                //.usernameParameter("username")
                //.passwordParameter("password")
                //.permitAll()
                //
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

       /* http.authorizeHttpRequests()
                .antMatchers("/person")
                        .permitAll()
                .and().httpBasic();;
       http.csrf()
                .ignoringAntMatchers("/person/**");*/



    }


}


