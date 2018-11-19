package byog.Core;

public class Distance {
    public static int distance(int x1, int y1, int x2, int y2) {
        /** returns distance by block units how far away two coordinates are */
        return Math.abs((x1 - x2)) + Math.abs((y1 - y2));
    }

    public static int[] distance(int[][] x1, int[][] x2) {
        /** returns shortest distance between x1 coor and x2 coordinates
         *  in format {x1: x, x1: y, x2: x, x2: y}
         */
        int[] small = new int[]{x1[0][0], x1[0][1], x2[0][0], x2[0][1]};
        int smallestDistance = distance(small[0], small[1], small[2], small[3]);
        for (int i = 0; i < x1.length; i++) {
            for (int k = 0; k < x2.length; k++) {
                if (distance(x1[i][0], x1[i][1], x2[k][0], x2[k][1]) < smallestDistance) {
                    small[0] = x1[i][0];
                    small[1] = x1[i][1];
                    small[2] = x2[k][0];
                    small[3] = x2[k][1];
                    smallestDistance = distance(x1[i][0], x1[i][1], x2[k][0], x2[k][1]);
                }
            }
        }
        return small;
    }

    public static void deepCopy(int[][] x, int[][] y) {
        for (int i = 0; i < x.length; i++) {
            for (int k = 0; k < x[i].length; k++) {
                x[i][k] = y[i][k];
            }
        }
    }
}
