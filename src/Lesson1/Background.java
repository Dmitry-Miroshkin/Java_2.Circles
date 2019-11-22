package Lesson1;

import java.awt.*;

class Background {
    static void setBG(GameCanvas canvas) {
        canvas.setBackground(Color.getHSBColor((float) (Math.random() * 200f), (float) (Math.random() * 200f), (float) (Math.random() * 200f)));
    }
}
