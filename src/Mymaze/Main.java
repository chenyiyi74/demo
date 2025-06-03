package Mymaze;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.concurrent.atomic.AtomicInteger;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Main extends Application {
    int[][] map;

    @Override
    public void start(Stage primaryStage) {
        AnchorPane root = new AnchorPane();

        // 左侧正方形地图展示框
        Pane fixedMapPane = new Pane();
        fixedMapPane.setPrefSize(450, 450);
        fixedMapPane.setStyle("-fx-border-color: black; -fx-border-width: 2; -fx-background-color: green;");
        fixedMapPane.setLayoutX(30);
        fixedMapPane.setLayoutY(80);

        // 栈内容显示区
        Pane stackContentPane = new Pane();
        stackContentPane.setPrefSize(200, 450);
        stackContentPane.setStyle("-fx-border-color: black; -fx-border-width: 2; -fx-background-color: white;");
        stackContentPane.setLayoutX(500);
        stackContentPane.setLayoutY(80);

        // 右侧按钮区
        VBox buttonBox = new VBox(20);
        buttonBox.setPrefWidth(150);
        buttonBox.setStyle("-fx-alignment: center;");
        buttonBox.setLayoutX(730);
        buttonBox.setLayoutY(120);

        Text buttonTitle = new Text("手动地图选择");
        buttonTitle.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
        buttonBox.getChildren().add(buttonTitle);


        Button manual1Btn = new Button("地图1");
        Button manual2Btn = new Button("地图2");
        Button manual3Btn = new Button("地图3");
        buttonBox.getChildren().addAll(manual1Btn, manual2Btn, manual3Btn);

        VBox buttonBox2 = new VBox(20);
        buttonBox2.setPrefWidth(150);
        buttonBox2.setStyle("-fx-alignment: center;");
        buttonBox2.setLayoutX(730);
        buttonBox2.setLayoutY(300);


        Text buttonTitle2 = new Text("自动地图选择");
        buttonTitle2.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
        Text buttonTitle2Help = new Text("地图规格：25*25");
        buttonTitle2Help.setStyle("-fx-font-size: 10px; -fx-font-style: italic;");
        Text buttonTitle2Help2 = new Text("自定义规格");
        buttonTitle2Help2.setStyle("-fx-font-size: 10px; -fx-font-style: italic;");
        buttonBox2.getChildren().add(buttonTitle2);

        Button auto1Btn = new Button("prim算法（自动）");
        Button auto2Btn = new Button("prim算法（手动）");
        buttonBox2.getChildren().addAll(auto1Btn);
        buttonBox2.getChildren().add(buttonTitle2Help);
        buttonBox2.getChildren().add(auto2Btn);
        buttonBox2.getChildren().add(buttonTitle2Help2);
        // 顶部标题
        Text titleText = new Text("地图显示区域");
        titleText.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");
        titleText.setLayoutX(30);
        titleText.setLayoutY(50);

        // 栈区标题（始终显示在root上）
        Text stackTitle = new Text("路径显示区域：");
        stackTitle.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");
        stackTitle.setLayoutX(500);
        stackTitle.setLayoutY(50);

        Text Name=new Text("   /\\_/\\\n" +
                "  ( o.o )\n" +
                "  > ^ <\n");
        Name.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
        Name.setLayoutX(10);
        Name.setLayoutY(560);

        Text Name2=new Text("-----\n-----");
        Name2.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
        Name2.setLayoutX(900);
        Name2.setLayoutY(580);
        root.getChildren().addAll(fixedMapPane, stackContentPane, buttonBox, titleText, stackTitle, buttonBox2,Name,Name2);

        Scene scene = new Scene(root, 950, 650);
        primaryStage.setTitle("地图迷宫鼠");
        primaryStage.setScene(scene);
        primaryStage.show();

        // 按钮事件
        manual1Btn.setOnAction(e -> {
            fixedMapPane.getChildren().setAll(getManual1Pane(fixedMapPane, stackContentPane));
            stackContentPane.getChildren().clear();
        });
        manual2Btn.setOnAction(e -> {
            fixedMapPane.getChildren().setAll(getManual2Pane(fixedMapPane, stackContentPane));
            stackContentPane.getChildren().clear();
        });
        manual3Btn.setOnAction(e -> {
            fixedMapPane.getChildren().setAll(getManual3Pane(fixedMapPane, stackContentPane));
            stackContentPane.getChildren().clear();
        });
        auto1Btn.setOnAction(e ->{
            fixedMapPane.getChildren().setAll(getManual4Pane(fixedMapPane, stackContentPane));
            stackContentPane.getChildren().clear();
        });
        auto2Btn.setOnAction(e -> {
            fixedMapPane.getChildren().setAll(getManual5Pane(fixedMapPane, stackContentPane));
            stackContentPane.getChildren().clear();
        });
    }

    public Pane getManual1Pane(Pane parent, Pane stackContentPane) {
        Pane pane = new Pane();
        try {
            createMaze maze = new createMaze(25, 1, 1, 1);
            map = maze.getmaze();
            int side = map.length;
            Text titleText = new Text("地图1");
            titleText.setLayoutX(10);
            titleText.setLayoutY(30);
            titleText.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
            pane.getChildren().add(titleText);

            Button findpath = new Button("获取最短路径");
            findpath.setPrefHeight(40);
            findpath.setPrefWidth(100);
            findpath.setLayoutX((450 - 100) / 2.0);
            findpath.setLayoutY(450 - 40 - 10);
            findpath.setOnAction(f -> {
                try {
                    path bfs = new path(map, 1, 1, side - 2, side - 2);
                    Stack<int[]> stack = bfs.getStack();
                    StringBuilder sb = new StringBuilder();
                    sb.append("最短路径：\n");
                    List<int[]> list = new ArrayList<>(stack);
                    for (int i = list.size() - 1, count = 1; i >= 0; i--, count++) {
                        int[] pos = list.get(i);
                        sb.append("(").append(pos[0]).append(",").append(pos[1]).append(")");
                        if (count % 2 == 1) {
                            if(count>10)
                                sb.append("\t");
                            else
                                sb.append("\t\t");
                        } else {
                            sb.append("\n");
                        }
                    }
                    Text stackText = new Text(10, 30, sb.toString());
                    stackContentPane.getChildren().setAll(stackText);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                pane mp = new pane(map);
                mp.setLayoutX((650 - side * 20) / 2.0);
                mp.setLayoutY((650 - side * 20) / 2.0);
                parent.getChildren().setAll(mp, findpath, titleText);
            });
            pane mazePane = new pane(map);
            mazePane.setLayoutX((650 - side * 20) / 2.0);
            mazePane.setLayoutY((650 - side * 20) / 2.0);
            pane.getChildren().addAll(mazePane, findpath);
            return pane;
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        }
        return pane;
    }

    public Pane getManual2Pane(Pane parent, Pane stackContentPane) {
        Pane pane = new Pane();
        try {
            createMaze maze = new createMaze(25, 1, 1, 2);
            map = maze.getmaze();
            int side = map.length;

            Text titleText = new Text("地图2");
            titleText.setLayoutX(10);
            titleText.setLayoutY(30);
            titleText.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
            pane.getChildren().add(titleText);

            Button findpath = new Button("获取最短路径");
            findpath.setPrefHeight(40);
            findpath.setPrefWidth(100);
            findpath.setLayoutX((450 - 100) / 2.0);
            findpath.setLayoutY(450 - 40 - 10);
            findpath.setOnAction(f -> {
                try {
                    path bfs = new path(map, 1, 1, side - 2, side - 2);
                    Stack<int[]> stack = bfs.getStack();
                    StringBuilder sb = new StringBuilder();
                    sb.append("最短路径：\n");
                    List<int[]> list = new ArrayList<>(stack);
                    for (int i = list.size() - 1, count = 1; i >= 0; i--, count++) {
                        int[] pos = list.get(i);
                        sb.append("(").append(pos[0]).append(",").append(pos[1]).append(")");
                        if (count % 2 == 1) {
                            if(count>16)
                                sb.append("\t");
                            else
                                sb.append("\t\t");
                        } else {
                            sb.append("\n");
                        }
                    }
                    Text stackText = new Text(10, 30, sb.toString());
                    stackContentPane.getChildren().setAll(stackText);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                pane mp = new pane(map);
                mp.setLayoutX((650 - side * 20) / 2.0);
                mp.setLayoutY((650 - side * 20) / 2.0);
                parent.getChildren().setAll(mp, findpath, titleText);
            });
            pane mazePane = new pane(map);
            mazePane.setLayoutX((650 - side * 20) / 2.0);
            mazePane.setLayoutY((650 - side * 20) / 2.0);
            pane.getChildren().addAll(mazePane, findpath);
            return pane;
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        }
        return pane;
    }

    public Pane getManual3Pane(Pane parent, Pane stackContentPane) {
        Pane pane = new Pane();
        try {
            createMaze maze = new createMaze(25, 1, 1, 3);
            map = maze.getmaze();
            int side = map.length;

            Text titleText = new Text("地图3");
            titleText.setLayoutX(10);
            titleText.setLayoutY(30);
            titleText.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
            pane.getChildren().add(titleText);

            Button findpath = new Button("获取最短路径");
            findpath.setPrefHeight(40);
            findpath.setPrefWidth(100);
            findpath.setLayoutX((450 - 100) / 2.0);
            findpath.setLayoutY(450 - 40 - 10);
            findpath.setOnAction(f -> {
                try {
                    path bfs = new path(map, 1, 1, side - 2, side - 2);
                    Stack<int[]> stack = bfs.getStack();
                    StringBuilder sb = new StringBuilder();
                    sb.append("最短路径：\n");
                    List<int[]> list = new ArrayList<>(stack);
                    for (int i = list.size() - 1, count = 1; i >= 0; i--, count++) {
                        int[] pos = list.get(i);
                        sb.append("(").append(pos[0]).append(",").append(pos[1]).append(")");
                        if (count % 2 == 1) {
                            if(count>10)
                                sb.append("\t");
                            else
                                sb.append("\t\t");
                        } else {
                            sb.append("\n");
                        }
                    }
                    Text stackText = new Text(10, 30, sb.toString());
                    stackContentPane.getChildren().setAll(stackText);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                pane mp = new pane(map);
                mp.setLayoutX((650 - side * 20) / 2.0);
                mp.setLayoutY((650 - side * 20) / 2.0);
                parent.getChildren().setAll(mp, findpath, titleText);
            });
            pane mazePane = new pane(map);
            mazePane.setLayoutX((650 - side * 20) / 2.0);
            mazePane.setLayoutY((650 - side * 20) / 2.0);
            pane.getChildren().addAll(mazePane, findpath);
            return pane;
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        }
        return pane;
    }
    public Pane getManual4Pane(Pane parent, Pane stackContentPane) {
        Pane pane = new Pane();
        try {
            createMaze maze = new createMaze(25, 1, 1, 4,0);
            // 使用prim算法生成迷宫
            map = maze.getmaze();
            int side = map.length;

            Text titleText = new Text("自动地图");
            titleText.setLayoutX(10);
            titleText.setLayoutY(30);
            titleText.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
            pane.getChildren().add(titleText);

            Button findpath = new Button("获取最短路径");
            findpath.setPrefHeight(40);
            findpath.setPrefWidth(100);
            findpath.setLayoutX((450 - 100) / 2.0);
            findpath.setLayoutY(450 - 40 - 10);
            findpath.setOnAction(f -> {
                try {
                    path bfs = new path(map, 1, 1, side - 2, side - 2);
                    Stack<int[]> stack = bfs.getStack();
                    StringBuilder sb = new StringBuilder();
                    sb.append("最短路径：\n");
                    List<int[]> list = new ArrayList<>(stack);
                    for (int i = list.size() - 1, count = 1; i >= 0; i--, count++) {
                        int[] pos = list.get(i);
                        sb.append("(").append(pos[0]).append(",").append(pos[1]).append(")");
                        if (count % 4 == 0) {
                            sb.append("\n");
                        } else {
                            sb.append(" ");
                        }
                    }
                    Text stackText = new Text(10, 30, sb.toString());
                    stackContentPane.getChildren().setAll(stackText);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                pane mp = new pane(map);
                mp.setLayoutX((650 - side * 20) / 2.0);
                mp.setLayoutY((650 - side * 20) / 2.0);
                parent.getChildren().setAll(mp, findpath, titleText);
            });
            pane mazePane = new pane(map);
            mazePane.setLayoutX((650 - side * 20) / 2.0);
            mazePane.setLayoutY((650 - side * 20) / 2.0);
            pane.getChildren().addAll(mazePane, findpath);
            return pane;
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        }
        return pane;
    }
    public Pane getManual5Pane(Pane parent, Pane stackContentPane) {
        Pane pane = new Pane();

        // 输入框和标签
        Text sizeLabel = new Text("请输入迷宫尺寸:");
        sizeLabel.setLayoutX(10);
        sizeLabel.setLayoutY(60);
        sizeLabel.setStyle("-fx-font-size: 16px;");
        javafx.scene.control.TextField sizeField = new javafx.scene.control.TextField("25");
        sizeField.setLayoutX(150);
        sizeField.setLayoutY(40);
        sizeField.setPrefWidth(60);

        // 地图标题
        Text titleText = new Text("自动地图");
        titleText.setLayoutX(10);
        titleText.setLayoutY(30);
        titleText.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        // 确定按钮
        Button confirmBtn = new Button("确定");
        confirmBtn.setPrefHeight(30);
        confirmBtn.setPrefWidth(60);
        confirmBtn.setLayoutX(230);
        confirmBtn.setLayoutY(40);

        Button findpath = new Button("获取最短路径");
        findpath.setPrefHeight(40);
        findpath.setPrefWidth(100);
        findpath.setLayoutX((450 - 100) / 2.0);
        findpath.setLayoutY(450 - 40 - 10);
        findpath.setDisable(true); // 初始禁用

        // 用于存放地图面板，便于刷新
        Pane mazePane = new Pane();
        mazePane.setPrefSize(450, 450);
        mazePane.setLayoutX(0);
        mazePane.setLayoutY(100);

        pane.getChildren().addAll(titleText, sizeLabel, sizeField, confirmBtn, mazePane, findpath);

        confirmBtn.setOnAction(e -> {
            try {
                int side;
                try {
                    side = Integer.parseInt(sizeField.getText());
                    if (side < 5 || side > 30 || side % 2 == 0) {
                        sizeLabel.setText("请输入奇数且>=5的尺寸！");
                        findpath.setDisable(true);
                        mazePane.getChildren().clear();
                        return;
                    }
                } catch (NumberFormatException ex) {
                    sizeLabel.setText("请输入有效数字！");
                    findpath.setDisable(true);
                    mazePane.getChildren().clear();
                    return;
                }
                createMaze maze = new createMaze(side, 1, 1, 5, 1);
                map = maze.getmaze();

                // 只刷新mazePane内容，不清空parent
                mazePane.getChildren().clear();
                pane mp = new pane(map);
                int offset = Math.max(0, (450 - side * 20) / 2);
                mp.setLayoutX(offset);
                mp.setLayoutY(offset);
                mazePane.getChildren().add(mp);

                findpath.setDisable(false);
                stackContentPane.getChildren().clear();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });

// 获取最短路径按钮
        findpath.setOnAction(f -> {
            try {
                if (map == null) return;
                int side = map.length;
                path bfs = new path(map, 1, 1, side - 2, side - 2);
                Stack<int[]> stack = bfs.getStack();
                StringBuilder sb = new StringBuilder();
                sb.append("最短路径：\n");
                List<int[]> list = new ArrayList<>(stack);
                for (int i = list.size() - 1, count = 1; i >= 0; i--, count++) {
                    int[] pos = list.get(i);
                    sb.append("(").append(pos[0]).append(",").append(pos[1]).append(")");
                    if (count % 4 == 0) {
                        sb.append("\n");
                    } else {
                        sb.append(" ");
                    }
                }
                Text stackText = new Text(10, 30, sb.toString());
                stackContentPane.getChildren().setAll(stackText);

                // 高亮路径
                mazePane.getChildren().clear();
                pane mp = new pane(map);
                int offset = Math.max(0, (450 - side * 20) / 2);
                mp.setLayoutX(offset);
                mp.setLayoutY(offset);
                mazePane.getChildren().add(mp);

                parent.getChildren().clear(); // 清空地图区域
                parent.getChildren().add(mazePane);
                parent.getChildren().addAll(titleText, sizeLabel, sizeField, confirmBtn, findpath);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        return pane;
    }
    public static void main(String[] args) {
        Application.launch(args);
    }
}