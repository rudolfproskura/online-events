package online.events.security;


import javax.security.enterprise.authentication.mechanism.http.FormAuthenticationMechanismDefinition;
import javax.security.enterprise.authentication.mechanism.http.LoginToContinue;

@FormAuthenticationMechanismDefinition(
        loginToContinue = @LoginToContinue(errorPage = "/error.xhtml",
                loginPage = "/welcome.xhtml"))
public class HomeServlet {

}
