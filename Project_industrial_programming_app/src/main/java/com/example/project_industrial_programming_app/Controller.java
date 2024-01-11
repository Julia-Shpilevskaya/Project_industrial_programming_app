package com.example.project_industrial_programming_app;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import archive.Compress_zip;
import encryption.AESUtil;
import expression.Regular;
import file_types.JSON;
import file_types.TXT;
import file_types.XML;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class Controller {
   ObservableList<String> formats = FXCollections.observableArrayList("txt","xml","json");

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private RadioButton archive;

    @FXML
    private RadioButton archived;

    @FXML
    private RadioButton encrypt;

    @FXML
    private RadioButton encrypted;

    @FXML
    private Button end_button;

    @FXML
    private TextField filename_input;

    @FXML
    private TextField filename_output;

    @FXML
    private TextField zip_in;

    @FXML
    private TextField zip_out;

    @FXML
    private ChoiceBox<String> format_in;

    @FXML
    private ChoiceBox<String> format_out;

    @FXML
    void work_with_input_file(ActionEvent event) throws IOException {
        String filename_inputText = filename_input.getText();
        String filename_outputText = filename_output.getText();
        String form_in = format_in.getValue();
        String form_out = format_out.getValue();

        if(archived.isSelected()) {
            Compress_zip.decompress(filename_inputText+"."+form_in, zip_in.getText());
        }

        String text = "";

        if(form_in=="txt") {
            TXT txt = new TXT();
            text = txt.Read(filename_inputText+"."+form_in);
        } else if(form_in==".xml") {
            XML xml = new XML();
            text = xml.Read(filename_inputText+"."+form_in);
        } else if(form_in=="json") {
            JSON json = new JSON();
            text = json.Read(filename_inputText+"."+form_in);
        }

        if( encrypted.isSelected()) {
           text = AESUtil.decrypt(text);
        }

        Regular reg_expression = new Regular();
        String answer = reg_expression.EvaluateExpression(text);

        if(encrypt.isSelected()){
            answer = AESUtil.encrypt(answer,filename_outputText+"."+form_out);
        }

        if(form_in=="txt") {
            TXT txt = new TXT();
            txt.Write(answer,filename_outputText+"."+form_out);
        } else if(form_in==".xml") {
            XML xml = new XML();
            xml.Write(answer,filename_outputText+"."+form_out);
        } else if(form_in=="json") {
            JSON json = new JSON();
            json.Write(answer,filename_outputText+"."+form_out);
        }

        if(archive.isSelected()) {
            Compress_zip.compress(filename_outputText+"."+form_out,zip_out.getText());
        }

        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("end_app.fxml"));
        Scene scene2 = new Scene(fxmlLoader.load(), 598, 413);
        Stage stage2 = new Stage();

        stage2.setTitle("Industrial programming");
        stage2.setScene(scene2);
        stage2.show();
    }

    @FXML
    void initialize() {
        format_in.setItems(formats);
        format_out.setItems(formats);
        format_in.setValue("txt");
        format_out.setValue("txt");
        filename_input.setText("in1");
        filename_output.setText("out1");
        zip_in.setText("in");
        zip_out.setText("out");
    }
}
