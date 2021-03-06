package pl.isa.autopartsJee.loginAndRegister.servlets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pl.isa.autopartsJee.loginAndRegister.dao.UsersRepositoryDao;
import pl.isa.autopartsJee.adminPanel.raportModule.rest.LogRequest;
//import pl.isa.autopartsJee.adminPanel.raportModule.dao.LogRepositoryDao;

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
    LogRequest logRequest;
    @Inject
    UsersRepositoryDao usersRepositoryDao;
    private boolean login;
    private String errorMessage;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doRecive(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (login == true) {
            RequestDispatcher requestDispatcher = req.getRequestDispatcher("/index.jsp");
            requestDispatcher.forward(req, resp);
            return;
        } else {
            req.setAttribute("errorMessage", errorMessage);
            RequestDispatcher requestDispatcher = req.getRequestDispatcher("/login.jsp");
            requestDispatcher.forward(req, resp);

            return;
        }
    }

    private void doRecive(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            req.login(req.getParameter("login"), req.getParameter("password"));
            logRequest.createLog("logged-in",
                    (usersRepositoryDao.findUserByLogin(req.getParameter("login")).getId()), "login");
            login = true;
            resp.sendRedirect("/index.jsp");
        } catch (ServletException e) {
            errorMessage = e.getMessage();
            req.setAttribute("errorMessage", errorMessage);
            RequestDispatcher requestDispatcher = req.getRequestDispatcher("/login.jsp");
            requestDispatcher.forward(req, resp);
            logger.error(e.getMessage(), e);
            logRequest.createLog("login-error",
                    null, "login");
            login = false;
            return;
        }

//        if (req.getHeader("Referer").contains("login.jsp")) {
//            resp.sendRedirect("/index.jsp");
//
//            return;
//        }
        resp.sendRedirect(req.getHeader("Referer"));
    }
}
