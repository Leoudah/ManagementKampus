/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package UI.mahasiswa.mahasiswaPanel;


import DAO.MhsDashboardDAO;
import Model.student;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.util.List;
import java.text.SimpleDateFormat;

public class MhsDashboard extends javax.swing.JPanel {
    private int studentId;
    private MhsDashboardDAO dashboardDAO;
    private DefaultTableModel tableModel;
    
   
    public MhsDashboard(int studentId) {
        this.studentId = studentId;
        this.dashboardDAO = new MhsDashboardDAO();
        
        initComponents();
        initializeTableModel();
        loadDashboardData();
    }
    
    private void initializeTableModel() {
        tableModel = new DefaultTableModel(
            new Object[][] {},
            new String[] {"Mata Kuliah", "Semester", "Grade"}
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // tabel read-only
            }
        };
        jTable1.setModel(tableModel);
    }
    
    private void loadDashboardData() {
       
        if (!dashboardDAO.isStudentExists(studentId)) {
            JOptionPane.showMessageDialog(this, 
                "Data mahasiswa tidak ditemukan di database!", 
                "Warning", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        
        loadStudentData();
        loadCourseData();
    }
    
    private void loadStudentData() {
        student mhs = dashboardDAO.getStudentById(studentId);
        
        if (mhs != null) {
            // Set judul
            jLabel1.setText("Halo, " + mhs.getFullName());
            
            // Isi data ke form
            nimField.setText(mhs.getNim());
            namaField.setText(mhs.getFullName());
            
            // Format tanggal lahir
            if (mhs.getBirthDate() != null) {
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                birthDateField.setText(sdf.format(mhs.getBirthDate()));
            } else {
                birthDateField.setText("");
            }
            
            // Gender
            String gender = mhs.getGender();
            if ("M".equals(gender)) {
                genderField.setText("Laki-laki");
            } else if ("F".equals(gender)) {
                genderField.setText("Perempuan");
            } else {
                genderField.setText("-");
            }
            
            // Format tanggal masuk
            if (mhs.getAdmissionDate() != null) {
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                admissionDateField.setText(sdf.format(mhs.getAdmissionDate()));
            } else {
                admissionDateField.setText("");
            }
            
            // Entry year
            entryYearField.setText(String.valueOf(mhs.getEntryYear()));
            
            // Program studi
            prodiField.setText(mhs.getProgramName());
            
            // Hitung dan tampilkan IPK
            double gpa = dashboardDAO.calculateGPA(studentId);
            gpaField.setText(String.format("%.2f", gpa));
            
        } else {
            // Jika mahasiswa tidak ditemukan
            jLabel1.setText("Halo, Mahasiswa");
            clearForm();
            JOptionPane.showMessageDialog(this, 
                "Gagal memuat data mahasiswa", 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void loadCourseData() {
        List<Object[]> courses = dashboardDAO.getCoursesByStudent(studentId);
        
        
        tableModel.setRowCount(0);
        
        if (courses.isEmpty()) {
            System.out.println("Tidak ada mata kuliah untuk mahasiswa ini");
        } else {
            
            for (Object[] course : courses) {
                // Format semester
                int semester = (int) course[1];
                course[1] = semester > 0 ? "Semester " + semester : "-";
                
                tableModel.addRow(course);
            }
            
            
            int totalSKS = dashboardDAO.getTotalCredits(studentId);
            System.out.println("✓ Data berhasil dimuat:");
            System.out.println("  - Total mata kuliah: " + courses.size());
            System.out.println("  - Total SKS: " + totalSKS);
        }
    }
    
    private void clearForm() {
        nimField.setText("");
        namaField.setText("");
        birthDateField.setText("");
        genderField.setText("");
        admissionDateField.setText("");
        entryYearField.setText("");
        prodiField.setText("");
        gpaField.setText("");
    }
    
    
    public void refreshData() {
        loadDashboardData();
    }
    
   
    public void setStudentId(int studentId) {
        this.studentId = studentId;
        refreshData();
    }
    
    
    public int getStudentId() {
        return studentId;
    }

    
   


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        nimField = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        birthDateField = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        genderField = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        entryYearField = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        admissionDateField = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        namaField = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        prodiField = new javax.swing.JTextField();
        gpaField = new javax.swing.JTextField();

        nimField.setEditable(false);
        nimField.setPreferredSize(new java.awt.Dimension(200, 200));
        nimField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nimFieldActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel2.setText("Nilai Mahasiswa");

        birthDateField.setEditable(false);
        birthDateField.setPreferredSize(new java.awt.Dimension(200, 200));
        birthDateField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                birthDateFieldActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel4.setText("Data Mahasiswa :");

        genderField.setEditable(false);
        genderField.setPreferredSize(new java.awt.Dimension(200, 200));
        genderField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                genderFieldActionPerformed(evt);
            }
        });

        jLabel6.setText("Nim");

        entryYearField.setEditable(false);
        entryYearField.setPreferredSize(new java.awt.Dimension(200, 200));
        entryYearField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                entryYearFieldActionPerformed(evt);
            }
        });

        jLabel7.setText("Nama ");

        admissionDateField.setEditable(false);
        admissionDateField.setPreferredSize(new java.awt.Dimension(200, 200));
        admissionDateField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                admissionDateFieldActionPerformed(evt);
            }
        });

        jLabel8.setText("Tanggal Lahair");

        jLabel9.setText("Jenis Kelamin");

        jLabel10.setText("Tanggal Masuki");

        namaField.setEditable(false);
        namaField.setPreferredSize(new java.awt.Dimension(200, 200));
        namaField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                namaFieldActionPerformed(evt);
            }
        });

        jLabel11.setText("Semester");

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setText("Halo, {Nama Mahasiswa}");

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Mata Kuliah", "Semester", "Grade"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 520, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 588, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel14.setText("Prodi");

        jLabel15.setText("IPK");

        prodiField.setEditable(false);
        prodiField.setPreferredSize(new java.awt.Dimension(200, 200));
        prodiField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                prodiFieldActionPerformed(evt);
            }
        });

        gpaField.setEditable(false);
        gpaField.setPreferredSize(new java.awt.Dimension(200, 200));
        gpaField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                gpaFieldActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(jLabel8)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGap(34, 34, 34))
                                    .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel15, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(namaField, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
                                    .addComponent(admissionDateField, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 1, Short.MAX_VALUE)
                                    .addComponent(genderField, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 1, Short.MAX_VALUE)
                                    .addComponent(entryYearField, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 1, Short.MAX_VALUE)
                                    .addComponent(birthDateField, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 1, Short.MAX_VALUE)
                                    .addComponent(nimField, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                    .addComponent(prodiField, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 1, Short.MAX_VALUE)
                                    .addComponent(gpaField, javax.swing.GroupLayout.PREFERRED_SIZE, 1, Short.MAX_VALUE))))
                        .addGap(7, 7, 7)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, 495, Short.MAX_VALUE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addComponent(nimField, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(namaField, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(8, 8, 8)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(birthDateField, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(6, 6, 6)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9)
                            .addComponent(genderField, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(6, 6, 6)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel10)
                            .addComponent(entryYearField, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel11)
                            .addComponent(admissionDateField, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel14)
                            .addComponent(prodiField, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel15)
                            .addComponent(gpaField, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(304, Short.MAX_VALUE))))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void nimFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nimFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_nimFieldActionPerformed

    private void birthDateFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_birthDateFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_birthDateFieldActionPerformed

    private void genderFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_genderFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_genderFieldActionPerformed

    private void entryYearFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_entryYearFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_entryYearFieldActionPerformed

    private void admissionDateFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_admissionDateFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_admissionDateFieldActionPerformed

    private void namaFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_namaFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_namaFieldActionPerformed

    private void prodiFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_prodiFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_prodiFieldActionPerformed

    private void gpaFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_gpaFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_gpaFieldActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField admissionDateField;
    private javax.swing.JTextField birthDateField;
    private javax.swing.JTextField entryYearField;
    private javax.swing.JTextField genderField;
    private javax.swing.JTextField gpaField;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField namaField;
    private javax.swing.JTextField nimField;
    private javax.swing.JTextField prodiField;
    // End of variables declaration//GEN-END:variables
}
