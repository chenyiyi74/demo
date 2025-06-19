package Mymaze;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.animation.Timeline;

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

        ScrollPane stackScrollPane = new ScrollPane(stackContentPane);
        stackScrollPane.setPrefSize(200, 450);
        stackScrollPane.setLayoutX(500);
        stackScrollPane.setLayoutY(80);
        stackScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        stackScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        stackScrollPane.setFitToWidth(true);
        stackScrollPane.setPannable(true);

        // 右侧按钮区
        VBox buttonBox = new VBox(20);
        buttonBox.setPrefWidth(150);
        buttonBox.setAlignment(javafx.geometry.Pos.CENTER);
        buttonBox.setLayoutX(730);
        buttonBox.setLayoutY(80);
        buttonBox.setStyle("-fx-background-color: white; -fx-padding: 10px; -fx-border-color: black; -fx-border-width: 1px;");

        Text buttonTitle = new Text("手动地图选择");
        buttonTitle.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
        buttonBox.getChildren().add(buttonTitle);

        Button manual1Btn = new Button("地图1");
        Button manual2Btn = new Button("地图2");
        Button manual3Btn = new Button("地图3");
        buttonBox.getChildren().addAll(manual1Btn, manual2Btn, manual3Btn);

        VBox buttonBox2 = new VBox(20);
        buttonBox2.setPrefWidth(150);
        buttonBox2.setAlignment(javafx.geometry.Pos.CENTER);
        buttonBox2.setLayoutX(730);
        buttonBox2.setLayoutY(330);
        buttonBox2.setStyle("-fx-background-color: white; -fx-padding: 10px; -fx-border-color: black; -fx-border-width: 1px;");

        Text buttonTitle2 = new Text("自动地图选择");
        buttonTitle2.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
        Text buttonTitle2Help = new Text("地图规格：25*25");
        buttonTitle2Help.setStyle("-fx-font-size: 10px; -fx-font-style: italic;");
        Text buttonTitle2Help2 = new Text("自定义规格");
        buttonTitle2Help2.setStyle("-fx-font-size: 10px; -fx-font-style: italic;");

        Button auto1Btn = new Button("DFS算法（自动）");
        Button auto2Btn = new Button("DFS算法（手动）");
        buttonBox2.getChildren().add(buttonTitle2);
        buttonBox2.getChildren().addAll(auto1Btn);
        buttonBox2.getChildren().add(buttonTitle2Help);
        buttonBox2.getChildren().add(auto2Btn);
        buttonBox2.getChildren().add(buttonTitle2Help2);

        // 顶部标题
        Text titleText = new Text("地图显示区域");
        titleText.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");
        titleText.setLayoutX(30);
        titleText.setLayoutY(50);

        // 栈区标题
        Text stackTitle = new Text("路径显示区域：");
        stackTitle.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");
        stackTitle.setLayoutX(500);
        stackTitle.setLayoutY(50);

        Text Name=new Text("""
                   /\\_/\\
                  ( o.o )
                  > ^ <
                """);
        Name.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
        Name.setLayoutX(700);
        Name.setLayoutY(560);

        Text Name2=new Text("""
                   /\\_/\\
                  ( o.o )
                  > ^ <
                """);
        Name2.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
        Name2.setLayoutX(850);
        Name2.setLayoutY(560);
        root.getChildren().addAll(fixedMapPane, stackScrollPane, buttonBox, titleText, stackTitle, buttonBox2,Name,Name2);

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
        return getGeneralManualPane(parent, stackContentPane, 1, "地图1");
    }

    public Pane getManual2Pane(Pane parent, Pane stackContentPane) {
        return getGeneralManualPane(parent, stackContentPane, 2, "地图2");
    }

    public Pane getManual3Pane(Pane parent, Pane stackContentPane) {
        return getGeneralManualPane(parent, stackContentPane, 3, "地图3");
    }

    public Pane getManual4Pane(Pane parent, Pane stackContentPane) {
        return getGeneralManualPane(parent, stackContentPane, 4, "自动地图");
    }

    private Pane getGeneralManualPane(Pane parent, Pane stackContentPane, int mapType, String title) {
        Pane pane = new Pane();
        try {
            createMaze maze = (mapType == 4)
                    ? new createMaze(25, 1, 1, 4, 0)
                    : new createMaze(25, 1, 1, mapType);
            map = maze.getMaze();
            int side = map.length;
            final boolean[] isAutoPlaying = {false};
            final Timeline[] timelineHolder = {null};

            Text titleText = new Text(title);
            titleText.setLayoutX(10);
            titleText.setLayoutY(30);
            titleText.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
            pane.getChildren().add(titleText);

            Button find_path = new Button("直接获取路径");
            find_path.setPrefHeight(40);
            find_path.setPrefWidth(100);
            find_path.setLayoutX(5);
            find_path.setLayoutY(470);

            Button one_find = new Button("手动分步获取");
            one_find.setPrefHeight(40);
            one_find.setPrefWidth(100);
            one_find.setLayoutX(110);
            one_find.setLayoutY(470);

            Button one_find2 = new Button("自动分步获取");
            one_find2.setPrefHeight(40);
            one_find2.setPrefWidth(100);
            one_find2.setLayoutX(215);
            one_find2.setLayoutY(470);

            Button one_find3 = new Button("获取更多路径");
            one_find3.setPrefHeight(40);
            one_find3.setPrefWidth(100);
            one_find3.setLayoutX(320);
            one_find3.setLayoutY(470);

            path bfs = new path(map, 1, 1, side - 2, side - 2);
            Stack<int[]> stack = bfs.getStack();
            List<int[]> list = new ArrayList<>(stack);
            java.util.Collections.reverse(list);
            final int[] stepIndex = {0};

            find_path.setOnAction(f -> {
                try {
                    StringBuilder sb = new StringBuilder();
                    sb.append("最短路径：\n");
                    for (int i = 0, count = 1; i < list.size(); i++, count++) {
                        int[] pos = list.get(i);
                        sb.append("(").append(pos[0]).append(",").append(pos[1]).append(")");
                        if ((i+1) % 4 == 0) {
                            sb.append("\n");
                        } else {
                            sb.append(" ");
                        }
                    }
                    Text stackText = new Text(10, 30, sb.toString());
                    stackText.setWrappingWidth(190);
                    stackContentPane.getChildren().setAll(stackText);
                    stackContentPane.setPrefHeight(stackText.getBoundsInLocal().getHeight() + 20);

                    stepIndex[0] = 0;
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                pane mp = new pane(map);
                mp.setPath(list);
                mp.applyCss();
                mp.layout();
                double mapWidth = mp.getBoundsInLocal().getWidth();
                double mapHeight = mp.getBoundsInLocal().getHeight();
                mp.setLayoutX((450 - mapWidth) / 2);
                mp.setLayoutY((450 - mapHeight) / 2);
                parent.getChildren().setAll(mp, find_path, titleText, one_find, one_find2, one_find3);
            });

            one_find.setOnAction(f -> {
                try {
                    if (stepIndex[0] < list.size()) {
                        StringBuilder sb = new StringBuilder();
                        sb.append("路径步进：\n");
                        for (int i = 0; i <= stepIndex[0]; i++) {
                            int[] pos = list.get(i);
                            sb.append("(").append(pos[0]).append(",").append(pos[1]).append(")");
                            if ((i+1) % 4 == 0) {
                                sb.append("\n");
                            } else {
                                sb.append(" ");
                            }
                        }
                        Text stackText = new Text(10, 30, sb.toString());
                        stackText.setWrappingWidth(190);
                        stackContentPane.getChildren().setAll(stackText);
                        stackContentPane.setPrefHeight(stackText.getBoundsInLocal().getHeight() + 20);

                        List<int[]> subPath = list.subList(0, stepIndex[0] + 1);
                        pane mp = new pane(map);
                        mp.setPath(subPath);
                        mp.applyCss();
                        mp.layout();
                        double mapWidth = mp.getBoundsInLocal().getWidth();
                        double mapHeight = mp.getBoundsInLocal().getHeight();
                        mp.setLayoutX((450 - mapWidth) / 2);
                        mp.setLayoutY((450 - mapHeight) / 2);
                        parent.getChildren().setAll(mp, find_path, titleText, one_find, one_find2, one_find3);

                        stepIndex[0]++;
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });

            one_find2.setOnAction(f -> {
                if (!isAutoPlaying[0]) {
                    one_find2.setText("获取中...");
                    Timeline timeline = new Timeline();
                    timeline.setCycleCount(list.size() - stepIndex[0]);
                    timeline.getKeyFrames().add(new javafx.animation.KeyFrame(
                            javafx.util.Duration.seconds(0.5),
                            event -> {
                                if (stepIndex[0] < list.size()) {
                                    StringBuilder sb = new StringBuilder();
                                    sb.append("路径步进：\n");
                                    for (int i = 0; i <= stepIndex[0]; i++) {
                                        int[] pos = list.get(i);
                                        sb.append("(").append(pos[0]).append(",").append(pos[1]).append(")");
                                        if ((i+1) % 4 == 0) {
                                            sb.append("\n");
                                        } else {
                                            sb.append(" ");
                                        }
                                    }
                                    Text stackText = new Text(10, 30, sb.toString());
                                    stackText.setWrappingWidth(190);
                                    stackContentPane.getChildren().setAll(stackText);
                                    stackContentPane.setPrefHeight(stackText.getBoundsInLocal().getHeight() + 20);

                                    List<int[]> subPath = list.subList(0, stepIndex[0] + 1);
                                    pane mp = new pane(map);
                                    mp.setPath(subPath);
                                    mp.applyCss();
                                    mp.layout();
                                    double mapWidth = mp.getBoundsInLocal().getWidth();
                                    double mapHeight = mp.getBoundsInLocal().getHeight();
                                    mp.setLayoutX((450 - mapWidth) / 2);
                                    mp.setLayoutY((450 - mapHeight) / 2);
                                    parent.getChildren().setAll(mp, find_path, titleText, one_find, one_find2, one_find3);

                                    stepIndex[0]++;
                                }
                            }
                    ));
                    timeline.setOnFinished(ev -> {
                        isAutoPlaying[0] = false;
                        one_find2.setText("自动分步获取");
                    });
                    timelineHolder[0] = timeline;
                    isAutoPlaying[0] = true;
                    timeline.play();
                } else {
                    if (timelineHolder[0] != null) {
                        timelineHolder[0].stop();
                    }
                    isAutoPlaying[0] = false;
                    one_find2.setText("自动分步获取");
                }
            });

            one_find3.setOnAction(f -> {
                try {
                    List<int[]> randomPath = null;
                    // 最多尝试100次，直到找到为止
                    for (int tryCount = 0; tryCount < 100; tryCount++) {
                        path randomPathObj = new path(map, 1, 1, side - 2, side - 2);
                        randomPath = randomPathObj.getRandomPathList();
                        if (randomPath != null && randomPath.size() > 0) break;
                    }
                    if (randomPath == null || randomPath.size() == 0) {
                        Text stackText = new Text(10, 30, "依然未找到随机通路！");
                        stackText.setWrappingWidth(190);
                        stackContentPane.getChildren().setAll(stackText);
                        stackContentPane.setPrefHeight(stackText.getBoundsInLocal().getHeight() + 20);
                        return;
                    }
                    StringBuilder sb = new StringBuilder();
                    sb.append("非最短随机通路：\n");
                    for (int i = randomPath.size() - 1, count = 1; i >= 0; i--, count++) {
                        int[] pos = randomPath.get(i);
                        sb.append("(").append(pos[0] + 1).append(",").append(pos[1] + 1).append(")");
                        if (count % 4 == 0) {
                            sb.append("\n");
                        } else {
                            sb.append(" ");
                        }
                    }
                    Text stackText = new Text(10, 30, sb.toString());
                    stackText.setWrappingWidth(190);
                    stackContentPane.getChildren().setAll(stackText);
                    stackContentPane.setPrefHeight(stackText.getBoundsInLocal().getHeight() + 20);

                    pane mp = new pane(map);
                    mp.setPath(randomPath);
                    mp.applyCss();
                    mp.layout();
                    double mapWidth = mp.getBoundsInLocal().getWidth();
                    double mapHeight = mp.getBoundsInLocal().getHeight();
                    mp.setLayoutX((450 - mapWidth) / 2);
                    mp.setLayoutY((450 - mapHeight) / 2);
                    parent.getChildren().setAll(mp, find_path, titleText, one_find, one_find2, one_find3);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });

            pane mazePane = new pane(map);
            mazePane.applyCss();
            mazePane.layout();
            double mapWidth = mazePane.getBoundsInLocal().getWidth();
            double mapHeight = mazePane.getBoundsInLocal().getHeight();
            mazePane.setLayoutX((450 - mapWidth) / 2);
            mazePane.setLayoutY((450 - mapHeight) / 2);
            pane.getChildren().addAll(mazePane, find_path, one_find, one_find2, one_find3);
            return pane;
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        }
        return pane;
    }

    public Pane getManual5Pane(Pane parent, Pane stackContentPane) {
        Pane pane = new Pane();

        Text sizeLabel = new Text("请输入迷宫尺寸:\n(奇数且>=5,<=27)");
        sizeLabel.setLayoutX(100);
        sizeLabel.setLayoutY(30);
        sizeLabel.setStyle("-fx-font-size: 16px;");
        TextField sizeField = new TextField("25");
        sizeField.setLayoutX(280);
        sizeField.setLayoutY(15);
        sizeField.setPrefWidth(60);

        Text titleText = new Text("自动地图");
        titleText.setLayoutX(10);
        titleText.setLayoutY(30);
        titleText.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        Button confirmBtn = new Button("确定");
        confirmBtn.setPrefHeight(30);
        confirmBtn.setPrefWidth(60);
        confirmBtn.setLayoutX(360);
        confirmBtn.setLayoutY(10);

        Button find_path = new Button("直接获取路径");
        find_path.setPrefHeight(40);
        find_path.setPrefWidth(100);
        find_path.setLayoutX(5);
        find_path.setLayoutY(470);
        find_path.setDisable(true);

        Button one_find = new Button("手动分步获取");
        one_find.setPrefHeight(40);
        one_find.setPrefWidth(100);
        one_find.setLayoutX(110);
        one_find.setLayoutY(470);
        one_find.setDisable(true);

        Button one_find2 = new Button("自动分步获取");
        one_find2.setPrefHeight(40);
        one_find2.setPrefWidth(100);
        one_find2.setLayoutX(215);
        one_find2.setLayoutY(470);
        one_find2.setDisable(true);

        Button one_find3 = new Button("获取更多路径");
        one_find3.setPrefHeight(40);
        one_find3.setPrefWidth(100);
        one_find3.setLayoutX(320);
        one_find3.setLayoutY(470);
        one_find3.setDisable(true);

        pane.getChildren().addAll(titleText, sizeLabel, sizeField, confirmBtn, find_path, one_find, one_find2, one_find3);

        final List<int[]>[] pathList = new List[]{null};
        final int[] stepIndex = {0};
        final int[][][] mapHolder = {null};
        final boolean[] isAutoPlaying = {false};
        final Timeline[] timelineHolder = {null};

        confirmBtn.setOnAction(e -> {
            try {
                int side;
                try {
                    side = Integer.parseInt(sizeField.getText());
                    sizeLabel.setText("请输入迷宫尺寸:\n(奇数且>=5,<=27)");
                    if (side < 5 || side > 27 || side % 2 == 0) {
                        sizeLabel.setText("输入有误！");
                        find_path.setDisable(true);
                        one_find.setDisable(true);
                        one_find2.setDisable(true);
                        one_find3.setDisable(true);
                        return;
                    }
                } catch (NumberFormatException ex) {
                    sizeLabel.setText("请输入有效数字！");
                    find_path.setDisable(true);
                    one_find.setDisable(true);
                    one_find2.setDisable(true);
                    one_find3.setDisable(true);
                    return;
                }
                createMaze maze = new createMaze(side, 1, 1, 5, 1);
                map = maze.getMaze();
                mapHolder[0] = map;
                sizeLabel.setText("输入正确！");
                pane mp = new pane(map);
                mp.applyCss();
                mp.layout();
                double mapWidth = mp.getBoundsInLocal().getWidth();
                double mapHeight = mp.getBoundsInLocal().getHeight();
                mp.setLayoutX((450 - mapWidth) / 2);
                mp.setLayoutY((450 - mapHeight) / 2);

                find_path.setDisable(false);
                one_find.setDisable(false);
                one_find2.setDisable(false);
                one_find3.setDisable(false);
                stepIndex[0] = 0;
                stackContentPane.getChildren().clear();
                parent.getChildren().setAll(mp, titleText, sizeLabel, sizeField, confirmBtn, find_path, one_find, one_find2, one_find3);

                path bfs = new path(map, 1, 1, side - 2, side - 2);
                Stack<int[]> stack = bfs.getStack();
                List<int[]> list = new ArrayList<>(stack);
                java.util.Collections.reverse(list);
                pathList[0] = list;
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });

        find_path.setOnAction(f -> {
            try {
                if (mapHolder[0] == null || pathList[0] == null) return;
                List<int[]> list = pathList[0];
                StringBuilder sb = new StringBuilder();
                sb.append("最短路径：\n");
                for (int i = 0, count = 1; i < list.size(); i++, count++) {
                    int[] pos = list.get(i);
                    sb.append("(").append(pos[0] + 1).append(",").append(pos[1] + 1).append(")");
                    if ((i+1) % 4 == 0) {
                        sb.append("\n");
                    } else {
                        sb.append(" ");
                    }
                }
                Text stackText = new Text(10, 30, sb.toString());
                stackText.setWrappingWidth(190);
                stackContentPane.getChildren().setAll(stackText);
                stackContentPane.setPrefHeight(stackText.getBoundsInLocal().getHeight() + 20);

                stepIndex[0] = 0;
                pane mp = new pane(mapHolder[0]);
                mp.setPath(list);
                mp.applyCss();
                mp.layout();
                double mapWidth = mp.getBoundsInLocal().getWidth();
                double mapHeight = mp.getBoundsInLocal().getHeight();
                mp.setLayoutX((450 - mapWidth) / 2);
                mp.setLayoutY((450 - mapHeight) / 2);
                parent.getChildren().setAll(mp, titleText, sizeLabel, sizeField, confirmBtn, find_path, one_find, one_find2, one_find3);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        one_find.setOnAction(f -> {
            try {
                if (mapHolder[0] == null || pathList[0] == null) return;
                List<int[]> list = pathList[0];
                if (stepIndex[0] < list.size()) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("路径步进：\n");
                    for (int i = 0; i <= stepIndex[0]; i++) {
                        int[] pos = list.get(i);
                        sb.append("(").append(pos[0] + 1).append(",").append(pos[1] + 1).append(")");
                        if ((i+1) % 4 == 0) {
                            sb.append("\n");
                        } else {
                            sb.append(" ");
                        }
                    }
                    Text stackText = new Text(10, 30, sb.toString());
                    stackText.setWrappingWidth(190);
                    stackContentPane.getChildren().setAll(stackText);
                    stackContentPane.setPrefHeight(stackText.getBoundsInLocal().getHeight() + 20);

                    List<int[]> subPath = list.subList(0, stepIndex[0] + 1);
                    pane mp = new pane(mapHolder[0]);
                    mp.setPath(subPath);
                    mp.applyCss();
                    mp.layout();
                    double mapWidth = mp.getBoundsInLocal().getWidth();
                    double mapHeight = mp.getBoundsInLocal().getHeight();
                    mp.setLayoutX((450 - mapWidth) / 2);
                    mp.setLayoutY((450 - mapHeight) / 2);
                    parent.getChildren().setAll(mp, titleText, sizeLabel, sizeField, confirmBtn, find_path, one_find, one_find2, one_find3);

                    stepIndex[0]++;
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        one_find2.setOnAction(f -> {
            if (!isAutoPlaying[0]) {
                one_find2.setText("获取中...");
                Timeline timeline = new Timeline();
                List<int[]> list = pathList[0];
                timeline.setCycleCount(list == null ? 0 : list.size() - stepIndex[0]);
                timeline.getKeyFrames().add(new javafx.animation.KeyFrame(
                        javafx.util.Duration.seconds(0.5),
                        event -> {
                            if (mapHolder[0] == null || pathList[0] == null) return;
                            List<int[]> l = pathList[0];
                            if (stepIndex[0] < l.size()) {
                                StringBuilder sb = new StringBuilder();
                                sb.append("路径步进：\n");
                                for (int i = 0; i <= stepIndex[0]; i++) {
                                    int[] pos = l.get(i);
                                    sb.append("(").append(pos[0] + 1).append(",").append(pos[1] + 1).append(")");
                                    if ((i+1) % 4 == 0) {
                                        sb.append("\n");
                                    } else {
                                        sb.append(" ");
                                    }
                                }
                                Text stackText = new Text(10, 30, sb.toString());
                                stackText.setWrappingWidth(190);
                                stackContentPane.getChildren().setAll(stackText);
                                stackContentPane.setPrefHeight(stackText.getBoundsInLocal().getHeight() + 20);

                                List<int[]> subPath = l.subList(0, stepIndex[0] + 1);
                                pane mp = new pane(mapHolder[0]);
                                mp.setPath(subPath);
                                mp.applyCss();
                                mp.layout();
                                double mapWidth = mp.getBoundsInLocal().getWidth();
                                double mapHeight = mp.getBoundsInLocal().getHeight();
                                mp.setLayoutX((450 - mapWidth) / 2);
                                mp.setLayoutY((450 - mapHeight) / 2);
                                parent.getChildren().setAll(mp, titleText, sizeLabel, sizeField, confirmBtn, find_path, one_find, one_find2, one_find3);

                                stepIndex[0]++;
                            }
                        }
                ));
                timeline.setOnFinished(ev -> {
                    isAutoPlaying[0] = false;
                    one_find2.setText("自动分步获取");
                });
                timelineHolder[0] = timeline;
                isAutoPlaying[0] = true;
                timeline.play();
            } else {
                if (timelineHolder[0] != null) {
                    timelineHolder[0].stop();
                }
                isAutoPlaying[0] = false;
                one_find2.setText("自动分步获取");
            }
        });

        one_find3.setOnAction(f -> {
            try {
                if (mapHolder[0] == null) return;
                int side = mapHolder[0].length;
                List<int[]> randomPath = null;
                // 最多尝试100次，直到找到为止
                for (int tryCount = 0; tryCount < 100; tryCount++) {
                    path randomPathObj = new path(mapHolder[0], 1, 1, side - 2, side - 2);
                    randomPath = randomPathObj.getRandomPathList();
                    if (randomPath != null && randomPath.size() > 0) break;
                }
                if (randomPath == null || randomPath.size() == 0) {
                    Text stackText = new Text(10, 30, "依然未找到随机通路！");
                    stackText.setWrappingWidth(190);
                    stackContentPane.getChildren().setAll(stackText);
                    stackContentPane.setPrefHeight(stackText.getBoundsInLocal().getHeight() + 20);
                    return;
                }
                StringBuilder sb = new StringBuilder();
                sb.append("非最短随机通路：\n");
                for (int i = randomPath.size() - 1, count = 1; i >= 0; i--, count++) {
                    int[] pos = randomPath.get(i);
                    sb.append("(").append(pos[0] + 1).append(",").append(pos[1] + 1).append(")");
                    if (count % 4 == 0) {
                        sb.append("\n");
                    } else {
                        sb.append(" ");
                    }
                }
                Text stackText = new Text(10, 30, sb.toString());
                stackText.setWrappingWidth(190);
                stackContentPane.getChildren().setAll(stackText);
                stackContentPane.setPrefHeight(stackText.getBoundsInLocal().getHeight() + 20);

                pane mp = new pane(mapHolder[0]);
                mp.setPath(randomPath);
                mp.applyCss();
                mp.layout();
                double mapWidth = mp.getBoundsInLocal().getWidth();
                double mapHeight = mp.getBoundsInLocal().getHeight();
                mp.setLayoutX((450 - mapWidth) / 2);
                mp.setLayoutY((450 - mapHeight) / 2);
                parent.getChildren().setAll(mp, titleText, sizeLabel, sizeField, confirmBtn, find_path, one_find, one_find2, one_find3);
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