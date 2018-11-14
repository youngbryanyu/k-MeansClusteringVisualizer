public class DoNothingFilter implements PixelFilter{

    @Override
    public int[] filter(int[] pixels, int width, int height) {
        // don't change input array 
        return pixels;
    }
}
