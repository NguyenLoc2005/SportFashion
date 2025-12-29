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

    private User requireLogin(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        if (user == null) {
            resp.sendRedirect("login.jsp");
            return null;
        }
        return user;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        User user = requireLogin(req, resp);
        if (user == null) return;

        int userId = user.getId();

        // ADD bằng GET: /cart?action=add&id=...
        String action = req.getParameter("action");
        if ("add".equals(action)) {
            String idRaw = req.getParameter("id");
            if (idRaw != null && !idRaw.isEmpty()) {
                int productId = Integer.parseInt(idRaw);
                cartDAO.addToCart(userId, productId);
            }
            resp.sendRedirect("cart");
            return;
        }

        // View cart
        List<CartItem> items = cartDAO.getItems(userId);
        int total = cartDAO.getTotal(userId);

        req.setAttribute("items", items);
        req.setAttribute("total", total);
        req.getRequestDispatcher("cart.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        User user = requireLogin(req, resp);
        if (user == null) return;

        int userId = user.getId();

        String action = req.getParameter("action");
        String idRaw = req.getParameter("id");

        if (action == null || idRaw == null || idRaw.isEmpty()) {
            resp.sendRedirect("cart");
            return;
        }

        int productId = Integer.parseInt(idRaw);

        if ("remove".equals(action)) {
            cartDAO.remove(userId, productId);
        } else if ("update".equals(action)) {
            // nếu sau này muốn update lại thì mở:
            int qty = Integer.parseInt(req.getParameter("qty"));
            cartDAO.updateQty(userId, productId, qty);
        } else if ("add".equals(action)) {
            cartDAO.addToCart(userId, productId);
        }

        resp.sendRedirect("cart");
    }
}
