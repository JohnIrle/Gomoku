package Gomoku;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.stage.Stage;

public class Board extends Application {
    private char whoseTurn = 'X';
    private Piece[][] board = new Piece[19][19];
    private Label lblStatus = new Label("White's turn to play");

    @Override
    public void start(Stage primaryStage) throws Exception{
        GridPane pane = new GridPane();
        for (int i = 0; i < 19; i++) {
            for (int j = 0; j < 19; j++) {
                pane.add(board[i][j] = new Piece(), j, i);
            }
        }

        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(pane);
        borderPane.setBottom(lblStatus);

        Scene scene = new Scene(borderPane, 450, 170);
        primaryStage.setTitle("Gomoku");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public boolean isFull() {
        for (int i = 0; i < 3; i++) {
            for (int j=0; j < 3; j++) {
                if (board[i][j].getToken() == ' ')
                    return false;
            }
        }

        return true;
    }

    public boolean hasPlayerWon(int player) {
        return isHorizontalWin(player) || isVerticalWin(player);

    }

    public boolean isHorizontalWin(int player) {
        for (int i = 0; i < board.length; i++) {
            int count = 0;
            for (int j = 0; j < board[i].length; j++) {
                if (player == 1) {
                    if (board[i][j].getToken() =='X') {
                        count++;
                    } else {
                        count = 0;
                    }
                } else if(player == 2) {
                    if (board[i][j].getToken() == 'O') {
                        count++;
                    } else {
                        count = 0;
                    }
                }

                if (count == 5) {
                    return true;
                }
            }
        }

        return false;
    }

    public boolean isVerticalWin(int player) {

        int count = 0;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                for (int k = i + 1; k < board.length; k++) {
                    if (player == 1) {
                        if(board[i][j].getToken() == 'X' && board[k][j].getToken() == 'x') {
                            count++;
                        } else {
                            count = 0;
                        }
                    }

                    if (player == 2) {
                        if(board[i][j].getToken() == 'O' && board[k][j].getToken() == 'O') {
                            count++;
                        } else {
                            count = 0;
                        }
                    }

                    if(count == 4) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public class Piece extends Pane {
        private char token = ' ';

        public Piece() {
            setStyle("-fx-border-color: black");
            this.setPrefSize(2000, 2000);
            this.setOnMouseClicked(e -> handleMouseClick());
        }

        public char getToken() {
            return token;
        }

        public void setToken(char c) {
            token = c;

            if (token == 'X') {
                Ellipse ellipse = new Ellipse(this.getWidth() / 2, this.getHeight() / 2,
                        this.getWidth() / 2 - 10, this.getHeight() / 2 - 10);
                ellipse.centerXProperty().bind(this.widthProperty().divide(2));
                ellipse.centerYProperty().bind(this.heightProperty().divide(2));
                ellipse.radiusXProperty().bind(this.widthProperty().divide(2).subtract(10));
                ellipse.radiusYProperty().bind(this.heightProperty().divide(2).subtract(10));
                ellipse.setStroke(Color.BLACK);
                ellipse.setFill(Color.WHITE);

                getChildren().add(ellipse);
            } else if (token == 'O') {
                Ellipse ellipse = new Ellipse(this.getWidth() / 2, this.getHeight() / 2,
                        this.getWidth() / 2 - 10, this.getHeight() / 2 - 10);
                ellipse.centerXProperty().bind(this.widthProperty().divide(2));
                ellipse.centerYProperty().bind(this.heightProperty().divide(2));
                ellipse.radiusXProperty().bind(this.widthProperty().divide(2).subtract(10));
                ellipse.radiusYProperty().bind(this.heightProperty().divide(2).subtract(10));
                ellipse.setStroke(Color.WHITE);
                ellipse.setFill(Color.BLACK);

                getChildren().add(ellipse);
            }
        }


        private void handleMouseClick() {
            if (token == ' ' && whoseTurn != ' ') {
                setToken(whoseTurn);

                if (hasPlayerWon(whoseTurn)) {
                    lblStatus.setText(whoseTurn + " won! The game is over");
                    whoseTurn = ' ';
                }
                else if (isFull()) {
                    lblStatus .setText("Draw! The game is over");
                    whoseTurn = ' ';
                }
                else {
                    whoseTurn = (whoseTurn == 'X') ? 'O' : 'X';
                    lblStatus.setText(whoseTurn + "'s turn");
                }
            }
        }
    }


    public static void main(String[] args) {
        launch(args);
    }
}
