package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.CartDAO;
import model.CartItem;
import model.User;

@WebServlet("/cart")
public class ClientCartServlet extends HttpServlet {

    private final CartDAO cartDAO = new CartDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

       
        HttpSession session = req.getSession(false);
        User user = (session == null) ? null : (User) session.getAttribute("user");
        if (user == null) {
            resp.sendRedirect("login.jsp");
            return;
        }

        int userId = user.getId();

        
        String action = req.getParameter("action");
        if ("add".equals(action)) {
            String idRaw = req.getParameter("id");
            if (idRaw != null && !idRaw.isEmpty()) {
                try {
                    int productId = Integer.parseInt(idRaw);
                    cartDAO.addToCart(userId, productId);
                } catch (NumberFormatException ignored) {}
            }
            resp.sendRedirect("cart");
            return;
        }

        // View cart
        List<CartItem> items = cartDAO.getItems(userId);
   
        req.setAttribute("items", items);
        
        req.getRequestDispatcher("cart.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");

        HttpSession session = req.getSession(false);
        User user = (session == null) ? null : (User) session.getAttribute("user");
        if (user == null) {
            resp.sendRedirect("login.jsp"); 
            return;
        }

        int userId = user.getId();

        String action = req.getParameter("action");
        String idRaw = req.getParameter("id");

        if (action == null || idRaw == null || idRaw.isEmpty()) {
            resp.sendRedirect("cart");
            return;
        }

        int productId;
        try {
            productId = Integer.parseInt(idRaw);
        } catch (NumberFormatException e) {
            resp.sendRedirect("cart");
            return;
        }

        if ("remove".equals(action)) {
            cartDAO.remove(userId, productId);

        } else if ("update".equals(action)) {
            String qtyRaw = req.getParameter("qty");
            try {
                int qty = Integer.parseInt(qtyRaw);
                if (qty < 1) qty = 1;
                cartDAO.updateQuantity(userId, productId, qty);
            } catch (Exception ignored) {}

        } else if ("add".equals(action)) {
            cartDAO.addToCart(userId, productId);
        }

        resp.sendRedirect("cart");
    }
}
