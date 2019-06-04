package sample;
import java.math.BigDecimal;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;
import javax.swing.JOptionPane;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import org.controlsfx.control.textfield.TextFields;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import impl.org.controlsfx.autocompletion.SuggestionProvider;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import java.io.IOException;
import javafx.scene.layout.AnchorPane;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.Parent;

import java.awt.event.ActionEvent;
import java.util.*;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;
import javax.swing.JOptionPane;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
public class xiaoshouController {
    @FXML private JFXComboBox<String> wareName;
    @FXML private JFXComboBox<String> wareNumber;
    @FXML private JFXTextField amount;
    @FXML private JFXButton exitButton;
    @FXML private JFXComboBox storage;

    private ObservableList<chuhuo> chuhuoData = FXCollections.observableArrayList();
    @FXML private TableView<chuhuo> chuhuoTable;
    @FXML private TableColumn<chuhuo, String> wareNameColumn;
    @FXML private TableColumn<chuhuo, String> wareNumberColumn;
    @FXML private TableColumn<chuhuo, String> amountColumn;
    @FXML private TableColumn<chuhuo, String> timeColumn;
    @FXML private TableColumn<chuhuo, String> storageColumn;
    @FXML public void initialize() {
        storage.getItems().addAll("货仓1", "货仓2", "货仓3", "货仓4", "货仓5", "货仓6", "货仓7", "货仓8");
        showList();
        wareNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().wareNameProperty()));
        wareNumberColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().wareNumberProperty()));
        amountColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().amountProperty()));
        timeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().timeProperty()));
        storageColumn.setCellValueFactory(cellData->new SimpleStringProperty(cellData.getValue().storageProperty()));
        showchuhuo();
        /*fetch data*/
        wareNumber.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                System.out.println(newValue);
                wareNumberChanged(wareNumber.getItems().get((int)newValue));
            }
        });
        wareName.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                System.out.println(newValue);
                wareNameChanged(wareName.getItems().get((int)newValue));
            }
        });
    }
    void showchuhuo() {
        linkMySql con = new linkMySql();
        Connection myCon = con.xiaoshouconnect();
        if (myCon == null) {
            JOptionPane.showMessageDialog(null, "无法连接到数据库！");
            return;
        }
        PreparedStatement pStatement = null;
        ResultSet rs = null;
        try {
            pStatement=(PreparedStatement) myCon.prepareStatement("SELECT * FROM export WHERE approved = 0");
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        try {
            rs = pStatement.executeQuery();
            System.out.println("selected successfully");
            String wareName = null;
            String wareNumber = null;
            String amount = null;
            String time = null;
            String storage = null;
            while (rs.next()) {
                wareName = rs.getString("name").trim();
                wareNumber = rs.getString("number").trim();
                amount = rs.getString("amount").trim();
                time = rs.getString("date").trim();
                time = time.substring(0,19);
                storage = rs.getString("place").trim();
                System.out.println(wareName + wareNumber + amount + time);
                chuhuoData.add(new chuhuo(wareName, wareNumber, amount, time, storage));
            }
        } catch (SQLException e1) {
            e1.printStackTrace();
        }

        /*close the connection*/
        try
        {
            myCon.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        chuhuoTable.setItems(chuhuoData);
    }
    void showList()
    {
        linkMySql con = new linkMySql();
        Connection myCon = con.xiaoshouconnect();
        if (myCon == null) {
            JOptionPane.showMessageDialog(null, "无法连接到数据库！");
            return;
        }
        PreparedStatement pStatement = null;
        ResultSet rs = null;
        LinkedList<String> searchResult = new LinkedList<>();
        try {
            pStatement = (PreparedStatement) myCon.prepareStatement("SELECT * from goodinfo");
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        try {
            rs = pStatement.executeQuery();
            while (rs.next()) {
                String str1 = rs.getString("wareNumber").trim();
                String str2 = rs.getString("wareName").trim();
                wareNumber.getItems().add(str1);
                wareName.getItems().add(str2);
            }
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
    }
    void wareNumberChanged(String newChoice)
    {
        if (wareName.getValue() != null)
        {
            if (isMatchNumber(newChoice,wareName.getValue())) return;
        }
        System.out.println("bb");
        linkMySql con = new linkMySql();
        Connection myCon = con.xiaoshouconnect();
        if (myCon == null) {
            JOptionPane.showMessageDialog(null, "无法连接到数据库！");
            return;
        }
        /*fetch data*/
        PreparedStatement pStatement = null;
        ResultSet rs = null;
        try {
            pStatement = (PreparedStatement) myCon.prepareStatement("SELECT wareName from goodinfo WHERE wareNumber = ?");
            pStatement.setString(1,newChoice);
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        try {
            rs = pStatement.executeQuery();
            while (rs.next()) {
                String str1 = rs.getString("wareName").trim();
                wareName.setValue(str1);
            }
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        try {
            myCon.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    void wareNameChanged(String newChoice)
    {
        if (wareNumber.getValue() != null)
        {
            if (isMatchName(newChoice, wareNumber.getValue())) return;
        }
        System.out.println("aa");
        linkMySql con = new linkMySql();
        Connection myCon = con.xiaoshouconnect();
        if (myCon == null) {
            JOptionPane.showMessageDialog(null, "无法连接到数据库！");
            return;
        }
        /*fetch data*/
        PreparedStatement pStatement = null;
        ResultSet rs = null;
        try {
            pStatement = (PreparedStatement) myCon.prepareStatement("SELECT wareNumber from goodinfo WHERE wareName = ?");
            pStatement.setString(1,newChoice);
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        try {
            rs = pStatement.executeQuery();
            while (rs.next()) {
                String str1 = rs.getString("wareNumber").trim();
                wareNumber.setValue(str1);
            }
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        try {
            myCon.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    boolean isMatchName(String str1, String str2)
    {
        linkMySql con = new linkMySql();
        Connection myCon = con.xiaoshouconnect();
        if (myCon == null) {
            JOptionPane.showMessageDialog(null, "无法连接到数据库！");
            return false;
        }
        /*fetch data*/
        PreparedStatement pStatement = null;
        ResultSet rs = null;
        try {
            pStatement = (PreparedStatement) myCon.prepareStatement("SELECT wareNumber from goodinfo WHERE wareName = ?");
            pStatement.setString(1,str1);
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        try {
            rs = pStatement.executeQuery();
            while (rs.next()) {
                String str3 = rs.getString("wareNumber").trim();
                if(str2.equals(str3)) return true;
            }
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        try {
            myCon.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    boolean isMatchNumber(String str1, String str2)
    {
        linkMySql con = new linkMySql();
        Connection myCon = con.xiaoshouconnect();
        if (myCon == null) {
            JOptionPane.showMessageDialog(null, "无法连接到数据库！");
            return false;
        }
        /*fetch data*/
        PreparedStatement pStatement = null;
        ResultSet rs = null;
        try {
            pStatement = (PreparedStatement) myCon.prepareStatement("SELECT wareName from goodinfo WHERE wareNumber = ?");
            pStatement.setString(1,str1);
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        try {
            rs = pStatement.executeQuery();
            while (rs.next()) {
                String str3 = rs.getString("wareName").trim();
                if(str2.equals(str3)) return true;
            }
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        try {
            myCon.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    @FXML public void on_register_clicked()
    {
            if (wareName.getValue().equals("")) {
                JOptionPane.showConfirmDialog(null,"货品名不能为空！","出错！",JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (wareNumber.getValue().equals("")) {
                JOptionPane.showConfirmDialog(null,"货品号不能为空！","出错！",JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (storage.getValue().equals("")) {
                JOptionPane.showConfirmDialog(null,"存放地点不能为空！","出错！",JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (amount.getText().equals("")) {
                JOptionPane.showConfirmDialog(null,"货品数量不能为空！","出错！",JOptionPane.ERROR_MESSAGE);
                return;
            }
            int isRegister = JOptionPane.showConfirmDialog(null, "确定要登记吗?", "提示",JOptionPane.YES_NO_OPTION);
            if(isRegister == 0) {
                String huowuName = wareName.getValue();
                String huowuNumber = wareNumber.getValue();
                String CFDD = storage.getValue().toString();
                int shuliang = Integer.parseInt(amount.getText());
                Date dNow = new Date();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String CKSJ = sdf.format(dNow);
                System.out.println(CKSJ);
                linkMySql con = new linkMySql();
                Connection myCon = con.xiaoshouconnect();
                PreparedStatement pStatement = null;
                ResultSet rs = null;
                System.out.println("preparing to read\n");
                try {
                    String insertImport = "INSERT INTO export VALUES(?,?,?,?,?,?)";
                    System.out.println(insertImport);
                    pStatement=(PreparedStatement) myCon.prepareStatement(insertImport);
                    pStatement.setString(1, huowuName);
                    pStatement.setString(2, huowuNumber);
                    pStatement.setString(3, CKSJ);
                    pStatement.setInt(4, shuliang);
                    pStatement.setInt(5, 0);
                    pStatement.setString(6, CFDD);
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }

                try {
                    int isSuc = pStatement.executeUpdate();
                    if(isSuc > 0)
                    {
                        JOptionPane.showMessageDialog(null, "登记成功！请等待审核人员审核。", "提示", 1);
                        on_clear_clicked();

                    }else
                    {
                        JOptionPane.showMessageDialog(null, "由于某种原因，登记失败了！", "出错！", 0);
                    }
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
                    Stage tempStage = (Stage) exitButton.getScene().getWindow();
                    tempStage.close();
                    FXMLLoader loader = new FXMLLoader(
                            getClass().getResource("xiaoshou.fxml")
                    );
                    AnchorPane root = new AnchorPane();
                    Scene myScene = new Scene(root);
                    try {
                        myScene.setRoot((Parent)loader.load());
                        Stage newStage = new Stage();
                        newStage.setTitle("销售人员操作界面");
                        newStage.setScene(myScene);
                        newStage.show();
                    }catch (IOException ex) {
                        ex.printStackTrace();
                    }
                /*close the connection*/
                try
                {
                    myCon.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
    }
    @FXML public void on_clear_clicked()
    {
        wareName.setValue(null);
        wareNumber.setValue(null);
        amount.clear();
        storage.setValue(null);
    }
    @FXML public void on_exit_clicked()
    {
        int n = JOptionPane.showConfirmDialog(null, "确定要退出吗?", "提示",JOptionPane.YES_NO_OPTION);
        if(n == 0) {
            JOptionPane.showMessageDialog(null, "欢迎下次使用！");
            Stage tempStage = (Stage)exitButton.getScene().getWindow();
            tempStage.close();
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("login.fxml")
            );
            AnchorPane root = new AnchorPane();
            Scene myScene = new Scene(root);
            try {
                myScene.setRoot((Parent) loader.load());
                Stage newStage = new Stage();
                newStage.setTitle("登录界面");
                newStage.setScene(myScene);
                newStage.show();
            }catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        else return;
    }
    @FXML public void on_delete_clicked()
    {
        int n = JOptionPane.showConfirmDialog(null, "确定要删除吗?", "提示",JOptionPane.YES_NO_OPTION);
        if(n == 0) {
            chuhuo jh = chuhuoTable.getSelectionModel().getSelectedItem();
            String wareNumber = jh.wareNumberProperty();
            String time = jh.timeProperty();
            String storage = jh.storageProperty();
            linkMySql con = new linkMySql();
            Connection myCon = con.xiaoshouconnect();
            if(myCon == null)
            {
                JOptionPane.showMessageDialog(null, "无法连接到数据库！");
                return;
            }
            /*fetch data*/
            Statement stmt = null;
            try{
                stmt = myCon.createStatement();
            } catch(SQLException SqlEX) {
                SqlEX.printStackTrace();
            }
            System.out.println("Preparing table data\n");
            System.out.println(time);
            String str = String.format("DELETE FROM export WHERE approved = 0 AND date = \"%s\" AND number = \"%s\" AND place = \"%s\"", time, wareNumber, storage);
            System.out.println(str);
            try {
                Stage tempStage = (Stage) exitButton.getScene().getWindow();
                tempStage.close();
                stmt.execute(str);
                JOptionPane.showMessageDialog(null, "删除成功！");
                FXMLLoader loader = new FXMLLoader(
                        getClass().getResource("xiaoshou.fxml")
                );
                AnchorPane root = new AnchorPane();
                Scene myScene = new Scene(root);
                try {
                    myScene.setRoot((Parent)loader.load());
                    Stage newStage = new Stage();
                    newStage.setTitle("销售人员操作界面");
                    newStage.setScene(myScene);
                    newStage.show();
                }catch (IOException ex) {
                    ex.printStackTrace();
                }
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }
    }
    @FXML public void on_update_clicked()
    {

    }
}