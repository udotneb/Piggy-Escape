package byog.Core;

import byog.TileEngine.Tileset;

public class Board {
    /**
     * old rectangles top(X,Y), bot(X,Y), left(X,Y), right(X,Y) coordinates
     */
    private static int[][] oldSides;
    /**
     * new rectangles top(X,Y), bot(X,Y),left(X,Y), right(X,Y) coordinates
     */
    private static int[][] newSides;

    /**
     * creates everything
     */
    public static void create() {
        oldSides = new int[][]{{0, 0}, {0, 0}, {0, 0}, {0, 0}};
        newSides = new int[4][2];
        int numRectangles = Game.RANDOM.nextInt(10) + 15;
        for (int i = 0; i < numRectangles; i++) {
            randRectangle();
            createPath();
        }
        Board.flowerBorders();
        Board.flowerToFloor();
    }

    /**
     * Game.RANDOM RECTANGLE CREATION
     */

    public static void randRectangle() {
        //Tries 300 times to create a random rectangle, otherwise returns
        int tries = 0;
        int width = Game.RANDOM.nextInt(8) + 3;
        int height = Game.RANDOM.nextInt(8) + 3;
        int xLocation = Game.RANDOM.nextInt(Game.WIDTH - width);
        int yLocation = Game.RANDOM.nextInt(Game.HEIGHT - height);
        while (!locationCheck(xLocation, yLocation, width, height) && tries < 300) {
            xLocation = Game.RANDOM.nextInt(Game.WIDTH - width);
            yLocation = Game.RANDOM.nextInt(Game.HEIGHT - height);
            tries += 1;
        }
        drawRect(xLocation, yLocation, width, height);
    }

    private static boolean locationCheck(int x, int y, int w, int h) {
        // checks to see if desired rectangle has been used and makes
        for (int i = 0; i < w; i++) {
            for (int k = 0; k < h; k++) {
                //checks to see if array index error
                if (x + i >= Game.WIDTH || y + k >= Game.HEIGHT) {
                    return false;
                }
                //checks to see if a wall has been placed there
                if (Game.world[x + i][y + k].equals(Tileset.WALL)
                        || Game.world[x + i][y + k] == Tileset.FLOOR) {
                    return false;
                }
            }
        }
        return true;
    }

    public static void drawRect(int x, int y, int w, int h) {
        // draws a rectangle
        for (int i = 0; i < w; i++) {
            for (int k = 0; k < h; k++) {
                if (Game.world[x + i][y + k] != Tileset.FLOWER) {
                    Game.world[x + i][y + k] = Tileset.FLOOR;
                }
            }
        }
        outlineRect(x, y, w, h);
    }

    private static void outlineRect(int x, int y, int w, int h) {
        //adds walls to the outside of the rectnalge + updates sides coordinates
        for (int i = 0; i <= w; i++) {
            //sets the up down center node
            //sets bottom border
            if (Game.world[x + i][y] != Tileset.FLOWER) {
                Game.world[x + i][y] = Tileset.WALL;
            }
            //sets top border
            if (Game.world[x + i][y + h] != Tileset.FLOWER) {
                Game.world[x + i][y + h] = Tileset.WALL;
            }
            if (i == w / 2) {
                newSides[1][0] = x + i; //bottom x
                newSides[1][1] = y; //bottom y
                newSides[0][0] = x + i; //top x
                newSides[0][1] = y + h; //top y
            }
        }
        for (int i = 0; i < h; i++) {
            //sets the left right center node
            //sets left border
            if (Game.world[x][y + i] != Tileset.FLOWER) {
                Game.world[x][y + i] = Tileset.WALL;
            }
            //sets right border
            if (Game.world[x + w][y + i] != Tileset.FLOWER) {
                Game.world[x + w][y + i] = Tileset.WALL;
            }
            if (i == h / 2) {
                newSides[2][0] = x; //left x
                newSides[2][1] = y + i; //left y
                newSides[3][0] = x + w; //right x
                newSides[3][1] = y + i; //right y
            }
        }
    }


