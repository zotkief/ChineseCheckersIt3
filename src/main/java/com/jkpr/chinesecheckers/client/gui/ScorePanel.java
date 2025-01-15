package com.jkpr.chinesecheckers.client.gui;

import com.jkpr.chinesecheckers.client.boards.AbstractBoardClient;
import com.jkpr.chinesecheckers.client.Client;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.control.Button;

public class ScorePanel extends HBox {
    private final AbstractBoardClient board;
    private final Client client;
    private Label turnLabel;
    private Label infoLabel;
    private Button skipButton;

    public ScorePanel(AbstractBoardClient board, Client client) {
        this.board = board;
        this.client = client;
        setAlignment(javafx.geometry.Pos.CENTER);
        setSpacing(20);

        infoLabel = new Label("Jesteś graczem " + board.getId());
        infoLabel.setStyle("-fx-font-size: 20px;");
        turnLabel = new Label("Ruch gracza: ");
        turnLabel.setStyle("-fx-font-size: 20px;");
        skipButton = new Button("Pomiń ruch");
        skipButton.setOnAction(e -> {
            System.out.println("SKIP");
            client.sendMessage("SKIP");
        });
        getChildren().addAll(infoLabel, turnLabel, skipButton);
    }

    public void refresh() {
        int currentInt = board.getCurrentTurn();
        String current = convertToString(currentInt);
        infoLabel.setText("Jesteś graczem " + convertToString(board.getId()));
        if(currentInt == -1) {
            turnLabel.setText("Ruch gracza: jeszcze nie rozpoczęty");
        } else if(currentInt == board.getId()) {
            turnLabel.setText("Twój ruch");
            turnLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: green; -fx-font-size: 20px;");
        } else {
            turnLabel.setStyle("-fx-font-size: 20px;");
            String text = "Ruch gracza: " + current;
            System.out.println(text);
            turnLabel.setText(text);
        }
    }
    private String convertToString(int number) {
        switch (number){
            case 0:
                  return "A";
            case 1:
                  return "B";
            case 2:
                  return "C";
            case 3:
                  return "D";
            case 4:
                  return "E";
            case 5:
                  return "F";
            default:
                  return "Nieznany";
        }
    }
}
