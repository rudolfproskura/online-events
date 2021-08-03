package online.events.security;


import javax.inject.Inject;
import javax.security.enterprise.SecurityContext;
import javax.security.enterprise.authentication.mechanism.http.FormAuthenticationMechanismDefinition;
import javax.security.enterprise.authentication.mechanism.http.LoginToContinue;
import javax.servlet.ServletException;
import javax.servlet.annotation.HttpConstraint;
import javax.servlet.annotation.ServletSecurity;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/home")
@FormAuthenticationMechanismDefinition(
        loginToContinue = @LoginToContinue(errorPage = "/error.xhtml",
                loginPage = "/login.xhtml"))
@ServletSecurity(value = @HttpConstraint(rolesAllowed = {"user", "organizer", "admin"}, transportGuarantee = ServletSecurity.TransportGuarantee.NONE))
public class HomeServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Inject
    private SecurityContext securityContext;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (securityContext.isCallerInRole("admin")) {
            response.sendRedirect("/online-events/secured/admin/upravljanjeDogadajima.xhtml");
        } else if (securityContext.isCallerInRole("organizer")) {
            response.sendRedirect("/online-events/secured/admin/upravljanjeDogadajima.xhtml");
        } else if (securityContext.isCallerInRole("user")) {
            response.sendRedirect("/online-events/secured/user/pregledDogadaja.xhtml");
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

}
