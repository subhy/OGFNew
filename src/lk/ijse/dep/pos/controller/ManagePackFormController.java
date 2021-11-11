package lk.ijse.dep.pos.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
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
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.dep.pos.business.BOFactory;
import lk.ijse.dep.pos.business.BOTypes;
import lk.ijse.dep.pos.business.custom.ItemBO;
import lk.ijse.dep.pos.business.custom.PackBO;
import lk.ijse.dep.pos.business.exception.AlreadyExistsInOrderException;
import lk.ijse.dep.pos.dto.ItemDTO;
import lk.ijse.dep.pos.dto.PackDTO;
import lk.ijse.dep.pos.util.ItemTM;
import lk.ijse.dep.pos.util.PackTM;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ManagePackFormController implements Initializable {

    public JFXTextField txtqty1;
    public JFXTextField txtqty2;
    public JFXTextField txtqty3;
    public JFXTextField txtqty4;
    public JFXTextField txtqty5;

    @FXML
    private AnchorPane root;

    @FXML
    private JFXTextField txtCode;

    @FXML
    private JFXTextField txtDescription;

    @FXML
    private JFXButton btnSave;

    @FXML
    private JFXButton btnDelete;

    @FXML
    private TableView<PackTM> tblPack;

    @FXML
    private JFXTextField txtUnitPrice;

    @FXML
    private TextField txtItemSearch;

    @FXML
    private Button btnReport;

    @FXML
    private JFXTextField txtnewQty;

    @FXML
    private JFXComboBox<String> cmbItem1;

    @FXML
    private JFXComboBox<String> cmbItem4;

    @FXML
    private JFXComboBox<String> cmbItem5;

    @FXML
    private JFXComboBox<String> cmbItem3;

    @FXML
    private JFXComboBox<String> cmbItem2;

    private ItemBO itemBO= BOFactory.getInstance().getBO(BOTypes.ITEM);
    private PackBO packBO= BOFactory.getInstance().getBO(BOTypes.PACK);

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        tblPack.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("code"));
        tblPack.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("packName"));
        tblPack.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        tblPack.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("pack01"));
        tblPack.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("pack02"));
        tblPack.getColumns().get(5).setCellValueFactory(new PropertyValueFactory<>("pack03"));
        tblPack.getColumns().get(6).setCellValueFactory(new PropertyValueFactory<>("pack04"));
        tblPack.getColumns().get(7).setCellValueFactory(new PropertyValueFactory<>("pack05"));


       //fields ready
        txtCode.setDisable(true);
        txtDescription.setDisable(true);
        txtUnitPrice.setDisable(true);
        cmbItem1.setDisable(true);
        cmbItem2.setDisable(true);
        cmbItem3.setDisable(true);
        cmbItem4.setDisable(true);
        cmbItem5.setDisable(true);

        btnDelete.setDisable(true);
        btnSave.setDisable(true);

        //load all items in to combo box
        try {
          loadCombo();
            //Load All packs into table
            getAllData();
        } catch (Exception e) {
            e.printStackTrace();
        }


      //When table row is clicked
        tblPack.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<PackTM>() {
            @Override
            public void changed(ObservableValue<? extends PackTM> observable, PackTM oldValue, PackTM newValue) {
                PackTM selectedItem = tblPack.getSelectionModel().getSelectedItem();

                if (selectedItem == null) {
                    btnSave.setText("Save");
                    btnDelete.setDisable(true);
                    return;
                }

                btnSave.setText("Update");
                btnSave.setDisable(false);
                btnDelete.setDisable(false);

                txtCode.setDisable(false);
                txtDescription.setDisable(false);
                txtUnitPrice.setDisable(false);
                txtCode.setText(selectedItem.getCode());
                txtDescription.setText(selectedItem.getPackName());
                txtUnitPrice.setText(selectedItem.getUnitPrice()+"");

                cmbItem1.getItems().clear();
                cmbItem1.setDisable(false);
                cmbItem1.getItems().add(selectedItem.getPack01().substring(0, selectedItem.getPack01().length() - 2));
                txtqty1.setText(selectedItem.getPack01().substring(selectedItem.getPack01().length() - 2));
                cmbItem1.getSelectionModel().selectFirst();

                cmbItem2.getItems().clear();
                cmbItem2.setDisable(false);
                cmbItem2.getItems().add(selectedItem.getPack02().substring(0, selectedItem.getPack02().length() - 2));
                txtqty2.setText(selectedItem.getPack02().substring(selectedItem.getPack02().length() - 2));
                cmbItem2.getSelectionModel().selectFirst();


                cmbItem3.getItems().clear();
                cmbItem3.setDisable(false);
                cmbItem3.getItems().add(selectedItem.getPack03().substring(0, selectedItem.getPack03().length() - 2));
                txtqty3.setText(selectedItem.getPack03().substring(selectedItem.getPack03().length() - 2));
                cmbItem3.getSelectionModel().selectFirst();

                cmbItem4.getItems().clear();
                cmbItem4.setDisable(false);
                cmbItem4.getItems().add(selectedItem.getPack04().substring(0, selectedItem.getPack04().length() - 2));
                txtqty4.setText(selectedItem.getPack04().substring(selectedItem.getPack04().length() - 2));
                cmbItem4.getSelectionModel().selectFirst();


                cmbItem5.getItems().clear();
                cmbItem5.setDisable(false);
                cmbItem5.getItems().add(selectedItem.getPack05().substring(0, selectedItem.getPack05().length() - 2));
                txtqty5.setText(selectedItem.getPack05().substring(selectedItem.getPack05().length() - 2));
                cmbItem5.getSelectionModel().selectFirst();

            }
        });

    }

    private void loadCombo() {
     try {
         cmbItem1.getItems().clear();
         cmbItem2.getItems().clear();
         cmbItem3.getItems().clear();
         cmbItem4.getItems().clear();
         cmbItem5.getItems().clear();
         List<ItemDTO> allItems = itemBO.findAllItems();
         for (ItemDTO item : allItems) {
             cmbItem1.getItems().add(item.getDescription());
             cmbItem1.getSelectionModel().selectFirst();
             cmbItem2.getItems().add(item.getDescription());
             cmbItem2.getSelectionModel().selectFirst();
             cmbItem3.getItems().add(item.getDescription());
             cmbItem3.getSelectionModel().selectFirst();
             cmbItem4.getItems().add(item.getDescription());
             cmbItem4.getSelectionModel().selectFirst();
             cmbItem5.getItems().add(item.getDescription());
             cmbItem5.getSelectionModel().selectFirst();
         }
     }catch (Exception e){
         System.out.println(e);
     }
    }

    private void getAllData() throws Exception {
       try {
           List<PackDTO> allItems = packBO.findAllItems();
           ObservableList<PackTM> items = tblPack.getItems();

           for (PackDTO item : allItems) {
               Double unitPrice = Double.parseDouble(String.format("%.2f", item.getUnitPrice()));

               items.add(new PackTM(item.getCode(), item.getPackName(), unitPrice,
                       item.getPack01(), item.getPack02(), item.getPack03(), item.getPack04(), item.getPack05()));
           }
       }catch (Exception e){
           System.out.println(e);
       }
    }

    @FXML
    void btnAddNew_OnAction(ActionEvent event) {

        try {
            tblPack.getItems().clear();
            loadCombo();
            getAllData();
            txtCode.setDisable(false);
            txtDescription.setDisable(false);
            txtUnitPrice.setDisable(false);
            cmbItem1.setDisable(false);
            cmbItem2.setDisable(false);
            cmbItem3.setDisable(false);
            cmbItem4.setDisable(false);
            cmbItem5.setDisable(false);
            btnSave.setDisable(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void btnDelete_OnAction(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                "Are you sure whether you want to delete this pack?",
                ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> buttonType = alert.showAndWait();
        if (buttonType.get() == ButtonType.YES) {
            PackTM selectedItem = tblPack.getSelectionModel().getSelectedItem();
            try {
                packBO.deletePack(selectedItem.getCode());
                tblPack.getItems().remove(selectedItem);
            } catch (AlreadyExistsInOrderException e) {
                new Alert(Alert.AlertType.INFORMATION, e.getMessage()).show();
            } catch (Exception e) {
                new Alert(Alert.AlertType.ERROR, "Something went wrong, please contact DEPPO").show();
                Logger.getLogger("lk.ijse.dep.pos.controller").log(Level.SEVERE, null, e);
            }
        }

    }

    @FXML
    void btnReport_onAction(ActionEvent event) {

    }

    @FXML
    void btnSave_OnAction(ActionEvent event) {

        String pack1="";
        String pack2="";
        String pack3="";
        String pack4="";
        String pack5="";
        int length = txtqty1.getText().length();

        if (cmbItem1.getSelectionModel().getSelectedItem() != null && txtqty1.getText().length() == 1) {
             pack1 = cmbItem1.getSelectionModel().getSelectedItem() + "0" + txtqty1.getText();
        } else if (cmbItem1.getSelectionModel().getSelectedItem() != null && txtqty1.getText().length() == 2) {
             pack1 = cmbItem1.getSelectionModel().getSelectedItem() + txtqty1.getText();
        } else{
             pack1 = "null";
        }
        if (cmbItem2.getSelectionModel().getSelectedItem() != null && txtqty2.getText().length() == 1) {
             pack2 = cmbItem2.getSelectionModel().getSelectedItem() + "0" + txtqty2.getText();
        } else if (cmbItem2.getSelectionModel().getSelectedItem() != null && txtqty2.getText().length() == 2) {
             pack2 = cmbItem2.getSelectionModel().getSelectedItem() + txtqty2.getText();
        } else {
             pack2 = "null";
        }
        if (cmbItem3.getSelectionModel().getSelectedItem()!=null && txtqty3.getText().length()==1){
             pack3=cmbItem3.getSelectionModel().getSelectedItem()+"0"+txtqty3.getText();
        }else if(cmbItem3.getSelectionModel().getSelectedItem()!=null && txtqty3.getText().length()==2) {
             pack3=cmbItem3.getSelectionModel().getSelectedItem()+txtqty3.getText();
        }else{
             pack3="null";
        }
        if (cmbItem4.getSelectionModel().getSelectedItem()!=null && txtqty4.getText().length()==1){
             pack4=cmbItem4.getSelectionModel().getSelectedItem()+"0"+txtqty4.getText();
        }else if(cmbItem4.getSelectionModel().getSelectedItem()!=null && txtqty4.getText().length()==2) {
             pack4=cmbItem4.getSelectionModel().getSelectedItem()+txtqty4.getText();
        }else{
             pack4="null";
        }
        if (cmbItem5.getSelectionModel().getSelectedItem()!=null && txtqty5.getText().length()==1){
             pack5=cmbItem5.getSelectionModel().getSelectedItem()+"0"+txtqty5.getText();
        }else if(cmbItem5.getSelectionModel().getSelectedItem()!=null && txtqty5.getText().length()==2) {
             pack5=cmbItem5.getSelectionModel().getSelectedItem()+txtqty5.getText();
        }else{
             pack5="null";
        }



        if (btnSave.getText().equals("Save")) {
            ObservableList<PackTM> items = tblPack.getItems();
            PackTM newItem = new PackTM(
                    txtCode.getText(),
                    txtDescription.getText(),
                   Double.parseDouble(txtUnitPrice.getText()),
                   pack1,pack2,pack3,pack4,pack5);
            PackDTO item = new PackDTO(
                    txtCode.getText(),
                    txtDescription.getText(),

                    pack1,pack2,pack3,pack4,pack5,Double.parseDouble(txtUnitPrice.getText()));
            try {
                packBO.savePack(item);
                items.add(newItem);
                btnAddNew_OnAction(event);
            } catch (Exception e) {
                new Alert(Alert.AlertType.ERROR, "Something went wrong, please contact DEPPO").show();
                Logger.getLogger("lk.ijse.dep.pos.controller").log(Level.SEVERE, null, e);
            }
        } else if (btnSave.getText().equals("Update")) {
            String selectedItem = txtCode.getText();
            try {
                packBO.updatePack(new PackDTO(selectedItem,
                        txtDescription.getText(),
                        pack1,pack2,pack3,pack4,pack5,Double.parseDouble(txtUnitPrice.getText())));
                tblPack.getItems().clear();
                getAllData();

                btnAddNew_OnAction(event);
            } catch (Exception e) {
                new Alert(Alert.AlertType.ERROR, "Something went wrong, please contact DEPPO").show();
                Logger.getLogger("lk.ijse.dep.pos.controller").log(Level.SEVERE, null, e);
            }
        }
    }

    @FXML
    void cmbItem1_onAction(ActionEvent event) {

    }

    @FXML
    void cmbItem2_onAction(ActionEvent event) {

    }

    @FXML
    void cmbItem3_onAction(ActionEvent event) {

    }

    @FXML
    void cmbItem4_onAction(ActionEvent event) {

    }

    @FXML
    void cmbItem5_onAction(ActionEvent event) {

    }

    @FXML
    void navigateToHome(MouseEvent event) throws IOException {
        URL resource = this.getClass().getResource("/lk/ijse/dep/pos/view/MainForm.fxml");
        Parent root = FXMLLoader.load(resource);
        Scene scene = new Scene(root);
        Stage primaryStage = (Stage) (this.root.getScene().getWindow());
        primaryStage.setScene(scene);
        primaryStage.centerOnScreen();
    }

    @FXML
    void txtCode_onAction(ActionEvent event) {

    }

    @FXML
    void txtDesc_onAction(ActionEvent event) {

    }

    @FXML
    void txtUnitPrice_onAction(ActionEvent event) {

    }

    @FXML
    void txtUnitPrice_onKeyPressed(KeyEvent event) {
        if (event.getCode()== KeyCode.ENTER){
            cmbItem1.requestFocus();
            cmbItem1.getSelectionModel().selectFirst();
        }
    }

    @FXML
    void txtnewQty_onAction(ActionEvent event) {

    }


    public void cmbItem1_onKeyPressed(KeyEvent keyEvent) {
        if (keyEvent.getCode()== KeyCode.ENTER){
            txtqty1.requestFocus();
            txtqty1.selectAll();
        }

    }

    public void cmbItem4_onKeyPressed(KeyEvent keyEvent) {
        if (keyEvent.getCode()== KeyCode.ENTER){
            txtqty4.requestFocus();
            txtqty4.selectAll();
        }
    }

    public void cmbItem5_onKeyPressed(KeyEvent keyEvent) {
        if (keyEvent.getCode()== KeyCode.ENTER){
            txtqty5.requestFocus();
            txtqty5.selectAll();
        }
    }

    public void cmbItem3_onKeyPressed(KeyEvent keyEvent) {
        if (keyEvent.getCode()== KeyCode.ENTER){
            txtqty3.requestFocus();
            txtqty3.selectAll();
        }
    }

    public void cmbItem2_onKeyPressed(KeyEvent keyEvent) {
        if (keyEvent.getCode()== KeyCode.ENTER){
            txtqty2.requestFocus();
            txtqty2.selectAll();
        }
    }

    public void txtqty1_onKeyPressed(KeyEvent keyEvent) {
        if (keyEvent.getCode()== KeyCode.ENTER){
            cmbItem2.requestFocus();
            cmbItem2.getSelectionModel().selectFirst();
        }
    }

    public void txtQty2_onKeyPressed(KeyEvent keyEvent) {
        if (keyEvent.getCode()== KeyCode.ENTER){
            cmbItem3.requestFocus();
            cmbItem3.getSelectionModel().selectFirst();
        }
    }

    public void txtQty3_onKeyPressed(KeyEvent keyEvent) {
        if (keyEvent.getCode()== KeyCode.ENTER){
            cmbItem4.requestFocus();
            cmbItem4.getSelectionModel().selectFirst();
        }
    }

    public void txtQty4_onKeyPressed(KeyEvent keyEvent) {
        if (keyEvent.getCode()== KeyCode.ENTER){
            cmbItem5.requestFocus();
            cmbItem5.getSelectionModel().selectFirst();
        }
    }

    public void txtQty5_onKeyPressed(KeyEvent keyEvent) {
    }

    public void txtCode_onKeyPressed(KeyEvent keyEvent) {
        if (keyEvent.getCode()== KeyCode.ENTER){
            txtDescription.requestFocus();
        }
    }

    public void txtDesc_onKeyPressed(KeyEvent keyEvent) {
        if (keyEvent.getCode()== KeyCode.ENTER){
            txtUnitPrice.requestFocus();
            txtUnitPrice.selectAll();
        }
    }
}
