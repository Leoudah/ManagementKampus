package UI;
import javax.swing.SwingUtilities;

public class ManagementKampus {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                ZLoginAll frame = new ZLoginAll(); // Gantilah YourJFrameClass dengan nama kelas Anda.
                frame.setDefaultCloseOperation(ZLoginAll.EXIT_ON_CLOSE);
                frame.setVisible(true);
            }
        });
    }
    
}
