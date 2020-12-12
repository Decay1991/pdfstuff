package com.tiago.pdfstuff;

import com.jfoenix.controls.JFXButton;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import com.jfoenix.controls.JFXListView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class MergePageController {

    public ArrayList<MyFile> selectedFiles = new ArrayList<>();

    @FXML
    private JFXListView<MyFile> listFiles;

    @FXML
    private JFXButton btnSelectMergeFiles;

    @FXML
    private JFXButton btnMergeFiles;

    @FXML
    private Label labelMergeStatusSuccess;

    @FXML
    private Label labelMergeStatusError;

    private ObservableList<MyFile> files;

    @FXML
    void initialize() {

        btnSelectMergeFiles.setOnAction(event -> {
            Stage stage=(Stage) btnSelectMergeFiles.getScene().getWindow();
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Select Files to merge");
            fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("PDF (*.PDF, *.pdf)", "*.pdf","*.PDF"));
            List<File> list = fileChooser.showOpenMultipleDialog(stage);

            for(File file: list){

                MyFile myFile = new MyFile();
                myFile.setFile(file);
                myFile.setName(file.getName());
                myFile.setPath(file.getPath());
                selectedFiles.add(myFile);
            }

            updateList();

            labelMergeStatusError.setVisible(false);
            labelMergeStatusSuccess.setVisible(false);
        });

        btnMergeFiles.setOnAction(event -> {

            Stage stage=(Stage) btnSelectMergeFiles.getScene().getWindow();
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Select Destination");
            fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("PDF (*.PDF, *.pdf)", "*.pdf","*.PDF"));
            File destFile = fileChooser.showSaveDialog(stage);
            //System.out.println(destFile.getPath());

            try {
                if(PDFutils.mergePDF(selectedFiles, destFile.getPath()) == 1){
                    labelMergeStatusSuccess.setVisible(true);
                }else{
                    labelMergeStatusError.setVisible(true);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public void updateList(){
        files = FXCollections.observableArrayList();
        for(MyFile file: selectedFiles){
            files.addAll(file);
        }
        listFiles.setItems(files);
        listFiles.setCellFactory(CellController -> new CellController(this));
    }

    public void moveUp(MyFile selectedFile){
        for(int i = 0; i< selectedFiles.size(); i++){
            if(selectedFiles.get(i).getName().equals(selectedFile.getName())){
                if(i==0){
                    return;
                }

                MyFile auxFile = new MyFile();
                auxFile.setName(selectedFiles.get(i).getName());
                auxFile.setPath(selectedFiles.get(i).getPath());
                auxFile.setFile(selectedFiles.get(i).getFile());

                selectedFiles.get(i).setName(selectedFiles.get(i-1).getName());
                selectedFiles.get(i).setPath(selectedFiles.get(i-1).getPath());
                selectedFiles.get(i).setFile(selectedFiles.get(i-1).getFile());

                selectedFiles.get(i-1).setName(auxFile.getName());
                selectedFiles.get(i-1).setPath(auxFile.getPath());
                selectedFiles.get(i-1).setFile(auxFile.getFile());
                updateList();
                break;
            }
        }
    }

    public void moveDown(MyFile selectedFile){
        for(int i = 0; i< selectedFiles.size(); i++){
            if(selectedFiles.get(i).equals(selectedFile)){
                if(i==selectedFiles.size()-1){
                    return;
                }

                MyFile auxFile = new MyFile();
                auxFile.setName(selectedFiles.get(i).getName());
                auxFile.setPath(selectedFiles.get(i).getPath());
                auxFile.setFile(selectedFiles.get(i).getFile());

                selectedFiles.get(i).setName(selectedFiles.get(i+1).getName());
                selectedFiles.get(i).setPath(selectedFiles.get(i+1).getPath());
                selectedFiles.get(i).setFile(selectedFiles.get(i+1).getFile());

                selectedFiles.get(i+1).setName(auxFile.getName());
                selectedFiles.get(i+1).setPath(auxFile.getPath());
                selectedFiles.get(i+1).setFile(auxFile.getFile());
                updateList();
                break;
            }
        }
    }

    public void removeFile(MyFile selectedFile){
        for(MyFile file: selectedFiles){
            if(file.getName().equals(selectedFile.getName())){
                selectedFiles.remove(file);
                updateList();
                break;
            }
        }
    }
}
