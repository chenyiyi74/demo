package Mymaze;

import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class path {
    private int[][] maze;
    private int side;
    private static final int wall = 0;
    private static final int road = 1;
    private int startx, starty;
    private int endx, endy;
    private Stack<int[]> stack = new Stack<>();

    public path(int[][] maze, int startx, int starty, int endx, int endy) throws FileNotFoundException {
        this.maze = maze;
        this.side = maze.length;
        this.startx = startx;
        this.starty = starty;
        this.endx = endx;
        this.endy = endy;
        bfsAndMarkPath();
    }

    // BFS寻找最短路径并在maze中标记路径为2，同时压入stack
    private void bfsAndMarkPath() {
        boolean[][] visited = new boolean[side][side];
        int[][] prev = new int[side * side][2];
        for (int i = 0; i < side * side; i++) prev[i][0] = prev[i][1] = -1;

        Queue<int[]> queue = new LinkedList<>();
        queue.offer(new int[]{startx, starty});
        visited[startx][starty] = true;

        int[][] dirs = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
        boolean found = false;
        while (!queue.isEmpty() && !found) {
            int[] cur = queue.poll();
            int x = cur[0], y = cur[1];
            for (int[] d : dirs) {
                int nx = x + d[0], ny = y + d[1];
                if (nx >= 0 && nx < side && ny >= 0 && ny < side && maze[nx][ny] == road && !visited[nx][ny]) {
                    queue.offer(new int[]{nx, ny});
                    visited[nx][ny] = true;
                    prev[nx * side + ny][0] = x;
                    prev[nx * side + ny][1] = y;
                    if (nx == endx && ny == endy) {
                        found = true;
                        break;
                    }
                }
            }
        }
        // 还原maze中的2为1
        for (int i = 0; i < side; i++)
            for (int j = 0; j < side; j++)
                if (maze[i][j] == 2) maze[i][j] = road;

        // 回溯最短路径并标记
        stack.clear();
        int cx = endx, cy = endy;
        if (prev[cx * side + cy][0] == -1 && prev[cx * side + cy][1] == -1) {
            // 没有路径
            return;
        }
        while (!(cx == startx && cy == starty)) {
            maze[cx][cy] = 2;
            stack.push(new int[]{cx, cy});
            int px = prev[cx * side + cy][0];
            int py = prev[cx * side + cy][1];
            cx = px; cy = py;
        }
        maze[startx][starty] = 2;
        stack.push(new int[]{startx, starty});
    }

    public Stack<int[]> getStack() {
        return stack;
    }
    public int[][] getMaze() {
        return maze;
    }
}