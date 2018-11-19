package byog.Core;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

public class Misc {
    static int[] bluePortalCoor;
    static int[] redPortalCoor;
    static boolean blue = true; //true if blue portal is up next


    /**
     * PORTAL FUNCTIONS
     */
    public static void resetPortals() {
        bluePortalCoor = new int[]{0, 0};
        redPortalCoor = new int[]{0, 0};
    }

    public static void portalLaunch(int x, int y) {
        // x = 1 dir right, x = -1 dir left, y = 1 dir up, y = -1 dir down
        if (x == 1) {
            for (int i = Player.coor[0]; i < Game.WIDTH; i++) {
                if (Game.world[i][Player.coor[1]].equals(Tileset.WALL)) {
                    placePortal(i - 1, Player.coor[1]);
                    return;
                }
            }
        } else if (x == -1) {
            for (int i = Player.coor[0]; i >= 0; i--) {
                if (Game.world[i][Player.coor[1]].equals(Tileset.WALL)) {
                    placePortal(i + 1, Player.coor[1]);
                    return;
                }
            }
        } else if (y == 1) {
            for (int i = Player.coor[1]; i < Game.HEIGHT; i++) {
                if (Game.world[Player.coor[0]][i].equals(Tileset.WALL)) {
                    placePortal(Player.coor[0], i - 1);
                    return;
                }
            }
        } else if (y == -1) {
            for (int i = Player.coor[1]; i >= 0; i--) {
                if (Game.world[Player.coor[0]][i].equals(Tileset.WALL)) {
                    placePortal(Player.coor[0], i + 1);
                    return;
                }
            }
        }
    }

    private static void placePortal(int x, int y) {
        //checks to see if red and blue are trying to be placed the same
        sameCoor();
        //if blue portal is next
        if (blue) {
            if (!(bluePortalCoor[0] == 0 && bluePortalCoor[1] == 0)) {
                if (Player.coor[0] == bluePortalCoor[0] && Player.coor[1] == bluePortalCoor[1]) {
                    Game.world[bluePortalCoor[0]][bluePortalCoor[1]] = Player.ICON;
                } else {
                    Game.world[bluePortalCoor[0]][bluePortalCoor[1]] = Tileset.FLOOR;
                }
            }
            Game.world[x][y] = Tileset.BLUEPORTAL;
            bluePortalCoor[0] = x;
            bluePortalCoor[1] = y;
            blue = false;

            if (x == Player.coor[0] && y == Player.coor[1]) {
                Player.lastTile = Tileset.BLUEPORTAL;
                Player.validMove(Player.coor[0], Player.coor[1]);
            }
            //if red portal is next
        } else {
            if (!(redPortalCoor[0] == 0 && redPortalCoor[1] == 0)) {
                if (Player.coor[0] == redPortalCoor[0] && Player.coor[1] == redPortalCoor[1]) {
                    Game.world[redPortalCoor[0]][redPortalCoor[1]] = Player.ICON;
                } else {
                    Game.world[redPortalCoor[0]][redPortalCoor[1]] = Tileset.FLOOR;
                }
            }
            Game.world[x][y] = Tileset.REDPORTAL;
            redPortalCoor[0] = x;
            redPortalCoor[1] = y;
            blue = true;
            if (x == Player.coor[0] && y == Player.coor[1]) {
                Player.lastTile = Tileset.REDPORTAL;
                Player.validMove(Player.coor[0], Player.coor[1]);
            }
        }

    }

    private static void sameCoor() {
        if (redPortalCoor[0] == bluePortalCoor[0] && redPortalCoor[1] == bluePortalCoor[1]) {
            if (blue) {
                redPortalCoor[0] = 0;
                redPortalCoor[1] = 0;
            } else {
                bluePortalCoor[0] = 0;
                bluePortalCoor[1] = 0;
            }
        }
    }

    /**
     * MISC FUNCTIONS
     */
    public static void addKey() {
        for (int x = Game.WIDTH - 1; x > -1; x -= 1) {
            for (int y = Game.HEIGHT - 1; y > -1; y -= 1) {
                if (Game.world[x][y] == Tileset.FLOOR) {
                    Game.world[x][y] = Tileset.KEY;
                    return;
                }
            }
        }
    }

    public static void addMoney(int count) {
        while (count > 0) {
            int x = Game.RANDOM.nextInt(Game.WIDTH - 1);
            int y = Game.RANDOM.nextInt(Game.HEIGHT - 1);
            while (Game.world[x][y] != Tileset.FLOOR) {
                x = Game.RANDOM.nextInt(Game.WIDTH);
                y = Game.RANDOM.nextInt(Game.HEIGHT);
            }
            Game.world[x][y] = Tileset.MONEY;
            count -= 1;
        }
    }

    private static void cleanBoard() {
        for (int i = 0; i < Game.WIDTH; i++) {
            for (int k = 0; k < Game.HEIGHT; k ++) {
                TETile x = Game.world[i][k];
                if (x == Tileset.REDPORTAL || x == Tileset.BLUEPORTAL) {
                    if (i != bluePortalCoor[0] && i != redPortalCoor[1]) {
                        Game.world[i][k] = Tileset.FLOOR;
                    } else if (k != bluePortalCoor[0] && k != redPortalCoor[1]) {
                        Game.world[i][k] = Tileset.FLOOR;
                    }
                }
            }
        }

    }

}
