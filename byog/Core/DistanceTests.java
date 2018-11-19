package byog.Core;

import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class DistanceTests {
    @Test
    public void testIntDistance() {
        int x = Distance.distance(2, 2, 0, 0);
        assertEquals(x, 4);
        int y = Distance.distance(2, 2, 1, 1);
        assertEquals(y, 2);
    }

    @Test
    public void testArrayDistance() {
        int[][] x = new int[][]{{0, 0}, {200, 100}, {20, 30}, {50, 50}};
        int[][] y = new int[][]{{2, 10}, {300, 39}, {1234123, 3}, {9, 10}};
        int[] answer = Distance.distance(x, y);
        assertArrayEquals(answer, new int[]{0, 0, 2, 10});

    }
}
