/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Student;

import ConnectDB.ConnectDB;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import GUI.MDI;

/**
 *
 * @author MCT
 */
public class AccessStudent {

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

    public void createNewStudent(String stuID, String stuName, String majorID, String stuDoB, String stuPhonenumber, String Gender, String stuEmail, String stuAddress) {
        try {
            this.getConnection();
            String str = "INSERT Student VALUES(?,?,?,?,?,?,?,?)";
            pstm = conn.prepareStatement(str);
            pstm.setString(1, stuID);
            pstm.setString(2, stuName);
            pstm.setString(3, majorID);
            pstm.setString(4, stuDoB);
            pstm.setString(5, Gender);
            pstm.setString(6, stuPhonenumber);
            pstm.setString(7, stuEmail);
            pstm.setString(8, stuAddress);
            pstm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(AccessStudent.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void updateStudent(String stuName, String majorID, String stuDoB, String stuPhonenumber, String Gender, String stuEmail, String stuAddress, String stuID) {
        try {
            this.getConnection();
            String str = "UPDATE Student SET stuName=?,majorID=?,studateOfBirth=?,stuGender=?,stuPNumber=?, stuEmail=?, stuAddress=? WHERE stuID=?";
            pstm = conn.prepareStatement(str);
            pstm.setString(1, stuName);
            pstm.setString(2, majorID);
            pstm.setString(3, stuDoB);
            pstm.setString(4, Gender);
            pstm.setString(5, stuPhonenumber);
            pstm.setString(6, stuEmail);
            pstm.setString(7, stuAddress);
            pstm.setString(8, stuID);
            pstm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(AccessStudent.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void deleteStudent(String id) {
        try {
            this.getConnection();
            String str = "DELETE Student WHERE stuID=?";
            pstm = conn.prepareStatement(str);
            pstm.setString(1, id);
            pstm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(AccessStudent.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ResultSet searchByStudentName(String cName) {
        rs = null;
        try {
            this.getConnection();
            String str = "SELECT * FROM Student WHERE stuName like '%" + cName + "%'";
            pstm = conn.prepareStatement(str);
//            pstm.setString(1, cName);
            rs = pstm.executeQuery();

        } catch (SQLException ex) {
            Logger.getLogger(AccessStudent.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rs;
    }
}
