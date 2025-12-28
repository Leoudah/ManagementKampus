package UI.admin.adminPanel;

import DAO.SemesterDAO;
import Model.Semester;

import javax.swing.table.DefaultTableModel;
import javax.swing.JOptionPane;

import java.util.List;
import java.sql.Date;

public class AManageSemester extends javax.swing.JPanel {

    private DefaultTableModel tableModel;
    private SemesterDAO semesterDAO = new SemesterDAO();

    
    public AManageSemester() {
        initComponents();
        initTable();
        initForm();
        loadTable();
    }
    
    private void initTable() {
        tableModel = new DefaultTableModel(
            new Object[]{"ID", "Tahun Ajaran", "Term", "Mulai", "Selesai", "Status"}, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        TabelFakultas.setModel(tableModel);
    }
    
    private void initForm() {
        spYear.setModel(new javax.swing.SpinnerNumberModel(2024, 2000, 2100, 1));

        cbTerm.setModel(new javax.swing.DefaultComboBoxModel<>(
            new String[]{"GANJIL", "GENAP"}
        ));

        spStartDate.setModel(new javax.swing.SpinnerDateModel());
        spStartDate.setEditor(
            new javax.swing.JSpinner.DateEditor(spStartDate, "yyyy-MM-dd")
        );

        spEndDate.setModel(new javax.swing.SpinnerDateModel());
        spEndDate.setEditor(
            new javax.swing.JSpinner.DateEditor(spEndDate, "yyyy-MM-dd")
        );
    }
    
    private void loadTable() {
        tableModel.setRowCount(0);

        List<Semester> list = semesterDAO.findAll();

        for (Semester s : list) {
            String tahunAjaran = s.getYear() + "/" + (s.getYear() + 1);

            tableModel.addRow(new Object[]{
                s.getSemesterId(),
                tahunAjaran,
                s.getTerm(),
                s.getStartDate(),
                s.getEndDate(),
                s.getStatus()
            });
        }
    }





    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel5 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        id = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        TabelFakultas = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        btnSetService = new javax.swing.JButton();
        btnCreate = new javax.swing.JButton();
        Delete = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        cbTerm = new javax.swing.JComboBox<>();
        spEndDate = new javax.swing.JSpinner();
        spYear = new javax.swing.JSpinner();
        spStartDate = new javax.swing.JSpinner();
        jLabel6 = new javax.swing.JLabel();

        jLabel5.setText("ID Yang dipilih");

        jLabel3.setText("Term");

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        TabelFakultas.setModel(new javax.swing.table.DefaultTableModel(
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
        TabelFakultas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TabelFakultasMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(TabelFakultas);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 608, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 589, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jLabel2.setText("Tahun Awal Ajaran");

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setText("Semester");

        btnSetService.setBackground(new java.awt.Color(255, 255, 102));
        btnSetService.setText("Edit");
        btnSetService.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSetServiceActionPerformed(evt);
            }
        });

        btnCreate.setBackground(new java.awt.Color(102, 255, 102));
        btnCreate.setText("Tambah");
        btnCreate.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnCreateMouseClicked(evt);
            }
        });
        btnCreate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCreateActionPerformed(evt);
            }
        });

        Delete.setBackground(new java.awt.Color(255, 102, 102));
        Delete.setText("Delete");
        Delete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DeleteActionPerformed(evt);
            }
        });

        jLabel4.setText("Tanggal Selesai");

        cbTerm.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        spEndDate.setModel(new javax.swing.SpinnerDateModel());
        spEndDate.setEditor(new javax.swing.JSpinner.DateEditor(spEndDate, "dd-MMM-yyyy"));

        spYear.setModel(new javax.swing.SpinnerNumberModel(2000, 2000, null, 1));

        spStartDate.setModel(new javax.swing.SpinnerDateModel());
        spStartDate.setEditor(new javax.swing.JSpinner.DateEditor(spStartDate, "dd-MMM-yyyy"));

        jLabel6.setText("Tanggal Mulai");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(id, javax.swing.GroupLayout.DEFAULT_SIZE, 160, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(Delete))
                            .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(9, 9, 9))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(76, 76, 76)
                                .addComponent(jLabel1))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addGap(115, 115, 115)
                                .addComponent(jLabel3))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(spYear, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(cbTerm, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel6)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(spStartDate, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(21, 21, 21)))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(spEndDate, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(btnCreate)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnSetService)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(spYear, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbTerm, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(spEndDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(spStartDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSetService)
                    .addComponent(btnCreate))
                .addGap(138, 138, 138)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Delete)
                    .addComponent(id, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(48, 48, 48))
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void TabelFakultasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TabelFakultasMouseClicked

    }//GEN-LAST:event_TabelFakultasMouseClicked

    private void btnSetServiceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSetServiceActionPerformed
        int row = TabelFakultas.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this,
                    "Pilih semester terlebih dahulu",
                    "Peringatan",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        int semesterId = Integer.parseInt(
            tableModel.getValueAt(row, 0).toString()
        );

        try {
            semesterDAO.setActive(semesterId);

            JOptionPane.showMessageDialog(this,
                    "Semester berhasil diaktifkan",
                    "Sukses",
                    JOptionPane.INFORMATION_MESSAGE);

            loadTable();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnSetServiceActionPerformed

    private void btnCreateMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCreateMouseClicked
        try {
            int year = (Integer) spYear.getValue();
            String term = (String) cbTerm.getSelectedItem();
            Date startDate = new Date(((java.util.Date) spStartDate.getValue()).getTime());
            Date endDate = new Date(((java.util.Date) spEndDate.getValue()).getTime());

            if (endDate.before(startDate)) {
                throw new RuntimeException("Tanggal selesai tidak boleh sebelum tanggal mulai");
            }

            Semester s = new Semester();
            s.setYear(year);
            s.setTerm(term);
            s.setStartDate(startDate);
            s.setEndDate(endDate);

            semesterDAO.create(s);

            JOptionPane.showMessageDialog(this,
                    "Semester berhasil dibuat",
                    "Sukses",
                    JOptionPane.INFORMATION_MESSAGE);

            loadTable();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnCreateMouseClicked

    private void btnCreateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCreateActionPerformed

    }//GEN-LAST:event_btnCreateActionPerformed

    private void DeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DeleteActionPerformed
        int row = TabelFakultas.getSelectedRow();
        if (row == -1) return;

        String status = tableModel.getValueAt(row, 5).toString();
        if ("ACTIVE".equalsIgnoreCase(status)) {
            JOptionPane.showMessageDialog(this,
                    "Tidak boleh menghapus semester aktif",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
    }//GEN-LAST:event_DeleteActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Delete;
    private javax.swing.JTable TabelFakultas;
    private javax.swing.JButton btnCreate;
    private javax.swing.JButton btnSetService;
    private javax.swing.JComboBox<String> cbTerm;
    private javax.swing.JTextField id;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSpinner spEndDate;
    private javax.swing.JSpinner spStartDate;
    private javax.swing.JSpinner spYear;
    // End of variables declaration//GEN-END:variables
}