    /**
     * PATHING FUNCTIONS IN BETWEEN RECTANLGES
     */
    public static void createPath() {
        //creates a path between last rectangle and new rectangle
        if (oldSides[0][0] == 0 && oldSides[1][0] == 0) {
            //if old hasn't been initialized, skip creating a new path
            Distance.deepCopy(oldSides, newSides);
            return;
        } else {
            int[] pathCoor = Distance.distance(oldSides, newSides);
            drawPath(pathCoor[0], pathCoor[1], pathCoor[2], pathCoor[3]);
        }
        //reassign new to old
        Distance.deepCopy(oldSides, newSides);
    }

    private static void drawPath(int x1, int y1, int x2, int y2) {
        /**
         * Draws a path made of flower tiles from coor a to coor b
         * takes in arguments old x, old y, new x, new y
         */
        while (x1 < x2) {
            Game.world[x1][y1] = Tileset.FLOWER;
            x1 += 1;
        }

        while (x1 > x2) {
            Game.world[x1][y1] = Tileset.FLOWER;
            x1 -= 1;
        }

        while (y1 < y2) {
            Game.world[x1][y1] = Tileset.FLOWER;
            y1 += 1;
        }

        while (y1 > y2) {
            Game.world[x1][y1] = Tileset.FLOWER;
            y1 -= 1;
        }
        Game.world[x2][y2] = Tileset.FLOWER; //sets new rectangles center piece to flower
    }

    public static void flowerBorders() {
        // draws borders around flower tiles if Tileset.NOTHING is there
        for (int i = 0; i < Game.WIDTH; i++) {
            for (int k = 0; k < Game.HEIGHT; k++) {
                if (Game.world[i][k] == Tileset.FLOWER) {
                    flowerBorderDrawer(i, k);
                }
            }
        }
    }

    private static void flowerBorderDrawer(int x, int y) {
        /** draws the flower borders */

        //draws top left
        if (spotCheck(x - 1, y + 1)) {
            Game.world[x - 1][y + 1] = Tileset.WALL;
        }
        //draws top center
        if (spotCheck(x, y + 1)) {
            Game.world[x][y + 1] = Tileset.WALL;
        }
        //draws top right
        if (spotCheck(x + 1, y + 1)) {
            Game.world[x + 1][y + 1] = Tileset.WALL;
        }
        //draws left
        if (spotCheck(x - 1, y)) {
            Game.world[x - 1][y] = Tileset.WALL;
        }
        //draws right
        if (spotCheck(x + 1, y)) {
            Game.world[x + 1][y] = Tileset.WALL;
        }
        //draws bottom left
        if (spotCheck(x - 1, y - 1)) {
            Game.world[x - 1][y - 1] = Tileset.WALL;
        }
        //draws bottom center
        if (spotCheck(x, y - 1)) {
            Game.world[x][y - 1] = Tileset.WALL;
        }
        //draws bottom right
        if (spotCheck(x + 1, y - 1)) {
            Game.world[x + 1][y - 1] = Tileset.WALL;
        }
    }

    private static boolean spotCheck(int x, int y) {
        /**
         * checks to see if Tilesset.Nothing is in certain spot
         */
        //checks for correct array index
        if (x >= Game.WIDTH || y >= Game.HEIGHT || x < 0 || y < 0) {
            return false;
        }
        if (Game.world[x][y] == Tileset.NOTHING) {
            return true;
        }
        return false;
    }

    public static void flowerToFloor() {
        /**
         * converts flower tiles to floor tiles
         */
        for (int i = 0; i < Game.WIDTH; i++) {
            for (int k = 0; k < Game.HEIGHT; k++) {
                if (Game.world[i][k] == Tileset.FLOWER) {
                    Game.world[i][k] = Tileset.FLOOR;
                }
            }
        }
    }

    /**
     * RESET BOARD
     */
    public static void paintNone() {
        /**
         * Paints the entire board with nothings
         */
        for (int x = 0; x < Game.WIDTH; x += 1) {
            for (int y = 0; y < Game.HEIGHT; y += 1) {
                Game.world[x][y] = Tileset.NOTHING;
            }
        }
    }

}
