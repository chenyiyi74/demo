package Mymaze;

import java.io.FileNotFoundException;
import java.util.Stack;

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
        fixedMapPane.setStyle("-fx-border-color: black; -fx-border-width: 2; -fx-background-color: white;");
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

        Button manual1Btn = new Button("地图1");
        Button manual2Btn = new Button("地图2");
        Button manual3Btn = new Button("地图3");
        buttonBox.getChildren().addAll(manual1Btn, manual2Btn, manual3Btn);

        // 顶部标题
        Text titleText = new Text("请选择地图");
        titleText.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");
        titleText.setLayoutX(30);
        titleText.setLayoutY(50);

        // 栈区标题（始终显示在root上）
        Text stackTitle = new Text("路径坐标栈：");
        stackTitle.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");
        stackTitle.setLayoutX(500);
        stackTitle.setLayoutY(50);

        root.getChildren().addAll(fixedMapPane, stackContentPane, buttonBox, titleText, stackTitle);

        Scene scene = new Scene(root, 950, 650);
        primaryStage.setTitle("固定地图展示");
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
                    sb.append("最终路径：\n");
                    java.util.List<int[]> list = new java.util.ArrayList<>(stack);
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
                    javafx.scene.text.Text stackText = new javafx.scene.text.Text(10, 30, sb.toString());
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
                    sb.append("最终路径：\n");
                    java.util.List<int[]> list = new java.util.ArrayList<>(stack);
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
                    javafx.scene.text.Text stackText = new javafx.scene.text.Text(10, 30, sb.toString());
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
                    sb.append("最终路径：\n");
                    java.util.List<int[]> list = new java.util.ArrayList<>(stack);
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
                    javafx.scene.text.Text stackText = new javafx.scene.text.Text(10, 30, sb.toString());
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

    public static void main(String[] args) {
        Application.launch(args);
    }
}