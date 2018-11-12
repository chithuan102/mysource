/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Class;

import ConnectDB.ConnectDB;
import Subject.AccessSubject;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author MCT
 */
public class AccessClass {

    Connection conn;
    PreparedStatement pstm;
    ResultSet rs;

    public void getConnection() {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            conn = DriverManager.getConnection("jdbc:sqlserver://localhost;database=SMS", "sa", "123");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ConnectDB.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ConnectDB.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void createNewClass(String classID, String classNumber) {
        try {
            this.getConnection();
            String str = "INSERT Class VALUES(?,?)";
            pstm = conn.prepareStatement(str);
            pstm.setString(1, classID);
            pstm.setString(2, classNumber);
            pstm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(AccessClass.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void updateClass(String classID, String classNumber) {
        try {
            this.getConnection();
            String str = "UPDATE Class SET classNumber=? WHERE classID=?";
            pstm = conn.prepareStatement(str);
            pstm.setString(1, classID);
            pstm.setString(2, classNumber);
            pstm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(AccessClass.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void deleteClass(String id) {
        try {
            this.getConnection();
            String str = "DELETE Class WHERE classID=?";
            pstm = conn.prepareStatement(str);
            pstm.setString(1, id);
            pstm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(AccessClass.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ResultSet searchByClassName(String cName) {
        rs = null;
        try {
            this.getConnection();
            String str = "SELECT * FROM Class WHERE classNumber like '%" + cName + "%'";
            pstm = conn.prepareStatement(str);
//            pstm.setString(1, cName);
            rs = pstm.executeQuery();

        } catch (SQLException ex) {
            Logger.getLogger(AccessClass.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rs;
    }
}
