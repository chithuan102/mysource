/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Subject;

import GUI.MDI;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class SubjectForm extends javax.swing.JFrame {

    DefaultTableModel dtm;
    DefaultComboBoxModel dcm;
    AccessSubject as = new AccessSubject();
    Connection conn;
    PreparedStatement pstm;
    ResultSet rs;

    public SubjectForm() {
        initComponents();
        this.setLocationRelativeTo(this);
        getConnection();
        loadClassId();
        loadMajorId();
        createTableSubject();
        loadSubject();

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
        if (txtSubjectID.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Subject ID is required", "Error", JOptionPane.ERROR_MESSAGE);
            txtSubjectID.requestFocus();
            return false;
        } else if (txtSubjectName.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Subject name is required", "Error", JOptionPane.ERROR_MESSAGE);
            txtSubjectName.requestFocus();
            return false;
        }

        return true;
    }

    private void createTableSubject() {
        dtm = new DefaultTableModel();
        dtm.addColumn("Subject ID");
        dtm.addColumn("Subject Name");
        dtm.addColumn("Class ID");
        dtm.addColumn("Term");
        dtm.addColumn("Major ID");
        tblSubject.setModel(dtm);
    }

    public boolean checkSubjectName_exist() {
        try {

            String str = "select * from Subject where subjectID= '" + txtSubjectID.getText() + "'";
            pstm = conn.prepareStatement(str);
            rs = pstm.executeQuery();
            if (rs.next()) {
                JOptionPane.showMessageDialog(this, "Subject ID is already exist!!!");
                txtSubjectID.setText("");
                txtSubjectID.requestFocus();

            } else {
                return true;
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error");
        }

        return false;

    }

    private void loadSubject() {
        try {
            String str = "SELECT * FROM Subject";
            pstm = conn.prepareStatement(str);
            rs = pstm.executeQuery();
            createTableSubject();
            while (rs.next()) {
                Vector<String> vec = new Vector<String>();
                vec.add(0, rs.getString("subjectID"));
                vec.add(1, rs.getString("SubjectName"));
                vec.add(2, rs.getString("classID"));
                vec.add(3, rs.getString("term"));
                vec.add(4, rs.getString("majorID"));

                dtm.addRow(vec);
            }
        } catch (SQLException ex) {
            Logger.getLogger(MDI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void bindingSubjectText() {
        try {

            String str = "SELECT * FROM Subject";
            pstm = conn.prepareStatement(str,
                    ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            rs = pstm.executeQuery();
            if (rs.next()) {
                int row = tblSubject.getSelectedRow();
                txtSubjectID.setText(tblSubject.getValueAt(row, 0).toString());
                txtSubjectName.setText(tblSubject.getValueAt(row, 1).toString());
                cbbClassID.setSelectedItem(tblSubject.getValueAt(row, 2).toString());
                cbbTerm.setSelectedItem(tblSubject.getValueAt(row, 3).toString());
                cbbMajorID.setSelectedItem(tblSubject.getValueAt(row, 4).toString());

            }
        } catch (SQLException ex) {
            Logger.getLogger(MDI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void loadMajorId() {
        cbbMajorID.removeAllItems();
        dcm = new DefaultComboBoxModel();
        try {
            String str = "SELECT * FROM Major";
            pstm = conn.prepareStatement(str);
            rs = pstm.executeQuery();
            while (rs.next()) {
                cbbMajorID.addItem(rs.getString("majorID"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(MDI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void loadClassId() {
        cbbClassID.removeAllItems();
        dcm = new DefaultComboBoxModel();
        try {
            String str = "SELECT * FROM Class";
            pstm = conn.prepareStatement(str);
            rs = pstm.executeQuery();
            while (rs.next()) {
                cbbClassID.addItem(rs.getString("classID"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(MDI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void filterName() {
        try {
            rs = as.searchBySubjectName(txtSubjectFilter.getText());
            createTableSubject();
            while (rs.next()) {
                Vector<String> vec = new Vector<String>();
                vec.add(0, rs.getString("subjectID"));
                vec.add(1, rs.getString("SubjectName"));
                vec.add(2, rs.getString("classID"));
                vec.add(3, rs.getString("term"));
                vec.add(4, rs.getString("majorID"));
                dtm.addRow(vec);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jButton5 = new javax.swing.JButton();
        btnDeleteSubject = new javax.swing.JButton();
        btnEditSubject = new javax.swing.JButton();
        btnAddSubject = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtSubjectID = new javax.swing.JTextField();
        txtSubjectName = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        cbbClassID = new javax.swing.JComboBox<>();
        cbbTerm = new javax.swing.JComboBox<>();
        cbbMajorID = new javax.swing.JComboBox<>();
        txtSubjectFilter = new javax.swing.JTextField();
        jButton3 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblSubject = new javax.swing.JTable();
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
        jButton5.setText("Refesh");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        btnDeleteSubject.setBackground(new java.awt.Color(204, 0, 51));
        btnDeleteSubject.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        btnDeleteSubject.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/if_Delete_132746.png"))); // NOI18N
        btnDeleteSubject.setText("Delete");
        btnDeleteSubject.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteSubjectActionPerformed(evt);
            }
        });

        btnEditSubject.setBackground(new java.awt.Color(204, 0, 51));
        btnEditSubject.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        btnEditSubject.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/if_notepad_(edit)_16x16_9909.gif"))); // NOI18N
        btnEditSubject.setText("Edit");
        btnEditSubject.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditSubjectActionPerformed(evt);
            }
        });

        btnAddSubject.setBackground(new java.awt.Color(204, 0, 51));
        btnAddSubject.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        btnAddSubject.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/if_add_16x16_9712.gif"))); // NOI18N
        btnAddSubject.setText("Add");
        btnAddSubject.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddSubjectActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnAddSubject, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnEditSubject, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnDeleteSubject, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton5)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton5)
                    .addComponent(btnDeleteSubject)
                    .addComponent(btnEditSubject)
                    .addComponent(btnAddSubject))
                .addContainerGap(29, Short.MAX_VALUE))
        );

        jPanel2.setBackground(new java.awt.Color(0, 51, 51));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Manage Subject", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 0, 18), new java.awt.Color(255, 255, 255))); // NOI18N

        jLabel1.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Subject ID");

        jLabel3.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Subject Name");

        jLabel5.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Term ");

        jLabel6.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Class ID");

        jLabel7.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Major ID");

        cbbClassID.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        cbbTerm.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Spring", "Summer", "Authumn", "Winter" }));

        cbbMajorID.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel1)
                                    .addComponent(jLabel3))
                                .addGap(30, 30, 30))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addGap(63, 63, 63)))
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addComponent(jLabel7)
                            .addGap(63, 63, 63)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addGap(65, 65, 65)))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(cbbTerm, javax.swing.GroupLayout.Alignment.LEADING, 0, 162, Short.MAX_VALUE)
                    .addComponent(txtSubjectID, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtSubjectName, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cbbClassID, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cbbMajorID, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtSubjectID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtSubjectName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6)
                    .addComponent(cbbClassID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(cbbTerm, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(cbbMajorID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(35, Short.MAX_VALUE))
        );

        txtSubjectFilter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSubjectFilterActionPerformed(evt);
            }
        });

        jButton3.setText("Search");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton7.setBackground(new java.awt.Color(204, 0, 51));
        jButton7.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jButton7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/if_undo1_16x16_10036.gif"))); // NOI18N
        jButton7.setText("Back to menu");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        tblSubject.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tblSubject.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblSubjectMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblSubject);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(txtSubjectFilter, javax.swing.GroupLayout.PREFERRED_SIZE, 261, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton3)
                        .addGap(165, 165, 165)
                        .addComponent(jButton7))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 650, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtSubjectFilter, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton3)
                            .addComponent(jButton7))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(10, 10, 10))
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
        jLabel10.setText("Manage Subject");

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/22497621_546897345663681_2003775176_n (1).png"))); // NOI18N

        lblMin.setFont(new java.awt.Font("Tahoma", 0, 48)); // NOI18N
        lblMin.setText("_");

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 409, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(51, 51, 51)
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
                    .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lblClose6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblMin))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel8Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(3, 3, 3))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed

        txtSubjectID.setEnabled(true);
        txtSubjectID.setText("");
        txtSubjectName.setText("");
        cbbMajorID.setSelectedIndex(0);
        cbbTerm.setSelectedIndex(0);
        cbbClassID.setSelectedIndex(0);

        // TODO add your handling code here:
    }//GEN-LAST:event_jButton5ActionPerformed

    private void btnDeleteSubjectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteSubjectActionPerformed
        if (txtSubjectID.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Subject not exist", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            int conf = JOptionPane.showConfirmDialog(null, "If you delete this record, another data which connect with this will be deleted too. Do you really want to delete this record?", "Delete", JOptionPane.YES_NO_OPTION);
            if (conf == 0) {
                as.deleteSubject(txtSubjectID.getText());
                JOptionPane.showMessageDialog(this, "Delete Successfully");
                txtSubjectID.setEnabled(true);
                txtSubjectID.setText("");
                txtSubjectName.setText("");
                cbbMajorID.setSelectedIndex(0);
                cbbTerm.setSelectedIndex(0);
                cbbClassID.setSelectedIndex(0);
            }
        }
        loadSubject();
        // TODO add your handling code here:
    }//GEN-LAST:event_btnDeleteSubjectActionPerformed

    private void btnEditSubjectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditSubjectActionPerformed
        if (!checkinfo()) {
            return;
        }
        try {
            as.updateSubject(txtSubjectName.getText(), cbbTerm.getSelectedItem().toString(), cbbMajorID.getSelectedItem().toString(), cbbClassID.getSelectedItem().toString(), txtSubjectID.getText());
            txtSubjectID.setText("");
            txtSubjectName.setText("");
            cbbMajorID.setSelectedIndex(0);
            cbbTerm.setSelectedIndex(0);
            cbbClassID.setSelectedIndex(0);
            txtSubjectID.setEnabled(true);
            JOptionPane.showMessageDialog(this, "Edit Successfully");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Failed");
        }
        loadSubject();
        // TODO add your handling code here:
    }//GEN-LAST:event_btnEditSubjectActionPerformed

    private void btnAddSubjectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddSubjectActionPerformed
        if (!checkinfo()) {
            return;
        }
        if (!checkSubjectName_exist()) {
            return;
        }
        try {
            as.createNewSubject(txtSubjectID.getText(), txtSubjectName.getText(), cbbClassID.getSelectedItem().toString(), cbbTerm.getSelectedItem().toString(), cbbMajorID.getSelectedItem().toString());
            txtSubjectID.setText("");
            txtSubjectName.setText("");
            cbbMajorID.setSelectedIndex(0);
            cbbTerm.setSelectedIndex(0);
            cbbClassID.setSelectedIndex(0);
            JOptionPane.showMessageDialog(this, "Add Successfully");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Failed");
        }
        loadSubject();

        // TODO add your handling code here:
    }//GEN-LAST:event_btnAddSubjectActionPerformed

    private void txtSubjectFilterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSubjectFilterActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSubjectFilterActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        this.filterName();
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed

        dispose();
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton7ActionPerformed

    private void tblSubjectMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblSubjectMouseClicked

