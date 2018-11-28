public class RemoveRedFilter implements PixelFilter {

    public int[] filter(int[] pixels, int width, int height) {
        PixelLib.ColorComponents2d vals = PixelLib.getColorComponents2d(pixels, width, height);

        for (int r = 0; r < height; r++) {
            for (int c = 0; c < width; c++) {
                vals.red[r][c] = 0;
            }
        }

        pixels = PixelLib.combineColorComponents(vals);
        return pixels;
    }
}
