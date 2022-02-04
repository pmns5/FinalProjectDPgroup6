package filmAPI;

public class Utils {

    public static String extractTrailerString(String trailer) {
        String[] splitted = trailer.split("/");
        return splitted[splitted.length - 1];
    }
}
