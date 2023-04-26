package com.example.rpkasa2;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.io.*;
import java.util.ArrayList;

public class popupController {
    @FXML
    Button checkbtn;
    @FXML
    TextField namefield;
    @FXML
    TextField pricefield;
    @FXML
    Text error;

    @FXML
    protected void onCheck() throws IOException, ClassNotFoundException {
        int price = 0;
        try {
            price = Integer.parseInt(pricefield.getText());
        } catch (NumberFormatException e) {
            pricefield.clear();
            error.setVisible(true);
        }
        String name = namefield.getText();
        if (name.isEmpty()) {
            error.setVisible(true);
        }
        if (price == 0) {
            error.setVisible(true);
        }
        if (!name.isEmpty() && price != 0) {
            System.out.println(name + " " + price);
            product pproduct = new product(name, price);
            File fn = new File("productlist.dat");
            if (fn.length() == 0) {
                ArrayList<product> list = new ArrayList<>();
                list.add(pproduct);
                FileOutputStream out = new FileOutputStream(fn);
                ObjectOutputStream oo = new ObjectOutputStream(out);
                oo.writeObject(list);
            } else {
                FileInputStream in = new FileInputStream(fn);
                ObjectInputStream inn = new ObjectInputStream(in);
                ArrayList<product> temp = (ArrayList<com.example.rpkasa2.product>) inn.readObject();
                temp.add(pproduct);
                in.close();
                FileOutputStream out = new FileOutputStream(fn);
                ObjectOutputStream oo = new ObjectOutputStream(out);
                oo.writeObject(temp);
                out.close();
            }
            Stage stage = (Stage) checkbtn.getScene().getWindow();
            stage.close();
        }
    }
}
