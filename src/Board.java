package src;

import javax.swing.JPanel;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

// Thêm "implements KeyListener" để Board có thể nghe được bàn phím
public class Board extends JPanel implements KeyListener {
    
    public Board() {
        
    }

    // Ba phương thức bắt buộc phải có khi dùng KeyListener
    @Override
    public void keyTyped(KeyEvent e) {
        // Để trống
    }

    @Override
    public void keyPressed(KeyEvent e) {
        // Đây là nơi xử lý khi nhấn phím (ví dụ: sang trái, sang phải)
        System.out.println("Bạn vừa nhấn một phím!");
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // Để trống
    }
}