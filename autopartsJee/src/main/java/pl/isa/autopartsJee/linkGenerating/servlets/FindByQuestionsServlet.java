package pl.isa.autopartsJee.linkGenerating.servlets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.isa.autoparts.questions.*;
import pl.isa.autopartsJee.linkGenerating.dao.FindQuestionDao;
import pl.isa.autopartsJee.linkGenerating.dao.TreeOperationsRepositoryDao;
import pl.isa.autopartsJee.loginAndRegister.dao.UsersRepositoryDao;
import pl.isa.autopartsJee.adminPanel.raportModule.rest.LogRequest;
//import pl.isa.autopartsJee.adminPanel.raportModule.dao.LogRepositoryDao;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("find-questions")
public class FindByQuestionsServlet extends HttpServlet {
    @Inject
    LogRequest logRequest;

    public FindByQuestionsServlet() throws IOException {
    }

    @Inject
    FindQuestionDao findQuestionDao;
    private static String step;
    private Logger logger = LoggerFactory.getLogger(FindByQuestionsServlet.class.getName());
    private WebFunctions webFunctions = new WebFunctions();

    private void doRecive(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {

        logRequest.createLog("searching-form",
                ((Long) req.getSession().getAttribute("userId")), "link-generation");

        try {
            /**     Step 1   */
            if (req.getParameter("step").equals("1")) {
                logger.debug("Web: invoke step 1");
                step = req.getParameter("step");

                req.getSession().setAttribute("groupQuestions", findQuestionDao.getQuestiongroup());
                RequestDispatcher requestDispatcher = req.getRequestDispatcher("/find-category-by-form-step1.jsp");
                requestDispatcher.forward(req, resp);

                /**     Step 2   */
            } else if (req.getParameter("step").equals("2")) {
                logger.debug("Web: invoke step 2");
                step = req.getParameter("step");
                String getParam = req.getParameter("selected_1");

                req.getSession().setAttribute("questions", findQuestionDao.getQuestionDao(getParam, webFunctions));
                req.getSession().setAttribute("selected", getParam);
                RequestDispatcher requestDispatcher = req.getRequestDispatcher("/find-category-by-form-step2.jsp");
                requestDispatcher.forward(req, resp);

                /**     Step 3   */
            } else if (req.getParameter("step").equals("3")) {
                logger.debug("Web: invoke step 3");
                step = req.getParameter("step");
                String getParam = req.getParameter("selected_2");

                req.getSession().setAttribute("selected", getParam);
                req.getSession().setAttribute("breakDown", findQuestionDao.getBreakDownDao(getParam, webFunctions));
                RequestDispatcher requestDispatcher = req.getRequestDispatcher("/find-category-by-form-step3.jsp");
                requestDispatcher.forward(req, resp);

                /**     Step 4   */
            } else if (req.getParameter("step").equals("4")) {
                logger.debug("Web: invoke step 4");
                step = req.getParameter("step");
                String getParam = req.getParameter("selected_3");

                req.getSession().setAttribute("parts", findQuestionDao.getPartsDao(getParam, webFunctions));
                req.getSession().setAttribute("selected", getParam);
                RequestDispatcher requestDispatcher = req.getRequestDispatcher("/find-category-by-form-step4.jsp");
                requestDispatcher.forward(req, resp);
            }
        } catch (NullPointerException e) {
            logger.info("language changed");

            RequestDispatcher requestDispatcher = req.
                    getRequestDispatcher("/find-category-by-form-step" + step + ".jsp");
            requestDispatcher.forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        doRecive(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        doRecive(req, resp);
    }
}
