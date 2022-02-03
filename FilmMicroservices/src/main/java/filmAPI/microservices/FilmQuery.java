package filmAPI.microservices;

import filmAPI.interfaces.DBConnection;
import filmAPI.models.Actor;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Path("/query")
public class FilmQuery extends DBConnection {
    public FilmQuery() {
        super();
    }

    @GET
    @Path("/getFilmDetails/{id}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Object getFilmDetails(@PathParam("id") int id){
        db.connect();
        try (PreparedStatement stmt = db.getConn().prepareStatement("")) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (!rs.next()) {
                    return null;
                }
                Actor a = new Actor(rs.getInt("id_actor"), rs.getString("name"), rs.getString("surname"));
                System.out.println(a);
                return a;
            }
        } catch (SQLException e) {
            return null;
        }finally {
            db.disconnect();
        }

    }

    @GET
    @Path("/getAll")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Object getAll(){
        db.connect();
        try (PreparedStatement stmt = db.getConn().prepareStatement("")) {
            //stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (!rs.next()) {
                    return null;
                }
                Actor a = new Actor(rs.getInt("id_actor"), rs.getString("name"), rs.getString("surname"));
                System.out.println(a);
                return a;
            }
        } catch (SQLException e) {
            return null;
        }finally {
            db.disconnect();
        }

    }


    @GET
    @Path("/getPerGenre/{genre}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Object getPerGenre(@PathParam("genre") String genre){
        db.connect();
        try (PreparedStatement stmt = db.getConn().prepareStatement("")) {
           // stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (!rs.next()) {
                    return null;
                }
                Actor a = new Actor(rs.getInt("id_actor"), rs.getString("name"), rs.getString("surname"));
                System.out.println(a);
                return a;
            }
        } catch (SQLException e) {
            return null;
        }finally {
            db.disconnect();
        }

    }


}
