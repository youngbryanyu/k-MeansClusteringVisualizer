import javax.swing.*;

public class ConvolutionFilter implements PixelFilter {
    int[][] kernal = {{-1, 0, 1}, {-2, 0, 2}, {-1, 0, 1}};
    int[][] kernal2 = {{1, 2, 1}, {0, 0, 0}, {-1, -2, -1}};
    static int threshold;

    public ConvolutionFilter() {
        threshold = Integer.parseInt(JOptionPane.showInputDialog("enter a threshold"));
    }

    public int[] filter(int[] pixels, int width, int height) {
        short[] bwpixels = PixelLib.convertToShortGreyscale(pixels);
        short[][] im = PixelLib.convertTo2dArray(bwpixels, width, height);
        short[][] output = new short[height][width];
        short weight = calculateWeight(kernal);
        short weight2 = calculateWeight(kernal2);
        for (int r = 0; r < im.length - kernal.length + 1; r++) {
            for (int c = 0; c < im[0].length - kernal[0].length + 1; c++) {
                short out1 = 0;
                short out2 = 0;
                out1 += loopThroughKernal(r, c, kernal, im);
                out2 += loopThroughKernal(r, c, kernal2, im);
                if (weight != 0)
                    out1 /= weight;
                if (weight2 != 0)
                    out2 /= weight2;
                if (out1 < 0)
                    out1 = 0;
                if (out2 < 0)
                    out2 = 0;
                short finalOut = (short) Math.sqrt(out1 * out1 + out2 * out2);
                if (finalOut > threshold)
                    finalOut = 255;
                else
                    finalOut = 0;
                output[r + kernal.length / 2][c + kernal[0].length / 2] = finalOut;

            }
        }
        thin(output, im);
        PixelLib.fill1dArray(output, pixels);
        return pixels;
    }

    public static short calculateWeight(int[][] kernal) {
        int weight = 0;
        for (int i = 0; i < kernal.length; i++) {
            for (int j = 0; j < kernal[0].length; j++) {
                weight += kernal[i][j];
            }
        }
        return (short) weight;
    }

    public static void thin(short[][] output, short[][] im) {
        int[][] kernal = {{0, 0, 0}, {0, 1, 0}, {1, 1, 1}};
        int[][] kernal2 = {{0, 0, 0}, {1, 1, 0}, {0, 1, 0}};
        short weight1 = calculateWeight(kernal);
        short weight2 = calculateWeight(kernal2);
        short out1, out2;
        for (int x = 0; x < 4; x++) {
            out1 = 0;
            out2 = 0;
            for (int r = 0; r < im.length - kernal.length + 1; r++) {
                for (int c = 0; c < im[0].length - kernal[0].length + 1; c++) {
                    for (int i = r; i < r + kernal.length; i++) {
                        for (int j = c; j < c + kernal[0].length; j++) {
                            out1 += output[i][j] * kernal[i - r][j - c];
                            out2 += output[i][j] * kernal2[i - r][j - c];
                        }
                    }
                    if (weight1 != 0)
                        out1 /= weight1;
                    if (weight2 != 0)
                        out2 /= weight2;
                    if (out1 < 0)
                        out1 = 0;
                    if (out2 < 0)
                        out2 = 0;
                    short finalOut = (short) Math.sqrt(out1 * out1 + out2 * out2);
                    if (finalOut > threshold)
                        finalOut = 255;
                    else
                        finalOut = 0;
                    output[r + kernal.length / 2][c + kernal[0].length / 2] = finalOut;
                    rotateArray(kernal);
                    rotateArray(kernal2);
                }
            }
        }
    }

    public static void rotateArray(int[][] arr) {
        int[][] newArr = new int[arr[0].length][arr.length];
        for (int i = 0; i < arr[0].length; i++) {
            for (int j = arr.length - 1; j >= 0; j--) {
                newArr[i][j] = arr[j][i];
            }
        }
    }

    public static short loopThroughKernal(int r, int c, int[][] kernal, short[][] im) {
        short out = 0;
        for (int i = r; i < r + kernal.length; i++) {
            for (int j = c; j < c + kernal[0].length; j++) {
                out += im[i][j] * kernal[i - r][j - c];
            }
        }
        return out;
    }
}

