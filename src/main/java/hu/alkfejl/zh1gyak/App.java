package hu.alkfejl.zh1gyak;

import hu.alkfejl.zh1gyak.config.ConfigurationHelper;
import hu.alkfejl.zh1gyak.dao.OrderDAOImpl;
import hu.alkfejl.zh1gyak.modell.Order;
import javafx.application.Application;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * JavaFX App
 */
public class App extends Application implements Initializable {

    private static Stage stage;
    @FXML
    private TableView tableView;
    @FXML
    private TableColumn<Order,String> customerCol;
    @FXML
    private TableColumn<Order,String> titleCol;
    @FXML
    private TableColumn<Order,Number> portionCol;
    @FXML
    private TableColumn<Order,String> paymentCol;

    public static FXMLLoader loadFXML(String fxml){

        FXMLLoader loader = new FXMLLoader(App.class.getResource(fxml));
        Scene scene = null;
        try {
            Parent root = loader.load();
            scene = new Scene(root);
        } catch (IOException e) {
            e.printStackTrace();
        }

        stage.setScene(scene);
        return loader;
    }

    @Override
    public void start(Stage stage) throws IOException {
        App.stage = stage;
        App.loadFXML("/fxml/main.fxml");
        stage.show();

        System.out.println(ConfigurationHelper.getValue("db.url"));
    }

    public static void main(String[] args) {
        launch();
    }

    public void addNewOrder(ActionEvent actionEvent) {
        App.loadFXML("/fxml/new_order.fxml");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        customerCol.setCellValueFactory(c-> new SimpleStringProperty(c.getValue().getCustomer()));
        titleCol.setCellValueFactory(c-> new SimpleStringProperty(c.getValue().getTitle()));
        paymentCol.setCellValueFactory(c-> new SimpleStringProperty(c.getValue().getPayment()));
        portionCol.setCellValueFactory(c-> new SimpleIntegerProperty(c.getValue().getPortion()));

        tableView.getItems().setAll(new OrderDAOImpl().getOrders());
    }
}