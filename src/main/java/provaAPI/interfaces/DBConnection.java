package provaAPI.interfaces;


public abstract class DBConnection {

    public DbInterface db;

    public DBConnection() {
        this.db = new MySQLDb();
        db.connect();
    }

//    @Override
//    protected void finalize() throws Throwable {
//        db.disconnect();
//    }
}
