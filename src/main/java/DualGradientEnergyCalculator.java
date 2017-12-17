import edu.princeton.cs.algs4.Picture;

import java.awt.Color;

public class DualGradientEnergyCalculator {

    private static double borderEnergy = 1000L;

    public static double energy(final Picture picture, final int x, final int y) {
        if (x < 0 || x > picture.width() - 1 || y < 0 || y > picture.height() - 1)
            throw new IllegalArgumentException();

        if (x == 0 || x == picture.width() - 1 || y == 0 || y == picture.height() - 1)
            return borderEnergy;
        return Math.sqrt(squareGradX(picture, x, y) + squareGrady(picture, x, y));
    }

    public static double energy(final int[][] pictureInRGB, final int x, final int y) {
        final int width = MatrixUtility.getWidth(pictureInRGB);
        final int height = MatrixUtility.getHeight(pictureInRGB);
        if (x < 0 || x > width - 1 || y < 0 || y > height - 1)
            throw new IllegalArgumentException();

        if (x == 0 || x == width - 1 || y == 0 || y == height - 1)
            return borderEnergy;
        return Math.sqrt(squareGradX(pictureInRGB, x, y) + squareGrady(pictureInRGB, x, y));
    }

    private static double squareGradX(final Picture picture, final int x, final int y) {
        final Color colorLeft = picture.get(x-1, y);
        final Color colorRight = picture.get(x+1, y);
        int deltaR = colorLeft.getRed() - colorRight.getRed();
        int deltaG = colorLeft.getGreen() - colorRight.getGreen();
        int deltaB = colorLeft.getBlue() - colorRight.getBlue();
        return deltaR*deltaR + deltaG*deltaG + deltaB*deltaB;
    }

    private static double squareGradX(final int[][] pictureInRGB, final int x, final int y) {
        final Color colorLeft = new Color(pictureInRGB[x-1][y]);
        final Color colorRight = new Color(pictureInRGB[x+1][y]);
        int deltaR = colorLeft.getRed() - colorRight.getRed();
        int deltaG = colorLeft.getGreen() - colorRight.getGreen();
        int deltaB = colorLeft.getBlue() - colorRight.getBlue();
        return deltaR*deltaR + deltaG*deltaG + deltaB*deltaB;
    }

    private static double squareGrady(final Picture picture, final int x, final int y) {
        final Color colorUp = picture.get(x, y+1);
        final Color colordown = picture.get(x, y-1);
        int deltaR = colorUp.getRed() - colordown.getRed();
        int deltaG = colorUp.getGreen() - colordown.getGreen();
        int deltaB = colorUp.getBlue() - colordown.getBlue();
        return deltaR*deltaR + deltaG*deltaG + deltaB*deltaB;
    }

    private static double squareGrady(final int[][] pictureInRGB, final int x, final int y) {
        final Color colorUp = new Color(pictureInRGB[x][y+1]);
        final Color colordown = new Color(pictureInRGB[x][y-1]);
        int deltaR = colorUp.getRed() - colordown.getRed();
        int deltaG = colorUp.getGreen() - colordown.getGreen();
        int deltaB = colorUp.getBlue() - colordown.getBlue();
        return deltaR*deltaR + deltaG*deltaG + deltaB*deltaB;
    }
}
