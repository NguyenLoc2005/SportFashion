package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.ProductDAO;
import model.Product;


@WebServlet("/clientHome")
public class ClientHomeServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html; charset=UTF-8");

        ProductDAO productDAO = new ProductDAO();

        String action = req.getParameter("action");
        if (action == null) action = "list";

        switch (action) {

            case "detail-product": {
                int id = Integer.parseInt(req.getParameter("id"));
                Product product = productDAO.getProductById(id);

                req.setAttribute("product", product);
                req.getRequestDispatcher("detail-product.jsp").forward(req, resp);
                break;
            }

            case "filter": {
                String type = req.getParameter("type");
                List<Product> products = productDAO.searchFilterProduct(type);
                req.setAttribute("products", products);
                req.getRequestDispatcher("index.jsp").forward(req, resp);
                break;
            }

            case "search": {
                String name = req.getParameter("name");
                List<Product> products = productDAO.searchProduct(name);
                req.setAttribute("products", products);
                req.getRequestDispatcher("index.jsp").forward(req, resp);
                break;
            }

            case "list":
            default: {
                List<Product> products = productDAO.getAllProducts();
                req.setAttribute("products", products);
                req.getRequestDispatcher("index.jsp").forward(req, resp);
                break;
            }
        }
    }
}
