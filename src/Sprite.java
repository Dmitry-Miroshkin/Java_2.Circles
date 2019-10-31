import java.awt.*;

class Sprite {
    float x;
    float y;
    float halfWidth;
    float halfHeight;

    float getLeft() {
        return x - halfWidth;
    }
    void setLeft(float left) {
        x = left + halfWidth;
    }
    float getRight() {
        return x + halfWidth;
    }
    void setRight(float right) {
        x = right - halfWidth;
    }
    float getTop() {
        return y - halfHeight;
    }
    void setTop(float top) {
        y = top + halfHeight;
    }
    float getBottom() {
        return y + halfHeight;
    }
    void setBottom(float bottom) {
        y = bottom - halfHeight;
    }
    float getWidth() {
        return 2f * halfWidth;
    }
    float getHeight() {
        return 2f * halfHeight;
    }

    void update(GameCanvas canvas, float deltaTime) {}
    void render(GameCanvas canvas, Graphics g) {}

}
