package com.tiago.pdfstuff;

import java.io.File;

public class MyFile {

    private File file;
    private String name;
    private String path;

    public MyFile(){ };

    public MyFile(File file, String name, String path){
        this.file = file;
        this.name = name;
        this.path = path;
    };



    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
