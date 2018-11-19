package byog.TileEngine;

import java.awt.Color;

/**
 * Contains constant tile objects, to avoid having to remake the same tiles in different parts of
 * the code.
 * <p>
 * You are free to (and encouraged to) create and add your own tiles to this file. This file will
 * be turned in with the rest of your code.
 * <p>
 * Ex:
 * world[x][y] = Tileset.FLOOR;
 * <p>
 * The style checker may crash when you try to style check this file due to use of unicode
 * characters. This is OK.
 */

public class Tileset {
    public static Color blues = new Color(91, 149, 229);
    public static Color oldWall = new Color(216, 128, 128);
    public static final TETile PLAYER = new TETile('@', Color.white, Color.orange, "player");
    public static TETile WALL = new TETile('╳', Color.darkGray, blues, "wall");
    public static final TETile FLOOR = new TETile('·', new Color(128, 192, 128), Color.black,
            "floor");
    public static final TETile NOTHING = new TETile(' ', Color.black, Color.black, "");
    public static final TETile GRASS = new TETile('"', Color.green, Color.black, "grass");
    public static final TETile WATER = new TETile('≈', Color.blue, Color.black, "water");
    public static final TETile FLOWER = new TETile('❀', Color.magenta, Color.pink, "flower");
    public static final TETile LOCKED_DOOR = new TETile('█', Color.orange, Color.black,
            "locked door");
    public static final TETile UNLOCKED_DOOR = new TETile('▢', Color.orange, Color.black,
            "unlocked door");
    public static final TETile SAND = new TETile('▒', Color.yellow, Color.black, "sand");
    public static final TETile MOUNTAIN = new TETile('▲', Color.gray, Color.black, "mountain");
    public static final TETile TREE = new TETile('♠', Color.green, Color.black, "tree");
    public static final TETile KEY = new TETile('☭', Color.yellow, Color.red, "key");
    public static final TETile MONEY = new TETile('$', Color.green, Color.black, "capitalist pigs");
    public static final TETile PIG = new TETile('♞', Color.WHITE, Color.black, "pig","pig-icon.png");
    private static Color red = new Color(209, 18, 18);
    public static final TETile REDPORTAL = new TETile('▨', red, Color.black, "red portal");
    private static Color blue = new Color(0, 209, 0);
    public static final TETile BLUEPORTAL = new TETile('▧', blue, Color.black, "blue portal");
    public static final TETile FOG = new TETile('~', Color.gray, Color.BLACK, "fog");

}


