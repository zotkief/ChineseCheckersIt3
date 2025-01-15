package com.jkpr.chinesecheckers.client.gui;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class LoadingScreen extends VBox {
    private final Label loadingLabel;
    public LoadingScreen() {
        super();
        setAlignment(Pos.CENTER);
        setSpacing(10);
        loadingLabel = new Label("Oczekwianie na reszte graczy...");
        getChildren().add(loadingLabel);
    }

}
