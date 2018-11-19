package byog.Core;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

public class Player {
    public static final TETile ICON = Tileset.TREE;
    /**
     * player coor in len 2 x y array
     */
    static int[] coor;
    static TETile lastTile = Tileset.FLOOR;
    static int lives = 3;
    private static boolean dead;

    public static void killPlayer() {
        dead = true;
    }

    public static void addPlayer() {
        for (int x = 0; x < Game.WIDTH; x += 1) {
            for (int y = 0; y < Game.HEIGHT; y += 1) {
                if (Game.world[x][y] == Tileset.FLOOR) {
                    Game.world[x][y] = ICON;
                    coor = new int[]{x, y};
                    lastTile = Tileset.FLOOR;
                    dead = false;
                    return;
                }
            }
        }
    }

    public static boolean stillPlaying() {
        /** Checks to see if game is over */
        if (Game.movesCount <= 0 || Game.victory || dead) {
            return false;
        }
        return true;
    }

    public static void shootPortal(char a) {
        if (a == 'j') {
            Misc.portalLaunch(-1, 0);
        }
        if (a == 'k') {
            Misc.portalLaunch(0, -1);
        }
        if (a == 'l') {
            Misc.portalLaunch(1, 0);
        }
        if (a == 'i') {
            Misc.portalLaunch(0, 1);
        }
    }

    public static void movePlayer(char a) {
        /** moves player WASD keys */
        if (a == 'a' || a == 'A') {
            validMove(Player.coor[0] - 1, Player.coor[1]);
        }
        if (a == 's' || a == 'S') {
            validMove(Player.coor[0], Player.coor[1] - 1);
        }
        if (a == 'd' || a == 'D') {
            validMove(Player.coor[0] + 1, Player.coor[1]);
        }
        if (a == 'w' || a == 'W') {
            validMove(Player.coor[0], Player.coor[1] + 1);
        }
    }

    public static void validMove(int x, int y) {
        if (Game.world[x][y].equals(Tileset.KEY)) {
            Game.victory = true;
        } else if (Game.world[x][y].equals(Tileset.PIG)) {
            dead = true;
        } else if (Game.world[x][y].equals(Tileset.BLUEPORTAL)) {
            x = Misc.redPortalCoor[0];
            y = Misc.redPortalCoor[1];
        } else if (Game.world[x][y].equals(Tileset.REDPORTAL)) {
            x = Misc.bluePortalCoor[0];
            y = Misc.bluePortalCoor[1];
        }
        if (Game.world[x][y].equals(Tileset.WALL) || Game.world[x][y].equals(Tileset.NOTHING)) {
            return;
        }
        if (lastTile == Tileset.REDPORTAL) {
            if (Misc.redPortalCoor[0] == coor[0] && Misc.redPortalCoor[1] == coor[1]) {
                Game.world[Player.coor[0]][Player.coor[1]] = lastTile;
            } else {
                Game.world[Player.coor[0]][Player.coor[1]] = Tileset.FLOOR;
            }
        } else if (lastTile == Tileset.BLUEPORTAL) {
            if (Misc.bluePortalCoor[0] == coor[0] && Misc.bluePortalCoor[1] == coor[1]) {
                Game.world[Player.coor[0]][Player.coor[1]] = lastTile;
            } else {
                Game.world[Player.coor[0]][Player.coor[1]] = Tileset.FLOOR;
            }
        } else {
            Game.world[Player.coor[0]][Player.coor[1]] = lastTile;
        }
        lastTile = Game.world[x][y];
        Player.coor[0] = x;
        Player.coor[1] = y;
        Game.world[x][y] = Player.ICON;
        Game.movesCount -= 1;
        if (Game.view) {
            Pig.movePigs();
        }
    }

}
