package byog.Core;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import edu.princeton.cs.introcs.StdAudio;
import edu.princeton.cs.introcs.StdDraw;

import java.awt.Font;
import java.awt.Color;


public class NewScreen {
    private static final Font boldBigFont = new Font("Arial", Font.BOLD, 20);
    private static final Font BIGFONT = new Font("Arial", Font.PLAIN, 20);

    public static void runLogin() {
        //StdDraw.setCanvasSize(Game.WIDTH, Game.HEIGHT);
        StdDraw.clear(Color.BLACK);
        StdDraw.setPenColor(Color.WHITE);
        StdDraw.text(0.5, 0.6, "Welcome back comrade");
        StdDraw.text(0.5, 0.5, "[ N ] to start");
        StdDraw.text(0.5, 0.45, "[ L ] to load");
        StdDraw.text(0.5, 0.40, "[ Q ] to exit");
        StdDraw.show();
        while (true) {
            if (StdDraw.hasNextKeyTyped()) {
                char x = StdDraw.nextKeyTyped();
                //start
                if (x == 'n' || x == 'N') {
                    runInstructions();
                    break;
                }
                //load
                if (x == 'l' || x == 'L') {
                    Game.initializeTerr();
                    Game.loadWorld();
                    runBoard();
                }
                //exit
                if (x == 'Q' || x == 'q') {
                    System.exit(0);
                }
            }
        }
        StdDraw.pause(50);
    }

    private static void runIntro() {
        StdDraw.clear(Color.black);
        StdDraw.setPenColor(Color.white);
        Font font = new Font("Arial", Font.CENTER_BASELINE, 15);
        StdDraw.setFont(font);
        StdDraw.text(0.5, 0.65, "Here is your mission");
        StdDraw.text(0.5, 0.60, "Capitalist pigs have overrun berkeley");
        StdDraw.text(0.5, 0.55, "You must make it to the extraction point before it is too late");
        StdDraw.text(0.5, 0.45, "Do you accept this mission");
        StdDraw.text(0.5, 0.4, "[ N ] to accept");
        StdDraw.text(0.5, 0.35, "[ Q ] to exit");
        StdDraw.show();
        while (true) {
            if (StdDraw.hasNextKeyTyped()) {
                char x = StdDraw.nextKeyTyped();
                if (x == 'N' || x == 'n') {
                    newGame();
                    levelOneScreen();
                    break;
                } else if (x == 'Q' || x == 'q') {
                    System.exit(0);
                }
            }
            StdDraw.pause(50);
        }
    }

