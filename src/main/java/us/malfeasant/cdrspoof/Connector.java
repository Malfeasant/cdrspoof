package us.malfeasant.cdrspoof;

public class Connector {
    public final Type type;

    private Connector(Type t) {
        assert t != null : "Type argument must not be null.";
        type = t;
    }

    public enum Type {
        SERIAL, IP;
    }
}
