import edu.princeton.cs.algs4.Picture;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class SeamCarverTest {

    private static Picture inputImg = null;
    private static SeamCarver carver = null;

    private static String PICTURE = "src/main/resources/7x3.png";
    private int[] VALID_VERTICAL_SEAM = {0, 0, 0};
    private int[] INVALID_VERICAL_SEAM_1 =  { 4, 4, 2 };
    private int[] INVALID_VERICAL_SEAM_2 =  { -1, 4, 3 };
    private int[] INVALID_VERICAL_SEAM_3 =  { 100, 4, 3 };
    private int[] INVALID_VERICAL_SEAM_4 =  { 4, 4, 4, 4 };

    private static int[] VALID_HORIZONTAL_SEAM = {0, 0, 0, 0, 0, 0, 0};
    private int[] INVALID_HORIZONTAL_SEAM_1 = {0, 0, 2, 2, 3, 4, 5};
    private int[] INVALID_HORIZONTAL_SEAM_2 = {-1, 0, 1, 2, 3, 4, 5};
    private int[] INVALID_HORIZONTAL_SEAM_3 = {100, 0, 1, 2, 3, 4, 5};
    private int[] INVALID_HORIZONTAL_SEAM_4 =  { 4, 4, 4, 4 };

    @BeforeClass
    public static void setUp() throws Exception {
        inputImg = new Picture(PICTURE);
    }

    @Before
    public void setup() {
        carver = new SeamCarver(inputImg);
    }

    @Test
    public void testRemoveHorizontalSeam_succeeds() throws Exception {
        carver.removeHorizontalSeam(VALID_HORIZONTAL_SEAM);
        assertEquals("width should be the same", inputImg.width(), carver.width());
        assertEquals("height should have been reduced by 1", inputImg.height() - 1, carver.height());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRemoveHorizontalSeam_withNullArgument() throws Exception {
        carver.removeVerticalSeam(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRemoveHorizontalSeam_withInvalidSeam1() throws Exception {
        carver.removeHorizontalSeam(INVALID_HORIZONTAL_SEAM_1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRemoveHorizontalSeam_withInvalidSeam2() throws Exception {
        carver.removeHorizontalSeam(INVALID_HORIZONTAL_SEAM_2);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRemoveHorizontalSeam_withInvalidSeam3() throws Exception {
        carver.removeVerticalSeam(INVALID_HORIZONTAL_SEAM_3);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRemoveHorizontalSeam_withInvalidSeam4() throws Exception {
        carver.removeVerticalSeam(INVALID_HORIZONTAL_SEAM_4);
    }

    @Test
    public void testRemoveVerticalSeam_succeeds() throws Exception {
        carver.removeVerticalSeam(VALID_VERTICAL_SEAM);
        assertEquals("Width should be reduced by 1", inputImg.width()-1, carver.width());
        assertEquals("Heights should be the same", inputImg.height(), carver.height());

    }

    @Test(expected = IllegalArgumentException.class)
    public void testRemoveVerticalSeam_withNullArgument() throws Exception {
        carver.removeVerticalSeam(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRemoveVerticalSeam_withInvalidSeam1() throws Exception {
        carver.removeVerticalSeam(INVALID_VERICAL_SEAM_1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRemoveVerticalSeam_withInvalidSeam2() throws Exception {
        carver.removeVerticalSeam(INVALID_VERICAL_SEAM_2);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRemoveVerticalSeam_withInvalidSeam3() throws Exception {
        carver.removeVerticalSeam(INVALID_VERICAL_SEAM_3);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRemoveVerticalSeam_withInvalidSeam4() throws Exception {
        carver.removeVerticalSeam(INVALID_VERICAL_SEAM_4);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testEnergy_withInvalidArgument1() {
        carver.energy(-1, 4);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testEnergy_withInvalidArgument2() {
        carver.energy(100, 1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testEnergy_withInvalidArgument3() {
        carver.energy(1, -1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testEnergy_withInvalidArgument4() {
        carver.energy(1, 100);
    }


}