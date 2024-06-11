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
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    DatabaseUtil databaseUtil = new DatabaseUtil();
    ObservableList<Product> observableProductList = null;
    int totalPages = 1;
    int currentPage = 1;
    int itemsPerPage = 10;
    int startIndex;
    int endIndex;
    String actualLoad = "all";

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
    private Label currentPageLabel;

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
            //atualizarTableView();
            loadProducts(currentPage);
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
        if(actualLoad.equals("all"))
            loadProducts(currentPage);
        else
            searchProduct();
    }
    @FXML
    public void searchProduct() {
        String name = textFieldSearch.getText();
        List<Product> productList = databaseUtil.getByName(name);
        observableProductList = FXCollections.observableArrayList(productList);
        loadProductsByName(observableProductList);
    }

    @FXML
    public void previousPage() {
        if (currentPage > 1) {
            currentPage--;
            atualizarTableView();
        }
    }

    @FXML
    public void nextPage() {
        if (currentPage < totalPages) {
            currentPage++;
            atualizarTableView();
        }
    }

    public void loadProducts(int page) {
        totalPages = (int) Math.ceil(databaseUtil.getTotalProductsCount() / 10.0);
        if(totalPages == 0) {
            currentPageLabel.setText("No results found.");
            return;
        }

        // Verifica se a página atual está fora do intervalo total de páginas
        if (page < 1) {
            page = 1;
        } else if (page > totalPages) {
            page = totalPages;
        }

        currentPageLabel.setText("Page " + page + " of " + totalPages);

        startIndex = (page - 1) * itemsPerPage;
        endIndex = Math.min(startIndex + itemsPerPage, databaseUtil.getTotalProductsCount());

        List<Product> products = databaseUtil.getProductsByRange(startIndex, endIndex);
        observableProductList = FXCollections.observableArrayList(products);
        actualLoad = "all";
        tableViewProduct.getItems().setAll(observableProductList);
    }

    public void loadProductsByName(ObservableList<Product> observableProductList) {
        if (observableProductList.isEmpty()) {
            tableViewProduct.getItems().clear();
            currentPageLabel.setText("No results found.");
            return;
        }

        totalPages = (int) Math.ceil(observableProductList.size() / 10.0);

        if (currentPage > totalPages) {
            currentPage = totalPages;
        }

        currentPageLabel.setText("Page " + currentPage + " of " + totalPages);

        startIndex = (currentPage - 1) * itemsPerPage;
        endIndex = Math.min(startIndex + itemsPerPage, observableProductList.size());

        List<Product>  products = observableProductList.subList(startIndex, endIndex);
        observableProductList = FXCollections.observableArrayList(products);
        actualLoad = "search";
        tableViewProduct.getItems().setAll(observableProductList);
    }


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initializeNodes();
        initializeTableView();
        messageLabel.setWrapText(true);
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

        loadProducts(1);
    }




}