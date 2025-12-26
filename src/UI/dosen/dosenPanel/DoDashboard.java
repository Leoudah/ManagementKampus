/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package UI.dosen.dosenPanel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.table.DefaultTableModel;
import Database.koneksiDB;
import DAO.CourseDAO;
import UI.admin.AFrame;
import UI.mahasiswa.MhsFrame;
import UI.dosen.DoFrame;

import DAO.LectureDAO;

import Model.lecturer;
import Session.UserSession;

import javax.swing.JPasswordField;
import java.awt.TextField;
import javax.swing.*;


public class DoDashboard extends javax.swing.JPanel {

    public DoDashboard() {
        initComponents();
        loadData();
        loadCourses();
    }
    
//    private int lecturerId;
//    private int userId;
//    private String prodi; //Program Studi
//    private int nidn;
//    private String fullName;
//    private int nip;
//    private String gender;
//    private String title;
//    private String edukasiTertinggi;
//    private String edukasiHistory;
//    private String bidangKeahlian;
//    private String telepon;
//    private String email;
    
    private void loadData() {
        lecturer lec = UserSession.getLecturer();
        if (lec == null) {
            throw new IllegalStateException("Lecturer session is null");
        }
        
        prodi.setText(lec.getProdi());
        nidn.setText(String.valueOf(lec.getNidn()));
        nama.setText(lec.getFullName());
        nip.setText(String.valueOf(lec.getNip()));
        kelamin.setText(lec.getGender());
        keahlian.setText(lec.getBidangKeahlian());
        telepon.setText(lec.getTelepon());
        email.setText(lec.getEmail());
        jabatan.setText(lec.getTitle());
        email.setText(lec.getEmail());
        edukasi.setText(lec.getEdukasiTertinggi());

        prodi.setEditable(false);
        nidn.setEditable(false);
        nama.setEditable(false);
        nip.setEditable(false);
        kelamin.setEditable(false);
        keahlian.setEditable(false);
        telepon.setEditable(false);
        email.setEditable(false);
        jabatan.setEditable(false);
        edukasi.setEditable(false);
        
        jLabel1.setText("Halo, " + lec.getFullName());
    }
    private void loadCourses() {
    lecturer lec = UserSession.getLecturer();
    if (lec == null) return;

    try {
        // Buat instance koneksiDB
        koneksiDB db = new koneksiDB();
        Connection con = db.connect(); // pakai connect()

        String sql = "SELECT course_code, name, credits, semester_suggestion FROM course WHERE lecturer_id = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, lec.getLecturerId());
        ResultSet rs = ps.executeQuery();

        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Kode");
        model.addColumn("Mata Kuliah");
        model.addColumn("SKS");
        model.addColumn("Semester");

        while(rs.next()) {
            model.addRow(new Object[]{
                rs.getString("course_code"),
                rs.getString("name"),
                rs.getInt("credits"),
                rs.getInt("semester_suggestion")
            });
        }

        jTable1.setModel(model);

        // Tutup koneksi
        rs.close();
        ps.close();
        con.close();

    } catch (SQLException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "Gagal memuat daftar mata kuliah: " + e.getMessage());
    }
}

    

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel9 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jabatan = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        kelamin = new javax.swing.JTextField();
        nip = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        nama = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        prodi = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel8 = new javax.swing.JLabel();
        keahlian = new javax.swing.JTextField();
        nidn = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        telepon = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        email = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        edukasi = new javax.swing.JTextField();

        setPreferredSize(new java.awt.Dimension(800, 600));

        jLabel9.setText("Jenis Kelamin");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel2.setText("Daftar Mata Kuliah");

        jLabel10.setText("Prodi");

        jabatan.setEditable(false);
        jabatan.setPreferredSize(new java.awt.Dimension(200, 200));
        jabatan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jabatanActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel4.setText("Data Pegawai :");

        kelamin.setEditable(false);
        kelamin.setPreferredSize(new java.awt.Dimension(200, 200));
        kelamin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                kelaminActionPerformed(evt);
            }
        });

        nip.setEditable(false);
        nip.setPreferredSize(new java.awt.Dimension(200, 200));
        nip.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nipActionPerformed(evt);
            }
        });

        jLabel6.setText("NIP");

        nama.setEditable(false);
        nama.setPreferredSize(new java.awt.Dimension(200, 200));
        nama.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                namaActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setText("Halo, {Nama Dosen}");

        prodi.setEditable(false);
        prodi.setPreferredSize(new java.awt.Dimension(200, 200));
        prodi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                prodiActionPerformed(evt);
            }
        });

        jLabel12.setText("Bidang keahlian");

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

        keahlian.setEditable(false);
        keahlian.setPreferredSize(new java.awt.Dimension(200, 200));
        keahlian.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                keahlianActionPerformed(evt);
            }
        });

        nidn.setEditable(false);
        nidn.setPreferredSize(new java.awt.Dimension(200, 200));
        nidn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nidnActionPerformed(evt);
            }
        });

        jLabel13.setText("NIDN");

        jLabel15.setText("Telepon");

        telepon.setEditable(false);
        telepon.setPreferredSize(new java.awt.Dimension(200, 200));
        telepon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                teleponActionPerformed(evt);
            }
        });

        jLabel16.setText("Email");

        email.setEditable(false);
        email.setPreferredSize(new java.awt.Dimension(200, 200));
        email.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                emailActionPerformed(evt);
            }
        });

        jLabel17.setText("Edukasi Terakhir");

        edukasi.setEditable(false);
        edukasi.setPreferredSize(new java.awt.Dimension(200, 200));
        edukasi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                edukasiActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel1)
                    .addComponent(jLabel4)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel13, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 127, Short.MAX_VALUE)
                            .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, 127, Short.MAX_VALUE)
                            .addComponent(jLabel12, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel16, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 127, Short.MAX_VALUE)
                            .addComponent(jLabel17, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 127, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(keahlian, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(nidn, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(telepon, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(email, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(edukasi, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(prodi, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jabatan, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(nama, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(kelamin, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(nip, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 32, Short.MAX_VALUE)
                        .addComponent(jLabel2)
                        .addGap(297, 297, 297))
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane1))))
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
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(nip, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(nama, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(jabatan, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(8, 8, 8)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9)
                            .addComponent(kelamin, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(prodi, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel10))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel12)
                            .addComponent(keahlian, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel13)
                            .addComponent(nidn, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel15)
                            .addComponent(telepon, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel16)
                            .addComponent(email, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel17)
                            .addComponent(edukasi, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 439, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(38, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jabatanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jabatanActionPerformed

    }//GEN-LAST:event_jabatanActionPerformed

    private void kelaminActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_kelaminActionPerformed

    }//GEN-LAST:event_kelaminActionPerformed

    private void nipActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nipActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_nipActionPerformed

    private void namaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_namaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_namaActionPerformed

    private void prodiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_prodiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_prodiActionPerformed

    private void keahlianActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_keahlianActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_keahlianActionPerformed

    private void nidnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nidnActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_nidnActionPerformed

    private void teleponActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_teleponActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_teleponActionPerformed

    private void emailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_emailActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_emailActionPerformed

    private void edukasiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_edukasiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_edukasiActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField edukasi;
    private javax.swing.JTextField email;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jabatan;
    private javax.swing.JTextField keahlian;
    private javax.swing.JTextField kelamin;
    private javax.swing.JTextField nama;
    private javax.swing.JTextField nidn;
    private javax.swing.JTextField nip;
    private javax.swing.JTextField prodi;
    private javax.swing.JTextField telepon;
    // End of variables declaration//GEN-END:variables
}
