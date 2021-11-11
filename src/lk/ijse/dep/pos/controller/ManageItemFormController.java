/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lk.ijse.dep.pos.controller;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.dep.pos.business.BOFactory;
import lk.ijse.dep.pos.business.BOTypes;
import lk.ijse.dep.pos.business.custom.ItemBO;
import lk.ijse.dep.pos.business.exception.AlreadyExistsInOrderException;
import lk.ijse.dep.pos.db.HibernateUtil;
import lk.ijse.dep.pos.dto.ItemDTO;
import lk.ijse.dep.pos.util.ItemTM;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.export.Exporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleXlsxReportConfiguration;
import net.sf.jasperreports.view.JasperViewer;
import org.hibernate.Session;

import java.io.*;
import java.net.URL;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * FXML Controller class
 *
 * @author Niran Sudharaka
 */
public class ManageItemFormController implements Initializable {

    public JFXTextField txtCode;
    public JFXTextField txtDescription;
    public JFXTextField txtQtyOnHand;
    public TableView<ItemTM> tblItems;
    public JFXTextField txtUnitPrice;
    public JFXButton btnCart;
    public TextField txtItemSearch;
    public Button btnReport;
    public JFXTextField txtnewQty;
    @FXML
    private Button btnSave;
    @FXML
    private Button btnDelete;
    @FXML
    private AnchorPane root;

    private ItemBO itemBO = BOFactory.getInstance().getBO(BOTypes.ITEM);


