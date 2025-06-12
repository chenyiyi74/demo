package Mymaze;

import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;
import java.util.Random;
import java.util.ArrayList;
import java.util.Collections;

public class path {
    private int[][] maze;
    private int side;
    private static final int wall = 0;
    private static final int road = 1;
    private int startx, starty;
    private int endx, endy;
    private Stack<int[]> stack = new Stack<>();

    // 随机通路存储
    private Stack<int[]> randomStack = new Stack<>();

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

    // ----------- 随机通路相关 -----------
// 生成一条随机通路（非最短路径），多次尝试避免找不到
    public void findRandomPath() {
        boolean found = false;
        int tryCount = 0;
        while (!found && tryCount < 100) { // 最多尝试100次
            boolean[][] visited = new boolean[side][side];
            randomStack.clear();
            Random rand = new Random();
            found = dfsRandom(startx, starty, visited, randomStack, rand);
            tryCount++;
        }
    }
    // 随机DFS递归
    private boolean dfsRandom(int x, int y, boolean[][] visited, Stack<int[]> pathStack, Random rand) {
        if (x == endx && y == endy) {
            pathStack.push(new int[]{x, y});
            return true;
        }
        visited[x][y] = true;
        pathStack.push(new int[]{x, y});

        int[][] dirs = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
        ArrayList<int[]> dirList = new ArrayList<>();
        for (int[] d : dirs) dirList.add(d);
        Collections.shuffle(dirList, rand);

        for (int[] d : dirList) {
            int nx = x + d[0], ny = y + d[1];
            if (nx >= 0 && nx < side && ny >= 0 && ny < side && maze[nx][ny] == road && !visited[nx][ny]) {
                if (dfsRandom(nx, ny, visited, pathStack, rand)) {
                    return true;
                }
            }
        }
        pathStack.pop();
        return false;
    }

    // 获取一条正序的随机通路
    public ArrayList<int[]> getRandomPathList() {
        findRandomPath();
        ArrayList<int[]> list = new ArrayList<>(randomStack);
        Collections.reverse(list);
        return list;
    }

    public Stack<int[]> getStack() {
        return stack;
    }
}