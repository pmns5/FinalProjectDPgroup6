<%@ page import="java.sql.*" %>
<%@ page import="dp.project.film.*" %>
<%@ page import="dp.project.MySqlDbConnection" %>
<%@ include file="configFilm.jsp" %>
<%
    String jsonResult;
    MySqlDbConnection db = new MySqlDbConnection();
    db.setDbUser(dbUser);
    db.setDbPassword(dbPassword);
    db.setDbName(dbName);

    Attore service = new Attore(db);
    response.setContentType("application/json");
    response.setHeader("Access-Control-Allow-Origin", "*");

%>
<%= service.getFilmAttoreToJSON(db, request.getParameter("id")) %>