package byog.Core;

import byog.TileEngine.TETile;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class InputStringTest {
    @Test
    public void testParse() {
        Game x = new Game();
        Game y = new Game();
        ArrayList<TETile> lst = new ArrayList<>();
        x.playWithInputString("n698s");
        for (int i = 0; i < x.world.length; i++) {
            for (int k = 0; k < x.world[i].length; k++) {
                lst.add(x.world[i][k]);
            }
        }
        x.playWithInputString("n698s");
        int count = 0;
        for (int i = 0; i < x.world.length; i++) {
            for (int k = 0; k < x.world[i].length; k++) {
                assertEquals(lst.get(count).description(), x.world[i][k].description());
                count += 1;
            }
        }
    }

    @Test
    public void testSave() {
        Game x = new Game();
        TETile[][] q = x.playWithInputString("n69sDDASDAWDWSDWSDASSD:Q");
        TETile[][] w = x.playWithInputString("L");
        for (int i = 0; i < q.length; i++) {
            for (int k = 0; k < q[i].length; k++) {
                assertTrue(q[i][k].equals(w[i][k]));
            }
        }
    }

}
