import edu.princeton.cs.algs4.Picture;

public class SeamCarver {

    private Picture picture;
    private double [][] energy;
    /*
     * create a seam carver object based on the given picture
     */
    public SeamCarver(final Picture picture) {
        this.picture = picture;

        energy = new double[width()][height()];

        for (int x = 0; x < width(); x++) {
            for (int y = 0; y < height(); y++) {
                energy[x][y] = energy(x, y);
            }
        }
    }

    /*
     * current picture
     */
    public Picture picture() {
        return picture;
    }

    /*
     * width of current picture
     */
    public int width() {
        return picture.width();
    }

    /*
     * height of current picture
     */
    public int height() {
        return picture.height();
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
     */
    public int[] findHorizontalSeam() {
        // Initialize transposed energy - Abstract transpose later.
        double[][] transposedEnergy = new double[height()][width()];
        for (int row = 0; row < height(); row++) {
            for (int col = 0; col < width(); col++) {
                transposedEnergy[row][col] = energy[col][row];
            }
        }
        final StreamCarverHelper streamFinder = new StreamCarverHelper(transposedEnergy, width(), height());
        return streamFinder.findVerticalStream();
    }

    /*
     * sequence of indices for vertical seam
     */
    public int[] findVerticalSeam() {
        final StreamCarverHelper streamFinder = new StreamCarverHelper(energy, height(), width());
        return streamFinder.findVerticalStream();
    }

    /*
     * Remove horizontal seam from current picture
     */
    public void removeHorizontalSeam(int[] seam) {
    }

    /*
     * remove vertical seam from current picture
     */
    public void removeVerticalSeam(int[] seam) {
    }
}
