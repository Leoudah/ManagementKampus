package UI.dosen.dosenPanel;

import DAO.DoDashboardDAO;
import Model.lecturer;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class DoDashboard extends javax.swing.JPanel {
    private int lecturerId;
    private DoDashboardDAO dashboardDAO;
    private DefaultTableModel tableModel;
    
    // Constructor dengan parameter lecturerId
    public DoDashboard(int lecturerId) {
        this.lecturerId = lecturerId;
        this.dashboardDAO = new DoDashboardDAO();
        
        initComponents();
        initializeTableModel();
        loadDashboardData();
    }
    
    
    private void initializeTableModel() {
        tableModel = new DefaultTableModel(
            new Object[][] {},
            new String[] {"Kode MK", "Mata Kuliah", "SKS", "Semester", "Kelas"}
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // tabel read-only
            }
        };
        jTable1.setModel(tableModel);
    }
    
    private void loadDashboardData() {
        // Cek dulu apakah dosen ada
        if (!dashboardDAO.isLecturerExists(lecturerId)) {
            JOptionPane.showMessageDialog(this, 
                "Data dosen tidak ditemukan di database!", 
                "Warning", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        // Load data dosen
        loadLecturerData();
        
        // Load mata kuliah
        loadCourseData();
    }
    
    private void loadLecturerData() {
        lecturer dosen = dashboardDAO.getLecturerById(lecturerId);
        
        if (dosen != null) {
            // Set judul
            jLabel1.setText("Halo, " + dosen.getFullName());
            
            // Isi data ke form sesuai dengan field yang ada
            // NIP
            nipField.setText(String.valueOf(dosen.getNip()));
            
            // Nama
            namadosenField.setText(dosen.getFullName());
            
            // NIDN
            nidnFiled.setText(String.valueOf(dosen.getNidn()));
            
            // Gender
            String gender = dosen.getGender();
            if ("M".equals(gender)) {
                genderField.setText("Laki-laki");
            } else if ("F".equals(gender)) {
                genderField.setText("Perempuan");
            } else {
                genderField.setText("-");
            }
            
            // Program studi
            prodi.setText(dosen.getProgramName());
            
            // Jabatan (position_title -> title)
            position_titleField.setText(dosen.getTitle() != null ? dosen.getTitle() : "-");
            
            // Pendidikan tertinggi
            highest_educationFeild.setText(dosen.getEdukasiTertinggi() != null ? dosen.getEdukasiTertinggi() : "-");
            
            // Bidang keahlian
            expertiseFeild.setText(dosen.getBidangKeahlian() != null ? dosen.getBidangKeahlian() : "-");
            
            // Telepon
            phoneFiled.setText(dosen.getTelepon() != null ? dosen.getTelepon() : "-");
            
            // Email (dari model, bisa kosong) - GANTI DARI email MENJADI emailField
            emailField.setText(dosen.getEmail() != null ? dosen.getEmail() : "");
            
        } else {
            // Jika dosen tidak ditemukan
            jLabel1.setText("Halo, Dosen");
            clearForm();
            JOptionPane.showMessageDialog(this, 
                "Gagal memuat data dosen", 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void loadCourseData() {
        List<Object[]> courses = dashboardDAO.getCoursesByLecturer(lecturerId);
        
        // Kosongkan tabel
        tableModel.setRowCount(0);
        
        if (courses.isEmpty()) {
            System.out.println("Tidak ada mata kuliah untuk dosen ini");
        } else {
            // Isi data ke tabel
            for (Object[] course : courses) {
                // Format semester
                int semester = (int) course[3];
                course[3] = semester > 0 ? "Semester " + semester : "-";
                
                tableModel.addRow(course);
            }
            
            // Tampilkan statistik di console
            int totalSKS = dashboardDAO.getTotalCredits(lecturerId);
            int totalCourses = dashboardDAO.getTotalCourses(lecturerId);
            System.out.println("✓ Data berhasil dimuat:");
            System.out.println("  - Total mata kuliah: " + totalCourses);
            System.out.println("  - Total SKS: " + totalSKS);
        }
    }
    
    private void clearForm() {
        nipField.setText("");
        namadosenField.setText("");
        nidnFiled.setText("");
        genderField.setText("");
        prodi.setText("");
        position_titleField.setText("");
        highest_educationFeild.setText("");
        expertiseFeild.setText("");
        phoneFiled.setText("");
        emailField.setText(""); // GANTI DARI email MENJADI emailField
    }
    
    // Method untuk refresh data
    public void refreshData() {
        loadDashboardData();
    }
    
    // Method untuk set lecturerId baru
    public void setLecturerId(int lecturerId) {
        this.lecturerId = lecturerId;
        refreshData();
    }
    
    // Getters
    public int getLecturerId() {
        return lecturerId;
    }
   
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    // KODE initComponents() DAN VARIABLES DECLARATION
    // DITEMPAKKAN OLEH NETBEANS DI SINI
    // </editor-fold>                         

    

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel9 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        nipField = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        expertise = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel8 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        phoneField = new javax.swing.JLabel();
        highest_education = new javax.swing.JLabel();
        highest_education1 = new javax.swing.JLabel();
        namadosenField = new javax.swing.JTextField();
        position_titleField = new javax.swing.JTextField();
        prodi = new javax.swing.JTextField();
        genderField = new javax.swing.JTextField();
        expertiseFeild = new javax.swing.JTextField();
        nidnFiled = new javax.swing.JTextField();
        phoneFiled = new javax.swing.JTextField();
        emailField = new javax.swing.JTextField();
        highest_educationFeild = new javax.swing.JTextField();

        setPreferredSize(new java.awt.Dimension(800, 600));

        jLabel9.setText("Jenis Kelamin");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel2.setText("Daftar Mata Kuliah");

        jLabel10.setText("Prodi");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel4.setText("Data Pegawai :");

        nipField.setEditable(false);
        nipField.setPreferredSize(new java.awt.Dimension(200, 200));
        nipField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nipFieldActionPerformed(evt);
            }
        });

        jLabel6.setText("NIP");

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setText("Halo, {Nama Dosen}");

        expertise.setText("Bidang keahlian");

        jLabel7.setText("Nama ");

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Mata Kuliah", "Semester", "Kelas"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        jLabel8.setText("Jabatan");

        jLabel13.setText("NIDN");

        phoneField.setText("Telepon");

        highest_education.setText("Email");

        highest_education1.setText("Pendidikan Terakhir");

        namadosenField.setEditable(false);
        namadosenField.setPreferredSize(new java.awt.Dimension(200, 200));
        namadosenField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                namadosenFieldActionPerformed(evt);
            }
        });

        position_titleField.setEditable(false);
        position_titleField.setPreferredSize(new java.awt.Dimension(200, 200));
        position_titleField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                position_titleFieldActionPerformed(evt);
            }
        });

        prodi.setEditable(false);
        prodi.setPreferredSize(new java.awt.Dimension(200, 200));
        prodi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                prodiActionPerformed(evt);
            }
        });

        genderField.setEditable(false);
        genderField.setPreferredSize(new java.awt.Dimension(200, 200));
        genderField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                genderFieldActionPerformed(evt);
            }
        });

        expertiseFeild.setEditable(false);
        expertiseFeild.setPreferredSize(new java.awt.Dimension(200, 200));
        expertiseFeild.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                expertiseFeildActionPerformed(evt);
            }
        });

        nidnFiled.setEditable(false);
        nidnFiled.setPreferredSize(new java.awt.Dimension(200, 200));
        nidnFiled.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nidnFiledActionPerformed(evt);
            }
        });

        phoneFiled.setEditable(false);
        phoneFiled.setPreferredSize(new java.awt.Dimension(200, 200));
        phoneFiled.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                phoneFiledActionPerformed(evt);
            }
        });

        emailField.setEditable(false);
        emailField.setPreferredSize(new java.awt.Dimension(200, 200));
        emailField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                emailFieldActionPerformed(evt);
            }
        });

        highest_educationFeild.setEditable(false);
        highest_educationFeild.setPreferredSize(new java.awt.Dimension(200, 200));
        highest_educationFeild.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                highest_educationFeildActionPerformed(evt);
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
                            .addComponent(jLabel1)
                            .addComponent(jLabel4))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(highest_education1, javax.swing.GroupLayout.PREFERRED_SIZE, 1, Short.MAX_VALUE)
                                .addComponent(jLabel13, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(expertise, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(highest_education, javax.swing.GroupLayout.PREFERRED_SIZE, 1, Short.MAX_VALUE)
                                .addComponent(phoneField, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 14, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(namadosenField, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(position_titleField, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(prodi, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(genderField, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(expertiseFeild, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(nidnFiled, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(phoneFiled, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(emailField, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(highest_educationFeild, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(nipField, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 81, Short.MAX_VALUE)
                        .addComponent(jLabel2)
                        .addGap(297, 297, 297))
                    .addComponent(jScrollPane1)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 37, Short.MAX_VALUE)
                        .addComponent(jLabel4))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel2)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(22, 22, 22)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel7)
                                    .addComponent(namadosenField, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel8)
                                    .addComponent(position_titleField, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel10)
                                    .addComponent(prodi, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel9)
                                    .addComponent(genderField, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(nipField, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel6)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(expertise)
                            .addComponent(expertiseFeild, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel13)
                            .addComponent(nidnFiled, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(phoneField)
                            .addComponent(phoneFiled, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(6, 6, 6)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(highest_education)
                            .addComponent(emailField, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(highest_education1)
                            .addComponent(highest_educationFeild, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 439, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(38, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void nipFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nipFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_nipFieldActionPerformed

    private void namadosenFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_namadosenFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_namadosenFieldActionPerformed

    private void position_titleFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_position_titleFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_position_titleFieldActionPerformed

    private void prodiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_prodiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_prodiActionPerformed

    private void genderFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_genderFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_genderFieldActionPerformed

    private void expertiseFeildActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_expertiseFeildActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_expertiseFeildActionPerformed

    private void nidnFiledActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nidnFiledActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_nidnFiledActionPerformed

    private void phoneFiledActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_phoneFiledActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_phoneFiledActionPerformed

    private void emailFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_emailFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_emailFieldActionPerformed

    private void highest_educationFeildActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_highest_educationFeildActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_highest_educationFeildActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField emailField;
    private javax.swing.JLabel expertise;
    private javax.swing.JTextField expertiseFeild;
    private javax.swing.JTextField genderField;
    private javax.swing.JLabel highest_education;
    private javax.swing.JLabel highest_education1;
    private javax.swing.JTextField highest_educationFeild;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField namadosenField;
    private javax.swing.JTextField nidnFiled;
    private javax.swing.JTextField nipField;
    private javax.swing.JLabel phoneField;
    private javax.swing.JTextField phoneFiled;
    private javax.swing.JTextField position_titleField;
    private javax.swing.JTextField prodi;
    // End of variables declaration//GEN-END:variables
}
