import edu.princeton.cs.algs4.Picture;
import edu.princeton.cs.algs4.StdOut;

/**
 * Created by nando on 12/16/17.
 */
public class RemoveStreams {
    public static void main(String[] args) {
        if (args.length != 1) {
            StdOut.println("Usage:\njava ResizeDemo [image filename] [num cols to remove] [num rows to remove]");
            return;
        }

        Picture inputImg = new Picture(args[0]);

//        StdOut.printf("image is %d columns by %d rows\n", inputImg.width(), inputImg.height());
//        SeamCarver sc = new SeamCarver(inputImg);
//        int[] horizontalSeam = sc.findHorizontalSeam();
//        sc.removeHorizontalSeam(horizontalSeam);
//        StdOut.printf("image is %d columns by %d rows\n", sc.width(), sc.height());
//        sc.picture();

        SeamCarver sc = new SeamCarver(inputImg);
        int [] verticalSeam =  { 1, 1, 2 };
        sc.removeVerticalSeam(verticalSeam);
        sc.picture();
    }
}
