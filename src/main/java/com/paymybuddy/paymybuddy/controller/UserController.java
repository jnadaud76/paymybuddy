package com.paymybuddy.paymybuddy.controller;


import com.paymybuddy.paymybuddy.dto.PersonLoginDto;
import com.paymybuddy.paymybuddy.service.MyUserDetailsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.security.Principal;

@RestController
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
public class UserController {


    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    private MyUserDetailsService myUserDetailsService;

   /* @PostMapping("/login")
    public boolean login(@RequestBody PersonLoginDto user) {
        //String encodedPassword = passwordEncoder().encode(user.getPassword());
        try {
          UserDetails userDetails = myUserDetailsService.loadUserByUsername(user.getEmail());
          return user.getEmail().equals(userDetails.getUsername()) && user.getPassword().equals(userDetails.getPassword());
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        }*/

    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestBody PersonLoginDto user) {
        //String encodedPassword = passwordEncoder().encode(user.getPassword());

            User userDetails = (User) myUserDetailsService.loadUserByUsername(user.getEmail());
            if (user.getEmail().equals(userDetails.getUsername())
                    && user.getPassword().equals(userDetails.getPassword())) {
                return new ResponseEntity<>(userDetails, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }


    }


    @GetMapping("/user")
    public Principal user (Principal user) {
        return user;
    }
}

    /*@RequestMapping("/user")
    public Principal user(HttpServletRequest request) {
        String authToken = request.getHeader("Authorization")
                .substring("Basic".length()).trim();
        return () ->  new String(Base64.getDecoder()
                .decode(authToken)).split(":")[0];
    }
}*/
    /*@RequestMapping("/user")
    public String getUser() {
        return "Welcome User";
    }*/

    /*@RequestMapping("/user")
    public Principal user(Principal user) {
        return user;
    }*/

   /* @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String loginPage(Model model) {

        return "http://localhost:4200/login";
    }*/

/*
@RestController
public class LoginController {

    @RolesAllowed("USER")
    @RequestMapping("/user")
    public String getUser() {
        ;
    }

    @RolesAllowed({"USER","ADMIN"})
    @RequestMapping("/admin")
    public String getAdmin()
    {
        return "Welcome Admin";
    }

    @RequestMapping("/*")
    public String getUserInfo(Principal user) {
        StringBuffer userInfo= new StringBuffer();
        if(user instanceof UsernamePasswordAuthenticationToken){
            userInfo.append(getUsernamePasswordLoginInfo(user));
        }
        else if(user instanceof OAuth2AuthenticationToken){
            userInfo.append(getOauth2LoginInfo(user));
        }
        return userInfo.toString();
    }

    private StringBuffer getUsernamePasswordLoginInfo(Principal user)
    {
        StringBuffer usernameInfo = new StringBuffer();

        UsernamePasswordAuthenticationToken token = ((UsernamePasswordAuthenticationToken) user);
        if(token.isAuthenticated()){
            User u = (User) token.getPrincipal();
            usernameInfo.append("Welcome, " + u.getUsername());
        }
        else{
            usernameInfo.append("NA");
        }
        return usernameInfo;
    }

    private StringBuffer getOauth2LoginInfo(Principal user){

        StringBuffer protectedInfo = new StringBuffer();

        OAuth2AuthenticationToken authToken = ((OAuth2AuthenticationToken) user);
        OAuth2User principal = ((OAuth2AuthenticationToken)user).getPrincipal();
        OAuth2AuthorizedClient authClient = this.authorizedClientService.loadAuthorizedClient(authToken.getAuthorizedClientRegistrationId(), authToken.getName());
        if(authToken.isAuthenticated()){

            Map<String,Object> userAttributes = ((DefaultOAuth2User) authToken.getPrincipal()).getAttributes();

            String userToken = authClient.getAccessToken().getTokenValue();
            protectedInfo.append("Welcome, " + userAttributes.get("name")+"<br><br>");
            protectedInfo.append("e-mail: " + userAttributes.get("email")+"<br><br>");
            protectedInfo.append("Access Token: " + userToken+"<br><br>");

            OidcIdToken idToken = getIdToken(principal);

            if(idToken != null) {

                protectedInfo.append("idToken value: " + idToken.getTokenValue()+"<br><br>");
                protectedInfo.append("Token mapped values <br><br>");

                Map<String, Object> claims = idToken.getClaims();

                for (String key : claims.keySet()) {
                    protectedInfo.append("  " + key + ": " + claims.get(key)+"<br>");
                }
            }
        }
        return protectedInfo;
    }

    private OidcIdToken getIdToken(OAuth2User principal){
        if(principal instanceof DefaultOidcUser) {
            DefaultOidcUser oidcUser = (DefaultOidcUser)principal;
            return oidcUser.getIdToken();
        }
        return null;
    }*/