    private static void runInstructions() {
        Font font = new Font("Arial", Font.CENTER_BASELINE, 20);
        StdDraw.clear(Color.black);
        StdDraw.setPenColor(Color.white);
        StdDraw.text(0.5, 0.8, "INSTRUCTIONS");
        StdDraw.text(0.5, 0.75, "Our informant has left a prototype portal gun to assist you");
        StdDraw.text(0.3, 0.66, "Move keys");
        StdDraw.text(0.7, 0.66, "Portal keys");


        StdDraw.text(0.5, 0.4, "There are four stages, each with increasing difficulty, ");
        StdDraw.text(0.5, 0.35, "above all else, avoid the capitalist pigs");

        //move keys
        StdDraw.filledRectangle(0.2, 0.5, 0.04, 0.04);
        StdDraw.filledRectangle(0.3, 0.5, 0.04, 0.04);
        StdDraw.filledRectangle(0.3, 0.6, 0.04, 0.04);
        StdDraw.filledRectangle(0.4, 0.5, 0.04, 0.04);
        //shoot keys
        StdDraw.filledRectangle(0.6, 0.5, 0.04, 0.04);
        StdDraw.filledRectangle(0.7, 0.5, 0.04, 0.04);
        StdDraw.filledRectangle(0.7, 0.6, 0.04, 0.04);
        StdDraw.filledRectangle(0.8, 0.5, 0.04, 0.04);

        StdDraw.setFont(font);
        StdDraw.setPenColor(Color.black);
        //move letters
        StdDraw.text(0.2, 0.5, "A");
        StdDraw.text(0.3, 0.5, "S");
        StdDraw.text(0.3, 0.6, "D");
        StdDraw.text(0.4, 0.5, "W");
        //shoot letters
        StdDraw.text(0.6, 0.5, "J");
        StdDraw.text(0.7, 0.5, "K");
        StdDraw.text(0.7, 0.6, "I");
        StdDraw.text(0.8, 0.5, "L");
        StdDraw.show();

        //seed generation
        long seed = 0;
        long currSeed = 0;
        StdDraw.setPenColor(Color.lightGray);
        StdDraw.text(0.5, 0.3, "Enter authentication code below");
        StdDraw.filledRectangle(0.5, 0.2, 0.8, 0.04);
        StdDraw.text(0.5, 0.1, "Press S to start, Q to exit");
        StdDraw.show();
        while (true) {
            if (StdDraw.hasNextKeyTyped()) {
                char x = StdDraw.nextKeyTyped();
                if (x == 's' || x == 'S') {
                    Game.SEED = (long) seed;
                    runIntro();
                    break;
                } else if (x == 'Q' || x == 'q') {
                    System.exit(0);
                } else {
                    try {
                        seed = 10 * seed + Long.parseLong(String.valueOf(x));
                    } catch (NumberFormatException nfe) {
                        assert true;
                    }
                }
            }
            if (currSeed != seed) {
                if (seed < 0) {
                    seed = 0;
                }
                currSeed = seed;
                StdDraw.setPenColor(Color.lightGray);
                StdDraw.filledRectangle(0.5, 0.2, 0.8, 0.04);
                StdDraw.setPenColor(Color.BLACK);
                StdDraw.text(0.5, 0.2, Long.toString(seed));
                StdDraw.show();
            }
            StdDraw.pause(50);
        }
    }

    public static void runFailScreen() {
        Player.lives -= 1;
        if (Player.lives == 0) {
            endLoseGame();
            return;
        }
        StdDraw.clear(Color.black);
        StdDraw.setPenColor(Color.white);
        StdDraw.text(Game.WIDTH / 2, Game.HEIGHT / 2, "You have failed the motherland");
        StdDraw.text(Game.WIDTH / 2, Game.HEIGHT / 2 - 3, "press n to restart");
        StdDraw.show();
        while (true) {
            if (StdDraw.hasNextKeyTyped()) {
                char x = StdDraw.nextKeyTyped();
                if (x == 'n') {
                    newGame();
                    break;
                }
            }
        }
        while (StdDraw.hasNextKeyTyped()) {
            StdDraw.nextKeyTyped();
        }
        runBoard();
    }

    public static void newGame() {
        Game.initializeRandom();
        Game.initializeWorld();
        Game.initializeTerr();
        Board.create();
        Player.addPlayer();
        Misc.resetPortals();
        Misc.addKey();
        Pig.addPigs(0 + (Game.level-1) * 6 + 1);
    }

    public static void runBoard() {
        Game.render();
        while (Player.stillPlaying()) {
            Game.keyboardChecker();
            GUI.getTileText();
            StdDraw.pause(50);
        }
        if (!Game.victory) {
            runFailScreen();
        } else {
            runVictoryScreen();
        }
    }

    public static void runVictoryScreen() {
        Game.level += 1;
        int x = Game.level;
        if (x == 2) {
            levelTwoScreen();
        } else if (x == 3) {
            levelThreeScreen();
        } else if (x == 4) {
            levelFourScreen();
        } else {
            endVictoryGame();

        }
    }

