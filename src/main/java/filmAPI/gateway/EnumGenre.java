package filmAPI.gateway;


public enum EnumGenre {
    _1("Action"),
    _2("Adventure"),
    _3("Comedy"),
    _4("Horror"),
    _5("Romance");

    public final String label;

    EnumGenre(String label) {
        this.label = label;
    }
}
