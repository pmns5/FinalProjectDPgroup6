package dp.project.film;

import dp.project.MySqlDbConnection;
import dp.project.Util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class FilmBrowseService {

    private Connection con;

    public String getAllCommentFilmToJSON(MySqlDbConnection db, String id_film) {

        String CommentFilmBrowseJSONFormat = "{\"id_film\":\"{ID}\",\"film\":\"{NOME}\",\"comments\":[{COMMENTO}]}";
        StringBuilder CommentFilmBrowseJSONResult = new StringBuilder();
        String JSONRow;
        String commentsToJSON;
        this.con = db.connect();

        int id;
        id = Integer.parseInt(id_film);

        try {
            Statement stmt = this.con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rs;
            rs = stmt.executeQuery("SELECT voto.film, film.nome, commento FROM voto, film WHERE voto.film = film.id");

            while (rs.next()) {
                JSONRow = CommentFilmBrowseJSONFormat.replace("{ID}", rs.getString(1));
                JSONRow = JSONRow.replace("{NOME}", Util.utf8Encode(rs.getString(2)));
                CommentFilmBrowseJSONResult.append(JSONRow).append(",");
            }
            this.con.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        commentsToJSON = this.processCommentsToJSON(id);
        CommentFilmBrowseJSONResult = new StringBuilder(CommentFilmBrowseJSONResult.toString().replace("{COMMENTO}", commentsToJSON));

        return "[" + Util.removeLastChar(CommentFilmBrowseJSONResult.toString()) + "]";
    }

    private String processCommentsToJSON(int id) {

        String commentsRecordJSONFormat = "{\"comment\":\"{COMMENTO}}";
        StringBuilder commentsJSONResult = new StringBuilder();
        String JSONRow;
        try {
            Statement stmt = this.con.createStatement();
            ResultSet rs;
            String selected;

            rs = stmt.executeQuery("SELECT commento FROM voto WHERE film = " + id);
            while (rs.next()) {
                JSONRow = commentsRecordJSONFormat.replace("{COMMENTO}", rs.getString(1));
                commentsJSONResult.append(JSONRow).append(",");
            }
            this.con.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return Util.removeLastChar(commentsJSONResult.toString());

    }

    public String getAverageVotoFilmToJSON(MySqlDbConnection db, String id_film) {

        String avgVotoFilmBrowseJSONFormat = "{\"avg_voto\":\"{VOTO}}";
        StringBuilder avgVotoFilmBrowseJSONResult = new StringBuilder();
        String JSONRow;
        this.con = db.connect();

        int id;
        id = Integer.parseInt(id_film);

        try {

            Statement stmt = this.con.createStatement();
            ResultSet rs;

            rs = stmt.executeQuery("SELECT AVG(voto) FROM film as f, voto as v WHERE f.id=v.film AND f.id = " + id);

            while (rs.next()) {
                JSONRow = avgVotoFilmBrowseJSONFormat.replace("{VOTO}", rs.getString(1));
                avgVotoFilmBrowseJSONResult.append(JSONRow).append(",");
            }
            this.con.close();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return Util.removeLastChar(avgVotoFilmBrowseJSONResult.toString());
    }

    public String getCommentsByVotoFilmToJSON(MySqlDbConnection db, String id_film, String votoFilm) {

        String commentoByVotoFilmBrowseJSONFormat = "{\"id_film\":\"{ID}\",\"film\":\"{NOME}\",\"voto\":\"{VOTO}\",\"comments\":[{COMMENTO}]}";
        StringBuilder commentoByVotoFilmBrowseJSONResult = new StringBuilder();
        String JSONRow;
        String commentsToJSON;
        this.con = db.connect();

        int id = Integer.parseInt(id_film);
        int voto = Integer.parseInt(votoFilm);

        try {
            Statement stmt = this.con.createStatement();
            ResultSet rs;

            rs = stmt.executeQuery("SELECT f.id, f.nome, v.voto, v.commento FROM film as f, voto as v " +
                    "WHERE f.id = v.film AND f.id=" + id + " AND v.voto = " + voto);

            while (rs.next()) {
                JSONRow = commentoByVotoFilmBrowseJSONFormat.replace("{ID}", rs.getString(1));
                JSONRow = JSONRow.replace("{NOME}", Util.utf8Encode(rs.getString(2)));
                JSONRow = JSONRow.replace("{VOTO}", Util.utf8Encode(rs.getString(3)));
                commentoByVotoFilmBrowseJSONResult.append(JSONRow).append(",");
            }
            this.con.close();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        commentsToJSON = this.processCommentsToJSON(id);
        commentoByVotoFilmBrowseJSONResult = new StringBuilder(commentoByVotoFilmBrowseJSONResult.toString().replace("{COMMENTO}", commentsToJSON));

        return "[" + Util.removeLastChar(commentoByVotoFilmBrowseJSONResult.toString()) + "]";

    }

    public String getNumVotoFilmToJSON(MySqlDbConnection db, String id_film) {

        String numVotoFilmBrowseJSONFormat = "{\"num_voto\":\"{VOTO}}";
        StringBuilder numVotoFilmBrowseJSONResult = new StringBuilder();
        String JSONRow;
        this.con = db.connect();

        int id;
        id = Integer.parseInt(id_film);

        try {

            Statement stmt = this.con.createStatement();
            ResultSet rs;

            rs = stmt.executeQuery("SELECT count(*) FROM film as f, voto as v WHERE f.id = v.film AND f.id = " + id);

            while (rs.next()) {
                JSONRow = numVotoFilmBrowseJSONFormat.replace("{VOTO}", rs.getString(1));
                numVotoFilmBrowseJSONResult.append(JSONRow).append(",");
            }
            this.con.close();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return Util.removeLastChar(numVotoFilmBrowseJSONResult.toString());
    }

    public String getFilmByGenreToJSON(MySqlDbConnection db, String genre) {

        String filmByGenreBrowseJSONFormat = "{\"genere\":\"{GENERE}\",\"film\":[{NOME}]}}";
        StringBuilder filmByGenreBrowseJSONResult = new StringBuilder();
        String JSONRow;
        this.con = db.connect();

        try {

            Statement stmt = this.con.createStatement();
            ResultSet rs;

            rs = stmt.executeQuery("SELECT nome FROM film WHERE genere = " + genre);

            while (rs.next()) {
                JSONRow = filmByGenreBrowseJSONFormat.replace("{GENERE}", genre);
                filmByGenreBrowseJSONResult.append(JSONRow).append(",");
            }
            this.con.close();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        String filmToJSON = this.processFilmToJSON(genre);
        filmByGenreBrowseJSONResult = new StringBuilder(filmByGenreBrowseJSONResult.toString().replace("{NOME}", filmToJSON));

        return "[" + Util.removeLastChar(filmByGenreBrowseJSONResult.toString()) + "]";
    }

    private String processFilmToJSON(String genre) {

        String filmRecordJSONFormat = "{\"film\":\"{NOME}}";
        StringBuilder filmJSONResult = new StringBuilder();
        String JSONRow;
        try {

            Statement stmt = this.con.createStatement();
            ResultSet rs;

            rs = stmt.executeQuery("SELECT nome FROM film WHERE genere = " + genre);
            while (rs.next()) {
                JSONRow = filmRecordJSONFormat.replace("{NOME}", rs.getString(1));
                filmJSONResult.append(JSONRow).append(",");
            }
            this.con.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return Util.removeLastChar(filmJSONResult.toString());

    }

    public String getFilmByVoto(MySqlDbConnection db) {

        String ordereFilmByVotoBrowseJSONFormat = "{\"id_film\":\"{ID}\",\"film\":\"{NOME}\",\"genere\":\"{GENERE}\"," +
                "\"voto\":\"{VOTO}\",\"trama\":\"{TRAMA}\",\"trailer\":\"{TRAILER}\",\"copertina\":\"{COPERTINA}}";
        StringBuilder orderedFilmByVotoBrowseJSONResult = new StringBuilder();
        String JSONRow;
        this.con = db.connect();

        try {

            Statement stmt = this.con.createStatement();
            ResultSet rs;

            rs = stmt.executeQuery("SELECT id, nome, genere, voto, trama, trailer, copertina FROM film , voto" +
                    " WHERE film.id = voto.film ORDER BY voto.voto");

            while (rs.next()) {
                JSONRow = ordereFilmByVotoBrowseJSONFormat.replace("{ID}", rs.getString(1));
                JSONRow = ordereFilmByVotoBrowseJSONFormat.replace("{NOME}", rs.getString(2));
                JSONRow = ordereFilmByVotoBrowseJSONFormat.replace("{GENERE}", rs.getString(3));
                JSONRow = ordereFilmByVotoBrowseJSONFormat.replace("{VOTO}", rs.getString(4));
                JSONRow = ordereFilmByVotoBrowseJSONFormat.replace("{TRAMA}", rs.getString(5));
                JSONRow = ordereFilmByVotoBrowseJSONFormat.replace("{TRAILER}", rs.getString(6));
                JSONRow = ordereFilmByVotoBrowseJSONFormat.replace("{COPERTINA}", rs.getString(7));
                orderedFilmByVotoBrowseJSONResult.append(JSONRow).append(",");
            }
            this.con.close();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return Util.removeLastChar(orderedFilmByVotoBrowseJSONResult.toString());
    }


}
