package byog.Core;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

public class Lighting {
    public static void initializeOverlay() {
        for (int i = 0; i < Game.WIDTH; i++) {
            for (int k = 0; k < Game.HEIGHT; k++) {
                Game.overlay[i][k] = viewCheck(i, k);
            }
        }
    }

    private static TETile viewCheck(int x, int y) {
        if (Game.world[x][y] == Tileset.NOTHING) {
            return Tileset.FOG;
        } else if (Game.world[x][y] == Tileset.KEY) {
            return Tileset.KEY;
        } else if (checkOthers(x, y)) {
            return Game.world[x][y];
        } else {
            return Tileset.FOG;
        }
    }

    private static boolean checkOthers(int x, int y) {
        int diffX = x - Player.coor[0];
        int diffY = y - Player.coor[1];
        int slope;
        int added;
        boolean vert;
        /** SLOPE AHEAD */
        if (diffX == 0) {
            slope = diffY;
            vert = true;
        } else if (diffY == 0) {
            slope = diffX;
            vert = false;
        } else if (diffX < 0 && diffY < 0) {
            if (Math.abs(diffX) > Math.abs(diffY)) {
                slope = -diffX / diffY;
                vert = false;
            } else if (Math.abs(diffX) < Math.abs(diffY)) {
                slope = -diffY / diffX;
                vert = true;
            } else {
                slope = -1;
                vert = true;
            }
        } else if (Math.abs(diffX) > Math.abs(diffY)) {
            slope = diffX / diffY;
            vert = false;
        } else if (Math.abs(diffX) < Math.abs(diffY)) {
            slope = diffY / diffX;
            vert = true;
        } else {
            slope = 1;
            vert = true;
        }

        if (slope < 0) {
            added = -1;
        } else {
            added = 1;
        }

        int checkX = Player.coor[0];
        int checkY = Player.coor[1];
        int count = 0;
        while (!(checkX == x && checkY == y)) {
            int inSlope = slope;
            while (inSlope != 0) {
                count += 1;
                if ((checkX == x && checkY == y)) {
                    break;
                }
                //check to see if going up
                if (vert) {
                    checkY += added;
                } else {
                    checkX += added;
                }
                //check
                if (Game.world[checkX][checkY] == Tileset.WALL) {
                    return false;
                }
                inSlope -= added;
            } //end of inner while loop
            if (vert) {
                checkX += added;
            } else {
                checkY += added;
            }
        } //end of outer while loop
        return true;
    }


}
