package us.malfeasant.cdrspoof;

import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;

public class MenuHandler {
    public final MenuBar bar;
    private final ReadOnlyObjectWrapper deviceWrapper;
    public final ReadOnlyObjectProperty deviceProperty;

    public MenuHandler() {
        var serial = new MenuItem("Serial...");
        var ip = new MenuItem("IP...");
        var connect = new Menu("Connect", null, serial, ip);
        bar = new MenuBar(connect);

        deviceWrapper = new ReadOnlyObjectWrapper<Connector>();
        deviceProperty = deviceWrapper.getReadOnlyProperty();
    }
}
