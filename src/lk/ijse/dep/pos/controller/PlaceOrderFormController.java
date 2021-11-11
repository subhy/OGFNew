package lk.ijse.dep.pos.controller;


import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TouchEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.dep.pos.business.BOFactory;
import lk.ijse.dep.pos.business.BOTypes;
import lk.ijse.dep.pos.business.custom.*;
import lk.ijse.dep.pos.db.HibernateUtil;
import lk.ijse.dep.pos.dto.*;
import lk.ijse.dep.pos.dto.payloadDTO.PosSalesPayloadDTO;
import lk.ijse.dep.pos.entity.ItemPayload;
import lk.ijse.dep.pos.entity.OrderDetailPK;
import lk.ijse.dep.pos.entity.PosSalesPayload;
import lk.ijse.dep.pos.util.OrderDetailTM;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.util.JRLoader;
import org.hibernate.Session;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PlaceOrderFormController {

    public JFXTextField txtDescription;
    public JFXTextField txtCustomerName;
    public JFXTextField txtQtyOnHand;
    public JFXButton btnSave;
    public TableView<OrderDetailTM> tblOrderDetails;
    public JFXTextField txtUnitPrice;
    public JFXComboBox<String> cmbCustomerId;

    public JFXTextField txtQty;
    public Label lblTotal;
    public JFXButton btnPlaceOrder;
    public AnchorPane root;
    public Label lblId;
    public JFXButton btnAddNewOrder;
    public JFXTextField txtItemCode;
    public Button btnSearch;
    public JFXComboBox cmbSearch;
    public JFXTextField txtAmount;
    public JFXComboBox<String> cmbPMethod;
    public JFXComboBox<String> cmbTransAcc;
    public JFXTextField txtDisc;
    public Label lblDisTotal;
    public JFXTextField txtSearchOrder;
    public JFXDatePicker dpDate;
    public JFXTextField txtSearchId;
    public JFXTextField txtICategory;
    public JFXTextField txtONo;
    public JFXButton btnNo7;
    public JFXButton btnNo4;
    public JFXButton btnNo1;
    public JFXButton btnNo8;
    public JFXButton btnNo5;
    public JFXButton btnNo2;
    public JFXButton btnNo9;
    public JFXButton btnNo6;
    public JFXButton btnNo3;
    public JFXButton btnAddNewOrder1311;
    public JFXButton btnClear;
    public JFXButton btnNo0;
    public JFXButton btnEnter;
    public JFXTextField txtICPoint;


    String maxTrans = "";
    Double pDisAmount = 0.00;
    Double pTotal = 0.00;
    Double transAmount = 0.00;
    Double transDisAmount = 0.00;
    String transDebNo = "";
    String debtorAccNo = "";
    String oilTransNo = "";

    private boolean readOnly = false;
    private List<ItemDTO> tempItems = new ArrayList<>();
    private List<PackDTO> tempPacks = new ArrayList<>();
    private CustomerBO customerBO = BOFactory.getInstance().getBO(BOTypes.CUSTOMER);
    private ItemBO itemBO = BOFactory.getInstance().getBO(BOTypes.ITEM);
    private OrderBO orderBO = BOFactory.getInstance().getBO(BOTypes.ORDER);
    private transactiondetailBO transacDetailBO = BOFactory.getInstance().getBO(BOTypes.TRANSACTIONDETAIL);
    private GenAccBO genAccBO = BOFactory.getInstance().getBO(BOTypes.GENACC);
    private PackBO packBO = BOFactory.getInstance().getBO(BOTypes.PACK);
    private OrderDetailBO orderDetailA = BOFactory.getInstance().getBO(BOTypes.ORDERDETAIL);
    private ODetailBO oDetailBO = BOFactory.getInstance().getBO(BOTypes.ODETAIL);
    private OrderListBO itemPayloadBO = BOFactory.getInstance().getBO(BOTypes.ORDERLIST);

    public void initialize() {

        reset();

        // Let's map columns with table model
        tblOrderDetails.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("code"));
        tblOrderDetails.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("description"));
        tblOrderDetails.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("qty"));
        tblOrderDetails.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        tblOrderDetails.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("total"));
        tblOrderDetails.getColumns().get(5).setCellValueFactory(new PropertyValueFactory<>("btnDelete"));
        tblOrderDetails.getColumns().get(6).setCellValueFactory(new PropertyValueFactory<>("icategory"));
        tblOrderDetails.getColumns().get(6).setCellValueFactory(new PropertyValueFactory<>("icategory2"));

        try {

          /*  List<String> ids = customerBO.getAllCustomerIDs();
            cmbCustomerId.setItems(FXCollections.observableArrayList(ids));
            cmbCustomerId.getSelectionModel().selectFirst();
            tempItems = itemBO.findAllItems();

            List<String> searchItems = itemBO.getAllItemCodes();
            for (String item : searchItems) {
                cmbSearch.setItems(FXCollections.observableArrayList(searchItems));
            }
*/
            //Insert Data into account
         /*   List<GenAccDTO> allAcc = genAccBO.findAllAcc();
            for (GenAccDTO genAccDTO : allAcc) {

                cmbTransAcc.getItems().add(genAccDTO.getAccNo());
            }*/

        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Something went wrong, please contact DEPPO2").show();
            Logger.getLogger("lk.ijse.dep.pos.controller").log(Level.SEVERE, null, e);
        }

        // When customer id is selected
        cmbCustomerId.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                String selectedCustomerID = cmbCustomerId.getSelectionModel().getSelectedItem();
                enablePlaceOrderButton();

                try {
                    if (selectedCustomerID != null) {
                        CustomerDTO customer = customerBO.findCustomer(selectedCustomerID);
                        txtCustomerName.setText(customer.getName());
                    } else {

                        cmbCustomerId.getSelectionModel().selectFirst();
                        txtCustomerName.setText("Default");
                    }

                } catch (Exception e) {
                    new Alert(Alert.AlertType.ERROR, "Something went wrong, please contact DEPPO3").show();
                    Logger.getLogger("lk.ijse.dep.pos.controller").log(Level.SEVERE, null, e);
                }
            }
        });


        // When a table row is selected
        tblOrderDetails.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<OrderDetailTM>() {
            @Override
            public void changed(ObservableValue<? extends OrderDetailTM> observable, OrderDetailTM oldValue, OrderDetailTM newValue) {

                OrderDetailTM selectedOrderDetail = tblOrderDetails.getSelectionModel().getSelectedItem();
                if (selectedOrderDetail == null) {
                    if (!readOnly) {
                        btnSave.setText("Add");
                    }
                    return;
                }


                PackDTO item = null;
                try {
                    item = packBO.findItem(selectedOrderDetail.getCode());
                    txtItemCode.setText(selectedOrderDetail.getCode());
                    txtDescription.setText(item.getPackName());
                    txtUnitPrice.setText(String.valueOf(item.getUnitPrice()));

                    String[] packs = {item.getPack01().substring(0, item.getPack01().length() - 2),
                            item.getPack02().substring(0, item.getPack02().length() - 2),
                            item.getPack03().substring(0, item.getPack03().length() - 2),
                            item.getPack04().substring(0, item.getPack04().length() - 2),
                            item.getPack05().substring(0, item.getPack05().length() - 2)};
                    System.out.println(packs);
                    double minQty = 0;
                    for (int i = 0; i < 4; i++) {
                        minQty = itemBO.findMinQty(packs[0], packs[1], packs[2], packs[3], packs[4]);
                    }
                    txtQtyOnHand.setText(String.valueOf(minQty));
                } catch (Exception e) {
                    e.printStackTrace();
                }

                txtQty.setText(selectedOrderDetail.getQty() + "");
                // Don't think about this now...!

                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        txtQty.requestFocus();
                        txtQty.selectAll();
                    }
                });
                if (!readOnly) {
                    btnSave.setText("Update");
                }
            }
        });


        cmbSearch.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                String selectedItem = (String) cmbSearch.getSelectionModel().getSelectedItem();

                try {
                    if (selectedItem != null) {
                        ItemDTO item = itemBO.findItem(selectedItem);
                        txtItemCode.setText(item.getIcode());
                        txtDescription.setText(item.getDescription());
                    } else {
                        txtItemCode.setText("");
                        txtDescription.setText("");

                    }

                } catch (Exception e) {
                    new Alert(Alert.AlertType.ERROR, "Something went wrong, please contact DEPPO3").show();
                    Logger.getLogger("lk.ijse.dep.pos.controller").log(Level.SEVERE, null, e);
                }
            }
        });

        //SearchOrder

        // dpDate.requestFocus();
        txtItemCode.requestFocus();
        txtICPoint.setText("txtItemCode");

        //Load cmb Payment Method data
        cmbPMethod.getItems().clear();
        cmbPMethod.getItems().addAll("Cash", "Credit Card", "Uber", "Pick Me", "Free Issue", "Staff");
        cmbPMethod.getSelectionModel().selectFirst();

        cmbTransAcc.setVisible(false);

    }

    private void reset() {
        // Initialize controls to their default states

        dpDate.setValue(LocalDate.now());
        btnSave.setDisable(true);
        txtCustomerName.setEditable(false);
        txtCustomerName.clear();
        txtDescription.setEditable(false);
        txtUnitPrice.setEditable(false);
        txtQtyOnHand.setEditable(false);
        txtQty.setEditable(false);
        txtQty.setText("0");
        txtDisc.setText("0.00");
        cmbCustomerId.getSelectionModel().clearSelection();
        tblOrderDetails.getItems().clear();
        lblTotal.setText("Total : 0.00");
        lblDisTotal.setText("Total : 0.00");
        enablePlaceOrderButton();
        cmbPMethod.getItems().clear();
        cmbPMethod.getItems().addAll("Cash", "Credit Card", "Uber", "Pick Me", "Free Issue", "Staff");
        cmbPMethod.getSelectionModel().selectFirst();
        btnPlaceOrder.setText("Place Order");
        clearItem();
        getNxtOrderNo();

    }

    private void getNxtOrderNo() {
        int orderNo = 1;
        txtONo.setText(String.valueOf(orderNo));
        int maxOrderId = 0;
        try {
           //Previously taken from oDetails Table Now from itemPayload
           /* maxOrderId = oDetailBO.getLastOrderId(); */
            maxOrderId = itemPayloadBO.getLastOrderId();
            maxOrderId++;
            System.out.println(maxOrderId +"Menna Max");
            if (maxOrderId < 10) {
                lblId.setText("Order ID : R00" + maxOrderId);
            } else if (maxOrderId < 100) {
                lblId.setText("Order ID : R0" + maxOrderId);
            } else {
                lblId.setText("Order ID : R" + maxOrderId);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private void clearItem() {
        txtItemCode.clear();
        txtDescription.clear();
        txtUnitPrice.clear();
        txtUnitPrice.setText("0.00");
        txtAmount.setText("0.00");
        txtQtyOnHand.clear();
        txtQtyOnHand.setText("0");
        txtQty.setText("0");
        btnSave.setDisable(true);

    }

    public void btnAddNew_OnAction(ActionEvent actionEvent) {
        // dpDate.requestFocus();
        if (tblOrderDetails.getItems().size() > 0) {
            btnAddNewOrder.setDisable(true);

            new Alert(Alert.AlertType.ERROR, "Please delete items in table", ButtonType.OK).show();

        } else {
            btnAddNewOrder.setDisable(false);
            txtItemCode.requestFocus();
            txtICPoint.setText("txtItemCode");
            reset();
        }

    }

    public void btnAdd_OnAction(ActionEvent actionEvent) {
        double qty = Double.parseDouble(txtQty.getText());
        double qtyOnHand = Double.parseDouble(txtQtyOnHand.getText());
        double unitPrice = Double.parseDouble(txtUnitPrice.getText());


        String selectedItemCode = txtItemCode.getText().toUpperCase();
        ObservableList<OrderDetailTM> details = tblOrderDetails.getItems();

        boolean isExists = false;
        for (OrderDetailTM detail : tblOrderDetails.getItems()) {
            if (detail.getCode().equals(selectedItemCode)) {
                isExists = true;

                if (btnSave.getText().equals("Update")) {
                    detail.setQty(Double.parseDouble(String.format("%.3f", detail.getQty() + qty)));

                } else {
                    detail.setQty(Double.parseDouble(String.format("%.3f", detail.getQty() + qty)));

                }
                detail.setTotal(Double.parseDouble(String.format("%.2f", detail.getQty() * detail.getUnitPrice())));
                tblOrderDetails.refresh();

                break;
            }
        }

        if (!isExists) {
            int orderNo = Integer.parseInt(txtONo.getText());
            NumberFormat nf = NumberFormat.getInstance();
            nf.setMaximumFractionDigits(2);
            nf.setMinimumFractionDigits(2);
            nf.setGroupingUsed(false);

            Double amount = Double.valueOf(nf.format(qty * unitPrice));

            JFXButton btnDelete = new JFXButton("Delete");
            OrderDetailTM detailTM = new OrderDetailTM(txtItemCode.getText().toUpperCase(),
                    txtDescription.getText(),
                    qty,
                    unitPrice, amount,
                    btnDelete, txtONo.getText(), txtONo.getText()
            );
            btnDelete.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    System.out.println("first Step");
                    /*for (PackDTO tempPack : tempPacks) {
                        if (tempPack.getCode().equals(detailTM.getCode())) {
                            System.out.println("Second Step"+ tempPack.getCode());

                            // Let's restore the qty at database

                            String[] packs = {tempPack.getPack01().substring(0, tempPack.getPack01().length() - 2),
                                    tempPack.getPack02().substring(0, tempPack.getPack02().length() - 2),
                                    tempPack.getPack03().substring(0, tempPack.getPack03().length() - 2),
                                    tempPack.getPack04().substring(0, tempPack.getPack04().length() - 2),
                                    tempPack.getPack05().substring(0, tempPack.getPack05().length() - 2)};

                            String pack = packs[0];
                            System.out.println(pack + "menna Machannnn");
                            break;
                        }
                    }*/
                    String code = detailTM.getCode();
                    try {
                        PackDTO pack = packBO.findItem(code);

                        String[] packs = {pack.getPack01().substring(0, pack.getPack01().length() - 2),
                                pack.getPack02().substring(0, pack.getPack02().length() - 2),
                                pack.getPack03().substring(0, pack.getPack03().length() - 2),
                                pack.getPack04().substring(0, pack.getPack04().length() - 2),
                                pack.getPack05().substring(0, pack.getPack05().length() - 2)};

                        for (int i = 0; i < 4; i++) {
                            if (packs[i].equals("nu")) {

                            } else {
                                double nQty;
                                double qty;
                                String iDesc;
                                String itemBydes;
                                ItemDTO item1;
                                double qtyOnHand;
                                double unitPrice;
                                double tQty;
                                switch (i) {
                                    case 0:
                                        nQty = Double.parseDouble(pack.getPack01().substring(pack.getPack01().length() - 2));
                                        qty = Double.parseDouble(txtQty.getText());
                                        iDesc = pack.getPack01().substring(0, pack.getPack01().length() - 2);
                                        itemBydes = itemBO.findItemBydes(iDesc);

                                        //update item table qtyOnHand
                                        item1 = itemBO.findItem(itemBydes);
                                        double shout = item1.getShout();
                                        qtyOnHand = item1.getQtyOnHand();
                                        unitPrice = item1.getUnitPrice();
                                        tQty = qtyOnHand + (nQty * qty);
                                        itemBO.updateItem(new ItemDTO(itemBydes, iDesc, unitPrice, tQty, shout, shout - tQty, shout - (shout - tQty)));
                                        break;
                                    case 1:
                                        nQty = Double.parseDouble(pack.getPack02().substring(pack.getPack02().length() - 2));
                                        qty = Double.parseDouble(txtQty.getText());
                                        iDesc = pack.getPack02().substring(0, pack.getPack02().length() - 2);
                                        itemBydes = itemBO.findItemBydes(iDesc);

                                        //update item table qtyOnHand
                                        item1 = itemBO.findItem(itemBydes);
                                        shout = item1.getShout();
                                        qtyOnHand = item1.getQtyOnHand();
                                        unitPrice = item1.getUnitPrice();

                                        tQty = qtyOnHand + (nQty * qty);
                                        itemBO.updateItem(new ItemDTO(itemBydes, iDesc, unitPrice, tQty, shout, shout - tQty, shout - (shout - tQty)));
                                        break;
                                    case 2:
                                        nQty = Double.parseDouble(pack.getPack03().substring(pack.getPack03().length() - 2));
                                        qty = Double.parseDouble(txtQty.getText());
                                        iDesc = pack.getPack03().substring(0, pack.getPack03().length() - 2);
                                        itemBydes = itemBO.findItemBydes(iDesc);

                                        //update item table qtyOnHand
                                        item1 = itemBO.findItem(itemBydes);
                                        shout = item1.getShout();
                                        qtyOnHand = item1.getQtyOnHand();
                                        unitPrice = item1.getUnitPrice();

                                        tQty = qtyOnHand + (nQty * qty);
                                        itemBO.updateItem(new ItemDTO(itemBydes, iDesc, unitPrice, tQty, shout, shout - tQty, shout - (shout - tQty)));
                                        break;
                                    case 3:
                                        nQty = Double.parseDouble(pack.getPack04().substring(pack.getPack04().length() - 2));
                                        qty = Double.parseDouble(txtQty.getText());
                                        iDesc = pack.getPack04().substring(0, pack.getPack04().length() - 2);
                                        itemBydes = itemBO.findItemBydes(iDesc);

                                        //update item table qtyOnHand
                                        item1 = itemBO.findItem(itemBydes);
                                        shout = item1.getShout();
                                        qtyOnHand = item1.getQtyOnHand();
                                        unitPrice = item1.getUnitPrice();

                                        tQty = qtyOnHand + (nQty * qty);
                                        itemBO.updateItem(new ItemDTO(itemBydes, iDesc, unitPrice, tQty, shout, shout - tQty, shout - (shout - tQty)));
                                        break;
                                    case 4:
                                        nQty = Double.parseDouble(pack.getPack05().substring(pack.getPack05().length() - 2));
                                        qty = Double.parseDouble(txtQty.getText());
                                        iDesc = pack.getPack05().substring(0, pack.getPack05().length() - 2);
                                        itemBydes = itemBO.findItemBydes(iDesc);

                                        //update item table qtyOnHand
                                        item1 = itemBO.findItem(itemBydes);
                                        shout = item1.getShout();
                                        qtyOnHand = item1.getQtyOnHand();
                                        unitPrice = item1.getUnitPrice();

                                        tQty = qtyOnHand + (nQty * qty);
                                        itemBO.updateItem(new ItemDTO(itemBydes, iDesc, unitPrice, tQty, shout, shout - tQty, shout - (shout - tQty)));
                                        break;
                                }
                            }
                        }

                        /* ));*/

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    // Let's restore the qty at database


                    tblOrderDetails.getItems().remove(detailTM);
                    calculateTotal();
                    enablePlaceOrderButton();
                    txtItemCode.requestFocus();
                    txtICPoint.setText("txtItemCode");
                    tblOrderDetails.getSelectionModel().clearSelection();
                }
            });
            details.add(detailTM);
            System.out.println(orderNo++);
            txtONo.setText(String.valueOf(orderNo++));

        }
        //update qty in item table as well as save order detail
        updateItemQty();
        clearItem();
        // Calculate the grand total
        calculateTotal();
        enablePlaceOrderButton();

        txtItemCode.requestFocus();
        txtICPoint.setText("txtItemCode");
        tblOrderDetails.getSelectionModel().clearSelection();

    }

    private void updateItemQty() {


        try {
            PackDTO item = packBO.findItem(txtItemCode.getText());
            String[] packs = {item.getPack01().substring(0, item.getPack01().length() - 2),
                    item.getPack02().substring(0, item.getPack02().length() - 2),
                    item.getPack03().substring(0, item.getPack03().length() - 2),
                    item.getPack04().substring(0, item.getPack04().length() - 2),
                    item.getPack05().substring(0, item.getPack05().length() - 2)};

            for (int i = 0; i < 4; i++) {
                if (packs[i].equals("nu")) {

                } else {
                    double nQty;
                    double qty;
                    String iDesc;
                    String itemBydes;
                    ItemDTO item1;
                    double qtyOnHand;
                    double unitPrice;
                    double tQty;
                    switch (i) {
                        case 0:
                            nQty = Double.parseDouble(item.getPack01().substring(item.getPack01().length() - 2));
                            qty = Double.parseDouble(txtQty.getText());
                            iDesc = item.getPack01().substring(0, item.getPack01().length() - 2);
                            itemBydes = itemBO.findItemBydes(iDesc);

                            //update item table qtyOnHand
                            item1 = itemBO.findItem(itemBydes);
                            double shout = item1.getShout();
                            qtyOnHand = item1.getQtyOnHand();
                            unitPrice = item1.getUnitPrice();
                            tQty = qtyOnHand - (nQty * qty);
                            itemBO.updateItem(new ItemDTO(itemBydes, iDesc, unitPrice, tQty, shout, 69, 96));
                            break;
                        case 1:
                            nQty = Double.parseDouble(item.getPack02().substring(item.getPack02().length() - 2));
                            qty = Double.parseDouble(txtQty.getText());
                            iDesc = item.getPack02().substring(0, item.getPack02().length() - 2);
                            itemBydes = itemBO.findItemBydes(iDesc);

                            //update item table qtyOnHand
                            item1 = itemBO.findItem(itemBydes);
                            shout = item1.getShout();
                            qtyOnHand = item1.getQtyOnHand();
                            unitPrice = item1.getUnitPrice();

                            tQty = qtyOnHand - (nQty * qty);
                            itemBO.updateItem(new ItemDTO(itemBydes, iDesc, unitPrice, tQty, shout, 59, 95));
                            break;
                        case 2:
                            nQty = Double.parseDouble(item.getPack03().substring(item.getPack03().length() - 2));
                            qty = Double.parseDouble(txtQty.getText());
                            iDesc = item.getPack03().substring(0, item.getPack03().length() - 2);
                            itemBydes = itemBO.findItemBydes(iDesc);

                            //update item table qtyOnHand
                            item1 = itemBO.findItem(itemBydes);
                            shout = item1.getShout();
                            qtyOnHand = item1.getQtyOnHand();
                            unitPrice = item1.getUnitPrice();

                            tQty = qtyOnHand - (nQty * qty);
                            itemBO.updateItem(new ItemDTO(itemBydes, iDesc, unitPrice, tQty, shout, 44, 55));
                            break;
                        case 3:
                            nQty = Double.parseDouble(item.getPack04().substring(item.getPack04().length() - 2));
                            qty = Double.parseDouble(txtQty.getText());
                            iDesc = item.getPack04().substring(0, item.getPack04().length() - 2);
                            itemBydes = itemBO.findItemBydes(iDesc);

                            //update item table qtyOnHand
                            item1 = itemBO.findItem(itemBydes);
                            shout = item1.getShout();
                            qtyOnHand = item1.getQtyOnHand();
                            unitPrice = item1.getUnitPrice();

                            tQty = qtyOnHand - (nQty * qty);
                            itemBO.updateItem(new ItemDTO(itemBydes, iDesc, unitPrice, tQty, shout, 16, 61));
                            break;
                        case 4:
                            nQty = Double.parseDouble(item.getPack05().substring(item.getPack05().length() - 2));
                            qty = Double.parseDouble(txtQty.getText());
                            iDesc = item.getPack05().substring(0, item.getPack05().length() - 2);
                            itemBydes = itemBO.findItemBydes(iDesc);

                            //update item table qtyOnHand
                            item1 = itemBO.findItem(itemBydes);
                            shout = item1.getShout();
                            qtyOnHand = item1.getQtyOnHand();
                            unitPrice = item1.getUnitPrice();

                            tQty = qtyOnHand - (nQty * qty);
                            itemBO.updateItem(new ItemDTO(itemBydes, iDesc, unitPrice, tQty, shout, 10, 01));
                            break;
                    }


                }
            }


        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private void updateQty(String selectedItemCode, double qty) {

        selectedItemCode = txtItemCode.getText().toUpperCase();
        ObservableList<OrderDetailTM> items = tblOrderDetails.getItems();
        for (OrderDetailTM item : items) {
            if (item.getCode().equals(selectedItemCode)) {
                double tblQty = item.getQty();
                double remainQty = Double.parseDouble(txtQtyOnHand.getText());
                qty = Double.parseDouble(txtQty.getText());
                txtQtyOnHand.setText(String.format("%.2f", remainQty - tblQty));

            }

        }

    }

    private void calculateTotal() {
        ObservableList<OrderDetailTM> details = tblOrderDetails.getItems();

        double total = 0;
        for (OrderDetailTM detail : details) {
            total += detail.getTotal();
        }

        // Let's format the total
        NumberFormat nf = NumberFormat.getInstance();
        nf.setMaximumFractionDigits(2);
        nf.setMinimumFractionDigits(2);
        nf.setGroupingUsed(false);
        double disTotal = total - Double.parseDouble(txtDisc.getText());
        lblTotal.setText(nf.format(total));
        lblDisTotal.setText("Total : " + String.format("%.2f", disTotal));
    }

    public void btnPlaceOrder_OnAction(ActionEvent actionEvent) {
        if (btnPlaceOrder.getText().equals("Place Order")) {
            actionPlaceOrder();
        } else {
            actionVoidSale();
        }
    }

    private void actionVoidSale() {
        //  this transaction is a credit sale with discount offer we should update amount of both rows(Discount given and crediter account)


        String orderId = txtSearchId.getText();
        Double lDisAmount = Double.valueOf(txtDisc.getText());
        Double disAmount;


        String accNO = "Oil Sale";
        String accDes = "Discount Given";
        try {
            String transNo = transacDetailBO.findTransDetailByDateCategory(accNO, String.valueOf(dpDate.getValue()), accDes);
            if (tblOrderDetails.getItems().isEmpty()) {
                disAmount = pDisAmount;
                orderBO.updateDis(Integer.parseInt(orderId), 0.00);

            } else {
                disAmount = pDisAmount - lDisAmount;
                orderBO.updateDis(Integer.parseInt(orderId), Double.valueOf(txtDisc.getText()));
            }
            //Transno ekata adala discount amount eka hoyanawa
            List<transactiondetailDTO> transDetail = transacDetailBO.findTransDetail(transNo);
            for (transactiondetailDTO transData : transDetail) {

                transDisAmount = transData.getTransAmount();
            }
            Double upDatedDis = transDisAmount - disAmount;
            transactiondetailDTO updateTrans = new transactiondetailDTO(transNo, Date.valueOf(dpDate.getValue()), "Oil Sale",
                    "Oil Sale", "Discount Given", "Discount Given", "Expenses", upDatedDis, "No Data");
            transacDetailBO.updateTransDetail(updateTrans);
            //Update debtor record
            if (transDebNo.equals(null) || transDebNo.equals("")) {

            } else {

                transactiondetailDTO debtorRecord = new transactiondetailDTO(transDebNo, Date.valueOf(dpDate.getValue()), cmbTransAcc.getSelectionModel().getSelectedItem(),
                        "Oil Sale", "Credit Oil Sale", "Oil Sale by Credit", "Expenses", Double.parseDouble(lblDisTotal.getText().replace("Total :", "")), txtSearchId.getText());
                transacDetailBO.updateTransDetail(debtorRecord);

            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void actionPlaceOrder() {
        String orderId = lblId.getText().replace("Order ID : R", "");
        int iorderId = Integer.parseInt(orderId);
        Date oDate = Date.valueOf(dpDate.getValue());

        String ReceiptNo= lblId.getText().replace("Order ID : R","");
        String PropertyCode="CCB1";
        String POSInterfaceCode="POS-02";
        String ReceiptDate= String.valueOf(oDate);
        String ReceiptTime="12.30";
        String NoOfItems="2";
        String SalesCurrency="LKR";
        String TotalSalesAmtB4Tax=lblTotal.getText();
        String TotalSalesAmtAfterTax=lblTotal.getText();
        String SalesTaxRate="0";
        String ServiceChargeAmt="0";
        String PaymentAmt=lblTotal.getText();
        String PaymentCurrency="LKR";
        String PaymentMethod="Cash";
        String SalesType="Sales";

        ItemPayload item1 = new ItemPayload();
        item1.setItemDesc("Apple");
        item1.setItemAmt("300");
        item1.setItemDiscountAmt("0");

        ItemPayload item2 = new ItemPayload();
        item2.setItemDesc("Apple2");
        item2.setItemAmt("300");
        item2.setItemDiscountAmt("0");

        ItemPayload item3 = new ItemPayload();
        item3.setItemDesc("Apple3");
        item3.setItemAmt("300");
        item3.setItemDiscountAmt("0");

        List<ItemPayload> itemList = new ArrayList<>();
        itemList.add(item1);
        itemList.add(item2);
        itemList.add(item3);

        PosSalesPayload posSalesPayload = new PosSalesPayload(ReceiptNo,PropertyCode,POSInterfaceCode,
                ReceiptDate,ReceiptTime,NoOfItems,SalesCurrency,TotalSalesAmtB4Tax,TotalSalesAmtAfterTax,
                SalesTaxRate,ServiceChargeAmt,PaymentAmt,PaymentCurrency,PaymentMethod,SalesType,itemList);



        //posSalesPayload

        //Here what we done previously
        /*  try {
            List<ODetailDTO> orderDetails = new ArrayList<>();
            for (OrderDetailTM item : tblOrderDetails.getItems()) {
                String oNo = item.getIcategory();
                int ioNo = Integer.parseInt(oNo);
                orderDetails.add(new ODetailDTO(Integer.parseInt(orderId + oNo), oDate, iorderId, ioNo, item.getCode(), item.getDescription(), item.getQty(), item.getUnitPrice(), cmbPMethod.getSelectionModel().getSelectedItem(), cmbCustomerId.getSelectionModel().getSelectedItem()));
            }
            for (ODetailDTO orderItem : orderDetails) {
                oDetailBO.saveOrder(orderItem);
            }

            reset();
        } catch (Exception e) {
            e.printStackTrace();
        }*/
        //Play in transactionDetails


       /* try {
            transactiondetailDTO newTransaction = new transactiondetailDTO(oilTransNo, oDate,
                    cmbPMethod.getSelectionModel().getSelectedItem(), "No Data", "No Data", "No Data",
                    "Income", Double.parseDouble(lblDisTotal.getText().replace("Total :", "")), "No Data");


            transacDetailBO.updateTransDetail(newTransaction);
        } catch (Exception e) {
            e.printStackTrace();
        }*/


       //Now 1st step is save in to itempayload table
        try {
            int oNo=1;
            List<OrderListDTO> itemPayloadDTOS = new ArrayList<>();
            for (OrderDetailTM item : tblOrderDetails.getItems()) {
             /*   String oNo = item.getIcategory();
                int ioNo = Integer.parseInt(oNo);*/
               int receiptNo= Integer.parseInt(lblId.getText().replace("Order ID : R",""));
                itemPayloadDTOS.add(new OrderListDTO(receiptNo+""+oNo,receiptNo,item.getDescription(),item.getUnitPrice()*item.getQty(),0.00));
              oNo++;
            }
            for (OrderListDTO orderItem : itemPayloadDTOS) {
                itemPayloadBO.saveItem(orderItem);
            }

            reset();
        } catch (Exception e) {
            e.printStackTrace();
        }

        JasperReport jasperReport = null;
        try {
            jasperReport = (JasperReport) JRLoader.loadObject(this.getClass().getResourceAsStream("/lk/ijse/dep/pos/report/Bill.jasper"));


            Map<String, Object> params1 = new HashMap<>();
            params1.put("orderId", orderId + "");

            Session session = HibernateUtil.getSessionFactory().openSession();
            JasperReport finalJasperReport = jasperReport;
            session.doWork(connection -> {

                try {
                    JasperPrint jasperPrint1 = JasperFillManager.fillReport(finalJasperReport, params1, connection);
                    //Set visible
                   /* JasperViewer Jviewer = new JasperViewer(jasperPrint1, false);
                    Jviewer.setVisible(true);
                   */ JasperPrintManager.printReport(jasperPrint1, false);

                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println(e);
                }
            });
            session.disconnect();


            //btn Place Order

            reset();
            //   dpDate.requestFocus();
            txtItemCode.requestFocus();
            txtICPoint.setText("txtItemCode");
            dpDate.setValue(LocalDate.now());
        } catch (JRException e) {
            e.printStackTrace();
        }
    }

    private void getNextTransNo() {
        try {
            Integer maxTransIds = transacDetailBO.getLastOrderId();

            if (maxTransIds == null) {

                maxTrans = "001";
            } else {

                maxTransIds++;
                if (maxTransIds < 10) {
                    oilTransNo = "00" + maxTransIds;
                } else if (maxTransIds < 100) {
                    oilTransNo = "0" + maxTransIds;
                } else {
                    oilTransNo = "" + maxTransIds;
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }


    @FXML
    private void navigateToHome(MouseEvent event) throws IOException {
        if (readOnly) {
            ((Stage) (txtQty.getScene().getWindow())).close();
            return;
        }
        URL resource = this.getClass().getResource("/lk/ijse/dep/pos/view/MainForm.fxml");
        Parent root = FXMLLoader.load(resource);
        Scene scene = new Scene(root);
        Stage primaryStage = (Stage) (this.root.getScene().getWindow());
        primaryStage.setScene(scene);
        primaryStage.centerOnScreen();
    }


    public void txtQty_OnAction(ActionEvent actionEvent) {

        double qty = Double.parseDouble(txtQty.getText());
        double qtyOnHand = Double.parseDouble(txtQtyOnHand.getText());
        double unitPrice = Double.parseDouble(txtUnitPrice.getText());

        // Let's validate the qty.
        if (qty <= 0 || qty > qtyOnHand) {
            new Alert(Alert.AlertType.ERROR, "Invalid Qty", ButtonType.OK).show();
            txtQty.requestFocus();
            txtQty.selectAll();
            return;
        } else {
            double amount = Double.parseDouble(String.format("%.2f", (qty * unitPrice)));
            System.out.println(amount);
            txtAmount.setText(String.valueOf(amount));
        }

        txtAmount.requestFocus();
        txtAmount.selectAll();
        txtICPoint.setText("txtAmount");
    }

    private void enablePlaceOrderButton() {

        double size = tblOrderDetails.getItems().size();

        if (size == 0) {
            btnPlaceOrder.setDisable(true);
        } else {
            btnPlaceOrder.setDisable(false);
        }
    }


    public void txtItemCode_onAction(ActionEvent actionEvent) {
        itemCodeAction();

    }


    private void itemCodeAction() {
        if (txtItemCode.getText().length() == 0) {
            cmbPMethod.requestFocus();
            txtICPoint.setText("cmbPMethod");
        } else {
            try {
                PackDTO item = packBO.findItem(txtItemCode.getText());
                txtDescription.setText(item.getPackName());
                txtUnitPrice.setText(String.valueOf(item.getUnitPrice()));
                //Select Remaining  qty
                /*Select packNo s from pack table using icode and then find minimum qty on hand of item
                on item table*/
                String[] packs = {item.getPack01().substring(0, item.getPack01().length() - 2),
                        item.getPack02().substring(0, item.getPack02().length() - 2),
                        item.getPack03().substring(0, item.getPack03().length() - 2),
                        item.getPack04().substring(0, item.getPack04().length() - 2),
                        item.getPack05().substring(0, item.getPack05().length() - 2)};

                double minQty = 0;
                for (int i = 0; i < 4; i++) {
                    minQty = itemBO.findMinQty(packs[0], packs[1], packs[2], packs[3], packs[4]);
                }
                txtQtyOnHand.setText(String.format("%.2f",minQty));
                txtQty.requestFocus();
                txtICPoint.setText("txtQty");
                txtQty.setEditable(true);

            } catch (Exception e) {
                new Alert(Alert.AlertType.ERROR, "Item code not available").show();
                Logger.getLogger("lk.ijse.dep.pos.controller").log(Level.SEVERE, null, e);
            }
        }
    }


    public void cmbSearch_onAction(ActionEvent actionEvent) {

    }

    public void cmbSearch_KeyPressed(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER) {
            itemCodeAction();
        }
    }

    public void txtAmount_onAction(ActionEvent actionEvent) {
        double qty = Double.parseDouble(txtQty.getText());
        double qtyOnHand = Double.parseDouble(txtQtyOnHand.getText());
        double damount = Double.parseDouble(txtAmount.getText());
        double unitPrice = Double.parseDouble(txtUnitPrice.getText());

        // Let's validate the qty.
        double aqty = (damount / unitPrice);


        txtQty.setText(String.format("%.3f", aqty));
        if (qty <= 0 || aqty > qtyOnHand) {
            new Alert(Alert.AlertType.ERROR, "Invalid Qty", ButtonType.OK).show();
            txtQty.setText("0");
            txtAmount.requestFocus();
            txtAmount.selectAll();
            return;
        }

        btnAdd_OnAction(actionEvent);
    }

    public void txtItemCode_onKeyPresesd(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.DOWN) {
            txtDisc.requestFocus();
            txtDisc.selectAll();
        } else if (keyEvent.getCode() == KeyCode.SPACE) {
            if (btnPlaceOrder.isDisabled()) {

            } else {
                actionPlaceOrder();
            }
        }
    }

    public void cmbPMethod_onkeyPressed(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER) {
            if (cmbPMethod.getSelectionModel().getSelectedItem().equals("Credit")) {
                cmbTransAcc.requestFocus();
            } else {
                actionPlaceOrder();
            }

        } else if (keyEvent.getCode() == KeyCode.E) {
            cmbPMethod.getItems().clear();
            cmbPMethod.getItems().addAll("Cash", "Credit Card", "Uber", "Pick Me", "Free Issue", "Staff");
            cmbPMethod.getSelectionModel().selectFirst();
        }
    }

    public void cmbTransAcc_onAction(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER) {
            if (btnPlaceOrder.getText().equals("Place Order")) {
                actionPlaceOrder();
            } else {
                actionVoidSale();
            }
        }
    }

    public void txtDisc_OnAction(ActionEvent actionEvent) {
        double total = Double.parseDouble(lblTotal.getText());
        double disc = Double.parseDouble(txtDisc.getText());
        double disTotal = total - disc;
        lblDisTotal.setText("Total : " + String.format("%.2f", disTotal));
        cmbPMethod.requestFocus();
    }

    public void txtDisc_keyPressed(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.SPACE) {
            double total = Double.parseDouble(lblTotal.getText());
            double disc = Double.parseDouble(txtDisc.getText());
            double disTotal = total - disc;
            lblDisTotal.setText("Total : " + String.format("%.2f", disTotal));
            actionPlaceOrder();
        }
    }

    public void dpDate_onKeyPressed(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER) {
            txtItemCode.requestFocus();
        }
    }


    public void txtSearchId_onKeyPressed(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER) {
            tblOrderDetails.getItems().clear();
            try {
                List<OrderDTO2> orderInfo = orderBO.getOrderInfo4(txtSearchId.getText());
                ObservableList<OrderDetailTM> items = tblOrderDetails.getItems();
                for (OrderDTO2 item : orderInfo) {

                    Double amount = Double.valueOf(String.format("%.2f", item.getUnitPrice() * item.getQty()));
                    JFXButton btnVoidSale = new JFXButton("Void Sale");
                    OrderDetailTM orderDetailTM = new OrderDetailTM(item.getItemCode(), item.getDescription(),
                            item.getQty(), item.getUnitPrice(), amount, btnVoidSale, item.getIcategory(), item.getIcategory2());
                    items.add(orderDetailTM);

                    //Get discount details
                    Double disDetail = orderBO.getDisDetail(Integer.parseInt(txtSearchId.getText()));
                    txtDisc.setText(String.valueOf(disDetail));
                    dpDate.setValue(item.getOrderDate().toLocalDate());


                    calculateTotal();
                    //getDisCount amount and creditor amount
                    pDisAmount = Double.valueOf(txtDisc.getText());
                    pTotal = Double.valueOf(lblDisTotal.getText().replace("Total :", ""));

                    //getTotal transaction Value of current date
                    String oilSale = "Oil Sale";
                    String transNo = transacDetailBO.findTransDetailByDateCategory(oilSale, String.valueOf(dpDate.getValue()), oilSale);

                    List<transactiondetailDTO> transDetail = transacDetailBO.findTransDetail(transNo);
                    for (transactiondetailDTO transData : transDetail) {
                        transAmount = transData.getTransAmount();
                    }


                    btnVoidSale.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            for (ItemDTO tempItem : tempItems) {
                                if (tempItem.getIcode().equals(orderDetailTM.getCode())) {
                                    // Let's restore the qty
                                    double qtyOnHand = tempItem.getQtyOnHand() + orderDetailTM.getQty();
                                    tempItem.setQtyOnHand(qtyOnHand);


                                    OrderDetailTM selectedItem = tblOrderDetails.getSelectionModel().getSelectedItem();
                                    Double selAmount = selectedItem.getQty() * selectedItem.getUnitPrice();


                                    try {
                                        Double upDatedAmount = transAmount - selAmount;
                                        transactiondetailDTO updateTrans = new transactiondetailDTO(transNo, Date.valueOf(dpDate.getValue()), "Oil Sale",
                                                "Oil Sale", "Oil Sale", "Oil Sale", "Income", upDatedAmount, "No Data");
                                        transacDetailBO.updateTransDetail(updateTrans);
                                        System.out.println("Success though");
                                        //if it is a discounted one select dis transno
                                        System.out.println(txtDisc.getText());
                                        if (txtDisc.getText().equals("0.0")) {
                                            System.out.println("Wanna Fuck you");
                                        } else {
                                            Double totalAndDisc = transacDetailBO.findTotalAndDisc("", "Oil Sale", String.valueOf(dpDate.getValue()), "Expenses", "Dis Oil Sale");
                                            String oilTransNo = transacDetailBO.findTransDetailByDateCategory("Discount Given", String.valueOf(dpDate.getValue()), "Oil Sale With Dis");


                                            //Update discount
                                            transacDetailBO.updateTransDetail(new transactiondetailDTO(oilTransNo, Date.valueOf(dpDate.getValue()), "Discount Given", "Oil Sale", "Dis Oil Sale", "Oil Sale With Dis", "Expenses", totalAndDisc - Double.parseDouble(txtDisc.getText()), "No Data", "No Data"));
                                            txtDisc.setText("0.00");
                                        }


                                        System.out.println("Menna Putha pMethos eka" + cmbPMethod.getSelectionModel().getSelectedItem());
                                        if (cmbPMethod.getSelectionModel().getSelectedItem().equals("Credit")) {
                                            transDebNo = transacDetailBO.getTransaNoByOrderId(txtSearchId.getText());
                                            System.out.println(transDebNo + "Comon baby");
                                            Double totalAndDisc = transacDetailBO.findTotalAndDisc(transDebNo, "Credit", String.valueOf(dpDate.getValue()), "Expenses", "Oil Sale By Credit");
                                            System.out.println(totalAndDisc + "Menna awa");
                                            if ((totalAndDisc - selAmount) == 0) {
                                                transacDetailBO.deleteTransDetail(transDebNo);
                                            } else {
                                                transacDetailBO.updateTransDetail(new transactiondetailDTO(transDebNo, Date.valueOf(dpDate.getValue()), cmbTransAcc.getSelectionModel().getSelectedItem(), "Oil Sale", "Credit Oil Sale", "Oil Sale By Credit", "Expenses", totalAndDisc - Double.parseDouble(txtDisc.getText()), transDebNo, "No Data"));
                                            }
                                        }
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                    try {
                                        ItemDTO item1 = itemBO.findItem(selectedItem.getCode());
                                        double shout = item1.getShout();
                                        itemBO.updateItem(new ItemDTO(selectedItem.getCode(),
                                                selectedItem.getDescription(),
                                                qtyOnHand,
                                                selectedItem.getUnitPrice(), shout, 13, 31));


                                        OrderDetailPK orderPK = new OrderDetailPK(Integer.parseInt(txtSearchId.getText()), selectedItem.getCode());
                                        orderBO.deleteOrderDetail(orderPK);


                                    } catch (Exception e) {
                                        System.out.println("Please Click Row first");
                                    }
                                    break;
                                }
                            }

                            tblOrderDetails.getItems().remove(orderDetailTM);
                            if (tblOrderDetails.getItems().isEmpty()) {
                                txtDisc.setText("0.00");
                            }
                            calculateTotal();
                        }
                    });

                    btnPlaceOrder.setText("Void Sale");
                    btnPlaceOrder.setDisable(false);
                }


                transDebNo = transacDetailBO.getTransaNoByOrderId(txtSearchId.getText());

                List<transactiondetailDTO> transData = transacDetailBO.findTransDetail(transDebNo);
                for (transactiondetailDTO transDatum : transData) {

                    debtorAccNo = transDatum.getTransAccNo();
                    if (debtorAccNo.equals(null)) {

                    } else {
                        cmbTransAcc.getItems().clear();
                        cmbPMethod.getItems().clear();
                        cmbPMethod.getItems().add("Credit");
                        cmbPMethod.getSelectionModel().selectFirst();
                        cmbTransAcc.setVisible(true);
                        cmbTransAcc.getItems().add(debtorAccNo);
                        cmbTransAcc.getSelectionModel().selectFirst();
                    }


                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    //btnNo testing



    String result="";
    String noOne="";
    String noTwo="";

    public void btnNo0_OnAction(ActionEvent actionEvent) {
       if (txtICPoint.getText().equals("txtItemCode")){
           noOne=txtItemCode.getText();
           noTwo = "0";
           result = noOne + noTwo;
           txtItemCode.setText(result);
           txtItemCode.requestFocus();
       }else if (txtICPoint.getText().equals("txtQty")){


               noOne=txtItemCode.getText();
               noOne = txtQty.getText();
               noTwo = "0";
               result = noOne + noTwo;

           txtQty.setText(result);
           txtQty.requestFocus();
       }


    }
    public void btnNo1_OnAction(ActionEvent actionEvent) {
        if (txtICPoint.getText().equals("txtItemCode")){
            noOne=txtItemCode.getText();
            noTwo = "1";
            result = noOne + noTwo;
            txtItemCode.setText(result);
            txtItemCode.requestFocus();
        }else if (txtICPoint.getText().equals("txtQty")){


                noOne=txtItemCode.getText();
                noOne = txtQty.getText();
                noTwo = "1";
                result = noOne + noTwo;


            txtQty.setText(result);
            txtQty.requestFocus();
        }

    }
    public void btnNo2_OnAction(ActionEvent actionEvent) {
        if (txtICPoint.getText().equals("txtItemCode")){
            noOne = txtItemCode.getText();
            noTwo = "2";
            result = "";
            result = noOne + noTwo;
            txtItemCode.setText(result);
            txtItemCode.requestFocus();
        }else if (txtICPoint.getText().equals("txtQty")){
            noOne = txtQty.getText();
            noTwo = "2";
            result = "";
            result = noOne + noTwo;
            txtQty.setText(result);
            txtQty.requestFocus();
        }

    }
    public void btnNo3_OnAction(ActionEvent actionEvent) {
        if (txtICPoint.getText().equals("txtItemCode")){
            noOne = txtItemCode.getText();
            noTwo = "3";
            result = "";
            result = noOne + noTwo;
            txtItemCode.setText(result);
            txtItemCode.requestFocus();
        }else if (txtICPoint.getText().equals("txtQty")){
            noOne = txtQty.getText();
            noTwo = "3";
            result = "";
            result = noOne + noTwo;
            txtQty.setText(result);
            txtQty.requestFocus();
        }

    }

    public void btnNo4_OnAction(ActionEvent actionEvent) {
        if (txtICPoint.getText().equals("txtItemCode")){
            noOne = txtItemCode.getText();
            noTwo = "4";
            result = "";
            result = noOne + noTwo;
            txtItemCode.setText(result);
            txtItemCode.requestFocus();
        }else if (txtICPoint.getText().equals("txtQty")){
            noOne = txtQty.getText();
            noTwo = "4";
            result = "";
            result = noOne + noTwo;
            txtQty.setText(result);
            txtQty.requestFocus();
        }

    }




    public void btnNo5_OnAction(ActionEvent actionEvent) {
        if (txtICPoint.getText().equals("txtItemCode")){
            noOne = txtItemCode.getText();
            noTwo = "5";
            result = "";
            result = noOne + noTwo;
            txtItemCode.setText(result);
            txtItemCode.requestFocus();
        }else if (txtICPoint.getText().equals("txtQty")){
            noOne = txtQty.getText();
            noTwo = "5";
            result = "";
            result = noOne + noTwo;
            txtQty.setText(result);
            txtQty.requestFocus();
        }

    }
    public void btnNo6_OnAction(ActionEvent actionEvent) {
        if (txtICPoint.getText().equals("txtItemCode")){
            noOne = txtItemCode.getText();
            noTwo = "6";
            result = "";
            result = noOne + noTwo;
            txtItemCode.setText(result);
            txtItemCode.requestFocus();
        }else if (txtICPoint.getText().equals("txtQty")){
            noOne = txtQty.getText();
            noTwo = "6";
            result = "";
            result = noOne + noTwo;
            txtQty.setText(result);
            txtQty.requestFocus();
        }

    }
    public void btnNo7_OnAction(ActionEvent actionEvent) {
        if (txtICPoint.getText().equals("txtItemCode")){
            noOne = txtItemCode.getText();
            noTwo = "7";
            result = "";
            result = noOne + noTwo;
            txtItemCode.setText(result);
            txtItemCode.requestFocus();
        }else if (txtICPoint.getText().equals("txtQty")){
            noOne = txtQty.getText();
            noTwo = "7";
            result = "";
            result = noOne + noTwo;
            txtQty.setText(result);
            txtQty.requestFocus();
        }

    }
    public void btnNo8_OnAction(ActionEvent actionEvent) {
        if (txtICPoint.getText().equals("txtItemCode")){
            noOne = txtItemCode.getText();
            noTwo = "8";
            result = "";
            result = noOne + noTwo;
            txtItemCode.setText(result);
            txtItemCode.requestFocus();
        }else if (txtICPoint.getText().equals("txtQty")){
            noOne = txtQty.getText();
            noTwo = "8";
            result = "";
            result = noOne + noTwo;
            txtQty.setText(result);
            txtQty.requestFocus();
        }

    }




    public void btnNo9_OnAction(ActionEvent actionEvent) {
        if (txtICPoint.getText().equals("txtItemCode")){
            noOne = txtItemCode.getText();
            noTwo = "9";
            result = "";
            result = noOne + noTwo;
            txtItemCode.setText(result);
            txtItemCode.requestFocus();
        }else if (txtICPoint.getText().equals("txtQty")){
            noOne = txtQty.getText();
            noTwo = "9";
            result = "";
            result = noOne + noTwo;
            txtQty.setText(result);
            txtQty.requestFocus();
        }
    }





    public void btnClear_OnAction(ActionEvent actionEvent) {
     if (txtICPoint.getText().equals("txtItemCode")){
        txtItemCode.setText("");
     }else if(txtICPoint.getText().equals("txtQty")){
         txtQty.setText("0");
     }
    }



    public void btnEnter_OnAction(ActionEvent actionEvent) {
        if (txtICPoint.getText().equals("txtItemCode")){
            itemCodeAction();
        }else if (txtICPoint.getText().equals("txtQty")){
          txtQty_OnAction(actionEvent);
        }else if(txtICPoint.getText().equals("txtAmount")){
            btnAdd_OnAction(actionEvent);

        }else if(txtICPoint.getText().equals("cmbPMethod")){
            actionPlaceOrder();
        }


    }





    public void txtItemCode_onMouseClicked(MouseEvent mouseEvent) {
        txtICPoint.setText("txtItemCode");
    }

    public void txtItemCode_onTouchPressed(TouchEvent touchEvent) {
        txtICPoint.setText("txtItemCode");
    }

    public void txtQty_onMouseClicked(MouseEvent mouseEvent) {
        txtICPoint.setText("txtQty");
    }

    public void txtQty_onTouchPressed(TouchEvent touchEvent) {
        txtICPoint.setText("txtItemCode");
    }


}






