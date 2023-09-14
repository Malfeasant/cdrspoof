package us.malfeasant.cdrspoof;

import com.fazecast.jSerialComm.SerialPort;

import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

public class SerialSettings {
    public final Pane pane;
    private final ChoiceBox<Integer> baudChoice;
    private final ChoiceBox<Integer> bitsChoice;
    private final ChoiceBox<Integer> stopChoice;
    private final ChoiceBox<Parity> parChoice;

    public SerialSettings() {
        var baudLabel = new Label("Baud:");
        baudChoice = new ChoiceBox<>();
        baudChoice.getItems().addAll(9600, 2400, 1200);
        baudChoice.getSelectionModel().select(0);
        var bitsLabel = new Label("Data bits:");
        bitsChoice = new ChoiceBox<>();
        bitsChoice.getItems().addAll(8, 7);
        bitsChoice.getSelectionModel().select(0);
        var stopLabel = new Label("Stop bits");
        stopChoice = new ChoiceBox<>();
        stopChoice.getItems().addAll(1, 2);
        stopChoice.getSelectionModel().select(0);
        var parLable = new Label("Parity:");
        parChoice = new ChoiceBox<>();
        parChoice.getItems().addAll(Parity.values());
        parChoice.getSelectionModel().select(Parity.None);

        var pane = new GridPane();

        pane.addColumn(0, baudLabel, bitsLabel, parLable, stopLabel);
        pane.addColumn(1, baudChoice, bitsChoice, parChoice, stopChoice);

        this.pane = pane;
    }

    public int getBaud() {
        return baudChoice.getSelectionModel().getSelectedItem();
    }
    public int getStopBits() {
        return stopChoice.getSelectionModel().getSelectedItem();
    }
    public int getDataBits() {
        return bitsChoice.getSelectionModel().getSelectedItem();
    }
    public int getParity() {
        return parChoice.getSelectionModel().getSelectedItem().id;
    }

    private enum Parity {
        None(SerialPort.NO_PARITY),
        Even(SerialPort.EVEN_PARITY),
        Odd(SerialPort.ODD_PARITY);
        final int id;
        Parity(int id) { this.id = id; }
    }
}
