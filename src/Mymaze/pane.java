package Mymaze;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class pane extends Pane{
    Rectangle rec;
    private int paneside = 10;

    public pane(int[][] maze)
    {
        getChildren().clear();
        int side = maze.length;
        for(int i=0;i<side;i++)
        {
            for(int j=0;j<side;j++)
            {
                if(maze[i][j] == 0)
                {
                    rec = new Rectangle(j*paneside,i*paneside,9,9);
                    rec.setFill(Color.BROWN);
                    getChildren().add(rec);
                }
                else if(maze[i][j] == 1)
                {
                    rec = new Rectangle(j*paneside,i*paneside,9,9);
                    rec.setFill(Color.WHITE);
                    getChildren().add(rec);
                }
                else
                {
                    rec = new Rectangle(j*paneside,i*paneside,9,9);
                    rec.setFill(Color.BLACK);
                    getChildren().add(rec);
                }
            }
        }
    }
}
