import edu.princeton.cs.algs4.Picture;

import java.awt.*;

public class DualGradientEnergyCalculator {

    private static double BORDER_ENERGY = 1000L;

    public static double energy(final Picture picture, final int x, final int y) {
        if (x < 0 || x > picture.width() - 1 || y < 0 || y > picture.height() - 1)
            throw new IllegalArgumentException();

        if (x == 0 || x == picture.width() - 1 || y == 0 || y == picture.height() - 1)
            return BORDER_ENERGY;
        return Math.sqrt(squareGradX(picture, x, y) + squareGrady(picture, x, y));
    }

    private static double squareGradX(final Picture picture, final int x, final int y) {
        final Color colorLeft = picture.get(x-1, y);
        final Color colorRight = picture.get(x+1, y);
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
}
