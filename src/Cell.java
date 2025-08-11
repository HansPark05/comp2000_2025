import java.awt.Graphics;
import java.awt.Point;

public class Cell extends Grid{
    void paint(Graphics g,int x, int y, Point mousePos) {
        if(mousePos.x>x && mousePos.x<x+35 && mousePos.y>y && mousePos.y<y+35) {
            g.setColor(java.awt.Color.LIGHT_GRAY);
                    g.fillRect(x,y,35,35);
        } else {

                    g.fillRect(x,y,35,35);
        }

    }
}
