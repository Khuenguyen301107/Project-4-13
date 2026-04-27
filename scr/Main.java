import javax.swing.JFrame;

public class Main {
    public static void main(String[] args) {
        
        JFrame window = new JFrame();
        
        
        window.setTitle("Project 4-13 alpha 0.5.1");
        
        
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);

        
        GamePanel gamePanel = new GamePanel(3); 
        
        
        window.add(gamePanel);
        window.pack(); 

        window.setLocationRelativeTo(null); 
        window.setVisible(true); 

        
        gamePanel.startGameThread();
    }
}