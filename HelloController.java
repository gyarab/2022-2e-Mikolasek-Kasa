package com.example.rpkasa2;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class HelloController {
    @FXML
    private Button addbtn;
    @FXML
    private Pane panetop;
    @FXML
    private Pane pane2;
    @FXML
    private Pane pane3;
    @FXML
    private TextArea textarea;
    @FXML
    private TextArea textarea2;
    @FXML
    private TextArea textarea3;
    @FXML
    private Button clearbtn;
    @FXML
    private Button donebtn;
    @FXML
    private Button overviewbtn;
    @FXML
    private Button backbtn;
    @FXML
    private TextArea overviewarea;
    @FXML
    private Button menubtn;
    @FXML
    private Button exitbtn;
    @FXML
    private Text title;
    @FXML
    private Button startbtn;
    @FXML
    private VBox vbox;

    final int[] total = {0};

    @FXML
    protected void onStart() throws IOException, ClassNotFoundException {
        pane3.setVisible(true);
        pane2.setVisible(true);
        panetop.setVisible(true);
        overviewbtn.setVisible(false);
        menubtn.setVisible(true);
        vbox.setVisible(true);
        exitbtn.setVisible(false);
        title.setVisible(false);

        GridPane gridpane = new GridPane();
        File fn = new File("productlist.dat");
        if (fn.length() != 0) {
            int radky = 0;
            if (vbox.getChildren().isEmpty()) {FileInputStream in = new FileInputStream(fn);
                ObjectInputStream inn = new ObjectInputStream(in);
                ArrayList<product> temp = (ArrayList<com.example.rpkasa2.product>) inn.readObject();
                for (int i = 0; i < temp.size(); i++) {
                    int sloupce = i;
                    if (i > 9) {
                        radky = (i/10);
                        sloupce = (i%10);
                    }
                    Button b = new Button();
                    b.setStyle("-fx-font-size: 16px;");
                    product pp = temp.get(i);
                    b.setText(pp.nazev + " " + pp.cena + "\n");
                    b.setMinSize(190, 190);
                    b.setOnAction((event) -> {
                        String curtext = textarea.getText();
                        textarea.setText(curtext + "\n" + pp.nazev);
                        String curnumbers = textarea2.getText();
                        total[0] += pp.cena;
                        textarea3.setText("Total: " + total[0]);
                        textarea2.setText(curnumbers + "\n" + pp.cena);
                    });
                    if ( i % 9 == 0) {
                        gridpane.addRow(radky);
                    }
                    gridpane.add(b, sloupce, radky);
                }
            }
        }
        ScrollPane scrollpane = new ScrollPane(gridpane);
        vbox.getChildren().add(scrollpane);
        textarea.setVisible(true);
        addbtn.setVisible(true);
        clearbtn.setVisible(true);
        scrollpane.setVisible(true);
        startbtn.setVisible(false);
        donebtn.setVisible(true);
    }

    @FXML
    protected void onAdd() throws IOException {
        FXMLLoader fxmlLoader2 = new FXMLLoader(HelloApplication.class.getResource("pop-up.fxml"));
        final Stage popup = new Stage();
        popup.initModality(Modality.APPLICATION_MODAL);
        Scene popupScene = new Scene(fxmlLoader2.load(), 600, 400);
        popup.setTitle("Add new product");
        popup.setScene(popupScene);
        popup.show();
    }

    @FXML
    protected void onClear() {
        textarea.clear();
        textarea2.clear();
        total[0] = 0;
        textarea3.clear();
    }

    @FXML
    protected void onDone() throws IOException, ClassNotFoundException {
        if (!textarea.getText().isEmpty()) {
            File done = new File("donelist.txt");
            if (done.length() == 0) {
                String date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
                String x =textarea.getText() +"\n" + "Total: " +  total[0] + "\n" + date;
                FileOutputStream out = new FileOutputStream(done);
                ObjectOutputStream oo = new ObjectOutputStream(out);
                oo.writeObject(x);
                out.close();
            } else {
                FileInputStream in = new FileInputStream(done);
                ObjectInputStream inn = new ObjectInputStream(in);
                String curlist = (String) inn.readObject();
                String date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
                String x =curlist +"\n"+ textarea.getText() + "\n" + "Total: " +  total[0] + "\n" + date;
                FileOutputStream out = new FileOutputStream(done);
                ObjectOutputStream oo = new ObjectOutputStream(out);
                oo.writeObject(x);
                out.close();
            }
            textarea.clear();
            textarea2.clear();
            total[0] = 0;
            textarea3.clear();
        }
    }

    @FXML
    private void onOverview() throws IOException, ClassNotFoundException {
        startbtn.setVisible(false);
        overviewbtn.setVisible(false);
        backbtn.setVisible(true);
        overviewarea.setVisible(true);
        menubtn.setVisible(false);
        exitbtn.setVisible(false);
        title.setVisible(false);

        File done = new File("donelist.txt");
        FileInputStream in = new FileInputStream(done);
        ObjectInputStream inn = new ObjectInputStream(in);
        String curlist = (String) inn.readObject();
        overviewarea.setText(curlist);
    }

    @FXML
    private void onBack() {
        startbtn.setVisible(true);
        overviewbtn.setVisible(true);
        backbtn.setVisible(false);
        overviewarea.setVisible(false);
        exitbtn.setVisible(true);
        title.setVisible(true);
    }

    @FXML
    private void onMenu() {
        pane3.setVisible(false);
        pane2.setVisible(false);
        panetop.setVisible(false);
        overviewbtn.setVisible(true);
        startbtn.setVisible(true);
        vbox.setVisible(false);
        donebtn.setVisible(false);
        menubtn.setVisible(false);
        addbtn.setVisible(false);
        clearbtn.setVisible(false);
        exitbtn.setVisible(true);
        textarea.clear();
        textarea2.clear();
        textarea3.clear();
        title.setVisible(true);
    }

    @FXML
    private void onExit() {
        Stage stage = (Stage) exitbtn.getScene().getWindow();
        stage.close();
    }
}
