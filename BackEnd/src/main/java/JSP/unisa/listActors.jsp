<%@ page import="dp.project.MySqlDbConnection" %>
<%@ page import="dp.project.film.FilmAttoreBrowse" %>
<%@ include file="configFilm.jsp" %>
<%
    String jsonResult;
    MySqlDbConnection db = new MySqlDbConnection();
    db.setDbUser(dbUser);
    db.setDbPassword(dbPassword);
    db.setDbName(dbName);

    FilmAttoreBrowse service = new FilmAttoreBrowse();
    response.setContentType("application/json");
    response.setHeader("Access-Control-Allow-Origin", "*");

%>
<%= service.getActorsBrowseToJSON(db) %>