package hu.alkfejl.zh1gyak.controller;

import hu.alkfejl.zh1gyak.App;
import hu.alkfejl.zh1gyak.dao.OrderDAOImpl;
import hu.alkfejl.zh1gyak.modell.Order;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class AddOrderController implements Initializable {

    @FXML
    private TextField name;
    @FXML
    private ComboBox<String> title;
    @FXML
    private ComboBox<Integer> portion;
    @FXML
    private DatePicker delivery;
    @FXML
    private ToggleGroup payment;

    public void onCreateOrder(ActionEvent actionEvent) {
        String name = this.name.getText();
        String title = this.title.getValue();
        Integer portion = this.portion.getValue();
        LocalDate delivery = this.delivery.getValue();
        String payment = ((RadioButton)this.payment.getSelectedToggle()).getText();

        Order order = new OrderDAOImpl().save(new Order(name,title,portion,delivery,payment));

        App.loadFXML("/fxml/main.fxml");

    }

    public void onCancel(ActionEvent actionEvent) {
        App.loadFXML("/fxml/main.fxml");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Integer[] portions = {1,2,3,4,5,6};
        portion.setItems(FXCollections.observableArrayList(portions));
        portion.getSelectionModel().select(0);

        String[] titles = {"Grand Moff","Captain","Clone","Others"};
        title.setItems(FXCollections.observableArrayList(titles));
        title.getSelectionModel().select(0);
    }
}
