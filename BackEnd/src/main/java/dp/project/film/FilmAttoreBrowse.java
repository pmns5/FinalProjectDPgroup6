package dp.project.film;

import dp.project.MySqlDbConnection;
import dp.project.Util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class FilmAttoreBrowse {

    private Connection con;

    public String getFilmAttoreToJSON(MySqlDbConnection db, int id_film) {

        String FilmAttoreBrowseJSONFormat = "{\"id_attore\":\"{ID}\",\"nome\":\"{NOME}\",\"cognome\":\"{COGNOME}\"}";
        StringBuilder FilmAttoreBrowseJSONResult = new StringBuilder();
        String JSONRow;
        this.con = db.connect();

        try {
            Statement stmt = this.con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rs;
            rs = stmt.executeQuery(
                    "SELECT actors.id, name, surname \n" +
                            "FROM film_attore as fa, actors WHERE fa.attore=actors.id and id=" + id_film);

            if (!rs.next()) {
                JSONRow = FilmAttoreBrowseJSONFormat.replace("{ID}", "");
                JSONRow = JSONRow.replace("{NOME}", "");
                JSONRow = JSONRow.replace("{COGNOME}", "");
                FilmAttoreBrowseJSONResult.append(JSONRow).append(",");

                return "[" + Util.removeLastChar(FilmAttoreBrowseJSONResult.toString()) + "]";
            }

            rs.previous();

            while (rs.next()) {
                JSONRow = FilmAttoreBrowseJSONFormat.replace("{ID}", Util.utf8Encode(String.valueOf(rs.getInt(1))));
                JSONRow = JSONRow.replace("{NOME}", Util.utf8Encode(rs.getString(2)));
                JSONRow = JSONRow.replace("{COGNOME}", Util.utf8Encode(rs.getString(3)));
                FilmAttoreBrowseJSONResult.append(JSONRow).append(",");
            }
            this.con.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return "[" + Util.removeLastChar(FilmAttoreBrowseJSONResult.toString()) + "]";
    }

    public String getActorsBrowseToJSON(MySqlDbConnection db) {

        String actorsBrowseJSONFormat = "{\"id\":\"{ID}\",\"name\":\"{NOME}\",\"surname\":\"{COGNOME}\"}";
        StringBuilder actorsBrowseJSONResult = new StringBuilder();
        String JSONRow;

        this.con = db.connect();

        try {
            Statement stmt = this.con.createStatement();
            ResultSet rs;

            rs = stmt.executeQuery("SELECT * FROM actors ORDER BY id");

            while (rs.next()) {
                JSONRow = actorsBrowseJSONFormat.replace("{ID}", String.valueOf(rs.getInt(1)));
                JSONRow = JSONRow.replace("{NOME}", rs.getString(2));
                JSONRow = JSONRow.replace("{COGNOME}", rs.getString(3));
                actorsBrowseJSONResult.append(JSONRow).append(",");
            }
            this.con.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return "[" + Util.removeLastChar(actorsBrowseJSONResult.toString()) + "]";
    }


}
