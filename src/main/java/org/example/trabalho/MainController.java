package org.example.trabalho;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.trabalho.database.DatabaseUtil;
import org.example.trabalho.entities.Product;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    DatabaseUtil databaseUtil = new DatabaseUtil();

    @FXML
    private TableView<Product> tableViewProduct;

    @FXML
    private TableColumn<Product, Integer> tableColumnId;
    @FXML
    private TableColumn<Product, String> tableColumnName;
    @FXML
    private TableColumn<Product, String> tableColumnShortDescription;
    @FXML
    private TableColumn<Product, String> tableColumnBrand;
    @FXML
    private TableColumn<Product, String> tableColumnCategory;
    @FXML
    private TableColumn<Product, Double> tableColumnListPrice;
    @FXML
    private TableColumn<Product, Double> tableColumnCost;

    @FXML
    private TextField textFieldSearch;
    @FXML
    private TextField textFieldName;
    @FXML
    private TextField textFieldShortDescription;
    @FXML
    private TextField textFieldBrand;
    @FXML
    private TextField textFieldCategory;
    @FXML
    private TextField textFieldListPrice;
    @FXML
    private TextField textFieldCost;

    @FXML
    private Button searchButton;
    @FXML
    private Button createButton;
    @FXML
    private Button updateButton;
    @FXML
    private Button deleteButton;

    @FXML
    private Label messageLabel;

    @FXML
    public void onSearchButtonAction() {
        System.out.println("onSearchButtonAction");
    }

    @FXML
    public void createNewProduct() {
        String name = textFieldName.getText();
        String listPriceText = textFieldListPrice.getText();

        if (name.isEmpty() || listPriceText.isEmpty()) {
            messageLabel.setText("Name and List Price are required.");
            return;
        }

        try {
            double listPrice = Double.parseDouble(listPriceText);
            String shortDescription = textFieldShortDescription.getText();
            String brand = textFieldBrand.getText();
            String category = textFieldCategory.getText();
            double cost = textFieldCost.getText().isEmpty() ? 0 : Double.parseDouble(textFieldCost.getText());

            Product product = new Product(name, shortDescription, brand, category, listPrice, cost);

            databaseUtil.createProduct(product);
            atualizarTableView();
            messageLabel.setText("");
        } catch (NumberFormatException e) {
            messageLabel.setText("List Price and Cost must be valid numbers.");
        }
    }

    @FXML
    public void updateProduct() {
        Product product = tableViewProduct.getSelectionModel().getSelectedItem();

        if (product == null) {
            messageLabel.setText("Select a product to update.");
            return;
        }

        String name = textFieldName.getText();
        String listPriceText = textFieldListPrice.getText();

        if (name.isEmpty() || listPriceText.isEmpty()) {
            messageLabel.setText("Name and List Price are required.");
            return;
        }

        try {
            double listPrice = Double.parseDouble(listPriceText);
            String shortDescription = textFieldShortDescription.getText();
            String brand = textFieldBrand.getText();
            String category = textFieldCategory.getText();
            double cost = textFieldCost.getText().isEmpty() ? 0 : Double.parseDouble(textFieldCost.getText());

            product.nameProperty().set(name);
            product.shortDescriptionProperty().set(shortDescription);
            product.brandProperty().set(brand);
            product.categoryProperty().set(category);
            product.listPriceProperty().set(listPrice);
            product.costProperty().set(cost);

            databaseUtil.updateProduct(product);
            atualizarTableView();
            messageLabel.setText("");
        } catch (NumberFormatException e) {
            messageLabel.setText("List Price and Cost must be valid numbers.");
        }
    }

    @FXML
    public void deleteProduct() {
        Product product = tableViewProduct.getSelectionModel().getSelectedItem();

        if (product == null) {
            messageLabel.setText("Select a product to delete.");
            return;
        }

        databaseUtil.deleteProduct(product);
        atualizarTableView();
        messageLabel.setText("");
    }

    public void atualizarTableView() {
        List<Product> productList = DatabaseUtil.getProducts();
        ObservableList<Product> observableProductList = FXCollections.observableArrayList(productList);
        tableViewProduct.setItems(observableProductList);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initializeNodes();
        initializeTableView();
    }

    private void initializeNodes() {
        tableColumnId.setCellValueFactory(new PropertyValueFactory<>("id"));
        tableColumnName.setCellValueFactory(new PropertyValueFactory<>("name"));
        tableColumnShortDescription.setCellValueFactory(new PropertyValueFactory<>("shortDescription"));
        tableColumnBrand.setCellValueFactory(new PropertyValueFactory<>("brand"));
        tableColumnCategory.setCellValueFactory(new PropertyValueFactory<>("category"));
        tableColumnListPrice.setCellValueFactory(new PropertyValueFactory<>("listPrice"));
        tableColumnCost.setCellValueFactory(new PropertyValueFactory<>("cost"));
    }

    private void initializeTableView() {
        tableColumnId.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());
        tableColumnName.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        tableColumnShortDescription.setCellValueFactory(cellData -> cellData.getValue().shortDescriptionProperty());
        tableColumnBrand.setCellValueFactory(cellData -> cellData.getValue().brandProperty());
        tableColumnCategory.setCellValueFactory(cellData -> cellData.getValue().categoryProperty());
        tableColumnListPrice.setCellValueFactory(cellData -> cellData.getValue().listPriceProperty().asObject());
        tableColumnCost.setCellValueFactory(cellData -> cellData.getValue().costProperty().asObject());

        tableViewProduct.getItems().addAll(DatabaseUtil.getProducts());
    }



}