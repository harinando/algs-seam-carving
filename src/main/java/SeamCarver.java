import edu.princeton.cs.algs4.Picture;
import java.awt.Color;

public class SeamCarver {

    private Picture picture;
    private double [][] energy;
    private int[][] pictureInRGB;
    private int width;
    private int height;

    /*
     * create a seam carver object based on the given picture
     */
    public SeamCarver(final Picture picture) {
        this.picture = picture;
        this.width = picture.width();
        this.height = picture.height();

        energy = new double[width][height];
        pictureInRGB = new int[width][height];

        for (int col = 0; col < width; col++) {
            for (int row = 0; row < height; row++) {
                energy[col][row] = energy(col, row);
                pictureInRGB[col][row] = picture.get(col, row).getRGB();
            }
        }
    }

    /*
     * current picture
     *
     * Optimization:
     *      - Since Picture objects are relatively expensive; there is no need to create a new Picture object after removing a seam
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
        Picture resizedPicture = new Picture(width, height);
        for (int col = 0; col < width; col++)
            for (int row = 0; row < height; row++)
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
    public double energy(final int x, final int y) {
        return DualGradientEnergyCalculator.energy(picture, x, y);
    }

    /*
     * Sequence of indices for horizontal seam
     *
     * Optimization:
     *
     */
    public int[] findHorizontalSeam() {
        /*
         * TODO: Don't explicitly transpose the Picture until you need to do so.
         * For example, if you perform a sequence of 50 consecutive horizontal seam removals, you should need only two transposes (not 100).
         */
        double[][] transposedEnergy = MatrixUtility.transpose(energy);
        final SeamFinder streamFinder = new SeamFinder(transposedEnergy);
        return streamFinder.findVerticalStream();
    }

    /*
     * sequence of indices for vertical seam
     */
    public int[] findVerticalSeam() {
        final SeamFinder seamFinder = new SeamFinder(energy);
        return seamFinder.findVerticalStream();
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
    public void removeVerticalSeam(int[] seam) {
        removeSeam(seam);
    }

    private void removeSeam(final int[] seam) {
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
        width = MatrixUtility.getWidth(pictureInRGB);
        height = MatrixUtility.getHeight(pictureInRGB);
    }
}
