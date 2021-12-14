package filmAPI.gateway;

import java.util.HashMap;
import java.util.Map;

public enum EnumGenre {
    Action(1), Adventure(2), Comedy(3), Horror(4), Romance(5);
    private static final Map<Integer, EnumGenre> map = new HashMap<Integer, EnumGenre>();

    static {
        for (EnumGenre legEnum : EnumGenre.values()) {
            map.put(legEnum.enumGenre, legEnum);
        }
    }

    private final int enumGenre;

    EnumGenre(final int enumGenre) {
        this.enumGenre = enumGenre;
    }

    public static String valueOf(int enumGenre) {
        return map.get(enumGenre).toString();
    }
}
