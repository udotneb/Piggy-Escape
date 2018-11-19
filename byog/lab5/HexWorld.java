package byog.lab5;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

/**
 * Draws a world consisting of hexagonal regions.
 */
public class HexWorld {
    private static final int WIDTH = 30;
    private static final int HEIGHT = 30;

    public static void addHexagon(int x, int y, int size, TETile[][] world) {
        // check to see if it fits in built world
        int currY = y;
        int currX = x;
        int length = size;
        //create top half of hex
        for (int i = 0; i < size; i++) {
            drawRow(x, y, length, world);
            y -= 1;
            x -= 1;
            length += 2;
        }
        //create bottom half of hex
        for (int i = 0; i <= size; i++) {
            drawRow(x, y, length, world);
            y -= 1;
            x += 1;
            length -= 2;
        }
    }

    public static void addNothing(TETile[][] world) {
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                world[x][y] = Tileset.NOTHING;
            }
        }
    }

    public static void drawRow(int x, int y, int length, TETile[][] world) {
        for (int i = 0; i < length; i++) {
            world[x + i][y] = Tileset.WALL;
        }
    }

    public static void main(String[] args) {
        TERenderer ter = new TERenderer();
        TETile[][] world = new TETile[WIDTH][HEIGHT];
        addNothing(world);
        addHexagon(13, 29, 3, world);
        ter.initialize(WIDTH, HEIGHT);
        ter.renderFrame(world);
    }

}
