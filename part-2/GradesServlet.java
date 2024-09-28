import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/grades")
public class GradesServlet extends HttpServlet {

    private GradeDAO gradeDAO = new GradeDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        String username = (String) session.getAttribute("username");

        if (username != null) {
            // Fetch grades and forward to JSP
            req.getRequestDispatcher("grades.jsp").forward(req, resp);
        } else {
            resp.sendRedirect("login.jsp");
        }
    }
}

