package com.paymybuddy.paymybuddy.configuration;


import com.paymybuddy.paymybuddy.service.MyUserDetailsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

import javax.sql.DataSource;


@Configuration
@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true, jsr250Enabled = true)
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
        //http.cors().disable();
        //http.csrf().disable()
        //http
                //.authorizeRequests()
                //.antMatchers("/registration", "/person**").permitAll()
                //.antMatchers( "/registration", "/login", "/person", "/user", "/").permitAll()
                //.antMatchers("/user", "/person").permitAll()
                //.antMatchers( "/", "/*", "/**").authenticated();
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

        //Configuration à garder
     /*http.cors().disable();
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers( "/**").permitAll()
                .and()
                .httpBasic();*/
        http.csrf().disable();
        //http.exceptionHandling().authenticationEntryPoint(new RestAuthenticationEntryPoint());
        http

                //.cors().configurationSource(corsConfigurationSource())
                //.and()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/api/person**").permitAll()
                .anyRequest().authenticated()
                //.and()
               // .rememberMe().userDetailsService(this.userDetailsService)
                //.key("Xgt124589*-thHHfhotfR")
                //.tokenValiditySeconds(86400)
                .and()
        //.formLogin()
                //.loginPage("/login").permitAll()
                //.successForwardUrl("/api")
                //.defaultSuccessUrl("http://localhost:4200/dashboard", true)
                //.successHandler(appAuthenticationSuccessHandler())
                //.and().logout().clearAuthentication(true)
                //.and()
                .httpBasic();


                //.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        //.and().httpBasic();
                //.anyRequest().authenticated()
               // .and()
                //.formLogin()
               // .loginPage("/").permitAll()
                //.defaultSuccessUrl("http://localhost:4200/dashboard")
                //.permitAll()
                //.and()
               // .logout();
                //.and()
                //.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                //.and()
                //.httpBasic();
        //.and()
                //.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
               //.and().httpBasic();
               // .and().formLogin()
                //.loginPage("/login").permitAll()
              // .successForwardUrl("/dashboard")
              // .defaultSuccessUrl("http://localhost:4200/dashboard", true)
              // .permitAll()
              //.successHandler(appAuthenticationSuccessHandler())
                //.and().logout().clearAuthentication(true);



                 //loginPage("/")
                //.failureForwardUrl()
                //.successForwardUrl()
                //.successHandler()
                //.defaultSuccessUrl("/dashboard", true)
                //.usernameParameter("email")
                //.and()
                //.logout();

    }
    @Bean
    CorsConfigurationSource corsConfigurationSource() {

        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:4200"));
        configuration.setAllowedMethods(Arrays.asList("*"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        //configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource ();
        source.registerCorsConfiguration("/**", configuration);
        return source;

    }

    @Bean
    public AuthenticationSuccessHandler appAuthenticationSuccessHandler(){
        return new MySavedRequestAwareAuthenticationSuccessHandler();
    }

}


