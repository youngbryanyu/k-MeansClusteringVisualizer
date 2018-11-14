public class VerticalReflectFilter implements PixelFilter {
    @Override
    public int[] filter(int[] pixels, int width, int height) {
        short[] bwpixels = PixelLib.convertToShortGreyscale(pixels);
        short[][] img = PixelLib.convertTo2dArray(bwpixels, width, height);

        int lastRowIndex = img.length - 1;
        for (int r = 0; r < img.length; r++) {
            for (int c = 0; c < img[r].length; c++) {
                short temp = img[r][c];
                img[r][c] = img[lastRowIndex - r][c];
                img[lastRowIndex][c] = temp;
            }
        }
        PixelLib.fill1dArray(img, pixels);
        return pixels;
    }
}
