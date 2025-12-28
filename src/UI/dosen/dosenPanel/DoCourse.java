package UI.dosen.dosenPanel;
import DAO.DoCourseDAO;
import Model.Course;
import javax.swing.table.DefaultTableModel;
import java.util.List;
import javax.swing.JOptionPane;

public class DoCourse extends javax.swing.JPanel {

    public DoCourse() {
        initComponents();
        setupViewOnly();
        loadDataWithDebug(); // Debug version
    }

    private void setupViewOnly() {
        DefaultTableModel model = new DefaultTableModel(new Object[][]{}, new String[]{"Mata Kuliah", "Semester", "Grade"}) {
            @Override public boolean isCellEditable(int row, int column) { return false; }
        };
        jTable1.setModel(model);
        jLabel2.setText("My Course");
    }

    private void loadDataWithDebug() {
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        model.setRowCount(0);
        
        try {
            // TEST 1: Cek DAO ada tidak
            DoCourseDAO dao = new DoCourseDAO();
            System.out.println("✅ DoCourseDAO created OK");
            
            // TEST 2: Cek koneksi DB
            System.out.println("🔄 Loading courses...");
            List<Course> courses = dao.getAllCourses();
            
            if (courses == null || courses.isEmpty()) {
                model.addRow(new Object[]{"Tidak ada data course", "", ""});
                JOptionPane.showMessageDialog(this, "Tidak ada data course di database", "Info", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            
            System.out.println("✅ Found " + courses.size() + " courses");
            
            // TEST 3: Populate table
            for (Course course : courses) {
                String mataKuliah = course.getCourseCode() + " - " + course.getName();
                model.addRow(new Object[]{
                    mataKuliah,
                    "Semester " + course.getSemesterSuggestion(),
                    ""
                });
                System.out.println("➕ Added: " + mataKuliah);
            }
            
            JOptionPane.showMessageDialog(this, courses.size() + " courses loaded!", "Success", JOptionPane.INFORMATION_MESSAGE);
            
        } catch (Exception e) {
            e.printStackTrace();
            model.addRow(new Object[]{"ERROR: " + e.getMessage(), "", ""});
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel2.setText("My Cource");

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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(96, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 596, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(108, 108, 108))
            .addGroup(layout.createSequentialGroup()
                .addGap(346, 346, 346)
                .addComponent(jLabel2)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(45, 45, 45)
                .addComponent(jLabel2)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 448, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(64, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
}
