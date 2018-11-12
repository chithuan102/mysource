/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Major;

import ConnectDB.ConnectDB;
import Student.AccessStudent;
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
public class AccessMajor {

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

    public void createNewMajor(String majorID, String majorName, String duration) {
        try {
            this.getConnection();
            String str = "INSERT Major VALUES(?,?,?)";
            pstm = conn.prepareStatement(str);
            pstm.setString(1, majorID);
            pstm.setString(2, majorName);
            pstm.setString(3, duration);
            pstm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(AccessMajor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void updateMajor(String majorID, String duration, String majorName) {
        try {
            this.getConnection();
            String str = "UPDATE Major SET majorName=?, duration=? WHERE majorID=?";
            pstm = conn.prepareStatement(str);
            pstm.setString(1, majorName);
            pstm.setString(2, duration);
            pstm.setString(3, majorID);

            pstm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(AccessMajor.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void deleteMajor(String id) {
        try {
            this.getConnection();
            String str = "DELETE Major WHERE majorID=?";
            pstm = conn.prepareStatement(str);
            pstm.setString(1, id);
            pstm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(AccessMajor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ResultSet searchByMajorName(String cName) {
        rs = null;
        try {
            this.getConnection();
            String str = "SELECT * FROM Major WHERE majorName like '%" + cName + "%'";
            pstm = conn.prepareStatement(str);
//            pstm.setString(1, cName);
            rs = pstm.executeQuery();

        } catch (SQLException ex) {
            Logger.getLogger(AccessStudent.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rs;
    }

}
