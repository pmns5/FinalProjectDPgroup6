package filmAPI.microservices;

import com.mysql.cj.jdbc.Blob;
import filmAPI.interfaces.DBConnection;
import filmAPI.models.*;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Path("/query")
public class FilmQuery extends DBConnection {
    public FilmQuery() {
        super();
    }

    @GET
    @Path("/getFilmDetails/{id}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public ReviewPageFilm getFilmDetails(@PathParam("id") int id) {
        db.connect();
        Connection conn = db.getConn();
        Savepoint save1;
        try {
            db.getConn().setAutoCommit(false);
            save1 = conn.setSavepoint();

            PreparedStatement stmt = conn.prepareStatement("SELECT title, genre, plot, trailer, poster FROM film where film.id_film=?;");
            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (!rs.next()) {
                    return null;
                }

                String title = rs.getString("title");
                String genre = rs.getString("genre");
                String plot = rs.getString("plot");
                String trailer = rs.getString("trailer");
                com.mysql.cj.jdbc.Blob poster = (Blob) rs.getBlob("poster");
                int blobLength = (int) poster.length();
                byte[] blobAsBytes = poster.getBytes(1, blobLength);
                poster.free();

                PreparedStatement stmt4 = conn.prepareStatement("SELECT * FROM feedback where id_film=?;");
                stmt4.setInt(1, id);
                try (ResultSet rs4 = stmt4.executeQuery()) {
                    List<Feedback> feedbacks = new ArrayList<>();
                    while (rs4.next()) {
                        feedbacks.add(new Feedback(rs4.getInt("id_user"), id, rs4.getString("comment"),
                                rs4.getFloat("score"), rs4.getDate("date")));
                    }

                    PreparedStatement stmt2 = conn.prepareStatement("SELECT a.id_actor, a.name, a.surname FROM cast c join actor a on a.id_actor = c.id_actor where c.id_film=?;");
                    stmt2.setInt(1, id);
                    try (ResultSet rs2 = stmt2.executeQuery()) {
                        List<Actor> actors = new ArrayList<>();
                        while (rs2.next()) {
                            actors.add(new Actor(rs2.getInt("id_actor"), rs2.getString("name"), rs2.getString("surname")));
                        }

                        PreparedStatement stmt3 = conn.prepareStatement("SELECT AVG(score) FROM feedback WHERE id_film = ?");
                        stmt3.setInt(1, id);
                        try (ResultSet rs3 = stmt3.executeQuery()) {
                            float average = 0;
                            if (rs3.next()) {
                                average = rs3.getFloat(1);
                            }

                            return new ReviewPageFilm(new Film(id, title, plot, EnumGenre.valueOf(genre), trailer, blobAsBytes), actors, average, feedbacks);
                        }
                    }
                }
            } catch (SQLException e) {
                conn.rollback(save1);
            } finally {
                conn.commit();
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            db.disconnect();
        }
        return null;
    }

    @GET
    @Path("/getAll")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public List<HomePageFilm> getAll() throws SQLException {
        db.connect();
        Connection conn = db.getConn();
        Savepoint save1 = null;
        List<HomePageFilm> listFilms = new ArrayList<>();
        try {
            db.getConn().setAutoCommit(false);
            save1 = conn.setSavepoint();

            Statement stmt = db.getConn().createStatement();
            ResultSet rs = stmt.executeQuery("SELECT id_film, title, genre, plot, trailer, poster FROM film ORDER BY id_film");
            while (rs.next()) {
                int id_film = rs.getInt(1);
                String title = rs.getString(2);
                String genre = rs.getString(3);
                String plot = rs.getString(4);
                String trailer = rs.getString(5);
                Blob poster = (Blob) rs.getBlob(6);
                int blobLength = (int) poster.length();
                byte[] blobAsBytes = poster.getBytes(1, blobLength);
                poster.free();
                Film film = new Film(id_film, title, plot, EnumGenre.valueOf(genre), trailer, blobAsBytes);


                Statement stmt2 = db.getConn().createStatement();
                ResultSet rs2 = stmt2.executeQuery("SELECT a.id_actor, a.name, a.surname FROM film join cast c on film.id_film = c.id_film join actor a on a.id_actor = c.id_actor AND film.id_film = " + id_film);
                List<Actor> actorList = new ArrayList<>();
                while (rs2.next()) {
                    actorList.add(new Actor(rs2.getInt("id_actor"), rs2.getString("name"), rs2.getString("surname")));
                }

                Statement stmt3 = db.getConn().createStatement();
                ResultSet rs3 = stmt3.executeQuery("SELECT AVG(score) from feedback WHERE id_film = " + id_film);
                float average = 0;
                if (rs3.next()) {
                   average = rs3.getFloat(1);
                }

                listFilms.add(new HomePageFilm(film, actorList, average));
                conn.commit();
            }
        } catch (SQLException e) {
            conn.rollback(save1);
            return null;
        } finally {

            db.disconnect();
        }
        return listFilms;

    }


//    @GET
//    @Path("/getPerGenre/{genre}")
//    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
//    public Object getPerGenre(@PathParam("genre") String genre) {
//        db.connect();
//        try (PreparedStatement stmt = db.getConn().prepareStatement("")) {
//            // stmt.setInt(1, id);
//            try (ResultSet rs = stmt.executeQuery()) {
//                if (!rs.next()) {
//                    return null;
//                }
//                Actor a = new Actor(rs.getInt("id_actor"), rs.getString("name"), rs.getString("surname"));
//                System.out.println(a);
//                return a;
//            }
//        } catch (SQLException e) {
//            return null;
//        } finally {
//            db.disconnect();
//        }
//
//    }


}
