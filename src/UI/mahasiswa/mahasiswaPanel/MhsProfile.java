/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package UI.mahasiswa.mahasiswaPanel;

import DAO.StudentDAO;
import Model.student;
import Session.UserSession;  
import javax.swing.JOptionPane;
import java.text.SimpleDateFormat;
import java.awt.Color;
import javax.swing.ImageIcon;

public class MhsProfile extends javax.swing.JPanel {
    
    private StudentDAO studentDAO;
    private student currentStudent;
    private int currentUserId;
    private ImageIcon defaultPhoto;  
    private boolean isValidEmail(String email) {
    String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
    return email.matches(emailRegex);
}
    public MhsProfile() {
        
        if (!UserSession.isStudent()) {
            JOptionPane.showMessageDialog(null,
                "Anda tidak memiliki akses sebagai mahasiswa!",
                "Akses Ditolak",
                JOptionPane.ERROR_MESSAGE);
            
            initComponents();
            setDefaultValues();
            setViewMode();
            return;
        }
        
        
        Model.user loggedInUser = UserSession.getUser();
        this.currentUserId = loggedInUser.getUserId();  // ← INI YANG DINAMIS!
        
        System.out.println("DEBUG: Loading profile for user_id = " + currentUserId);
        
        this.studentDAO = new StudentDAO();
        initComponents();
        loadDataFromDatabase();
        setViewMode();
    }
    
    // Constructor untuk testing (optional)
    public MhsProfile(int userId) {
        this.currentUserId = userId;
        this.studentDAO = new StudentDAO();
        initComponents();
        loadDataFromDatabase();
        setViewMode();
    }
    
    private void loadDataFromDatabase() {
        System.out.println("DEBUG: Searching student with user_id = " + currentUserId);
        currentStudent = studentDAO.findByStudentId(currentUserId);
        
        if (currentStudent != null) {
            System.out.println("DEBUG: Student found! ID = " + currentStudent.getStudentId());
            // Format tanggal
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            
            // Set data ke field
            txtNIM.setText(currentStudent.getNim() != null ? currentStudent.getNim() : "-");
            txtNama.setText(currentStudent.getFullName() != null ? currentStudent.getFullName() : "-");
            
            // Format jenis kelamin
            String gender = currentStudent.getGender();
            if ("M".equals(gender)) {
                txtJenisKelamin.setText("Laki-laki");
            } else if ("F".equals(gender)) {
                txtJenisKelamin.setText("Perempuan");
            } else {
                txtJenisKelamin.setText(gender != null ? gender : "-");
            }
            
            // Tanggal lahir
            if (currentStudent.getBirthDate() != null) {
                txtTanggalLahir.setText(sdf.format(currentStudent.getBirthDate()));
            } else {
                txtTanggalLahir.setText("-");
            }
            
            // Kontak
            txtTelepon.setText(currentStudent.getPhone() != null ? currentStudent.getPhone() : "-");
            txtEmail.setText(currentStudent.getEmail() != null ? currentStudent.getEmail() : "-");
            txtAlamat.setText(currentStudent.getAddress() != null ? currentStudent.getAddress() : "-");
            
            // Asal sekolah
            txtAsalSekolah.setText(currentStudent.getPreviousSchool() != null ? currentStudent.getPreviousSchool() : "-");
            
            // Akademik
            if (currentStudent.getProgramName() != null) {
                txtProdi.setText(currentStudent.getProgramName());
            } else {
                txtProdi.setText("-");
            }
            
            if (currentStudent.getEntryYear() > 0) {
                txtAngkatan.setText(String.valueOf(currentStudent.getEntryYear()));
            } else {
                txtAngkatan.setText("-");
            }
            
            // Tanggal masuk
            if (currentStudent.getAdmissionDate() != null) {
                txtTanggalMasuk.setText(sdf.format(currentStudent.getAdmissionDate()));
            } else {
                txtTanggalMasuk.setText("-");
            }
            
            // Lama studi (hitung otomatis)
            if (currentStudent.getAdmissionDate() != null) {
                int years = calculateStudyDuration(currentStudent.getAdmissionDate());
                txtLamaStudi.setText(years + " Tahun");
            } else {
                txtLamaStudi.setText("-");
            }
            
            // Status
            if (currentStudent.getStatus() != null) {
                txtStatus.setText(currentStudent.getStatus());
            } else {
                txtStatus.setText("-");
            }
            
            // Set foto default (jika ada)
            setDefaultPhoto();
            
            // SIMPAN STUDENT KE SESSION (jika perlu untuk panel lain)
            UserSession.setStudent(currentStudent);
            
        } else {
            System.out.println("DEBUG: Student NOT found for user_id = " + currentUserId);
            JOptionPane.showMessageDialog(this,
                "Data mahasiswa tidak ditemukan untuk user ID: " + currentUserId + 
                "\nSilakan hubungi administrator.",
                "Error",
                JOptionPane.ERROR_MESSAGE);
            
            setDefaultValues();
        }
    }
    
