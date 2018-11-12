/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Assessment;

import GUI.MDI;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.*;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class AssessmentForm extends javax.swing.JFrame {

    DefaultTableModel dtm;
    DefaultComboBoxModel dcm;
    AccessAssessment aa = new AccessAssessment();
    Connection conn;
    PreparedStatement pstm;
    ResultSet rs;

    public AssessmentForm() {
        initComponents();
        getConnection();
        this.setLocationRelativeTo(this);
//        loadSubjectId();
        txtStudentID.setEditable(false);
        txtStudentName.setEditable(false);
        txtSubjectName.setEditable(false);
        txtMark.setEditable(false);
        txtStatus.setEditable(false);
        loadMajorName();
        cbbSubjectID.removeAllItems();
        cbbSubjectID.setEditable(false);
        txtMark.setEditable(false);
        createTableAssessment();
        createTableStudent();

    }

    private void getConnection() {
        String url = "jdbc:sqlserver://localhost;database=SMS";
        String user = "sa";
        String password = "123";
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            conn = DriverManager.getConnection(url, user, password);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(MDI.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(MDI.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public boolean checkinfo() {
        if (txtMark.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Mark is required", "Error", JOptionPane.ERROR_MESSAGE);
            txtMark.requestFocus();
            return false;
        } else if (txtSubjectName.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Subject name is required", "Error", JOptionPane.ERROR_MESSAGE);
            txtSubjectName.requestFocus();
            return false;
        }
        return true;
    }

    public boolean checkSubjectName_exist() {
        try {

            String str = "select * from Assessment where subjectName = '" + txtSubjectName.getText() + "' and stuID='" + txtStudentID.getText() + "'";
            pstm = conn.prepareStatement(str);
            rs = pstm.executeQuery();
            if (rs.next()) {
                JOptionPane.showMessageDialog(this, "This record is already exist!!!");
                txtSubjectName.requestFocus();

            } else {
                return true;
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error");
        }

        return false;

    }

    private void createTableAssessment() {
        dtm = new DefaultTableModel();
        dtm.addColumn("Student ID");
        dtm.addColumn("Subject ID");
        dtm.addColumn("Student Name");
        dtm.addColumn("Subject Name");
        dtm.addColumn("Mark");
        dtm.addColumn("Status");
        tblAssessment.setModel(dtm);
    }

    private void createTableStudent() {
        dtm = new DefaultTableModel();
        dtm.addColumn("Student ID");
        dtm.addColumn("Student Name");
        dtm.addColumn("Major ID");
        tblStudent.setModel(dtm);
    }

    private void loadAssessment() {
        try {
            int row = tblStudent.getSelectedRow();
            String str = "SELECT * FROM Assessment where stuID='" + tblStudent.getValueAt(row, 0) + "'";
            pstm = conn.prepareStatement(str);
            rs = pstm.executeQuery();
            createTableAssessment();
            while (rs.next()) {
                Vector<String> vec = new Vector<String>();
                vec.add(0, rs.getString("stuID"));
                vec.add(1, rs.getString("subjectID"));
                vec.add(2, rs.getString("stuName"));
                vec.add(3, rs.getString("subjectName"));
                vec.add(4, rs.getString("mark"));
                vec.add(5, rs.getString("Status"));
                dtm.addRow(vec);
            }
        } catch (SQLException ex) {
            Logger.getLogger(MDI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void bindingAssessmentText() {
        try {

            String str = "SELECT * FROM Assessment";
            pstm = conn.prepareStatement(str,
                    ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            rs = pstm.executeQuery();
            if (rs.next()) {
                int row = tblAssessment.getSelectedRow();
                txtStudentID.setText(tblAssessment.getValueAt(row, 0).toString());
                cbbSubjectID.setSelectedItem(tblAssessment.getValueAt(row, 1).toString());
                txtStudentName.setText(tblAssessment.getValueAt(row, 2).toString());
                txtSubjectName.setText(tblAssessment.getValueAt(row, 3).toString());
                txtMark.setText(tblAssessment.getValueAt(row, 4).toString());
                txtStatus.setText(tblAssessment.getValueAt(row, 5).toString());

            }
        } catch (SQLException ex) {
            Logger.getLogger(MDI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void loadMajorName() {
        jComboBox1.removeAllItems();
        dcm = new DefaultComboBoxModel();
        try {
            String str = "SELECT * FROM Major";
            pstm = conn.prepareStatement(str);
            rs = pstm.executeQuery();
            while (rs.next()) {
                jComboBox1.addItem(rs.getString("majorID"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(MDI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void loadSubjectID() {
        cbbSubjectID.removeAllItems();
        dcm = new DefaultComboBoxModel();
        try {
            String str = "SELECT * FROM Subject where majorID='" + jComboBox1.getSelectedItem() + "'";
            pstm = conn.prepareStatement(str);
            rs = pstm.executeQuery();
            while (rs.next()) {
                cbbSubjectID.addItem(rs.getString("subjectID"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(MDI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void bindingStudentText() {
        try {

            String str = "SELECT * FROM Student";
            pstm = conn.prepareStatement(str,
                    ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            rs = pstm.executeQuery();
            if (rs.next()) {
                int row = tblStudent.getSelectedRow();
                txtStudentID.setText(tblStudent.getValueAt(row, 0).toString());
                txtStudentName.setText(tblStudent.getValueAt(row, 1).toString());
            }
        } catch (SQLException ex) {
            Logger.getLogger(MDI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void loadStudentList() {
        try {
            int row = tblStudent.getSelectedRow();
            String cellCourseID = tblStudent.getValueAt(row, 0).toString();
            String str = "SELECT * FROM Assessment WHERE stuID=?";
            pstm = conn.prepareStatement(str,
                    ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            pstm.setString(1, cellCourseID);
            rs = pstm.executeQuery();
            createTableAssessment();
            while (rs.next()) {
                Vector<String> vec = new Vector<String>();
                vec.add(0, rs.getString("stuID"));
                vec.add(1, rs.getString("subjectID"));
                vec.add(2, rs.getString("stuName"));
                vec.add(3, rs.getString("subjectName"));
                vec.add(4, rs.getString("mark"));
                vec.add(5, rs.getString("Status"));
                dtm.addRow(vec);
            }
        } catch (SQLException ex) {
            Logger.getLogger(MDI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jButton5 = new javax.swing.JButton();
        btnDeleteAssessment = new javax.swing.JButton();
        btnEditAssessment = new javax.swing.JButton();
        btnAddAssessment = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblAssessment = new javax.swing.JTable();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblStudent = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtStudentID = new javax.swing.JTextField();
        txtMark = new javax.swing.JTextField();
        txtStudentName = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        cbbSubjectID = new javax.swing.JComboBox<>();
        txtSubjectName = new javax.swing.JTextField();
        txtStatus = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jButton1 = new javax.swing.JButton();
        jPanel8 = new javax.swing.JPanel();
        lblClose6 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        lblMin = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        jPanel1.setBackground(new java.awt.Color(44, 62, 80));

        jPanel3.setBackground(new java.awt.Color(0, 51, 51));
        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jButton5.setBackground(new java.awt.Color(204, 0, 51));
        jButton5.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/if_refresh_16x16_9933.gif"))); // NOI18N
        jButton5.setText("Refresh");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        btnDeleteAssessment.setBackground(new java.awt.Color(204, 0, 51));
        btnDeleteAssessment.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        btnDeleteAssessment.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/if_Delete_132746.png"))); // NOI18N
        btnDeleteAssessment.setText("Delete");
        btnDeleteAssessment.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteAssessmentActionPerformed(evt);
            }
        });

        btnEditAssessment.setBackground(new java.awt.Color(204, 0, 51));
        btnEditAssessment.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        btnEditAssessment.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/if_notepad_(edit)_16x16_9909.gif"))); // NOI18N
        btnEditAssessment.setText("Edit");
        btnEditAssessment.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditAssessmentActionPerformed(evt);
            }
        });

        btnAddAssessment.setBackground(new java.awt.Color(204, 0, 51));
        btnAddAssessment.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        btnAddAssessment.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/if_add_16x16_9712.gif"))); // NOI18N
        btnAddAssessment.setText("Add");
        btnAddAssessment.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddAssessmentActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(btnAddAssessment, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnEditAssessment, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnDeleteAssessment, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton5)
                .addGap(0, 32, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton5)
                    .addComponent(btnDeleteAssessment)
                    .addComponent(btnEditAssessment)
                    .addComponent(btnAddAssessment))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jButton7.setBackground(new java.awt.Color(204, 0, 51));
        jButton7.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jButton7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/if_undo1_16x16_10036.gif"))); // NOI18N
        jButton7.setText("Back to menu");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        tblAssessment.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Student ID", "Student Name", "Subject Name", "Mark", "Status"
            }
        ));
        tblAssessment.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblAssessmentMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblAssessment);

        tblStudent.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Student ID", "Student Name", "MajorID"
            }
        ));
        tblStudent.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblStudentMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblStudent);

        jPanel2.setBackground(new java.awt.Color(0, 51, 51));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Manage Assessment", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 0, 18), new java.awt.Color(255, 255, 255))); // NOI18N

        jLabel1.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Student ID");

        jLabel3.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Student Name");

        jLabel5.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Mark");

        jLabel6.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Subject Name");

        txtMark.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMarkActionPerformed(evt);
            }
        });
        txtMark.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtMarkKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtMarkKeyTyped(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Status");

        jLabel8.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Subject ID");

        cbbSubjectID.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbbSubjectID.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
                cbbSubjectIDPopupMenuWillBecomeInvisible(evt);
            }
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
            }
        });
        cbbSubjectID.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbbSubjectIDActionPerformed(evt);
            }
        });

        txtSubjectName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSubjectNameActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                        .addGap(165, 165, 165)
                        .addComponent(txtStatus))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                        .addGap(73, 73, 73)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtStudentName, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jLabel3)
                                .addGroup(jPanel2Layout.createSequentialGroup()
                                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel5)
                                        .addComponent(jLabel1)
                                        .addComponent(jLabel8)
                                        .addComponent(jLabel7))
                                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(cbbSubjectID, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                            .addGap(30, 30, 30)
                                            .addComponent(txtStudentID))
                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                            .addGap(34, 34, 34)
                                            .addComponent(txtMark, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                    .addComponent(jLabel6)
                                    .addGap(13, 13, 13)
                                    .addComponent(txtSubjectName))))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtStudentID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(28, 28, 28)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbbSubjectID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtStudentName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(23, 23, 23)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtSubjectName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addComponent(txtMark, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addContainerGap(25, Short.MAX_VALUE))
        );

        jLabel2.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Major");

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jComboBox1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jComboBox1MouseClicked(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jComboBox1MouseReleased(evt);
            }
        });
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });

        jButton1.setText("Select");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane2)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 617, Short.MAX_VALUE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(19, 19, 19)
                                .addComponent(jButton1)
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(jButton7))))
                .addGap(10, 10, 10))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1)
                    .addComponent(jLabel2)
                    .addComponent(jButton7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 247, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel8.setBackground(new java.awt.Color(248, 148, 6));

        lblClose6.setFont(new java.awt.Font("Tahoma", 0, 48)); // NOI18N
        lblClose6.setText("X");
        lblClose6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblClose6lblCloseMouseClicked(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 48)); // NOI18N
        jLabel10.setText("Manage Assessment");

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/22497621_546897345663681_2003775176_n (1).png"))); // NOI18N

        lblMin.setFont(new java.awt.Font("Tahoma", 0, 48)); // NOI18N
        lblMin.setText("_");

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 440, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(98, 98, 98)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblMin)
                .addGap(18, 18, 18)
                .addComponent(lblClose6)
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGap(0, 1, Short.MAX_VALUE)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblMin)
                            .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jLabel4)
                                .addComponent(lblClose6)))))
                .addGap(2, 2, 2))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        txtStudentID.setText("");
        txtStudentName.setText("");
        txtSubjectName.setText("");
        txtMark.setText("");
        txtMark.setEnabled(false);
        txtStatus.setText("");
        txtStatus.setBackground(Color.white);
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton5ActionPerformed

    private void btnDeleteAssessmentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteAssessmentActionPerformed
        try {

            if (txtStudentID.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Student not exist", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                int conf = JOptionPane.showConfirmDialog(null, "Do you really want to delete this record?", "Delete", JOptionPane.YES_NO_OPTION);
                if (conf == 0) {
                    aa.deleteAssessment(txtSubjectName.getText(), txtStudentID.getText());
                    JOptionPane.showMessageDialog(this, "Delete Successfully");
                    txtStudentID.setText("");
                    txtStudentName.setText("");
                    cbbSubjectID.setSelectedIndex(0);
                    txtMark.setText("");
                    txtStatus.setText("");
                    txtMark.setEnabled(false);
                    txtStatus.setEnabled(false);
                }
            }
            loadAssessment();
        } catch (Exception e) {
        }

        // TODO add your handling code here:
    }//GEN-LAST:event_btnDeleteAssessmentActionPerformed

    private void btnEditAssessmentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditAssessmentActionPerformed

        try {
            if (txtStudentID.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Student not exist", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                aa.updateAssessment(txtMark.getText(), txtStatus.getText(), txtStudentID.getText(), cbbSubjectID.getSelectedItem().toString());
                txtStudentID.setText("");
                txtStudentName.setText("");
                cbbSubjectID.setSelectedIndex(0);
                txtMark.setText("");
                txtStatus.setText("");
                txtMark.setEnabled(false);
                txtStatus.setEnabled(false);
                JOptionPane.showMessageDialog(this, "Edit Successfully");
                loadAssessment();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Failed");
        }

        // TODO add your handling code here:
    }//GEN-LAST:event_btnEditAssessmentActionPerformed

    private void btnAddAssessmentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddAssessmentActionPerformed
        if (!checkinfo()) {
            return;
        }
        if (!checkSubjectName_exist()) {
            return;
        }
        try {
            aa.createNewAssessment(txtStudentID.getText(), cbbSubjectID.getSelectedItem().toString(), txtStudentName.getText(), txtSubjectName.getText(), txtMark.getText(), txtStatus.getText());
            txtStudentID.setText("");
            txtStudentName.setText("");
            cbbSubjectID.setSelectedIndex(0);
            txtMark.setText("");
            txtStatus.setText("");
            txtStatus.setBackground(Color.WHITE);
            JOptionPane.showMessageDialog(this, "Add Successfully");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Failed");
        }
        loadAssessment();

        // TODO add your handling code here:
    }//GEN-LAST:event_btnAddAssessmentActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed

        dispose();
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton7ActionPerformed

    private void tblAssessmentMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblAssessmentMouseClicked
        bindingAssessmentText();
        txtStudentID.setEditable(false);
        txtStudentName.setEditable(false);
        cbbSubjectID.setEnabled(false);
        txtMark.setEditable(true);
        txtMark.setEnabled(true);
        txtStatus.setEnabled(true);

        if (txtMark.getText().equals("1")) {
            txtStatus.setText("Not Pass");
            txtStatus.setBackground(Color.red);
        } else if (txtMark.getText().equals("2")) {
            txtStatus.setText("Not Pass");
            txtStatus.setBackground(Color.red);
        } else if (txtMark.getText().equals("3")) {
            txtStatus.setText("Not Pass");
            txtStatus.setBackground(Color.red);
        } else if (txtMark.getText().equals("4")) {
            txtStatus.setText("Not Pass");
            txtStatus.setBackground(Color.red);
        } else if (txtMark.getText().equals("5")) {
            txtStatus.setText("Not Pass");
            txtStatus.setBackground(Color.red);
        } else if (txtMark.getText().equals("6")) {
            txtStatus.setText("Pass");
            txtStatus.setBackground(Color.GREEN);
        } else if (txtMark.getText().equals("7")) {
            txtStatus.setText("Pass");
            txtStatus.setBackground(Color.GREEN);
        } else if (txtMark.getText().equals("8")) {
            txtStatus.setText("Merit");
            txtStatus.setBackground(Color.MAGENTA);
        } else if (txtMark.getText().equals("9")) {
            txtStatus.setText("Merit");
            txtStatus.setBackground(Color.MAGENTA);
        } else if (txtMark.getText().equals("10")) {
            txtStatus.setText("Distincion");
            txtStatus.setBackground(Color.orange);

        }

        // TODO add your handling code here:
    }//GEN-LAST:event_tblAssessmentMouseClicked

    private void lblClose6lblCloseMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblClose6lblCloseMouseClicked
        int exit = JOptionPane.showConfirmDialog(null, "Are you sure want to exit?", "System message", JOptionPane.YES_NO_OPTION);
        if (exit == 0) {
            System.exit(0);
        }

        //         TODO add your handling code here:
    }//GEN-LAST:event_lblClose6lblCloseMouseClicked

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed

        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void jComboBox1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jComboBox1MouseClicked

        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox1MouseClicked

    private void jComboBox1MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jComboBox1MouseReleased

        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox1MouseReleased

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        try {

            String str = "SELECT * FROM Student WHERE majorID='" + jComboBox1.getSelectedItem() + "'";
            pstm = conn.prepareStatement(str,
                    ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);

            rs = pstm.executeQuery();
            createTableStudent();
            while (rs.next()) {
                Vector<String> vec = new Vector<String>();
                vec.add(0, rs.getString("stuID"));
                vec.add(1, rs.getString("stuName"));
                vec.add(2, rs.getString("majorID"));
                vec.add(3, rs.getString("studateOfBirth"));
                vec.add(4, rs.getString("stuPNumber"));
                vec.add(5, rs.getString("stuGender"));
                vec.add(6, rs.getString("stuEmail"));
                vec.add(7, rs.getString("stuAddress"));
                dtm.addRow(vec);
            }

        } catch (SQLException ex) {
            Logger.getLogger(MDI.class.getName()).log(Level.SEVERE, null, ex);
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

    private void tblStudentMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblStudentMouseClicked
        loadSubjectID();
        this.loadStudentList();
        txtMark.setEnabled(true);
        txtStatus.setEnabled(true);
        bindingStudentText();
        txtStudentID.setEditable(false);
        txtStudentName.setEditable(false);
        txtSubjectName.setEditable(false);
        txtMark.setEditable(true);
        txtMark.setText("");
        txtStatus.setText("");
        cbbSubjectID.setEnabled(true);
        txtStatus.setBackground(Color.white);
        // TODO add your handling code here:
    }//GEN-LAST:event_tblStudentMouseClicked

    private void cbbSubjectIDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbbSubjectIDActionPerformed

        // TODO add your handling code here:
    }//GEN-LAST:event_cbbSubjectIDActionPerformed

    private void cbbSubjectIDPopupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_cbbSubjectIDPopupMenuWillBecomeInvisible
        String tmp = (String) cbbSubjectID.getSelectedItem();
        String str = "Select *from Subject where subjectID=?";
        try {
            pstm = conn.prepareStatement(str);
            pstm.setString(1, tmp);
            rs = pstm.executeQuery();
            if (rs.next()) {
                String add = rs.getString("subjectName");
                txtSubjectName.setText(add);
            }
        } catch (Exception e) {
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_cbbSubjectIDPopupMenuWillBecomeInvisible

    private void txtSubjectNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSubjectNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSubjectNameActionPerformed

    private void txtMarkKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtMarkKeyTyped
        char c = evt.getKeyChar();
        if (!(Character.isDigit(c)) || c == KeyEvent.VK_BACK_SPACE || c == KeyEvent.VK_DELETE) {
            evt.consume();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMarkKeyTyped

    private void txtMarkKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtMarkKeyReleased
        int key = evt.getKeyCode();
        if ((key > 11)) {
            txtStatus.setText("Unknown");
            txtStatus.setBackground(Color.WHITE);
        }
        if (txtMark.getText().equals("0")) {
            txtStatus.setText("Not Pass");
            txtStatus.setBackground(Color.red);
        }
        if (txtMark.getText().equals("1")) {
            txtStatus.setText("Not Pass");
            txtStatus.setBackground(Color.red);
        }
        if (txtMark.getText().equals("2")) {
            txtStatus.setText("Not Pass");
            txtStatus.setBackground(Color.red);
        }
        if (txtMark.getText().equals("3")) {
            txtStatus.setText("Not Pass");
            txtStatus.setBackground(Color.red);
        }
        if (txtMark.getText().equals("4")) {
            txtStatus.setText("Not Pass");
            txtStatus.setBackground(Color.red);
        }
        if (txtMark.getText().equals("5")) {
            txtStatus.setText("Not Pass");
            txtStatus.setBackground(Color.red);
        }
        if (txtMark.getText().equals("6")) {
            txtStatus.setText("Pass");
            txtStatus.setBackground(Color.GREEN);
        }
        if (txtMark.getText().equals("7")) {
            txtStatus.setText("Pass");
            txtStatus.setBackground(Color.GREEN);
        }
        if (txtMark.getText().equals("8")) {
            txtStatus.setText("Merit");
            txtStatus.setBackground(Color.MAGENTA);
        }
        if (txtMark.getText().equals("9")) {
            txtStatus.setText("Merit");
            txtStatus.setBackground(Color.MAGENTA);
        }
        if (txtMark.getText().equals("10")) {
            txtStatus.setText("Distincion");
            txtStatus.setBackground(Color.orange);
        }
        if (txtMark.getText().equals("")) {
            txtStatus.setText("");
            txtStatus.setBackground(Color.WHITE);
        }

        // TODO add your handling code here:
    }//GEN-LAST:event_txtMarkKeyReleased

    private void txtMarkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMarkActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMarkActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;

                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(AssessmentForm.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AssessmentForm.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AssessmentForm.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AssessmentForm.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AssessmentForm().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddAssessment;
    private javax.swing.JButton btnDeleteAssessment;
    private javax.swing.JButton btnEditAssessment;
    private javax.swing.JComboBox<String> cbbSubjectID;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton7;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblClose6;
    private javax.swing.JLabel lblMin;
    private javax.swing.JTable tblAssessment;
    private javax.swing.JTable tblStudent;
    private javax.swing.JTextField txtMark;
    private javax.swing.JTextField txtStatus;
    private javax.swing.JTextField txtStudentID;
    private javax.swing.JTextField txtStudentName;
    private javax.swing.JTextField txtSubjectName;
    // End of variables declaration//GEN-END:variables
}
