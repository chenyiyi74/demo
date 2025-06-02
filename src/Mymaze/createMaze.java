package Mymaze;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

class createMaze {
    private int[][] maze;//手动迷宫
    private int[][] maze1;//prim算法
    private int[][] maze2;//递归分割
    private static final int road = 1;//0代表墙，1代表路
    private static final int wall = 0;
    private int side;
    private int option;


    createMaze(int side, int start_x, int start_y, int option) throws FileNotFoundException {
        //自动生成的构造方法
        this.side = side;
        this.option = option;
        read();
    }

    public int[][] getmaze() {
        return maze;
    }

    /*******************读取 ***********************/
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
        }
    }
}