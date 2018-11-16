public class DoNothingFilter implements PixelFilter {

    public int[] filter(int[] pixels, int width, int height) {
        return pixels;
    }

}