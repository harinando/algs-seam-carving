import edu.princeton.cs.algs4.Picture;

/**
 * Created by nando on 12/16/17.
 */
public class MatrixUtility {

    public static Picture transpose(final Picture picture) {
        final int width = picture.width();
        final int height = picture.height();
        final Picture transposedPicture = new Picture(height, width);
        for (int col = 0; col < width; col++)
            for (int row = 0; row < height; row++)
                transposedPicture.set(row, col, picture.get(col, row));
        return transposedPicture;
    }

    public static double[][] transpose(final double[][] array) {
        final int width = array.length;
        final int height = array[0].length;
        double[][] transposedArray = new double[height][width];
        for (int row = 0; row < height; row++)
            for (int col = 0; col < width; col++)
                transposedArray[row][col] = array[col][row];
        return transposedArray;
    }

    public static int[][] transpose(final int[][] array) {
        final int width = array.length;
        final int height = array[0].length;
        int[][] transposedArray = new int[height][width];
        for (int row = 0; row < height; row++)
            for (int col = 0; col < width; col++)
                transposedArray[row][col] = array[col][row];
        return transposedArray;
    }
}
