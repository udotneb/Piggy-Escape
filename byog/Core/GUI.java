package byog.Core;

import edu.princeton.cs.introcs.StdDraw;

import java.awt.Color;

public class GUI {
    private static String currDescription = "";

    public static void getTileText() {
        if (Game.view) {
            int x = (int) StdDraw.mouseX() - Game.xNudge;
            int y = (int) StdDraw.mouseY() - Game.yNudge;
            if (x < 0 || x >= Game.WIDTH || y < 0 || y >= Game.HEIGHT) {
                x = 0;
                y = 0;
            }
            StdDraw.setPenColor(Color.white);
            if (currDescription != Game.world[x][y].description()) {
                Game.render();
            }
            currDescription = Game.world[x][y].description();
            StdDraw.text(Game.WIDTH - 5, 45, currDescription);
            StdDraw.text(10, 45, "Level: " + Integer.toString(Game.level));
            StdDraw.text(15, 45, "Lives: " + Integer.toString(Player.lives));
            StdDraw.text(5, 45, "Time:" + Game.movesCount);
            StdDraw.text(Game.WIDTH / 2, 45, Game.location);
            StdDraw.show();
        }
    }

}
