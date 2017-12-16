import edu.princeton.cs.algs4.Picture;

/**
 * Created by nando on 12/15/17.
 */
public class HorizontalSeamRemover implements SeamRemover {

    private Picture picture;
    private double[][] energy;
    private int[] seam;
    private Picture resizedPicture;
    private double[][] resizedEnergy;

    private int width;
    private int height;

    public HorizontalSeamRemover(final Picture picture, final double[][] energy, final int[] seam) {
        this.picture = picture;
        this.energy = energy;
        this.seam = seam;
        this.width = picture.width();
        this.height = picture.height();

        if (seam == null) {
            throw new IllegalArgumentException();
        }

        if (seam.length != width) {
            throw new IllegalArgumentException();
        }

        if (height <= 1) {
            throw new IllegalArgumentException();
        }

        // initializing outputs
        resizedEnergy = new double[width][height-1];
        resizedPicture = new Picture(width, height-1);

        removeSeam();
    }

    public double[][] getEnergy() {
        return resizedEnergy;
    }

    public Picture getPicture() {
        return resizedPicture;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    private void removeSeam() {
        for (int col = 0; col < width; col++) {

            // copy upper half of the seam
            for (int row = 0; row < seam[col]; row++) {
                resizedEnergy[col][row] = energy[col][row];
                resizedPicture.set(col, row, picture.get(col, row));
            }

            // copy lower half of the seam
            for (int row = seam[col] + 1; row < height; row++) {
                resizedEnergy[col][row - 1] = energy[col][row];
                resizedPicture.set(col, row - 1, picture.get(col, row));
            }
        }

        height = resizedPicture.height();
        width = resizedPicture.width();
    }
}
