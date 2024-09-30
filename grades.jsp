<%@ page import="java.sql.*" %>
<!DOCTYPE html>
<html>
<head>
    <title>Your Grades</title>
</head>
<body>
    <h1>Your Grades</h1>
    <table border="1">
        <tr>
            <th>Course</th>
            <th>Grade</th>
        </tr>
        <%
            Connection conn = (Connection) session.getAttribute("dbConnection");
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT courses.course_name, grades.grade FROM grades JOIN courses ON grades.course_id = courses.id WHERE grades.student_id = " + session.getAttribute("studentId"));
            while (rs.next()) {
                String course = rs.getString("course_name");
                double grade = rs.getDouble("grade");
        %>
        <tr>
            <td><%= course %></td>
            <td><%= grade %></td>
        </tr>
        <% } %>
    </table>
</body>
</html>