    public static void levelOneScreen() {
        StdDraw.clear(Color.black);
        StdDraw.setPenColor(Color.white);
        StdDraw.setFont(boldBigFont);
        StdDraw.textLeft(1, Game.HEIGHT, "WURSTER");
        StdDraw.setFont(BIGFONT);
        StdDraw.textLeft(1, Game.HEIGHT - 2, "The wurst of the wurst, "
                + "even the pigs don't want to be here");
        StdDraw.textLeft(1, Game.HEIGHT - 4, "Get in "
                + "and get out quickly comrade");
        StdDraw.textLeft(1, Game.HEIGHT - 10, "'The rich will "
                + "do anything for the poor but get off their backs'");
        StdDraw.textLeft(5, Game.HEIGHT - 12, "- Father Denero");
        StdDraw.textLeft(1, Game.HEIGHT - 16, "[ n ] to continue");
        StdDraw.picture(Game.WIDTH - 10, 30, "wurster.jpg", 20, 20);
        StdDraw.show();

        while (true) {
            if (StdDraw.hasNextKeyTyped()) {
                char x = StdDraw.nextKeyTyped();
                if (x == 'n') {
                    Game.location = "WURSTER";
                    if (Game.level == 5) {
                        endVictoryGame();
                        break;
                    }
                    newGame();
                    break;
                }
            }
        }
        while (StdDraw.hasNextKeyTyped()) {
            StdDraw.nextKeyTyped();
        }
        runBoard();
    }

    public static void levelTwoScreen() {
        StdDraw.clear(Color.black);
        StdDraw.setPenColor(Color.white);
        StdDraw.setFont(boldBigFont);
        StdDraw.textLeft(1, Game.HEIGHT, "GBC");
        StdDraw.setFont(BIGFONT);
        StdDraw.textLeft(1, Game.HEIGHT - 2, "The damn capitalists have cut everything here " +
                "and replaced it with sadness");
        StdDraw.textLeft(1, Game.HEIGHT - 4, "Move quickly before they consume you too");
        StdDraw.textLeft(1, Game.HEIGHT - 10, "'Seize the memes of production comrade'");
        StdDraw.textLeft(5, Game.HEIGHT - 12, "- Socrates");
        StdDraw.textLeft(1, Game.HEIGHT - 16, "[ n ] to continue");
        StdDraw.picture(Game.WIDTH -5, 30, "gbc.jpg", 20, 20);
        StdDraw.show();
        Tileset.WALL = new TETile('#', Color.darkGray, new Color(74, 47, 84), "wall");
        while (true) {
            if (StdDraw.hasNextKeyTyped()) {
                char x = StdDraw.nextKeyTyped();
                if (x == 'n') {
                    Game.location = "GBC";
                    if (Game.level == 5) {
                        endVictoryGame();
                        break;
                    }
                    newGame();
                    break;
                }
            }
        }
        while (StdDraw.hasNextKeyTyped()) {
            StdDraw.nextKeyTyped();
        }
        runBoard();
    }

    public static void levelThreeScreen() {
        StdDraw.clear(Color.black);
        StdDraw.setPenColor(Color.white);
        StdDraw.setFont(boldBigFont);
        StdDraw.textLeft(1, Game.HEIGHT, "SPROUL HALL");
        StdDraw.setFont(BIGFONT);
        StdDraw.textLeft(1, Game.HEIGHT - 2,  "Millions in debt already? Lets spend 445 million on a new stadium!");
        StdDraw.textLeft(1, Game.HEIGHT - 4, "This place is teaming with pigs, stay safe comrade");
        StdDraw.textLeft(1, Game.HEIGHT - 10, "'The Proletarian has nothing to lose but their chains'");
        StdDraw.textLeft(5, Game.HEIGHT - 12, "- Josh Hug");
        StdDraw.textLeft(1, Game.HEIGHT - 16, "[ n ] to continue");
        StdDraw.picture(Game.WIDTH -5, 30, "sproulHall.jpg", 20, 20);
        StdDraw.show();
        Tileset.WALL = new TETile('*', Color.darkGray, new Color(94, 96, 74), "wall");
        while (true) {
            if (StdDraw.hasNextKeyTyped()) {
                char x = StdDraw.nextKeyTyped();
                if (x == 'n') {
                    Game.location = "SPROUL HALL";
                    newGame();
                    break;
                }
            }
        }
        while (StdDraw.hasNextKeyTyped()) {
            StdDraw.nextKeyTyped();
        }
        runBoard();
    }

