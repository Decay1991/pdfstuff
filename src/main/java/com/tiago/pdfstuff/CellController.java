package com.tiago.pdfstuff;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXListCell;
import com.tiago.pdfstuff.MyFile;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;


public class CellController extends JFXListCell<MyFile> {

    MergePageController mpc;

    @FXML
    private AnchorPane rootAnchorPane;

    @FXML
    private ImageView cellIconPDF;

    @FXML
    private Label labelNamePDF;

    @FXML
    private ImageView cellMoveUp;

    @FXML
    private ImageView cellRemove;

    @FXML
    private ImageView cellMoveDown;


    private FXMLLoader fxmlLoader;

    @FXML
    void initialize() {

    }

    public CellController(MergePageController mpc){
        this.mpc = mpc;
    }

    @Override
    public void updateItem(MyFile myFile, boolean empty) {
        super.updateItem(myFile, empty);

        if(empty || myFile == null){
            setText(null);
            setGraphic(null);
        }else{
            if(fxmlLoader == null){
                fxmlLoader = new FXMLLoader(getClass().getResource("cell.fxml"));
                fxmlLoader.setController(this);
                try {
                    fxmlLoader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            labelNamePDF.setText(myFile.getName());

            cellRemove.setOnMouseClicked(event -> {
                mpc.removeFile(getItem());
            });

            cellMoveUp.setOnMouseClicked(event -> {
                mpc.moveUp(getItem());
            });

            cellMoveDown.setOnMouseClicked(event -> {
                mpc.moveDown(getItem());
            });
        }

        setText(null);
        setGraphic(rootAnchorPane);

    }
}

