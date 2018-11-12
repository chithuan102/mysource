/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Assessment;

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
public class AccessAssessment {

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

    public void createNewAssessment(String stuID, String subjectID, String stuName, String subjectName, String mark, String status) {
        try {
            this.getConnection();
            String str = "INSERT Assessment VALUES(?,?,?,?,?,?)";
            pstm = conn.prepareStatement(str);
            pstm.setString(1, stuID);
            pstm.setString(2, subjectID);
            pstm.setString(3, stuName);
            pstm.setString(4, subjectName);
            pstm.setString(5, mark);
            pstm.setString(6, status);
            pstm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(AccessAssessment.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void updateAssessment(String mark, String status, String stuID, String subjectID) {
        try {
            this.getConnection();
            String str = "UPDATE Assessment SET  mark=?, status=? WHERE stuID=? and subjectID=?";
            pstm = conn.prepareStatement(str);
            pstm.setString(1, mark);
            pstm.setString(2, status);
            pstm.setString(3, stuID);
            pstm.setString(4, subjectID);
//            pstm.setString(5, stuName);
            pstm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(AccessAssessment.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void deleteAssessment(String name, String stuID) {
        try {
            this.getConnection();
            String str = "DELETE Assessment WHERE subjectName=? and stuID=?";
            pstm = conn.prepareStatement(str);
            pstm.setString(1, name);
            pstm.setString(2, stuID);
            pstm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(AccessAssessment.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ResultSet searchByStudentName(String cName) {
        rs = null;
        try {
            this.getConnection();
            String str = "SELECT * FROM Assessment WHERE stuName like '%" + cName + "%'";
            pstm = conn.prepareStatement(str);
//            pstm.setString(1, stuID);
            rs = pstm.executeQuery();

        } catch (SQLException ex) {
            Logger.getLogger(AccessAssessment.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rs;
    }

}
