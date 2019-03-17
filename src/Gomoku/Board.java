package Gomoku;
// Gomoku
// John Irle
// March 15, 2019

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Ellipse;
import javafx.stage.Stage;

public class Board extends Application {

  private String whoseTurn = "White";
  GridPane pane = new GridPane();
  private Piece[][] board = new Piece[19][19];
  private Label lblStatus = new Label("White's turn to play");
  Button btnAgain = new Button("Play again?");

  @Override
  public void start(Stage primaryStage) {

    // Initialize board
    initializeBoard(pane);

    // Style board
    pane.setPadding(new Insets(15,15,15,15));
    pane.setStyle("-fx-background-color: tan");

    btnAgain.managedProperty().bind(btnAgain.visibleProperty());
    btnAgain.setVisible(false);
    btnAgain.setOnMouseClicked(e -> handleAgain());

    HBox status = new HBox(330);
    status.getChildren().addAll(lblStatus, btnAgain);

    BorderPane borderPane = new BorderPane();
    borderPane.setCenter(pane);
    borderPane.setBottom(status);

    Scene scene = new Scene(borderPane, 600, 600);
    primaryStage.setTitle("Gomoku");
    primaryStage.setScene(scene);
    primaryStage.setResizable(false);
    primaryStage.show();
  }

  private void handleAgain() {
    // Reset Turn
    whoseTurn = "White";

    // Clear the board
    pane.getChildren().clear();

    // Reset grid
    initializeBoard(pane);

    // Reset board state
    for (int i = 0; i < board.length; i++) {
      for (int j = 0; j < board[i].length; j++) {
        board[i][j].setToken(" ");
      }
    }

    lblStatus.setText(whoseTurn + "'s turn");
    btnAgain.setVisible(false);
  }

  private void initializeBoard(GridPane pane) {
    for (int i = 0; i < 19; i++) {
      for (int j = 0; j < 19; j++) {
        pane.add(board[i][j] = new Piece(), j, i);
      }
    }
  }

  private void highlight(Piece piece) {
    piece.setStyle("-fx-background-color: yellow; -fx-border-color: black");
  }

  private boolean isFull() {
    for (int i = 0; i < board.length; i++) {
      for (int j=0; j < board[i].length; j++) {
        if (board[i][j].getToken().equals(" "));
        return false;
      }
    }

    return true;
  }

  // Check all win conditions
  private boolean hasPlayerWon(String player) {
    return isHorizontalWin(player) || isVerticalWin(player) || isDiagonalWin(player);

  }

  private boolean isHorizontalWin(String player) {

    for (int i = 0; i < board.length; i++) {
      for (int j = 0; j < board[i].length - 4; j++) {
        if (board[i][j].getToken().equals(player)
                && board[i][j + 1].getToken().equals(player)
                && board[i][j + 2].getToken().equals(player)
                && board[i][j + 3].getToken().equals(player)
                && board[i][j + 4].getToken().equals(player)) {

          highlight(board[i][j]);
          highlight(board[i][j + 1]);
          highlight(board[i][j + 2]);
          highlight(board[i][j + 3]);
          highlight(board[i][j + 4]);
          return true;

        }
      }
    }
    return false;
  }

  private boolean isVerticalWin(String player) {
    for (int i = 0; i < board.length - 4; i++) {
      for (int j = 0; j < board[i].length; j++) {
        if (board[i][j].getToken().equals(player)
                && board[i + 1][j].getToken().equals(player)
                && board[i + 2][j].getToken().equals(player)
                && board[i + 3][j].getToken().equals(player)
                && board[i + 4][j].getToken().equals(player)) {
          highlight(board[i][j]);
          highlight(board[i + 1][j]);
          highlight(board[i + 2][j]);
          highlight(board[i + 3][j]);
          highlight(board[i + 4][j]);
          return true;
        }
      }
    }
    return false;
  }

  private boolean isDiagonalWin(String player) {
    return checkFirstDiagonal(player) || checkSecondDiagonal(player);
  }

  private boolean checkFirstDiagonal(String player) {
    for (int i = 0; i < board.length - 4; i++) {
      for (int j = 0; j < board[i].length - 4; j++) {
        if (board[i][j].getToken().equals(player)
                && board[i + 1][j + 1].getToken().equals(player)
                && board[i + 2][j + 2].getToken().equals(player)
                && board[i + 3][j + 3].getToken().equals(player)
                && board[i + 4][j + 4].getToken().equals(player)) {
          highlight(board[i][j]);
          highlight(board[i + 1][j + 1]);
          highlight(board[i + 2][j + 2]);
          highlight(board[i + 3][j + 3]);
          highlight(board[i + 4][j + 4]);
          return true;
        }
      }
    }
    return false;
  }

  private boolean checkSecondDiagonal(String player) {
    for (int i = 0; i < board.length - 4; i++) {
      for (int j = 4; j < board[i].length; j++) {
        if (board[i][j].getToken().equals(player)
                && board[i + 1][j - 1].getToken().equals(player)
                && board[i + 2][j - 2].getToken().equals(player)
                && board[i + 3][j - 3].getToken().equals(player)
                && board[i + 4][j - 4].getToken().equals(player)) {
          highlight(board[i][j]);
          highlight(board[i + 1][j - 1]);
          highlight(board[i + 2][j - 2]);
          highlight(board[i + 3][j - 3]);
          highlight(board[i + 4][j - 4]);
          return true;
        }
      }
    }
    return false;
  }

  public class Piece extends Pane {
    private String token = " ";
    private Ellipse ellipse = new Ellipse(this.getWidth() / 2, this.getHeight() / 2,
            this.getWidth() / 2 - 10, this.getHeight() / 2 - 10);


    public Piece() {
      this.setPrefSize(2000, 2000);
      this.setStyle("-fx-border-color: black");
      ellipse.centerXProperty().bind(this.widthProperty().divide(2));
      ellipse.centerYProperty().bind(this.heightProperty().divide(2));
      ellipse.radiusXProperty().bind(this.widthProperty().divide(1.4).subtract(10));
      ellipse.radiusYProperty().bind(this.heightProperty().divide(1.4).subtract(10));
      this.setOnMouseClicked(e -> handleMouseClick());
    }

    public String getToken() {
      return token;
    }

    // Draw a piece
    public void setToken(String c) {
      token = c;

      RadialGradient white = new RadialGradient(0.5, 0.5,0, 0, 1.5,true, CycleMethod.REFLECT, new Stop(0, Color.WHITE), new Stop(1, Color.GREY));

      RadialGradient black = new RadialGradient(0.5, 0.5,0, 0, 1.5,true, CycleMethod.REFLECT, new Stop(0, Color.DARKSLATEGRAY), new Stop(1, Color.BLACK));


      if (token.equals("White")) {
        ellipse.setFill(white);
        getChildren().add(ellipse);
      } else if (token.equals("Black")) {
        ellipse.setFill(black);
        getChildren().add(ellipse);
      }
    }

    // Check for win, draw or continue on each click.
    private void handleMouseClick() {
      if (token.equals(" ") && !whoseTurn.equals(" ")) {
        setToken(whoseTurn);

        if (hasPlayerWon(whoseTurn)) {
          lblStatus.setText(whoseTurn + " won! The game is over.");
          whoseTurn = " ";
          btnAgain.setVisible(true);
        }
        else if (isFull()) {
          lblStatus .setText("Draw! The game is over.");
          whoseTurn = " ";
          btnAgain.setVisible(true);
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
