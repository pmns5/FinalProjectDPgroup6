<%@ page import="dp.project.MySqlDbConnection" %>
<%@ page import="newBackEnd.models.Actor" %>
<%@ include file="configFilm.jsp" %>
<%
    String jsonResult;
    MySqlDbConnection db = new MySqlDbConnection();
    db.setDbUser(dbUser);
    db.setDbPassword(dbPassword);
    db.setDbName(dbName);

    Actor service = new Actor(db);
    response.setContentType("application/json");
    response.setHeader("Access-Control-Allow-Origin", "*");

%>
<%= service.getFilmAttoreToJSON(db, request.getParameter("id")) %>