    private void setDefaultPhoto() {
        try {
            // Coba load foto dari resources
            java.net.URL imgURL = getClass().getResource("/Images/default_profile.png");
            if (imgURL != null) {
                defaultPhoto = new ImageIcon(imgURL);
                lblFoto.setIcon(defaultPhoto);
            } else {
                // Jika tidak ada foto, set border saja
                lblFoto.setText("No Photo");
                lblFoto.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
            }
        } catch (Exception e) {
            System.err.println("Error loading profile photo: " + e.getMessage());
        }
    }
    
    private void setDefaultValues() {
        txtNIM.setText("-");
        txtNama.setText("-");
        txtJenisKelamin.setText("-");
        txtTanggalLahir.setText("-");
        txtTelepon.setText("-");
        txtEmail.setText("-");
        txtAlamat.setText("-");
        txtAsalSekolah.setText("-");
        txtProdi.setText("-");
        txtAngkatan.setText("-");
        txtTanggalMasuk.setText("-");
        txtLamaStudi.setText("-");
        txtStatus.setText("-");
        
        // Set foto default juga
        lblFoto.setText("No Data");
        lblFoto.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
    }
    
    private int calculateStudyDuration(java.util.Date admissionDate) {
        if (admissionDate == null) return 0;
        
        java.util.Date now = new java.util.Date();
        long diff = now.getTime() - admissionDate.getTime();
        long days = diff / (1000 * 60 * 60 * 24);
        int years = (int) (days / 365);
        
        // Pastikan minimal 1 tahun
        return Math.max(1, years);
    }
    
    private void setViewMode() {
        // Semua field tidak bisa diedit (view only)
        txtNIM.setEditable(false);
        txtNama.setEditable(false);
        txtJenisKelamin.setEditable(false);
        txtTanggalLahir.setEditable(false);
        txtTelepon.setEditable(false);
        txtEmail.setEditable(false);
        txtAlamat.setEditable(false);
        txtAsalSekolah.setEditable(false);
        txtProdi.setEditable(false);
        txtAngkatan.setEditable(false);
        txtTanggalMasuk.setEditable(false);
        txtLamaStudi.setEditable(false);
        txtStatus.setEditable(false);
        
        // Sembunyikan tombol edit/update/cancel (kalo hanya view)
        btnEdit.setVisible(true);
        btnUpdate.setVisible(true);
        btnCancel.setVisible(true);
        
        // Set background color untuk read-only
        Color readonlyColor = new Color(240, 240, 240);
        txtNIM.setBackground(readonlyColor);
        txtNama.setBackground(readonlyColor);
        txtJenisKelamin.setBackground(readonlyColor);
        txtTanggalLahir.setBackground(readonlyColor);
        txtTelepon.setBackground(readonlyColor);
        txtEmail.setBackground(readonlyColor);
        txtAlamat.setBackground(readonlyColor);
        txtAsalSekolah.setBackground(readonlyColor);
        txtProdi.setBackground(readonlyColor);
        txtAngkatan.setBackground(readonlyColor);
        txtTanggalMasuk.setBackground(readonlyColor);
        txtLamaStudi.setBackground(readonlyColor);
        txtStatus.setBackground(readonlyColor);
    }
    
