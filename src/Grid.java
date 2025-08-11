import java.awt.Graphics;
import java.awt.Point;

public class Grid {
    void paint(Graphics g, Point mousePos ){
        for(int i=0;i<700;i+=35){
            for(int j=0;j<700;j+=35){
                Cell cell = new Cell();
                cell.paint(g,10+i,10+j, mousePos);
            }
        }
    }
}
