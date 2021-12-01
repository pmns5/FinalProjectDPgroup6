package dp.project;

public class UserRecord {

    public User dbProcedures(MySqlDbConnection db, String dbOperation, String username, String email, String password) {

        User user = new User(db);
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(password);

        switch (dbOperation) {
            case "insert":
                user.add();
                break;
            case "delete":
                user.delete();
                break;
            default:
                user.select();
        }
        return user;
    }

}
