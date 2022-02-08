package ru.job4j.cars.servlet;

import ru.job4j.cars.model.Item;
import ru.job4j.cars.repository.HbmStore;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ChangeStatusServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        int id = Integer.parseInt(req.getParameter("id"));
        boolean check = Boolean.parseBoolean(req.getParameter("check"));
        Item post = HbmStore.instOf().findById(Item.class, id);
        post.setStatus(check);
        HbmStore.instOf().update(post);
    }
}