    public static void levelFourScreen() {
        StdDraw.clear(Color.black);
        StdDraw.setPenColor(Color.white);
        StdDraw.setFont(boldBigFont);
        StdDraw.textLeft(1, Game.HEIGHT, "HAAS");
        StdDraw.setFont(BIGFONT);
        StdDraw.textLeft(1, Game.HEIGHT - 2,  "Theres no turning back comrade, this capitalism at its peak");
        StdDraw.textLeft(1, Game.HEIGHT - 4, "Can you get out without being " +
                "pitched a startup idea?");
        StdDraw.textLeft(1, Game.HEIGHT - 10, "'Carol Christ'");
        StdDraw.textLeft(5, Game.HEIGHT - 12, "- Carol Christ");
        StdDraw.textLeft(1, Game.HEIGHT - 16, "[ n ] to continue");
        StdDraw.picture(Game.WIDTH -5, 30, "snakes.jpg", 20, 20);
        StdDraw.show();
        Color darkGreen = new Color(18, 64, 114);
        Tileset.WALL = new TETile('$', Color.GREEN, darkGreen, "wall");
        while (true) {
            if (StdDraw.hasNextKeyTyped()) {
                char x = StdDraw.nextKeyTyped();
                if (x == 'n') {
                    Game.location = "HAAS";
                    newGame();
                    break;
                }
            }
        }
        while (StdDraw.hasNextKeyTyped()) {
            StdDraw.nextKeyTyped();
        }
        runBoard();
    }

    public static void endVictoryGame() {
        StdDraw.clear(Color.black);
        StdDraw.setPenColor(Color.white);
        StdDraw.setFont(boldBigFont);
        StdDraw.textLeft(1, Game.HEIGHT, "THAT ONE COMMUNIST BOOKSTORE");
        StdDraw.setFont(BIGFONT);
        StdDraw.textLeft(1, Game.HEIGHT - 3, "Congradulations comrade!");
        StdDraw.textLeft(1, Game.HEIGHT - 6, "[ n ] for a new game");
        StdDraw.picture(Game.WIDTH -5, 30, "revbookstore.jpg", 20, 20);
        StdDraw.show();
        while (true) {
            if (StdDraw.hasNextKeyTyped()) {
                char x = StdDraw.nextKeyTyped();
                if (x == 'n') {
                    Game.level = 1;
                    Player.lives = 3;
                    Tileset.WALL = new TETile('╳', Color.darkGray, Tileset.blues, "wall");
                    levelOneScreen();
                }
            }
        }
    }

    public static void endLoseGame() {
        StdDraw.clear(Color.black);
        StdDraw.setPenColor(Color.white);
        StdDraw.setFont(boldBigFont);
        StdDraw.textLeft(1, Game.HEIGHT, "HAAS BASEMENT");
        StdDraw.setFont(BIGFONT);
        StdDraw.textLeft(1, Game.HEIGHT - 2, "Morgan Stanley and Goldman Sachs close in on you");
        StdDraw.textLeft(1, Game.HEIGHT - 4, "'NO PLEASE NOT LIKE THIS' you scream");
        StdDraw.textLeft(1, Game.HEIGHT - 6, "They hand you a banking offer");
        StdDraw.textLeft(1, Game.HEIGHT - 8, "'NOOOOOOOOO'");
        StdDraw.textLeft(1, Game.HEIGHT - 10, "'shhhhhhhhhh it'll all be over soon' they tell "
                + "you while wiping your tears with hundreds");
        StdDraw.textLeft(1, Game.HEIGHT - 14, "[ n ] to restart game");
        StdDraw.picture(Game.WIDTH / 2, 13, "endGame.png", 40, 20);
        StdDraw.show();
        while (true) {
            if (StdDraw.hasNextKeyTyped()) {
                char x = StdDraw.nextKeyTyped();
                if (x == 'n') {
                    Game.level = 1;
                    Player.lives = 3;
                    Tileset.WALL = new TETile('╳', Color.darkGray, Tileset.blues, "wall");
                    levelOneScreen();
                }
            }
        }
    }

}
