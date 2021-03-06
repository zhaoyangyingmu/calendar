package ui.pane;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import kernel.Display;
import todoitem.Item;
import ui.Config;

public class PromptPopPane extends StackPane {
    private Item item;
    private Label label;
    public PromptPopPane(Item item) {
        int row = 0 ;
        int col = 0;
        GridPane contentGrid = new GridPane();
        Label promptLabel = new Label("提示");
        promptLabel.getStyleClass().add("promptLabel");
        contentGrid.add(promptLabel , col , row++);
        label = new Label("暂无待办事项");
        label.getStyleClass().add("hintLabel");
        contentGrid.add(label, col , row++);
        Label knowBt = new Label("朕知道了");
        knowBt.getStyleClass().add("button");
        Label setBt = new Label("设置");
        setBt.getStyleClass().add("button");
        GridPane buttons = new GridPane();
        buttons.getStyleClass().add("buttons");
        buttons.add(setBt, 0 , 0);
        setBt.setOnMouseClicked(event -> {
            if(item != null) {
                PromptSetPane.getInstance().setItem(item);
                Display.addPromptSetPane();
            }
        });

        buttons.add(knowBt , 1, 0);
        knowBt.setOnMouseClicked(event -> {
            Display.removePromptPopPane(this);
        });

        buttons.setAlignment(Pos.CENTER);
        buttons.setHgap(10);
        contentGrid.add(buttons, col , row++);
        contentGrid.setMaxSize(390, 240);
        contentGrid.setMinSize(390 , 240);
        contentGrid.getStyleClass().add("mainPrompt");
        this.item = item;
        long currentMinutes = System.currentTimeMillis()/ (60*1000);
        long startMinutes = item.getFrom().getMinutes();
        long minutesDelta = startMinutes - currentMinutes;
        int day = (int) minutesDelta / (60 * 24);
        int hour = ((int) minutesDelta - 60 * 24 * day) / 60;
        int minute = (int) minutesDelta % 60;
        String timeStr = "距离待办事项开始还有"+day + "天，"+ hour + "小时，" + minute + "分钟！\n";
        String typeStr = "待办事项：" + item.getItemType().getCnTypeStr() + "\n";
        String detailStr = "事项详情：" + item.getDetailText() + "\n";

        // other information
        String otherStr = "其他： ";
        switch(item.getItemType()) {
            case CUSTOM:
                otherStr += "无";
                break;
            case MEETING:
                otherStr += "\n主题：" + item.getValue("topic") + "\n地点：" + item.getValue("place")
                        + "\n内容：" + item.getValue("content");
                break;
            case DATE:
                otherStr += "\n对象：" + item.getValue("people") + "\n地点：" + item.getValue("place") +
                        "\n约会内容：" + item.getValue("content");
                break;
            case ANNIVERSARY:
                otherStr += "\n纪念日类型：" + item.getValue("anniversaryType")
                        + "\n纪念日描述：" + item.getValue("description");
                break;
            case COURSE:
                otherStr += "\n课程名：" + item.getValue("name")
                        + "\n课程内容：" + item.getValue("content")
                        + "\n老师： " + item.getValue("teacher")
                        + "\n课程备忘：" + item.getValue("remark")
                        + "\n地点： " + item.getValue("place");
                break;
            case TRAVEL:
                otherStr += "\n目的地：" + item.getValue("place")
                        + "\n方式：" + item.getValue("way")
                        + "\n航班号：" + item.getValue("number")
                        + "\n备忘： " + item.getValue("remark");
            case INTERVIEW:
                otherStr += "\n面试公司： " + item.getValue("company")
                        + "\n面试岗位： " + item.getValue("job")
                        + "\n地点：" + item.getValue("place")
                        + "\n备忘： " + item.getValue("remark");
                break;
        }

        label.setText(timeStr + typeStr + detailStr + otherStr);
        this.getChildren().add(contentGrid);
        this.getStyleClass().add("mainScene");
        this.setMaxSize(1200, 720);
        this.setMinSize(1200 , 720);
        this.setStyle("-fx-background-color: rgba(0 ,0 , 0 , 0.7);");
        contentGrid.getStylesheets().add(Config.class.getResource("/stylesheet/promptPopPane.css").toString());
    }
}
