/**
 * Author: Benjamin Ulrich
 * Creates a randomized video game enviroment!
 */

package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import edu.princeton.cs.introcs.StdDraw;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.IOException;

import java.util.Random;

public class Game {
    static final int WIDTH = 60;
    static final int HEIGHT = 40;
    static TERenderer terr;
    static TETile[][] world;
    static TETile[][] overlay;
    static int xNudge = 3;
    static int yNudge = 3;
    static int movesCount;
    static boolean victory;
    static boolean view;
    static long SEED;
    static Random RANDOM;
    static int level = 1;
    static String location;
    private static boolean saveFlag = false;

    public static void initializeRandom() {
        RANDOM = new Random(SEED);
        SEED = RANDOM.nextLong();
    }

    public static void initializeWorld() {
        /**
         * Initializes world
         */
        world = new TETile[WIDTH][HEIGHT];
        Board.paintNone();
        movesCount = 50 + (50 / level);
        victory = false;
    }

    public static void initializeTerr() {
        terr = new TERenderer();
        terr.initialize(WIDTH + 6, HEIGHT + 6, xNudge, yNudge);
    }

    private static void printBoard() {
        int numXTiles = WIDTH;
        int numYTiles = HEIGHT;
        for (int x = 0; x < numXTiles; x += 1) {
            for (int y = 0; y < numYTiles; y += 1) {
                System.out.print(world[x][y].character() + " ");
            }
            System.out.println();
        }

    }

    private static long parseSeed(String input) {
        boolean parseSEED = false;
        String seed = "";
        for (int i = 0; i < input.length(); i++) {
            char x = input.charAt(i);
            if (parseSEED) {
                if (x == 'S' || x == 's') {
                    return Long.parseLong(seed);
                } else {
                    seed += Character.toString(x);
                }
            } else {
                if (x == 'N' || x == 'n') {
                    parseSEED = true;
                }
            }
        }
        return Long.parseLong(seed);
    }

    private static void parseMove(String input) {
        boolean mover = false; //checks to see if passed the seed
        for (int i = 0; i < input.length(); i++) {
            if (mover) {
                if (input.charAt(i) == ':') {
                    return;
                }
                Player.movePlayer(input.charAt(i));
            }
            if (input.charAt(i) == 's' || input.charAt(i) == 'S' || input.charAt(i) == 'l'
                    || input.charAt(i) == 'L') {
                mover = true;
            }
        }
    }

    public static void playWithKeyboard() {
        view = true;
        StdDraw.enableDoubleBuffering();
        NewScreen.runLogin();
    }

    public static void keyboardChecker() {
        if (StdDraw.hasNextKeyTyped()) {
            char x = StdDraw.nextKeyTyped();
            if (saveFlag) {
                if (x == 'q' || x == 'Q') {
                    saveWorld();
                    System.exit(0);
                } else {
                    saveFlag = false;
                }
            }
            if (x == ':') {
                saveFlag = true;
            } else {
                Player.movePlayer(x);
                Player.shootPortal(x);
                render();
            }
        }
    }

    public static void render() {
        terr.renderFrame(world);
    }

    //@source from the demo in the skeleton code
    private static void saveWorld() {
        File f = new File("./Game.txt");
        try {
            if (!f.exists()) {
                f.createNewFile();
            }
            FileOutputStream fs = new FileOutputStream(f);
            ObjectOutputStream os = new ObjectOutputStream(fs);
            SavedWorld x = new SavedWorld();
            x.world = world;
            x.SEED = SEED;
            x.RANDOM = RANDOM;
            x.playerCoor = Player.coor;
            if (view) {
                x.movesCount = movesCount;
                x.pigList = Pig.pigList;
                x.redPortalCoor = Misc.redPortalCoor;
                x.bluePortalCoor = Misc.bluePortalCoor;
                x.lives = Player.lives;
                x.level = level;
                x.location = location;
            }
            os.writeObject(x);
            os.close();
        } catch (FileNotFoundException e) {
            System.out.println("file not found");
            System.exit(0);
        } catch (IOException e) {
            System.out.println(e);
            System.exit(0);
        }
    }

    //@source from the demo in the skeleton code
    public static void loadWorld() {
        File f = new File("./Game.txt");
        if (f.exists()) {
            try {
                FileInputStream fs = new FileInputStream(f);
                ObjectInputStream os = new ObjectInputStream(fs);
                SavedWorld x = (SavedWorld) os.readObject();
                os.close();
                world = x.world;
                SEED = x.SEED;
                RANDOM = x.RANDOM;
                Player.coor = x.playerCoor;
                if (view) {
                    movesCount = x.movesCount;
                    Pig.pigList = x.pigList;
                    Misc.redPortalCoor = x.redPortalCoor;
                    Misc.bluePortalCoor = x.bluePortalCoor;
                    location = x.location;
                    Player.lives = x.lives;
                    level = x.level;
                }
            } catch (FileNotFoundException e) {
                System.out.println("file not found");
                System.exit(0);
            } catch (IOException e) {
                System.out.println(e);
                System.exit(0);
            } catch (ClassNotFoundException e) {
                System.out.println("class not found");
                System.exit(0);
            }
        } else {
            System.exit(0);
        }
    }

    private static boolean firstL(String input) {
        if (input.charAt(0) == 'L' || input.charAt(0) == 'l') {
            return true;
        }
        return false;
    }

    private static void checkSave(String input) {
        for (int i = 0; i < input.length() - 1; i++) {
            if (input.charAt(i) == ':' && (input.charAt(i + 1) == 'q'
                    || input.charAt(i + 1) == 'Q')) {
                saveWorld();
            }
        }
    }

    public TETile[][] playWithInputString(String input) {
        view = false;
        if (firstL(input)) {
            loadWorld();
            parseMove(input);
            checkSave(input);
            return world;
        } else {
            SEED = parseSeed(input);
            initializeRandom();
            initializeWorld();
            Board.create();
            Player.addPlayer();
            parseMove(input);
            checkSave(input);
            return world;
        }
    }
}
