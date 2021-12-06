package dp.project;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class UserBrowse {
    private Connection con;

    public String getUserBrowseToJSON(MySqlDbConnection db) {
        String UsersBrowseJSONFormat = "{\"username\":\"{USERNAME}\",\"email\":\"{EMAIL}\",\"password\":\"{PASSW}\"}";
        StringBuilder UsersBrowseJSONResult = new StringBuilder();
        String JSONRow;
        this.con = db.connect();
        try {
            Statement stmt = this.con.createStatement();
            ResultSet rs;
            rs = stmt.executeQuery("SELECT * FROM USERS ORDER BY USERNAME");
            while (rs.next()) {
                JSONRow = UsersBrowseJSONFormat.replace("{USERNAME}", rs.getString(1));
                JSONRow = JSONRow.replace("{EMAIL}", rs.getString(2));
                JSONRow = JSONRow.replace("{PASSW}", rs.getString(3));
                UsersBrowseJSONResult.append(JSONRow).append(",");
            }
            this.con.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return "[" + Util.removeLastChar(UsersBrowseJSONResult.toString()) + "]";
    }
}
