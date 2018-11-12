/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Subject;

import ConnectDB.ConnectDB;
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
public class AccessSubject {

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

    public void createNewSubject(String subjectID, String subjectName, String classID, String term, String majorID) {
        try {
            this.getConnection();
            String str = "INSERT Subject VALUES(?,?,?,?,?)";
            pstm = conn.prepareStatement(str);
            pstm.setString(1, subjectID);
            pstm.setString(2, subjectName);
            pstm.setString(3, classID);
            pstm.setString(4, term);
            pstm.setString(5, majorID);
            pstm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(AccessSubject.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void updateSubject(String subjectName, String term, String majorID, String classID, String subjectID) {
        try {
            this.getConnection();
            String str = "UPDATE Subject SET subjectName=?, term=?, majorID=?,classID=? WHERE subjectID=?";
            pstm = conn.prepareStatement(str);
            pstm.setString(1, subjectName);
            pstm.setString(2, term);
            pstm.setString(3, majorID);
            pstm.setString(4, classID);
            pstm.setString(5, subjectID);
            pstm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(AccessSubject.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void deleteSubject(String id) {
        try {
            this.getConnection();
            String str = "DELETE Subject WHERE subjectID=?";
            pstm = conn.prepareStatement(str);
            pstm.setString(1, id);
            pstm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(AccessSubject.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ResultSet searchBySubjectName(String cName) {
        rs = null;
        try {
            this.getConnection();
            String str = "SELECT * FROM Subject WHERE subjectName like '%" + cName + "%'";
            pstm = conn.prepareStatement(str);
//            pstm.setString(1, cName);
            rs = pstm.executeQuery();

        } catch (SQLException ex) {
            Logger.getLogger(AccessSubject.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rs;
    }

}
