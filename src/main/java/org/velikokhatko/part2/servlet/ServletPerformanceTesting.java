package org.velikokhatko.part2.servlet;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

public class ServletPerformanceTesting extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        try {
            Thread.sleep(100);
            resp.setStatus(200);
            PrintWriter writer = resp.getWriter();
            writer.print("{message: 'all is ok, thread №" + Thread.currentThread().getId() + "'}");
            writer.flush();
            writer.close();
        } catch (InterruptedException e) {
            resp.setStatus(500);
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
