public class SeamCarverHelper {

    private double[][] energy;
    private int width;
    private int height;

    private double[][] distTo;
    private int[][] edgeTo;

    public SeamCarverHelper(final double[][] energy, final int height, final int width) {
        this.energy = energy;
        this.width = width;
        this.height = height;

        this.edgeTo = new int[width][height];
        this.distTo = new double[width][height];
        initialize();
    }

    /*
     * Relax
     */
    public int[] findVerticalStream() {

        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {

                // relax bottom-right if it is valid
                relax(col, row, col+1, row+1);

                // relax bottom
                relax(col, row, col, row+1);

                // relax bottom-left
                relax(col, row, col-1, row+1);
            }
        }

        return pathTo();
    }

    private int[] pathTo() {
        int minIndex = -1;
        double minVal = Double.MAX_VALUE;
        for (int col = 0; col < width; col++) {
            final double minValCandidate = distTo[col][height-1];
            if (minValCandidate < minVal) {
                minVal = minValCandidate;
                minIndex = getIndex(col, height-1);
            }
        }

        int nextIndex = minIndex;
        int[] path = new int[height];
        int posPtr = height - 1;

        while(nextIndex != -1) {
            int lastCol = getColumnIndex(nextIndex);
            int lastRow = getRowIndex(nextIndex);
            nextIndex = edgeTo[lastCol][lastRow];
            path[posPtr] = lastCol;
            posPtr--;
        }
        return path;
    }


    private void relax(final int fromCol, final int fromRow, final int toCol, final int toRow) {
        if (fromCol < 0 || fromRow < 0 || toCol < 0 || toRow < 0)
            return;

        if (fromCol > width - 1 || fromRow > height - 1 || toCol > width - 1 || toRow > height - 1)
            return;

        final double distToCandidate = distTo[fromCol][fromRow] + energy[toCol][toRow];
        if (distToCandidate < distTo[toCol][toRow]) {
            distTo[toCol][toRow] = distToCandidate;
            edgeTo[toCol][toRow] = getIndex(fromCol, fromRow);
        }
    }

    /*
     * Initialize distTo to Infinity
     */
    private void initialize() {
        // Initialize first row
        for (int col = 0; col < this.width; col++) {
            this.edgeTo[col][0] = -1;
            this.distTo[col][0] = energy[col][0];
        }

        // Initialize the rest of the rows
        for (int row = 1; row < this.height; row++) {
            for (int col = 0; col < width; col++) {
                this.edgeTo[col][row] = -1;
                this.distTo[col][row] = Double.MAX_VALUE;
            }
        }
    }

    /*
     * Get 1D index from pixels
     *
     */
    private int getIndex(final int col, final int row) {
        return width * row + col;
    }

    /*
     *  Get the column index from 1D index
     */
    private int getColumnIndex(final int pixelIndex) {
        return pixelIndex % width;
    }

    /*
     * Get the row index from 1D index
     */
    private int getRowIndex(final int pixelIndex) {
        return pixelIndex / width;
    }
}
