package Mymaze;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.*;


class createMaze {
    private int[][] maze;//手动迷宫
    private int side;
    private int option;

    createMaze(int side, int start_x, int start_y, int option) throws FileNotFoundException {
        //自动生成的构造方法
        this.side = side;
        this.option = option;
        read();
    }
    createMaze(int side,int start_x,int start_y,int option,int k) throws FileNotFoundException {
         this.side = side;
         this.option = option;
         prim();
         read();
    }
    public int[][] getMaze() {
        return maze;
    }

    public void read() throws FileNotFoundException {
        int i, j;
        switch (option) {
            case 1:
                File file1 = new File("Manual1.txt");
                maze = new int[25][25];
                i = 0;
                j = 0;
                try (
                        Scanner input = new Scanner(file1);
                ) {
                    while (input.hasNext()) {
                        if (j < 25) {
                            maze[i][j] = input.nextInt();
                            j++;
                        } else {
                            maze[i + 1][0] = input.nextInt();
                            j = 1;
                            i++;
                        }
                    }
                }
                break;
            case 2:
                File file2 = new File("Manual2.txt");
                maze = new int[25][25];
                i = 0;
                j = 0;
                try (
                        Scanner input = new Scanner(file2);
                ) {
                    while (input.hasNext()) {
                        if (j < 25) {
                            maze[i][j] = input.nextInt();
                            j++;
                        } else {
                            maze[i + 1][0] = input.nextInt();
                            j = 1;
                            i++;
                        }
                    }
                }
                break;
            case 3:
                File file3 = new File("Manual3.txt");
                maze = new int[25][25];
                i = 0;
                j = 0;
                try (
                        Scanner input = new Scanner(file3);
                ) {
                    while (input.hasNext()) {
                        if (j < 25) {
                            maze[i][j] = input.nextInt();
                            j++;
                        } else {
                            maze[i + 1][0] = input.nextInt();
                            j = 1;
                            i++;
                        }
                    }
                }
                break;
            case 4:
                File file4 = new File("Auto.txt");
                maze = new int[25][25];
                i=0;
                j=0;
                try (
                        Scanner input = new Scanner(file4);
                ) {
                    while (input.hasNext()) {
                        if (j < 25) {
                            maze[i][j] = input.nextInt();
                            j++;
                        } else {
                            maze[i + 1][0] = input.nextInt();
                            j = 1;
                            i++;
                        }
                    }
                }
                break;

            case 5:
                File file5 = new File("Auto.txt");
                maze = new int[side][side];
                i = 0;
                j = 0;
                try (
                        Scanner input = new Scanner(file5);
                ) {
                    while (input.hasNext()) {
                        if (j < side) {
                            maze[i][j] = input.nextInt();
                            j++;
                        } else {
                            maze[i + 1][0] = input.nextInt();
                            j = 1;
                            i++;
                        }
                    }
                }
        }
    }
    public void prim() {
        maze = new int[side][side];
        for (int i = 0; i < side; i++)
            for (int j = 0; j < side; j++)
                maze[i][j] = 0;

        Random rand = new Random();
        dfsMaze(1, 1, rand);

        // 随机打通一些墙，制造环路
        int extraOpen = side;
        for (int k = 0; k < extraOpen; k++) {
            int x = rand.nextInt(side - 2) + 1;
            int y = rand.nextInt(side - 2) + 1;
            if (maze[x][y] == 0) {
                int cnt = 0;
                if (maze[x-1][y] == 1) cnt++;
                if (maze[x+1][y] == 1) cnt++;
                if (maze[x][y-1] == 1) cnt++;
                if (maze[x][y+1] == 1) cnt++;
                if (cnt >= 2) maze[x][y] = 1;
            }
        }

        maze[1][0] = 0;
        maze[side-2][side-1] = 0;

        // 保存到文件
        try {
            File file = new File("Auto.txt");
            if (!file.exists()) {
                file.createNewFile();
            }
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < side; i++) {
                for (int j = 0; j < side; j++) {
                    sb.append(maze[i][j]).append(" ");
                }
                sb.append("\n");
            }
            java.nio.file.Files.write(file.toPath(), sb.toString().getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void dfsMaze(int x, int y, Random rand) {
        maze[x][y] = 1; // 标记为通路
        List<int[]> directions = new ArrayList<>();
        directions.add(new int[]{0, 2}); // 下
        directions.add(new int[]{2, 0}); // 右
        directions.add(new int[]{0, -2}); // 上
        directions.add(new int[]{-2, 0}); // 左
        Collections.shuffle(directions, rand);

        for (int[] dir : directions) {
            int nx = x + dir[0];
            int ny = y + dir[1];
            if (nx > 0 && nx < side - 1 && ny > 0 && ny < side - 1 && maze[nx][ny] == 0) {
                maze[x + dir[0] / 2][y + dir[1] / 2] = 1; // 打通墙壁
                dfsMaze(nx, ny, rand);
            }
        }
    }
}