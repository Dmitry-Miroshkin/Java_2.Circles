import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

// user input, update, render

public class GameCanvas extends JPanel {

    private final MainCircles gameController;
    private long lastFrameTime;
    static int count = 10; // счетчик дополнительных шаров

    GameCanvas(MainCircles gameController) {
        this.gameController = gameController;
        lastFrameTime = System.nanoTime();

        int delay = 2000;// время между сменой цвета фона
        ActionListener listener = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Background.setBG(GameCanvas.this);
            }
        };
        Timer timer = new Timer(delay, listener);
        timer.start();
        this.addMouseListener(new CustomListener());
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        long currentTime = System.nanoTime();
        float deltaTime = (currentTime - lastFrameTime) * 0.000000001f;
        lastFrameTime = currentTime;
        gameController.onDrawFrame(this, g, deltaTime);
        try {
            Thread.sleep(17);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        repaint();
    }

    int getLeft() {
        return 0;
    }

    int getRight() {
        return getWidth() - 1;
    }

    int getTop() {
        return 0;
    }

    int getBottom() {
        return getHeight() - 1;
    }

    static class CustomListener implements MouseListener {
        @Override
        public void mouseClicked(MouseEvent e) {
        }

        @Override
        public void mousePressed(MouseEvent e) {
        }

        public void mouseReleased(MouseEvent e) {
//            Background.setBG(GameCanvas.this); Смена цвета фона по клику мышки
            if (count < 20 && e.getButton() == MouseEvent.BUTTON1) {
                MainCircles.sprites[count] = new Ball(); // добавляем новый шар по клику левой кнопкой
                count++;
            }
            if (count > 0 && e.getButton() == MouseEvent.BUTTON3) {
                count--; // убираем шары по правой кнопке
            }
        }

        @Override
        public void mouseEntered(MouseEvent e) {
        }

        @Override
        public void mouseExited(MouseEvent e) {
        }
    }
}