    @Override
    public void initialize(URL url, ResourceBundle rb) {

        tblItems.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("icode"));
        tblItems.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("description"));
        tblItems.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("qtyOnHand"));
        tblItems.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("shout"));
        tblItems.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("balance"));
        tblItems.getColumns().get(5).setCellValueFactory(new PropertyValueFactory<>("vary"));


        txtCode.setDisable(true);
        txtDescription.setDisable(true);
        txtQtyOnHand.setDisable(true);
        txtUnitPrice.setDisable(true);
        btnDelete.setDisable(true);
        btnSave.setDisable(true);


        loadAllItems();

        //Disable textFields
        txtQtyOnHand.setDisable(true);
        txtnewQty.setVisible(false);


        tblItems.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<ItemTM>() {
            @Override
            public void changed(ObservableValue<? extends ItemTM> observable, ItemTM oldValue, ItemTM newValue) {
                ItemTM selectedItem = tblItems.getSelectionModel().getSelectedItem();

                if (selectedItem == null) {
                    btnSave.setText("Save");
                    btnDelete.setDisable(true);
                    return;
                }

                btnSave.setText("Update");
                btnSave.setDisable(false);
                btnDelete.setDisable(false);
                txtDescription.setDisable(false);
                txtQtyOnHand.setDisable(false);
                txtUnitPrice.setDisable(false);
                txtCode.setEditable(false);
                txtCode.setDisable(false);
                txtnewQty.setVisible(false);
                txtnewQty.setText("0");

                txtCode.setText(selectedItem.getIcode());
                txtDescription.setText(selectedItem.getDescription());
                txtQtyOnHand.setText(selectedItem.getQtyOnHand() + "");
                txtUnitPrice.setText("0");
            }
        });

        txtItemSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                tblItems.getItems().clear();
                List<ItemDTO> searchItems = itemBO.findSearchItems(newValue);
                ObservableList<ItemTM> items = tblItems.getItems();
                for (ItemDTO item : searchItems) {
                    Double getQtyOnHand = Double.parseDouble(String.format("%.3f", item.getQtyOnHand()));
                    Double shout = Double.parseDouble(String.format("%.3f", item.getShout()));
                    items.add(new ItemTM(item.getIcode(), item.getDescription(),
                            getQtyOnHand, shout, shout - getQtyOnHand, Double.parseDouble(String.format("%.2f", shout - (shout - getQtyOnHand)))));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }


        });


        txtnewQty.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                double newQty = Double.parseDouble(txtnewQty.getText());
                if (newQty > 0) {
                    btnSave.setText("Purchase");
                } else {
                    btnSave.setText("Update");
                }
            }
        });

    }

    private void loadAllItems() {
        try {
            List<ItemDTO> allItems = itemBO.findAllItems();
            ObservableList<ItemTM> items = tblItems.getItems();

            for (ItemDTO item : allItems) {
                Double getQtyOnHand = Double.parseDouble(String.format("%.3f", item.getQtyOnHand()));

                items.add(new ItemTM(item.getIcode(), item.getDescription(),
                        item.getShout(), item.getShout() - getQtyOnHand, item.getBalance(), item.getVary()));
            }

        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Something went wrong, please contact DEPPO").show();
            Logger.getLogger("lk.ijse.dep.pos.controller").log(Level.SEVERE, null, e);
        }
    }

    @FXML
    private void navigateToHome(MouseEvent event) throws IOException {
        URL resource = this.getClass().getResource("/lk/ijse/dep/pos/view/MainForm.fxml");
        Parent root = FXMLLoader.load(resource);
        Scene scene = new Scene(root);
        Stage primaryStage = (Stage) (this.root.getScene().getWindow());
        primaryStage.setScene(scene);
        primaryStage.centerOnScreen();
    }

    @FXML
    private void btnSave_OnAction(ActionEvent event) {
        saveItem(event);

    }

    private void saveItem(ActionEvent event) {
        Double qtyOnHand = Double.parseDouble(txtQtyOnHand.getText());
        Double uPrice = Double.parseDouble(txtUnitPrice.getText());

        if (btnSave.getText().equals("Save")) {
            ObservableList<ItemTM> items = tblItems.getItems();
            ItemTM newItem = new ItemTM(
                    txtCode.getText(),
                    txtDescription.getText(),
                    qtyOnHand,
                    qtyOnHand,
                    0.00,
                    0.00);
            ItemDTO item = new ItemDTO(txtCode.getText(),
                    txtDescription.getText(),
                    0.00,
                    Double.parseDouble(txtQtyOnHand.getText()),
                    Double.parseDouble(txtQtyOnHand.getText()),
                    0.00, 0.00);
            try {
                itemBO.saveItem(item);
                items.add(newItem);
                btnAddNew_OnAction(event);
            } catch (Exception e) {
                new Alert(Alert.AlertType.ERROR, "Something went wrong, please contact DEPPO").show();
                Logger.getLogger("lk.ijse.dep.pos.controller").log(Level.SEVERE, null, e);
            }
        } else if (btnSave.getText().equals("Update")) {
            String selectedItem = txtCode.getText();
            try {
                itemBO.updateItem(new ItemDTO(selectedItem,
                        txtDescription.getText(),
                        0.00,
                        Double.parseDouble(txtQtyOnHand.getText()),
                        Double.parseDouble(txtQtyOnHand.getText()),
                        0.00, 0.00));
                tblItems.getItems().clear();
                loadAllItems();

                btnAddNew_OnAction(event);
            } catch (Exception e) {
                new Alert(Alert.AlertType.ERROR, "Something went wrong, please contact DEPPO").show();
                Logger.getLogger("lk.ijse.dep.pos.controller").log(Level.SEVERE, null, e);
            }
        }
    }

    @FXML
    private void btnDelete_OnAction(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                "Are you sure whether you want to delete this item?",
                ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> buttonType = alert.showAndWait();
        if (buttonType.get() == ButtonType.YES) {
            ItemTM selectedItem = tblItems.getSelectionModel().getSelectedItem();
            try {
                itemBO.deleteItem(selectedItem.getIcode());
                tblItems.getItems().remove(selectedItem);
            } catch (AlreadyExistsInOrderException e) {
                new Alert(Alert.AlertType.INFORMATION, e.getMessage()).show();
            } catch (Exception e) {
                new Alert(Alert.AlertType.ERROR, "Something went wrong, please contact DEPPO").show();
                Logger.getLogger("lk.ijse.dep.pos.controller").log(Level.SEVERE, null, e);
            }
        }
    }

    @FXML
    private void btnAddNew_OnAction(ActionEvent actionEvent) {
        txtCode.clear();
        txtCode.setEditable(true);
        txtCode.setDisable(false);
        txtDescription.clear();
        txtQtyOnHand.clear();
        txtUnitPrice.clear();
        tblItems.getSelectionModel().clearSelection();
        txtDescription.setDisable(false);
        txtQtyOnHand.setDisable(false);
        txtQtyOnHand.setText("0");
        txtUnitPrice.setDisable(false);
        txtUnitPrice.setText("0");
        txtCode.requestFocus();
        btnSave.setDisable(false);

    }


    public void txtDesc_onAction(ActionEvent actionEvent) {
        txtQtyOnHand.requestFocus();
    }

    public void txtQOH_onAction(ActionEvent actionEvent) {
        txtUnitPrice.requestFocus();
    }

    public void btnCart_OnAction(ActionEvent actionEvent) {

    }

    public void tblOrders_onMouseClick(MouseEvent mouseEvent) throws IOException {
        if (mouseEvent.getClickCount() == 2) {

            URL resource = this.getClass().getResource("/view/PlaceOrderForm.fxml");
            FXMLLoader fxmlLoader = new FXMLLoader(resource);
            Parent root = fxmlLoader.load();
            Scene placeOrderScene = new Scene(root);


            PlaceOrderFormController ctrl = fxmlLoader.getController();
            ItemTM selectedOrder = tblItems.getSelectionModel().getSelectedItem();
            System.out.println(selectedOrder.getIcode());
            ctrl.txtItemCode.setText(selectedOrder.getIcode());


        }
    }

    public void txtCode_onAction(ActionEvent actionEvent) {
        try {
            ItemDTO item = itemBO.findItem(txtCode.getText());
            if (item == null) {
                txtDescription.requestFocus();
                txtDescription.clear();
                txtnewQty.setVisible(false);
                txtQtyOnHand.setDisable(false);

            } else {
                txtDescription.setText(item.getDescription());
                txtQtyOnHand.setDisable(true);
                txtQtyOnHand.setText(String.format("%.3f", item.getQtyOnHand()));
                txtnewQty.setVisible(false);
                txtnewQty.requestFocus();
                txtnewQty.selectAll();
                txtUnitPrice.setText(String.valueOf(item.getUnitPrice()));
                btnSave.setText("Update");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public void btnReport_onAction(ActionEvent actionEvent) {




        try {
/*
            JasperReport jasperReport;
            try (InputStream inputStream = JRLoader.getResourceInputStream("/lk/ijse/dep/pos/report/StockReport.jrxml")) {
                jasperReport = JasperCompileManager.compileReport(JRXmlLoader.load(inputStream));
            }
            Map<String, Object> params = new HashMap<>();

            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, new JREmptyDataSource());

            SimpleXlsxReportConfiguration configuration = new SimpleXlsxReportConfiguration();
            configuration.setOnePagePerSheet(true);
            configuration.setIgnoreGraphics(false);

            File outputFile = new File("media/sudharaka/MyFiles/Accadamic/ShopList.xlsx");
            try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                 OutputStream fileOutputStream = new FileOutputStream(outputFile)) {
                Exporter exporter = new JRXlsxExporter();
                exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
                exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(byteArrayOutputStream));
                exporter.setConfiguration(configuration);
                exporter.exportReport();
                byteArrayOutputStream.writeTo(fileOutputStream);
            }*/

            JasperReport jasperReport = (JasperReport) JRLoader.loadObject(this.getClass().getResourceAsStream("/lk/ijse/dep/pos/report/StockReport.jasper"));

            Map<String, Object> params1 = new HashMap<>();
            Session session = HibernateUtil.getSessionFactory().openSession();
            session.doWork(connection -> {

                try {
                    JasperPrint jasperPrint1 = JasperFillManager.fillReport(jasperReport, params1, connection);
                    JasperViewer Jviewer = new JasperViewer(jasperPrint1, false);
                    Jviewer.setVisible(true);
                    JasperPrintManager.printReport(jasperPrint1, false);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            session.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public void txtnewQty_onAction(ActionEvent actionEvent) {
        if (txtnewQty.getText().equals("") || txtnewQty.getText().equals("0")) {
            txtnewQty.setText("0");
            txtQtyOnHand.setDisable(false);
            txtQtyOnHand.requestFocus();
            txtQtyOnHand.selectAll();
        }

    }

    public void onEditChange(TableColumn.CellEditEvent cellEditEvent) {

    }

    public void txtUnitPrice_onAction(ActionEvent actionEvent) {

    }

    public void txtUnitPrice_onKeyPressed(KeyEvent keyEvent) {


    }
}


