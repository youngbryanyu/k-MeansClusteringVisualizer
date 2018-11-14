import processing.core.PApplet;

public class AddCircleFilter implements DrawableFilter {

    @Override
    public void drawingFilter(PApplet window) {
        window.fill(255, 0, 0, 127);
        window.ellipse(window.width / 2, window.height / 2, 100, 100);
    }
}
