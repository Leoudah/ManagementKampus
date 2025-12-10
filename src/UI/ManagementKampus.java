package UI;
import javax.swing.SwingUtilities;

public class ManagementKampus {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                loginAll frame = new loginAll(); // Gantilah YourJFrameClass dengan nama kelas Anda.
                frame.setDefaultCloseOperation(loginAll.EXIT_ON_CLOSE);
                frame.setVisible(true);
            }
        });
    }
    
}
