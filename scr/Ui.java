import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.*;

public class Ui extends JPanel {
    private GamePanel gamePanel;
    private int score = 0;
    private int winScale;
    private int[][] nextShape;
    private int nextColorID;
    private int nextSpecialIndex;
    
    // --- BIẾN MỚI: Hình nền bên phải ---
    private BufferedImage sidebarBg;

    public void setNextShapeData(int[][] shape, int colorID, int specialIndex) {
        this.nextShape = shape;
        this.nextColorID = colorID;
        this.nextSpecialIndex = specialIndex;
    }

    public Ui(int winScale) {
        this.winScale = winScale;
        
        // --- LOAD HÌNH NỀN BIỂN XANH (HÌNH SỐ 3) ---
        try {
            sidebarBg = ImageIO.read(new File("resources/textures/backgrounds/bg_sidebar.png"));
        } catch (Exception e) { 
            System.out.println("Sidebar BG Error: " + e.getMessage()); 
        }

        int gameWidth = GamePanel.cols * GamePanel.brickPixelHitBox * winScale; 
        int sidebarWidth = 8 * GamePanel.brickPixelHitBox * winScale;
        int totalHeight = GamePanel.rows * GamePanel.brickPixelHitBox * winScale;

        this.setPreferredSize(new Dimension(gameWidth + sidebarWidth, totalHeight));
        this.setLayout(new BorderLayout());
        this.setBackground(Color.DARK_GRAY);

        gamePanel = new GamePanel(winScale, this);
        this.add(gamePanel, BorderLayout.WEST);
    }

    public void updateScore(int points) {
        this.score += points;
    }

    public void resetScore () {
        this.score = 0;
    }

    // --- HÀM TRẢ VỀ ĐIỂM SỐ (Dùng cho Gameplay History) ---
    public int getScore() {
        return this.score;
    }

    public void setNextShape(int[][] shape) {
        this.nextShape = shape;
    }

    private Color getSimpleColor(int id) {
        switch(id) {
            case 1: return Color.MAGENTA;
            case 2: return Color.RED;
            case 3: return Color.ORANGE;
            case 4: return Color.YELLOW;
            case 5: return Color.GREEN;
            case 6: return Color.CYAN;
            default: return Color.WHITE;
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        // Vị trí bắt đầu của sidebar
        int sidebarX = gamePanel.getWidth();
        int sidebarWidth = getWidth() - sidebarX;

        // --- 1. VẼ HÌNH NỀN BIỂN XANH THAY CHO MÀU XÁM ---
        if (sidebarBg != null) {
            g2.drawImage(sidebarBg, sidebarX, 0, sidebarWidth, getHeight(), null);
        } else {
            // Nếu không load được ảnh thì dùng màu tối dự phòng
            g2.setColor(new Color(30, 30, 30));
            g2.fillRect(sidebarX, 0, sidebarWidth, getHeight());
        }

        //---[UI text styling stuff - Vẽ đè lên trên nền]---
        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Arial", Font.BOLD, 10 * winScale));
        
        int textX = sidebarX + (20 * winScale / 2);

        g2.drawString("SCORE", textX, 50 * winScale / 2);
        g2.drawString(String.format("%06d", score), textX, 80 * winScale / 2);

        g2.drawString("NEXT", textX, 150 * winScale / 2);

        // Draw the next piece preview
        if (nextShape != null) {
            int nextBlockCount = 0;
            for (int r = nextShape.length - 1; r >= 0; r--) {
                for (int c = 0; c < nextShape[r].length; c++) {
                    if (nextShape[r][c] == 1) {
                        int previewX = textX + (c * gamePanel.brickPixelHitBox * winScale);
                        int previewY = (gamePanel.previewNextPiecePositionY * winScale / 2) + (r * gamePanel.brickPixelHitBox * winScale);

                        if (nextBlockCount == nextSpecialIndex) {
                            g2.setColor(Color.DARK_GRAY);
                        } else {
                            g2.setColor(getSimpleColor(nextColorID));
                        }

                        g2.fillRect(previewX, previewY, gamePanel.brickPixelHitBox * winScale - 1, gamePanel.brickPixelHitBox * winScale - 1);
                        nextBlockCount++;
                    }
                }
            }
        }
    }
}