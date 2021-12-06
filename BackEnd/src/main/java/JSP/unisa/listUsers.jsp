<%@ page import="dp.project.MySqlDbConnection" %>
<%@ page import="dp.project.UserBrowse" %>
<%@ include file="config.jsp" %>
<%
    String jsonResult;
    MySqlDbConnection db = new MySqlDbConnection();
    db.setDbUser(dbUser);
    db.setDbPassword(dbPassword);
    db.setDbName(dbName);

    UserBrowse service = new UserBrowse();
    response.setContentType("application/json");
    response.setHeader("Access-Control-Allow-Origin", "*");

%>
<%= service.getUserBrowseToJSON(db) %>