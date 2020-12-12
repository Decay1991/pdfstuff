package com.tiago.pdfstuff;

import org.apache.pdfbox.multipdf.PDFMergerUtility;
import org.apache.pdfbox.multipdf.Splitter;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentInformation;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

abstract class PDFutils {


    public static int mergePDF(ArrayList<MyFile> files, String destination) throws IOException {

    	PDFMergerUtility PDFmerger = new PDFMergerUtility();
        PDFmerger.setDestinationFileName(destination);

        for(MyFile file: files){
            PDFmerger.addSource(file.getPath());
        }

        PDFmerger.mergeDocuments();
        System.out.println("Documents merged");

        return 1;
    }

    public static int splitPDF(File file, int page, String destination){


        if(page == -1){
            try {
                PDDocument document = PDDocument.load(file);
                Splitter splitter = new Splitter();

                List<PDDocument> Pages = splitter.split(document);

                Iterator<PDDocument> iterator = Pages.listIterator();
                destination = destination.substring(0, destination.length()-4);
                int i = 1;
                while(iterator.hasNext()) {
                    PDDocument pd = iterator.next();
                    pd.save(destination+i+".pdf");
                    i++;
                }
                document.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{

            try {

                PDDocument document = PDDocument.load(file);
                Splitter splitter = new Splitter();
                //splitter.setStartPage(fromPage);
                //splitter.setEndPage(toPage);
                splitter.setSplitAtPage(page );

                List<PDDocument> splitDocuments = splitter.split(document);
                PDDocument pdfDoc1 = splitDocuments.get(0);
                File newFile1 = new File(destination);
                pdfDoc1.save(newFile1);

                PDDocument pdfDoc2 = splitDocuments.get(1);
                File newFile2 = new File(destination.substring(0, destination.length()-4)+"2.pdf");
                pdfDoc2.save(newFile2);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }





        return 1;
    }


    public static int numberFilePages(File file){

        PDDocument document= null;
        try {
            document = PDDocument.load(file);
        } catch (IOException e) {
            e.printStackTrace();
            return -1;
        }
        PDDocumentInformation info = document.getDocumentInformation();
        return document.getNumberOfPages();
    }
}
