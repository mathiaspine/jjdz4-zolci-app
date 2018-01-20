package pl.isa.autopartsJee.servlets;

import pl.isa.autoparts.categories.TreeOperations;
import pl.isa.autoparts.tools.LinkGenerator;
import pl.isa.autopartsJee.tools.WebLinkGenerator;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/find-by-name")
public class FindByNameServlet extends HttpServlet {
/*
* TODO: servlet ktory wykona sie jednoczesnie z jsp findByName ktory zbuduje obiekt TreeOperations i przechowa go w sesji,
* TODO następnie ten servlet go wczyta i przekaże do generateLink w argumencie dzięki czemu nie bedzie sie wczytywalo przy
* TODO kazdym wyszukaniu //inna sprawa ze i tak dziala dosc szybko
*/
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LinkGenerator linkGenerator = new LinkGenerator();
//        String link = linkGenerator.generateLink(req.getParameter("search"));
        Map<String, String> searchedLink = new HashMap<>();
        searchedLink.put(linkGenerator.generateLink(req.getParameter("search")), req.getParameter("search"));
        req.setAttribute("link", searchedLink);
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("/find-by-name-result.jsp");
        requestDispatcher.forward(req, resp);

    }
}