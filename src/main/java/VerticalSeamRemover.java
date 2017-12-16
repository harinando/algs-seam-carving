import edu.princeton.cs.algs4.Picture;

public class VerticalSeamRemover implements SeamRemover {

    private Picture picture;
    private double[][] energy;
    private int[] seam;

    private final double[][] resizedEnergy;
    private final Picture resizedPicture;

    private int width;
    private int height;

    public VerticalSeamRemover(final Picture picture, final double[][] energy, final int[] seam) {
        this.picture = picture;
        this.energy = energy;
        this.seam = seam;
        this.height = picture.height();
        this.width = picture.width();

        if (seam == null) {
            throw new IllegalArgumentException();
        }

        if (seam.length != height) {
            throw new IllegalArgumentException();
        }

        if (width <= 1) {
            throw new IllegalArgumentException();
        }

        // initializing outputs
        resizedEnergy = new double[width-1][height];
        resizedPicture = new Picture(width-1, height);

        removeSeam();
    }

    public double[][] getEnergy() {
        return resizedEnergy;
    }

    public int getHeight() {
        return resizedPicture.width();
    }

    public int getWidth() {
        return resizedPicture.height();
    }

    public Picture getPicture() {
        return resizedPicture;
    }


    private void removeSeam() {
        /*
         * Reuse the energy array and shift
         * array elements to plug
         * the holes left from the seam that was just removed.
         */
        for (int row = 0; row < height; row++) {

            // copy left half of the picture
            for (int col = 0; col < seam[row]; col++) {
                resizedEnergy[col][row] = energy[col][row];
                resizedPicture.set(col, row, picture.get(col, row));
            }

            // copy right half of the picture
            for (int col = seam[row] + 1; col < width; col++) {
                resizedEnergy[col-1][row] = energy[col][row];
                resizedPicture.set(col-1, row, picture.get(col, row));
            }
        }
        height = resizedPicture.height();
        width = resizedPicture.width();
    }
}
