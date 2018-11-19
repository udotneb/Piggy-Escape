package byog.Core;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.io.Serializable;
import java.util.ArrayList;

public class Pig implements Serializable {
    static ArrayList<Pig> pigList;
    private final TETile ICON = Tileset.PIG;
    private int[] coor;
    private TETile lastTile = Tileset.FLOOR;

    public Pig() {
        coor = getEmptyFloor();
        Game.world[coor[0]][coor[1]] = ICON;
    }

    public static void addPigs(int n) {
        pigList = new ArrayList<>();
        while (n > 0) {
            Pig b = new Pig();
            pigList.add(b);
            n -= 1;
        }
    }

    public static void movePigs() {
        for (Pig x : pigList) {
            x.move();
        }
    }

    public int[] getEmptyFloor() {
        int x = Game.RANDOM.nextInt(Game.WIDTH - 1);
        int y = Game.RANDOM.nextInt(Game.HEIGHT - 1);
        while (Game.world[x][y] != Tileset.FLOOR) {
            x = Game.RANDOM.nextInt(Game.WIDTH - 1);
            y = Game.RANDOM.nextInt(Game.HEIGHT - 1);
        }
        return new int[]{x, y};
    }

    public void move() {
        int playerX = Player.coor[0];
        int playerY = Player.coor[1];
        int pigX = coor[0];
        int pigY = coor[1];
        //move down left
        if (pigX > playerX && pigY > playerY) {
            moveTiles(pigX - 1, pigY - 1);
        }
        //move down right
        if (pigX < playerX && pigY > playerY) {
            moveTiles(pigX + 1, pigY - 1);
        } else if (pigX < playerX && pigY < playerY) {
            moveTiles(pigX + 1, pigY + 1);
        } else if (pigX > playerX && pigY < playerY) {
            moveTiles(pigX - 1, pigY + 1);
        } else if (pigX == playerX && pigY > playerY) {
            moveTiles(pigX, pigY - 1);
        } else if (pigX == playerX && pigY < playerY) {
            moveTiles(pigX, pigY + 1);
        } else if (pigX < playerX && pigY == playerY) {
            moveTiles(pigX + 1, pigY);
        } else if (pigX > playerX && pigY == playerY) {
            moveTiles(pigX - 1, pigY);
        }
    }

    public void moveTiles(int x, int y) {
        //ensures that pigs are not colliding running into pigs
        if (Game.world[x][y] == Tileset.PIG) {
            return;
        }
        if (Game.world[x][y] == Tileset.BLUEPORTAL || Game.world[x][y] == Tileset.REDPORTAL) {
            return;
        }
        Game.world[coor[0]][coor[1]] = lastTile;
        lastTile = Game.world[x][y];
        if (lastTile == Player.ICON) {
            Player.killPlayer();
        }
        Game.world[x][y] = ICON;
        coor[0] = x;
        coor[1] = y;
    }

}
