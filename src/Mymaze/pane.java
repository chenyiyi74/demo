package Mymaze;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import java.util.List;

public class pane extends Pane {
    private int paneside = 14;
    private int[][] map;
    private List<int[]> path = null;

    public pane(int[][] map) {
        this.map = map;
        drawMaze();
    }

    public void setPath(List<int[]> path) {
        this.path = path;
        drawMaze();
    }

    private void drawMaze() {
        getChildren().clear();
        int side = map.length;
        // 绘制迷宫格子
        for (int i = 0; i < side; i++) {
            for (int j = 0; j < side; j++) {
                Rectangle rec = new Rectangle(j * paneside, i * paneside, paneside, paneside);
                if (map[i][j] == 0) {
                    rec.setFill(Color.BROWN); // 墙
                } else {
                    rec.setFill(Color.WHITE); // 路
                }
                rec.setStroke(Color.LIGHTGRAY);
                getChildren().add(rec);
            }
        }
        // 高亮路径
        if (path != null) {
            for (int[] pos : path) {
                Rectangle rect = new Rectangle(pos[1] * paneside, pos[0] * paneside, paneside, paneside);
                rect.setFill(Color.BLACK);
                getChildren().add(rect);
            }
        }
    }
}