    public void refreshData() {
        loadDataFromDatabase();
    }
    
    public void setUserId(int userId) {
        this.currentUserId = userId;
        refreshData();
    }
    
   
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        txtNIM = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        lblFoto = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        txtNama = new javax.swing.JTextField();
        txtJenisKelamin = new javax.swing.JTextField();
        txtTanggalLahir = new javax.swing.JTextField();
        txtTelepon = new javax.swing.JTextField();
        txtEmail = new javax.swing.JTextField();
        txtAlamat = new javax.swing.JTextField();
        txtProdi = new javax.swing.JTextField();
        txtAngkatan = new javax.swing.JTextField();
        txtTanggalMasuk = new javax.swing.JTextField();
        txtLamaStudi = new javax.swing.JTextField();
        txtAsalSekolah = new javax.swing.JTextField();
        txtStatus = new javax.swing.JTextField();
        btnEdit = new javax.swing.JButton();
        btnUpdate = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();
        jPasswordField1 = new javax.swing.JPasswordField();
        jPasswordField2 = new javax.swing.JPasswordField();
        jPanel5 = new javax.swing.JPanel();

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Identitas Mahasiswa"));
        jPanel2.setMaximumSize(new java.awt.Dimension(0, 0));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 110, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        txtNIM.setEditable(false);
        txtNIM.setPreferredSize(new java.awt.Dimension(200, 200));
        txtNIM.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNIMActionPerformed(evt);
            }
        });

        jLabel21.setText("Status");

        jLabel6.setText("NIM");

        jLabel10.setText("Angkatan");

        jLabel32.setText("Nomor Telepon :");

        jLabel34.setText("Asal Sekolah ");

        jLabel5.setText("Program Studi");

        jLabel17.setText("Email : ");

        lblFoto.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        lblFoto.setOpaque(true);
        lblFoto.setPreferredSize(new java.awt.Dimension(150, 200));

        jLabel31.setText("Tanggal Lahir :");

        jLabel28.setText("Jenis Kelamin");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel4.setText("Profile Mahasiswa");

        jLabel36.setText("Nama :  ");

        jLabel8.setText("Tanggal Masuk");

        jLabel22.setText("Lama Studi");

        jLabel30.setText("Alamat :");

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("Akademik"));
        jPanel4.setMaximumSize(new java.awt.Dimension(0, 0));

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 132, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder("Kontak"));
        jPanel6.setMaximumSize(new java.awt.Dimension(0, 0));

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 66, Short.MAX_VALUE)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        txtNama.setEditable(false);
        txtNama.setPreferredSize(new java.awt.Dimension(200, 200));
        txtNama.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNamaActionPerformed(evt);
            }
        });

        txtJenisKelamin.setEditable(false);
        txtJenisKelamin.setPreferredSize(new java.awt.Dimension(200, 200));
        txtJenisKelamin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtJenisKelaminActionPerformed(evt);
            }
        });

        txtTanggalLahir.setEditable(false);
        txtTanggalLahir.setPreferredSize(new java.awt.Dimension(200, 200));
        txtTanggalLahir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTanggalLahirActionPerformed(evt);
            }
        });

        txtTelepon.setEditable(false);
        txtTelepon.setPreferredSize(new java.awt.Dimension(200, 200));
        txtTelepon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTeleponActionPerformed(evt);
            }
        });

        txtEmail.setEditable(false);
        txtEmail.setPreferredSize(new java.awt.Dimension(200, 200));
        txtEmail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtEmailActionPerformed(evt);
            }
        });

        txtAlamat.setEditable(false);
        txtAlamat.setPreferredSize(new java.awt.Dimension(200, 200));
        txtAlamat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtAlamatActionPerformed(evt);
            }
        });

        txtProdi.setEditable(false);
        txtProdi.setPreferredSize(new java.awt.Dimension(200, 200));
        txtProdi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtProdiActionPerformed(evt);
            }
        });

        txtAngkatan.setEditable(false);
        txtAngkatan.setPreferredSize(new java.awt.Dimension(200, 200));
        txtAngkatan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtAngkatanActionPerformed(evt);
            }
        });

        txtTanggalMasuk.setEditable(false);
        txtTanggalMasuk.setPreferredSize(new java.awt.Dimension(200, 200));
        txtTanggalMasuk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTanggalMasukActionPerformed(evt);
            }
        });

        txtLamaStudi.setEditable(false);
        txtLamaStudi.setPreferredSize(new java.awt.Dimension(200, 200));
        txtLamaStudi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtLamaStudiActionPerformed(evt);
            }
        });

        txtAsalSekolah.setEditable(false);
        txtAsalSekolah.setPreferredSize(new java.awt.Dimension(200, 200));
        txtAsalSekolah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtAsalSekolahActionPerformed(evt);
            }
        });

        txtStatus.setEditable(false);
        txtStatus.setPreferredSize(new java.awt.Dimension(200, 200));
        txtStatus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtStatusActionPerformed(evt);
            }
        });

        btnEdit.setText("Edit Profile");
        btnEdit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnEditMouseClicked(evt);
            }
        });
        btnEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditActionPerformed(evt);
            }
        });

        btnUpdate.setText("Update Data");
        btnUpdate.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnUpdateMouseClicked(evt);
            }
        });

        btnCancel.setText("Cancel");
        btnCancel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnCancelMouseClicked(evt);
            }
        });

        jPasswordField1.setText("jPasswordField1");

        jPasswordField2.setText("jPasswordField2");

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder("Pasword"));
        jPanel5.setMaximumSize(new java.awt.Dimension(0, 0));

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 110, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel31)
                        .addGap(18, 18, 18)
                        .addComponent(txtTanggalLahir, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel36, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel28, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtNama, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtJenisKelamin, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtNIM, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel30, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel32, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtTelepon, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtAlamat, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtEmail, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(77, 77, 77)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPasswordField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                            .addComponent(btnEdit)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(btnUpdate)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 43, Short.MAX_VALUE)
                            .addComponent(btnCancel))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel10)
                                .addComponent(jLabel8)
                                .addComponent(jLabel22)
                                .addComponent(jLabel34)
                                .addComponent(jLabel21)
                                .addComponent(jLabel5))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(txtAngkatan, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtProdi, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtTanggalMasuk, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtLamaStudi, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtAsalSekolah, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addComponent(jPasswordField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(69, 69, 69))
            .addGroup(layout.createSequentialGroup()
                .addGap(232, 232, 232)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(73, 73, 73)
                        .addComponent(lblFoto, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                    .addComponent(jLabel4))
                .addGap(365, 365, 365))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel4)
                .addGap(27, 27, 27)
                .addComponent(lblFoto, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(txtNIM, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtNama, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel36))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel28)
                            .addComponent(txtJenisKelamin, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel31)
                            .addComponent(txtTanggalLahir, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel17)
                            .addComponent(txtTelepon, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(15, 15, 15)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel32)
                            .addComponent(txtAlamat, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel30)
                            .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(txtProdi, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel10)
                            .addComponent(txtAngkatan, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(txtTanggalMasuk, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel22)
                            .addComponent(txtLamaStudi, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel34)
                            .addComponent(txtAsalSekolah, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel21)
                            .addComponent(txtStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(5, 5, 5)
                        .addComponent(jPasswordField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPasswordField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnEdit)
                    .addComponent(btnUpdate)
                    .addComponent(btnCancel))
                .addContainerGap(55, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void txtNIMActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNIMActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNIMActionPerformed

    private void txtNamaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNamaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNamaActionPerformed

    private void txtJenisKelaminActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtJenisKelaminActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtJenisKelaminActionPerformed

    private void txtTanggalLahirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTanggalLahirActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTanggalLahirActionPerformed

    private void txtTeleponActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTeleponActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTeleponActionPerformed

    private void txtEmailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtEmailActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtEmailActionPerformed

    private void txtAlamatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtAlamatActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtAlamatActionPerformed

    private void txtProdiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtProdiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtProdiActionPerformed

    private void txtAngkatanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtAngkatanActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtAngkatanActionPerformed

    private void txtTanggalMasukActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTanggalMasukActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTanggalMasukActionPerformed

    private void txtLamaStudiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtLamaStudiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtLamaStudiActionPerformed

    private void txtAsalSekolahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtAsalSekolahActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtAsalSekolahActionPerformed

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
         txtTelepon.setEditable(true);
        txtEmail.setEditable(true);
        txtAlamat.setEditable(true);
        txtTelepon.setBackground(Color.WHITE);
        txtEmail.setBackground(Color.WHITE);
        btnEdit.setEnabled(false);
        btnUpdate.setEnabled(true);
        btnCancel.setEnabled(true);
        txtTelepon.requestFocus();
        
    }//GEN-LAST:event_btnEditActionPerformed

    private void txtStatusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtStatusActionPerformed
       
    }//GEN-LAST:event_txtStatusActionPerformed

    private void btnUpdateMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnUpdateMouseClicked
         try {
              if (!isValidEmail(txtEmail.getText())) {
            JOptionPane.showMessageDialog(this, "Format email tidak valid!");
            return;
        }
        currentStudent.setPhone(txtTelepon.getText());
        currentStudent.setEmail(txtEmail.getText());
        currentStudent.setAddress(txtAlamat.getText());
        boolean success = studentDAO.update(currentStudent);
        
        if (success) {
            JOptionPane.showMessageDialog(this, "Data berhasil diperbarui!");
            setViewMode(); // Kembali ke view mode
        } else {
            JOptionPane.showMessageDialog(this, "Gagal memperbarui data!");
        }} catch (Exception e) {
        JOptionPane.showMessageDialog(this, 
            "Error: " + e.getMessage(), 
            "Error", 
            JOptionPane.ERROR_MESSAGE);
    
         }
                          
    }//GEN-LAST:event_btnUpdateMouseClicked

    private void btnEditMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEditMouseClicked
       
    }//GEN-LAST:event_btnEditMouseClicked

    private void btnCancelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCancelMouseClicked
        loadDataFromDatabase();
        setViewMode();
        btnEdit.setEnabled(true);
        btnUpdate.setEnabled(false);
        btnCancel.setEnabled(false);
    
    }//GEN-LAST:event_btnCancelMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnEdit;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPasswordField jPasswordField1;
    private javax.swing.JPasswordField jPasswordField2;
    private javax.swing.JLabel lblFoto;
    private javax.swing.JTextField txtAlamat;
    private javax.swing.JTextField txtAngkatan;
    private javax.swing.JTextField txtAsalSekolah;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtJenisKelamin;
    private javax.swing.JTextField txtLamaStudi;
    private javax.swing.JTextField txtNIM;
    private javax.swing.JTextField txtNama;
    private javax.swing.JTextField txtProdi;
    private javax.swing.JTextField txtStatus;
    private javax.swing.JTextField txtTanggalLahir;
    private javax.swing.JTextField txtTanggalMasuk;
    private javax.swing.JTextField txtTelepon;
    // End of variables declaration//GEN-END:variables
}
