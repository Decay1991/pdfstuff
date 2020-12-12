package com.tiago.pdfstuff;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class SplitPageController {

    @FXML
    private JFXButton btnSelectSplitFile;

    @FXML
    private JFXButton btnSplitFile;

    @FXML
    private Label labelSelectedFile;

    @FXML
    private JFXComboBox<String> cbSelectPage;

    @FXML
    private Label labelSplitStatus;

    @FXML
    private Label labelSplitStatusError;

    File selectedFile;
    @FXML
    void initialize() {


        btnSelectSplitFile.setOnAction(event -> {
            Stage stage=(Stage) btnSelectSplitFile.getScene().getWindow();
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Select File to split");
            fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("PDF (*.PDF, *.pdf)", "*.pdf","*.PDF"));
            selectedFile = fileChooser.showOpenDialog(stage);
            labelSelectedFile.setText(selectedFile.getName());

            if(cbSelectPage.getItems().size() > 0){
                cbSelectPage.getItems().remove(0, cbSelectPage.getItems().size());
            }

            cbSelectPage.getItems().add("All");
            int numPages = PDFutils.numberFilePages(selectedFile);

            for(int i=1; i<=numPages; i++){
                cbSelectPage.getItems().add(""+i);
            }

            labelSplitStatusError.setVisible(false);
            labelSplitStatus.setVisible(false);
        });


        btnSplitFile.setOnAction(event -> {

            labelSplitStatusError.setVisible(false);
            labelSplitStatus.setVisible(false);

            Stage stage=(Stage) btnSplitFile.getScene().getWindow();
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Select Destination");
            fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("PDF (*.PDF, *.pdf)", "*.pdf","*.PDF"));
            File destFile = fileChooser.showSaveDialog(stage);

            String selectedPage = cbSelectPage.getSelectionModel().getSelectedItem();
            int selectedSplitPage = selectedPage.equals("All") ? -1 : Integer.parseInt(selectedPage);

            if(PDFutils.splitPDF(selectedFile,selectedSplitPage, destFile.getPath()) == 1){
                labelSplitStatus.setVisible(true);
            }else{
                labelSplitStatusError.setVisible(true);
            }

        });
    }
}
