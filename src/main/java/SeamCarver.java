import edu.princeton.cs.algs4.Picture;
import java.awt.Color;

public class SeamCarver {

    private Picture picture;
    private double [][] energy;
    private int[][] pictureInRGB;


    /*
     * create a seam carver object based on the given picture
     */
    public SeamCarver(final Picture picture) {

        if (picture == null)
            throw new IllegalArgumentException();

        this.picture = picture;
        final int width = picture.width();
        final int height = picture.height();

        energy = new double[width][height];
        pictureInRGB = new int[width][height];

        /*
         * Updating pictureInRGB needs to happen first because
         * energy() computation is based on pictureInRGB not picture
         */
        for (int col = 0; col < width; col++) {
            for (int row = 0; row < height; row++) {
                pictureInRGB[col][row] = picture.get(col, row).getRGB();
                energy[col][row] = DualGradientEnergyCalculator.energy(picture, col, row);
            }
        }
    }

    /*
     * current picture
     *
     * Optimization:
     *      - Since Picture objects are relatively expensive; there is no need to create a new
     *      Picture object after removing a seam
     *      — Instead:
     *           - Maintain the color information in a 2D array of integers.
     *           - Defer creating a Picture object until required to do (when the client calls picture())
     *
     * Caveat
     *      - picture is set to null when removeVerticalSeam or removeHorizontalSeam is called
     *
     *
     */
    public Picture picture() {
        Picture resizedPicture = new Picture(width(), height());
        for (int col = 0; col < width(); col++)
            for (int row = 0; row < height(); row++)
                resizedPicture.set(col, row, new Color(pictureInRGB[col][row]));
        return resizedPicture;
    }

    /*
     * width of current picture
     */
    public int width() {
        return MatrixUtility.getWidth(pictureInRGB);
    }

    /*
     * height of current picture
     */
    public int height() {
        return MatrixUtility.getHeight(pictureInRGB);
    }

    /*
     * @param x
     *    column of picture
     * @param y
     *    row of picture
     * @return
     *    energy of pixel at column x and row y
     */
    public double energy(final int col, final int row) {
        if (col < 0 || col >= width() || row < 0 || row >= height())
            throw new IllegalArgumentException();

        return energy[col][row];
    }

    /*
     * Sequence of indices for horizontal seam
     *
     * Optimization:
     *
     */
    public int[] findHorizontalSeam() {
        /*
         * Don't explicitly transpose the Picture until you need to do so.
         * For example, if you perform a sequence of 50 consecutive horizontal seam removals,
         * you should need only two transposes (not 100).
         */
        double[][] transposedEnergy = MatrixUtility.transpose(energy);
        final SeamFinder streamFinder = new SeamFinder(transposedEnergy);
        return streamFinder.findVerticalStream(transposedEnergy);
    }

    /*
     * sequence of indices for vertical seam
     */
    public int[] findVerticalSeam() {
        final SeamFinder seamFinder = new SeamFinder(energy);
        return seamFinder.findVerticalStream(energy);
    }

    /*
     * Remove horizontal seam from current picture
     */
    public void removeHorizontalSeam(int[] seam) {
        pictureInRGB = MatrixUtility.transpose(pictureInRGB);
        energy = MatrixUtility.transpose(energy);
        removeVerticalSeam(seam);
        pictureInRGB = MatrixUtility.transpose(pictureInRGB);
        energy = MatrixUtility.transpose(energy);
    }

    /*
     * remove vertical seam from current picture
     */
    public void removeVerticalSeam(final int[] seam) {
        if (seam == null)
            throw new IllegalArgumentException();

        if (width() <= 1)
            throw new IllegalArgumentException();

        if (height() != seam.length)
            throw new IllegalArgumentException();

        for (int i = 0; i < seam.length; i++) {
            if (seam[i] < 0 || seam[i] >= width())
                throw new IllegalArgumentException();

            if (i > 0 && Math.abs(seam[i] - seam[i-1]) > 1)
                throw new IllegalArgumentException();
        }

        double[][] resizedEnergy = new double[width()-1][height()];
        int[][] resizedPictureInRGB = new int[width()-1][height()];

        for (int row = 0; row < height(); row++) {
            // copy left half of the picture
            for (int col = 0; col < seam[row]; col++) {
                resizedEnergy[col][row] = energy[col][row];
                resizedPictureInRGB[col][row] = pictureInRGB[col][row];
            }

            // copy right half of the picture
            for (int col = seam[row] + 1; col < width(); col++) {
                resizedEnergy[col-1][row] = energy[col][row];
                resizedPictureInRGB[col-1][row] = pictureInRGB[col][row];
            }
        }
        pictureInRGB = resizedPictureInRGB;
        energy = resizedEnergy;

        /*
         * TODO: Only need to optimize around the seam
         */
        for (int row = 0; row < height(); row++) {
            for (int col = 0; col < width(); col++)
                energy[col][row] = DualGradientEnergyCalculator.energy(pictureInRGB, col, row);
        }
    }
}
