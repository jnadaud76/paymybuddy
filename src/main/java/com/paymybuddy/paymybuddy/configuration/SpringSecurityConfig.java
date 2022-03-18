package com.paymybuddy.paymybuddy.configuration;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/*
@Configuration
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

  @Autowired
    private DataSource dataSource;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
       auth.inMemoryAuthentication()
                .withUser("test").password(passwordEncoder().encode("test")).roles("ADMIN");
        /*auth.jdbcAuthentication()
                .withDefaultSchema()
                .withUser(User.withUsername("email")
                        .password(passwordEncoder().encode("password"))
                        .roles("USER"));

    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests()
                .antMatchers("/person**").hasRole("ADMIN")
                .anyRequest().authenticated()
                .and()
                .httpBasic();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}*/


