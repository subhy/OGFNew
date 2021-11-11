package lk.ijse.dep.pos.controller;


import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.dep.pos.business.BOFactory;
import lk.ijse.dep.pos.business.BOTypes;
import lk.ijse.dep.pos.business.custom.*;
import lk.ijse.dep.pos.db.HibernateUtil;
import lk.ijse.dep.pos.dto.ItemDTO;
import lk.ijse.dep.pos.dto.OrderDTO2;
import lk.ijse.dep.pos.util.OrderTM;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
import org.hibernate.Session;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SearchOrdersFormController {


    public TableView<OrderTM> tblReports;
    public JFXTextField txtItemCode;
    public JFXDatePicker jdcFrom;
    public JFXDatePicker jdcTo;
    public JFXTextField txtDescription;
    public AnchorPane root;
    public Button btnSearch;
    public RadioButton rbtItem;
    public RadioButton rbtDate;
    public Label lblTotal;
    public Button btnPrint;
    public Label lblCashSale;
    public Label lblCardSale;
    public Label lblPMSale;
    public Label lblUberSale;
    public Label lblStaffSale;
    public Label lblFISale;
    public Button btnMonthly;

    private LocalDate dateto;
    private LocalDate datefrom;
    private String itemCode;

    private ODetailBO oDetailBO=BOFactory.getInstance().getBO(BOTypes.ODETAIL);
    private ItemBO itemBO = BOFactory.getInstance().getBO(BOTypes.ITEM);

    public void initialize() throws Exception {

        tblReports.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        tblReports.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("description"));
        tblReports.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("qty"));
        tblReports.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        tblReports.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("total"));

        ObservableList<OrderTM> olOrders = tblReports.getItems();
        jdcTo.setValue(LocalDate.now());
        jdcFrom.setValue(LocalDate.now());

        loadInitialTable();
        calculateTotal();

        //Set rbt selected
        rbtDate.setSelected(true);
    }

    private void loadInitialTable() {
        ObservableList<OrderTM> tempOrders = FXCollections.observableArrayList();
        dateto = jdcTo.getValue();
        datefrom = jdcFrom.getValue();
        double ttotal=0;

        try {
            List<OrderDTO2> orderInfo = oDetailBO.getOrderInfo3(Date.valueOf(datefrom), Date.valueOf(dateto));
            for (OrderDTO2 data : orderInfo) {

                Double dtotal = data.getQty() * data.getUnitPrice();
                Double total = Double.valueOf(String.format("%.2f", dtotal));
                  Double qty = Double.valueOf(String.format("%.3f", data.getQty()));

                tempOrders.add(new OrderTM(data.getItemCode(), data.getDescription(), qty, data.getUnitPrice(),total));
               ttotal+=total;
                String dtotoal = String.format("%.2f", ttotal);
                lblTotal.setText("Total : "+dtotoal);
            }
//Start
           /* dateto = LocalDate.now();*/
            String sDateTo = String.valueOf(dateto);
            String sDateFrom=String.valueOf(datefrom);
            //Get total discount
            Double cash = oDetailBO.findByPMethod("", "Cash", sDateTo, "", "");

                lblCashSale.setText("Cash : " + String.format("%.2f", cash));


            Double card = oDetailBO.findByPMethod("", "Credit Card", sDateTo, "", "");

            lblCardSale.setText("Credit Card : " + String.format("%.2f", card));

            Double uber = oDetailBO.findByPMethod("", "Uber", sDateTo, "", "");

            lblUberSale.setText("Uber : " + String.format("%.2f", uber));


            Double pm = oDetailBO.findByPMethod("", "Pick Me", sDateTo, "", "");

            lblPMSale.setText("Pick Me: " + String.format("%.2f", pm));

            Double staff = oDetailBO.findByPMethod("", "Staff", sDateTo, "", "");

            lblStaffSale.setText("Staff : " + String.format("%.2f", staff));


            Double fi = oDetailBO.findByPMethod("", "Free Issue", sDateTo, "", "");

            lblFISale.setText("Free Issue : " + String.format("%.2f", fi));



        } catch (Exception e) {
            e.printStackTrace();
        }
        tblReports.setItems(tempOrders);
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

    public void btnSearch_OnAction(ActionEvent actionEvent) {
       /* ObservableList<OrderTM> tempOrders = FXCollections.observableArrayList();
        itemCode = txtItemCode.getText();
        datefrom = jdcFrom.getValue();
        dateto = jdcTo.getValue();
       if (rbtDate.isSelected()) {
            try {
                List<OrderDTO2> orderInfo = orderBO.getOrderInfo3(Date.valueOf(datefrom), Date.valueOf(dateto));
                for (OrderDTO2 data : orderInfo) {

                    Double dtotal = data.getQty() * data.getUnitPrice();
                    Double total = Double.valueOf(String.format("%.2f", (dtotal*0.1)+dtotal));
                    Double qty = Double.valueOf(String.format("%.3f", data.getQty()));

                    tempOrders.add(new OrderTM( data.getItemCode(), data.getDescription(), qty, data.getUnitPrice(),dtotal, total));
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            tblReports.setItems(tempOrders);
            calculateTotal();

        } else {
            try {

                List<OrderDTO2> orderInfo = orderBO.getOrderInfo3(Date.valueOf(datefrom), Date.valueOf(dateto));
                for (OrderDTO2 data : orderInfo) {
                    Double dtotal = data.getQty() * data.getUnitPrice();
                    Double total = Double.valueOf(String.format("%.2f", dtotal));
                    Double qty = Double.valueOf(String.format("%.3f", data.getQty()));

                    tempOrders.add(new OrderTM(data.getItemCode(), data.getDescription(), qty, data.getUnitPrice(),dtotal));
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            tblReports.setItems(tempOrders);
            calculateTotal();
        }*/

       loadInitialTable();


    }

    public void txtItemCode_onAction(ActionEvent actionEvent) {
        String itemCode = txtItemCode.getText();
        try {
            ItemDTO item = itemBO.findItem(itemCode);
            String description = item.getDescription();

            txtDescription.setText(description);
        } catch (Exception e) {
            new Alert(Alert.AlertType.WARNING, "Scan again Incorrect Item", ButtonType.OK).show();
        }
    }

    private void calculateTotal() {
        ObservableList<OrderTM> details = tblReports.getItems();

        double total = 0;
        for (OrderTM detail : details) {
            total += detail.getTotal();
        }

        // Let's format the total
        NumberFormat nf = NumberFormat.getInstance();
        nf.setMaximumFractionDigits(2);
        nf.setMinimumFractionDigits(2);
        nf.setGroupingUsed(false);

        lblTotal.setText("Total : " + nf.format(total));
    }

    public void btnPrint_OnAction(ActionEvent actionEvent) throws JRException {


        JasperReport jasperReport = null;

            jasperReport = JasperCompileManager.compileReport(this.getClass().getResourceAsStream("/lk/ijse/dep/pos/report/DailyReport.jrxml"));


        Map<String, Object> params = new HashMap<>();
        params.put("dateFrom", datefrom + "");
        Map<String, Object> params1 = new HashMap<>();
        params.put("dateTo",  dateto+ "");
        JasperReport subReport = (JasperReport) JRLoader.loadObject(this.getClass().getResourceAsStream("/lk/ijse/dep/pos/report/DailySubMain.jasper"));
        params.put("subReport", subReport);
        JasperReport subReport1 = (JasperReport) JRLoader.loadObject(this.getClass().getResourceAsStream("/lk/ijse/dep/pos/report/DailySub.jasper"));
        params.put("subReport1", subReport1);



        Session session = HibernateUtil.getSessionFactory().openSession();
        JasperReport finalJasperReport = jasperReport;
        session.doWork(connection -> {

            try {
                JasperPrint jasperPrint1 = JasperFillManager.fillReport(finalJasperReport, params, connection);
                JasperViewer Jviewer = new JasperViewer(jasperPrint1, false);
                Jviewer.setVisible(true);
                JasperPrintManager.printReport(jasperPrint1, true);
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println(e);
            }
        });
        session.disconnect();
  }

    public void btnMonthly_OnAction(ActionEvent actionEvent) throws JRException {
        JasperReport jasperReport = null;

        jasperReport = JasperCompileManager.compileReport(this.getClass().getResourceAsStream("/lk/ijse/dep/pos/report/Account.jrxml"));


        Map<String, Object> params = new HashMap<>();
        params.put("dateFrom", datefrom + "");
        Map<String, Object> params1 = new HashMap<>();
        params.put("dateTo",  dateto+ "");

        Session session = HibernateUtil.getSessionFactory().openSession();
        JasperReport finalJasperReport = jasperReport;
        session.doWork(connection -> {

            try {
                JasperPrint jasperPrint1 = JasperFillManager.fillReport(finalJasperReport, params, connection);
                JasperViewer Jviewer = new JasperViewer(jasperPrint1, false);
                Jviewer.setVisible(true);
             //   JasperPrintManager.printReport(jasperPrint1, true);
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println(e);
            }
        });
        session.disconnect();
    }
}
