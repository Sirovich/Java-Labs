package sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.Label;

import java.util.concurrent.atomic.AtomicInteger;

public class MainController {
    @FXML
    private TextField percentField;
    @FXML
    private TextField dryMatterMassField;
    @FXML
    private TextField soluteMassField;
    @FXML
    private Label warningField;

    @FXML
    public void calculateSoluteMass(ActionEvent event)
    {
        int dryMass = 0;
        int percent = 0;

        clearWarnings();

        try {
            dryMass = Integer.parseInt(dryMatterMassField.getText());
        }
        catch (NumberFormatException ex) {
            warningField.setText("Неверная масса сухого вещества");
            return;
        }

        try {
            percent = Integer.parseInt(percentField.getText());
        }
        catch (NumberFormatException ex) {
            warningField.setText("Неверное процентное содержание");
            return;
        }

        soluteMassField.setText(String.valueOf((dryMass * 100 / percent)));
    }

    @FXML
    public void calculateDryMass(ActionEvent event)
    {
        int soluteMass = 0;
        int percent = 0;

        clearWarnings();

        try {
            soluteMass = Integer.parseInt(soluteMassField.getText());
        }
        catch (NumberFormatException ex) {
            warningField.setText("Неверная масса сухого вещества");
            return;
        }

        try {
            percent = Integer.parseInt(percentField.getText());
        }
        catch (NumberFormatException ex) {
            warningField.setText("Неверное процентное содержание");
            return;
        }

        dryMatterMassField.setText(String.valueOf((soluteMass * percent / 100)));
    }

    private void clearWarnings(){
        warningField.setText("");
    }



}