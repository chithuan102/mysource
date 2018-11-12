/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Student;

import Condition.CheckCode;
import GUI.MDI;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.util.Date;

/**
 *
 * @author MCT
 */
public class StudentForm extends javax.swing.JFrame {

    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    DefaultTableModel dtm;
    DefaultComboBoxModel dcm;
    AccessStudent as = new AccessStudent();
    Connection conn;
    PreparedStatement pstm;
    ResultSet rs;

    public StudentForm() {
        initComponents();
        this.setLocationRelativeTo(this);
        getConnection();
        loadCourseId();
        createTableStudent();
        loadStudent();

    }

    public boolean checkinfo() {
        CheckCode c = new CheckCode();
        if (txtStuID.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Student ID is required", "Error", JOptionPane.ERROR_MESSAGE);
            txtStuID.requestFocus();
            return false;

        } else if (txtStuName.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Student name is required", "Error", JOptionPane.ERROR_MESSAGE);
            txtStuName.requestFocus();
            return false;
        } else if (txtStuPNumber.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Phone number is required", "Error", JOptionPane.ERROR_MESSAGE);
            txtStuPNumber.requestFocus();
            return false;
        } else if (txtStuEmail.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Email is required", "Error", JOptionPane.ERROR_MESSAGE);
            txtStuEmail.requestFocus();
            return false;
        } else if (txtStuAddress.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Address is required", "Error", JOptionPane.ERROR_MESSAGE);
            txtStuAddress.requestFocus();
            return false;
        } else if (!c.checkStudentID(txtStuID.getText())) {
            JOptionPane.showMessageDialog(this, "Input ID wrong. ID must start with GCSxxxxx or GBSxxxxx(x is digits)", "Error", JOptionPane.ERROR_MESSAGE);
            txtStuID.setText("");
            txtStuID.requestFocus();
            return false;

        } else if (!c.checkName(txtStuName.getText())) {
            JOptionPane.showMessageDialog(this, "Input Name wrong. Name must not have number.", "Error", JOptionPane.ERROR_MESSAGE);
            txtStuName.setText("");
            txtStuName.requestFocus();
            return false;

        } else if (!c.checkPhone(txtStuPNumber.getText())) {
            JOptionPane.showMessageDialog(this, "Input Phone wrong. Phone must have 10-11 digits", "Error", JOptionPane.ERROR_MESSAGE);
            txtStuPNumber.setText("");
            txtStuPNumber.requestFocus();
            return false;
        } else if (!c.checkEmail(txtStuEmail.getText())) {
            JOptionPane.showMessageDialog(this, "Input Email wrong. Email must end with @FPT.EDU.VN", "Error", JOptionPane.ERROR_MESSAGE);
            txtStuEmail.setText("");
            txtStuEmail.requestFocus();
            return false;
        }
        return true;
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

    private void createTableStudent() {
        dtm = new DefaultTableModel();
        dtm.addColumn("Student ID");
        dtm.addColumn("Student Name");
        dtm.addColumn("Major ID");
        dtm.addColumn("DoB");
        dtm.addColumn("Gender");
        dtm.addColumn("PhoneNumber");
        dtm.addColumn("Email");
        dtm.addColumn("Address");
        tblStudent.setModel(dtm);
    }

    private void loadStudent() {
        try {
            String str = "SELECT * FROM Student";

            pstm = conn.prepareStatement(str);
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
    }

    public boolean checkID_exist() {
        try {

            String str = "select * from Student where stuID = '" + txtStuID.getText() + "'";
            pstm = conn.prepareStatement(str);
            rs = pstm.executeQuery();
            if (rs.next()) {
                JOptionPane.showMessageDialog(this, "ID is already exist");

                txtStuID.requestFocus();

            } else {
                return true;
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error");
        }

        return false;

    }

    private void loadCourseId() {
        cbbMajor.removeAllItems();
        dcm = new DefaultComboBoxModel();
        try {
            String str = "SELECT * FROM Major";
            pstm = conn.prepareStatement(str);
            rs = pstm.executeQuery();
            while (rs.next()) {
                cbbMajor.addItem(rs.getString("majorID"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(MDI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void bindingStudentText() {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
            String str = "SELECT * FROM Student";
            pstm = conn.prepareStatement(str,
                    ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            rs = pstm.executeQuery();
            if (rs.next()) {
                int row = tblStudent.getSelectedRow();
                txtStuID.setText(tblStudent.getValueAt(row, 0).toString());
                txtStuName.setText(tblStudent.getValueAt(row, 1).toString());
                cbbMajor.setSelectedItem(tblStudent.getValueAt(row, 2).toString());

                Date theDate = dateFormat.parse((String) tblStudent.getValueAt(row, 3).toString());
                txtStuDoB.setDate(theDate);
//                txtStuDoB.setDateFormatString(tblStudent.getValueAt(row, 3).toString());
                Female.setSelected(tblStudent.getValueAt(row, 4).toString().equals("Female"));
                Male.setSelected(tblStudent.getValueAt(row, 4).toString().equals("Male"));
                txtStuPNumber.setText(tblStudent.getValueAt(row, 5).toString());
                txtStuEmail.setText(tblStudent.getValueAt(row, 6).toString());
                txtStuAddress.setText(tblStudent.getValueAt(row, 7).toString());
            }
        } catch (SQLException ex) {
            Logger.getLogger(MDI.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(StudentForm.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void filterName() {
        try {
            rs = as.searchByStudentName(txtStudentFilter.getText());
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
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        stuID = new javax.swing.JLabel();
        stuName = new javax.swing.JLabel();
        dob = new javax.swing.JLabel();
        major = new javax.swing.JLabel();
        gender = new javax.swing.JLabel();
        txtStuID = new javax.swing.JTextField();
        txtStuName = new javax.swing.JTextField();
        txtStuEmail = new javax.swing.JTextField();
        address = new javax.swing.JLabel();
        email = new javax.swing.JLabel();
        Male = new javax.swing.JRadioButton();
        cbbMajor = new javax.swing.JComboBox<>();
        Female = new javax.swing.JRadioButton();
        number = new javax.swing.JLabel();
        txtStuPNumber = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtStuAddress = new javax.swing.JTextArea();
        txtStuDoB = new com.toedter.calendar.JDateChooser();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblStudent = new javax.swing.JTable();
        jButton3 = new javax.swing.JButton();
        txtStudentFilter = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        btnAddStu = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jPanel7 = new javax.swing.JPanel();
        lblClose5 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        lblMin5 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        jPanel1.setBackground(new java.awt.Color(44, 62, 80));

        jPanel2.setBackground(new java.awt.Color(0, 51, 51));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Manage Student", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 0, 18), new java.awt.Color(255, 255, 255))); // NOI18N

        stuID.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        stuID.setForeground(new java.awt.Color(255, 255, 255));
        stuID.setText("Student ID");

        stuName.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        stuName.setForeground(new java.awt.Color(255, 255, 255));
        stuName.setText("Student Name ");

        dob.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        dob.setForeground(new java.awt.Color(255, 255, 255));
        dob.setText("Date Of Birth");

        major.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        major.setForeground(new java.awt.Color(255, 255, 255));
        major.setText("Major");

        gender.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        gender.setForeground(new java.awt.Color(255, 255, 255));
        gender.setText("Gender");

        address.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        address.setForeground(new java.awt.Color(255, 255, 255));
        address.setText("Address");

        email.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        email.setForeground(new java.awt.Color(255, 255, 255));
        email.setText(" Email");

        Male.setBackground(new java.awt.Color(0, 51, 51));
        buttonGroup1.add(Male);
        Male.setForeground(new java.awt.Color(255, 255, 255));
        Male.setText("Male");
        Male.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MaleActionPerformed(evt);
            }
        });

        cbbMajor.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbbMajor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbbMajorActionPerformed(evt);
            }
        });

        Female.setBackground(new java.awt.Color(0, 51, 51));
        buttonGroup1.add(Female);
        Female.setForeground(new java.awt.Color(255, 255, 255));
        Female.setText("Female");
        Female.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FemaleActionPerformed(evt);
            }
        });

        number.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        number.setForeground(new java.awt.Color(255, 255, 255));
        number.setText("Phone number");

        txtStuAddress.setColumns(20);
        txtStuAddress.setRows(5);
        jScrollPane2.setViewportView(txtStuAddress);

        txtStuDoB.setDateFormatString("dd-MM-yyyy");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(gender)
                            .addComponent(stuID)
                            .addComponent(major)
                            .addComponent(dob)
                            .addComponent(stuName))
                        .addGap(30, 30, 30)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtStuID)
                            .addComponent(txtStuName)
                            .addComponent(cbbMajor, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(Male)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(Female))
                            .addComponent(txtStuDoB, javax.swing.GroupLayout.DEFAULT_SIZE, 162, Short.MAX_VALUE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(email)
                            .addComponent(address)
                            .addComponent(number))
                        .addGap(30, 30, 30)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtStuEmail)
                            .addComponent(txtStuPNumber)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(stuID)
                    .addComponent(txtStuID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(stuName)
                    .addComponent(txtStuName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(17, 17, 17)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(major)
                    .addComponent(cbbMajor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(dob)
                    .addComponent(txtStuDoB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(19, 19, 19)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(gender)
                    .addComponent(Male)
                    .addComponent(Female))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 6, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(number)
                    .addComponent(txtStuPNumber, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtStuEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(email))
                .addGap(20, 20, 20)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(address)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        tblStudent.setModel(new javax.swing.table.DefaultTableModel(
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
        tblStudent.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblStudentMouseClicked(evt);
            }
        });
        tblStudent.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tblStudentKeyReleased(evt);
            }
        });
        jScrollPane1.setViewportView(tblStudent);

        jButton3.setText("Search");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        txtStudentFilter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtStudentFilterActionPerformed(evt);
            }
        });
        txtStudentFilter.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtStudentFilterKeyReleased(evt);
            }
        });

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

        jButton6.setBackground(new java.awt.Color(204, 0, 51));
        jButton6.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jButton6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/if_Delete_132746.png"))); // NOI18N
        jButton6.setText("Delete");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jButton4.setBackground(new java.awt.Color(204, 0, 51));
        jButton4.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/if_notepad_(edit)_16x16_9909.gif"))); // NOI18N
        jButton4.setText("Edit");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        btnAddStu.setBackground(new java.awt.Color(204, 0, 51));
        btnAddStu.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        btnAddStu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/if_add_16x16_9712.gif"))); // NOI18N
        btnAddStu.setText("Add");
        btnAddStu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddStuActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnAddStu, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton5)
                .addContainerGap(15, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton5)
                    .addComponent(jButton6)
                    .addComponent(jButton4)
                    .addComponent(btnAddStu))
                .addContainerGap(28, Short.MAX_VALUE))
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
                        .addComponent(txtStudentFilter, javax.swing.GroupLayout.PREFERRED_SIZE, 261, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton7))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 659, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtStudentFilter, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton3)
                            .addComponent(jButton7))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(12, 12, 12))
        );

        jPanel7.setBackground(new java.awt.Color(248, 148, 6));

        lblClose5.setFont(new java.awt.Font("Tahoma", 0, 48)); // NOI18N
        lblClose5.setText("X");
        lblClose5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblClose5lblCloseMouseClicked(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 48)); // NOI18N
        jLabel9.setText("Manage Student");

        lblMin5.setFont(new java.awt.Font("Tahoma", 0, 48)); // NOI18N
        lblMin5.setText("_");
        lblMin5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblMin5lblMinMouseClicked(evt);
            }
        });

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/22497621_546897345663681_2003775176_n (1).png"))); // NOI18N

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel9)
                .addGap(62, 62, 62)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblMin5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblClose5)
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblClose5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(0, 9, Short.MAX_VALUE)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel2)
                            .addComponent(lblMin5))))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void lblClose5lblCloseMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblClose5lblCloseMouseClicked
        int exit = JOptionPane.showConfirmDialog(null, "Are you sure want to exit?", "System message", JOptionPane.YES_NO_OPTION);
        if (exit == 0) {
            System.exit(0);
        }

