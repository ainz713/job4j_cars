package ru.job4j.cars.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.job4j.cars.repository.BodyRep;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class GetBodyServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/plain");
        resp.setCharacterEncoding("windows-1251");
        int id = Integer.parseInt(req.getParameter("id"));
        var bodies = new BodyRep().findAllBodies(id);
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(bodies);
        PrintWriter writer = new PrintWriter(resp.getOutputStream());
        writer.println(json);
        writer.flush();
    }
}
