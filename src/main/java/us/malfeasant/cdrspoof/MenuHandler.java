package us.malfeasant.cdrspoof;

import com.fazecast.jSerialComm.SerialPort;

import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Dialog;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.TextInputDialog;

public class MenuHandler {
    private static final int DEFAULTPORT = 9101;

    public final MenuBar bar;
    private final ReadOnlyObjectWrapper<Connector> deviceWrapper;
    public final ReadOnlyObjectProperty<Connector> deviceProperty;

    public MenuHandler() {
        deviceWrapper = new ReadOnlyObjectWrapper<Connector>();
        deviceProperty = deviceWrapper.getReadOnlyProperty();

        var serial = new MenuItem("Serial...");
        serial.setOnAction(e -> showSerialDialog());
        serial.disableProperty().bind(deviceWrapper.isNotNull());
        var ip = new MenuItem("IP...");
        ip.setOnAction(e -> showIpDialog());
        ip.disableProperty().bind(deviceWrapper.isNotNull());
        var disconnect = new MenuItem("Disconnect");
        disconnect.disableProperty().bind(deviceWrapper.isNull());
        disconnect.setOnAction(e -> {
            deviceWrapper.set(null);
        });

        var connect = new Menu("Connect", null, serial, ip, new SeparatorMenuItem(), disconnect);
        bar = new MenuBar(connect);

    }

    private void showSerialDialog() {
        var availablePorts = SerialPort.getCommPorts();
        var dialog = new ChoiceDialog<SerialPort>(null, availablePorts);
        var result = dialog.showAndWait();
        result.ifPresent(r -> {
            deviceWrapper.set(new Connector.Serial(r));
        });
    }

    private void showIpDialog() {
        var dialog = new TextInputDialog(String.valueOf(DEFAULTPORT));
        dialog.setHeaderText("Specify a port to listen to:");
        var result = dialog.showAndWait();
        result.ifPresent(r -> {
            // This can throw an exception if text can't be converted to int-
            // I don't care yet.  If it does, no new Connection will be created,
            // and I'm ok with that.
            int port = Integer.parseInt(r);
            deviceWrapper.set(new Connector.IP(port));
        });
    }
}
