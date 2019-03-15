package Gomoku;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.stage.Stage;

public class Board extends Application {
    private String whoseTurn = "White";
    private Piece[][] board = new Piece[19][19];
    private Label lblStatus = new Label("White's turn to play");

    @Override
    public void start(Stage primaryStage) {

        GridPane pane = new GridPane();
        for (int i = 0; i < 19; i++) {
            for (int j = 0; j < 19; j++) {
                pane.add(board[i][j] = new Piece(), j, i);
            }
        }


        pane.setPadding(new Insets(15,15,15,15));
        pane.setStyle("-fx-background-color: tan");

        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(pane);
        borderPane.setBottom(lblStatus);

        Scene scene = new Scene(borderPane, 600, 600);
        primaryStage.setTitle("Gomoku");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public void highlight(int[][] pieces) {
      board[3][2].setStyle("-fx-background-color: yellow; -fx-border-color: black");
    }

    public boolean isFull() {
        for (int i = 0; i < 3; i++) {
            for (int j=0; j < 3; j++) {
                if (board[i][j].getToken().equals(" "));
                    return false;
            }
        }

        return true;
    }

    public boolean hasPlayerWon(String player) {
        return isHorizontalWin(player) || isVerticalWin(player);

    }

    public boolean isHorizontalWin(String player) {
        for (int i = 0; i < board.length; i++) {
            int count = 0;
            for (int j = 0; j < board[i].length; j++) {
                if (player.equals("White")) {
                    if (board[i][j].getToken().equals("White")) {
                        count++;
                    } else {
                        count = 0;
                    }
                } else if(player.equals("Black")) {
                    if (board[i][j].getToken().equals("Black")) {
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

    public boolean isVerticalWin(String player) {

        int count = 0;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                for (int k = i + 1; k < board.length; k++) {
                    if (player.equals("White")) {
                        if(board[i][j].getToken().equals("White") && board[k][j].getToken().equals("White")) {
                            count++;
                        } else {
                            count = 0;
                        }
                    }

                    if (player.equals("Black")) {
                        if(board[i][j].getToken().equals("Black") && board[k][j].getToken().equals("Black")) {
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
        private String token = " ";

        public Piece() {
            this.setPrefSize(2000, 2000);
            this.setStyle("-fx-border-color: black");
            this.setOnMouseClicked(e -> handleMouseClick());
        }

        public String getToken() {
            return token;
        }

        public void setToken(String c) {
            token = c;


            if (token.equals("White")) {
                Ellipse ellipse = new Ellipse(this.getWidth() / 2, this.getHeight() / 2,
                        this.getWidth() / 2 - 10, this.getHeight() / 2 - 10);
                ellipse.centerXProperty().bind(this.widthProperty().divide(2));
                ellipse.centerYProperty().bind(this.heightProperty().divide(2));
                ellipse.radiusXProperty().bind(this.widthProperty().divide(1.4).subtract(10));
                ellipse.radiusYProperty().bind(this.heightProperty().divide(1.4).subtract(10));
                ellipse.setStroke(Color.BLACK);
                ellipse.setFill(Color.WHITE);

                getChildren().add(ellipse);
                highlight(new int[][]{{2,3}});
            } else if (token.equals("Black")) {
                Ellipse ellipse = new Ellipse(this.getWidth() / 2, this.getHeight() / 2,
                        this.getWidth() / 2 - 10, this.getHeight() / 2 - 10);
                ellipse.centerXProperty().bind(this.widthProperty().divide(2));
                ellipse.centerYProperty().bind(this.heightProperty().divide(2));
                ellipse.radiusXProperty().bind(this.widthProperty().divide(1.4).subtract(10));
                ellipse.radiusYProperty().bind(this.heightProperty().divide(1.4).subtract(10));

                ellipse.setFill(Color.BLACK);

                getChildren().add(ellipse);
            }
        }


        private void handleMouseClick() {
            if (token.equals(" ") && !" ".equals(whoseTurn)) {
                setToken(whoseTurn);

                if (hasPlayerWon(whoseTurn)) {
                    lblStatus.setText(whoseTurn + " won! The game is over");
                    whoseTurn.equals(" ");
                }
                else if (isFull()) {
                    lblStatus .setText("Draw! The game is over");
                    whoseTurn.equals(" ");
                }
                else {
                    whoseTurn = (whoseTurn.equals("White")) ? "Black" : "White";
                    lblStatus.setText(whoseTurn + "'s turn");
                }
            }
        }
    }


    public static void main(String[] args) {
        launch(args);
    }
}
