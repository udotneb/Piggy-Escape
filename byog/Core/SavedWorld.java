package byog.Core;

import byog.TileEngine.TETile;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;


public class SavedWorld implements Serializable {
    private static final long serialVersionUID = 123123123123123L;
    TETile[][] world;
    int movesCount;
    long SEED;
    Random RANDOM;
    int[] playerCoor;
    ArrayList<Pig> pigList;
    int[] bluePortalCoor;
    int[] redPortalCoor;
    String location;
    int lives;
    int level;
    boolean blue;

    public SavedWorld() {

    }
}
