package pl.isa.autopartsJee.loginAndRegister.servlets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pl.isa.autopartsJee.loginAndRegister.dao.UsersRepositoryDao;
import pl.isa.autopartsJee.raportModule.dao.LogRepositoryDao;

import javax.inject.Inject;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private Logger logger = LoggerFactory.getLogger(LoginServlet.class.getName());
    @Inject
    LogRepositoryDao logRepositoryDao;
    @Inject
    UsersRepositoryDao usersRepositoryDao;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            req.login(req.getParameter("login"), req.getParameter("password"));
        } catch (ServletException e) {
            req.setAttribute("errorMessage", e.getMessage());
            RequestDispatcher requestDispatcher = req.getRequestDispatcher("/index.jsp");
            requestDispatcher.forward(req, resp);
            logger.error(e.getMessage(), e);
            logRepositoryDao.addSingleLog("Login error",
                    null, "login-error");
            return;
        }

        if (req.getHeader("Referer").contains("login.jsp")) {
            resp.sendRedirect("/index.jsp");
            logRepositoryDao.addSingleLog("User logged successfully",
                    (usersRepositoryDao.findUserByLogin(req.getParameter("login")).getId()), "login");
            return;
        }
        resp.sendRedirect(req.getHeader("Referer"));
    }
}
