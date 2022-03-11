package com.paymybuddy.paymybuddy.controller;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import javax.annotation.security.RolesAllowed;

@RestController
public class LoginController {

    @RolesAllowed("USER")
    @RequestMapping("/**")
    public String getUser()
    {
        return "Welcome User";
    }

   /* @RolesAllowed({"USER","ADMIN"})
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


}
