package filmAPI.interfaces;

public abstract class DBConnection {
    public DbInterface db;

    public DBConnection() {
        this.db = new MySQLDb();
        db.connect();
    }
}