//        this.loadAssessmentList();
        bindingSubjectText();
        txtSubjectID.setEnabled(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_tblSubjectMouseClicked

    private void lblClose6lblCloseMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblClose6lblCloseMouseClicked
        int exit = JOptionPane.showConfirmDialog(null, "Are you sure want to exit?", "System message", JOptionPane.YES_NO_OPTION);
        if (exit == 0) {
            System.exit(0);
        }

        //         TODO add your handling code here:
        //        System.exit(0);
    }//GEN-LAST:event_lblClose6lblCloseMouseClicked

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
            java.util.logging.Logger.getLogger(SubjectForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SubjectForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SubjectForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SubjectForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new SubjectForm().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddSubject;
    private javax.swing.JButton btnDeleteSubject;
    private javax.swing.JButton btnEditSubject;
    private javax.swing.JComboBox<String> cbbClassID;
    private javax.swing.JComboBox<String> cbbMajorID;
    private javax.swing.JComboBox<String> cbbTerm;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton7;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblClose6;
    private javax.swing.JLabel lblMin;
    private javax.swing.JTable tblSubject;
    private javax.swing.JTextField txtSubjectFilter;
    private javax.swing.JTextField txtSubjectID;
    private javax.swing.JTextField txtSubjectName;
    // End of variables declaration//GEN-END:variables
}