//         TODO add your handling code here:
//        System.exit(0);
    }//GEN-LAST:event_lblClose5lblCloseMouseClicked

    private void lblMin5lblMinMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblMin5lblMinMouseClicked
        this.setState(JFrame.ICONIFIED);
        // TODO add your handling code here:
    }//GEN-LAST:event_lblMin5lblMinMouseClicked

    private void FemaleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_FemaleActionPerformed

        // TODO add your handling code here:
    }//GEN-LAST:event_FemaleActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        this.filterName();
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        try {
            if (txtStuID.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Student not exist", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                int conf = JOptionPane.showConfirmDialog(null, "Do you really want to delete this record?", "Delete", JOptionPane.YES_NO_OPTION);

                if (conf == 0) {
                    as.deleteStudent(txtStuID.getText());
                    JOptionPane.showMessageDialog(this, "Delete Successfully");
                    txtStuID.setText("");
                    txtStuID.setEnabled(true);
                    txtStuName.setText("");
                    txtStuAddress.setText("");
                    txtStuDoB.setCalendar(null);
                    txtStuPNumber.setText("");
                    txtStuEmail.setText("");
                    cbbMajor.setSelectedIndex(0);
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Delete Faild");
        }

        loadStudent();
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton6ActionPerformed

    private void btnAddStuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddStuActionPerformed
        if (!checkID_exist()) {
            return;
        }
        if (!checkinfo()) {
            return;
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String theDate = dateFormat.format(txtStuDoB.getDate());
        try {
            as.createNewStudent(txtStuID.getText(), txtStuName.getText(), cbbMajor.getSelectedItem().toString(),
                    theDate, Female.isSelected() ? "Female" : "Male", txtStuPNumber.getText(), txtStuEmail.getText(), txtStuAddress.getText());
            txtStuID.setText("");
            txtStuName.setText("");
            txtStuAddress.setText("");
            txtStuDoB.setCalendar(null);
            txtStuPNumber.setText("");
            txtStuEmail.setText("");
            cbbMajor.setSelectedIndex(0);
            JOptionPane.showMessageDialog(this, "ADD Successfully");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
        loadStudent();

        // TODO add your handling code here:
    }//GEN-LAST:event_btnAddStuActionPerformed

    private void MaleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MaleActionPerformed

        // TODO add your handling code here:
    }//GEN-LAST:event_MaleActionPerformed

    private void tblStudentMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblStudentMouseClicked
        bindingStudentText();
        txtStuID.setEnabled(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_tblStudentMouseClicked

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        txtStuID.setText("");
        txtStuID.setEnabled(true);
        txtStuName.setText("");
        txtStuAddress.setText("");
        txtStuDoB.setCalendar(null);
        txtStuPNumber.setText("");
        txtStuEmail.setText("");
        cbbMajor.setSelectedIndex(0);
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton5ActionPerformed

    private void txtStudentFilterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtStudentFilterActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtStudentFilterActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed

        dispose();
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed

        if (!checkinfo()) {
            return;
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String theDate = dateFormat.format(txtStuDoB.getDate());
        try {
            if (txtStuID.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Student not exist", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                as.updateStudent(txtStuName.getText(), cbbMajor.getSelectedItem().toString(),
                        theDate, Female.isSelected() ? "Female" : "Male", txtStuPNumber.getText(), txtStuEmail.getText(), txtStuAddress.getText(), txtStuID.getText());
                txtStuName.setText("");
                txtStuAddress.setText("");
                txtStuDoB.setCalendar(null);
                txtStuPNumber.setText("");
                txtStuEmail.setText("");
                txtStuID.setText("");
                cbbMajor.setSelectedIndex(0);
                JOptionPane.showMessageDialog(this, "Edit Successfully");
                loadStudent();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Failed");
        }

        // TODO add your handling code here:
    }//GEN-LAST:event_jButton4ActionPerformed

    private void tblStudentKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tblStudentKeyReleased
        bindingStudentText();
        txtStuID.setEnabled(false);
// TODO add your handling code here:
    }//GEN-LAST:event_tblStudentKeyReleased

    private void txtStudentFilterKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtStudentFilterKeyReleased
        this.filterName();
        // TODO add your handling code here:
    }//GEN-LAST:event_txtStudentFilterKeyReleased

    private void cbbMajorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbbMajorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbbMajorActionPerformed

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
            java.util.logging.Logger.getLogger(StudentForm.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(StudentForm.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(StudentForm.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(StudentForm.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new StudentForm().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JRadioButton Female;
    private javax.swing.JRadioButton Male;
    private javax.swing.JLabel address;
    private javax.swing.JButton btnAddStu;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox<String> cbbMajor;
    private javax.swing.JLabel dob;
    private javax.swing.JLabel email;
    private javax.swing.JLabel gender;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblClose5;
    private javax.swing.JLabel lblMin5;
    public javax.swing.JLabel major;
    private javax.swing.JLabel number;
    public javax.swing.JLabel stuID;
    public javax.swing.JLabel stuName;
    private javax.swing.JTable tblStudent;
    private javax.swing.JTextArea txtStuAddress;
    private com.toedter.calendar.JDateChooser txtStuDoB;
    private javax.swing.JTextField txtStuEmail;
    private javax.swing.JTextField txtStuID;
    private javax.swing.JTextField txtStuName;
    private javax.swing.JTextField txtStuPNumber;
    private javax.swing.JTextField txtStudentFilter;
    // End of variables declaration//GEN-END:variables
}
