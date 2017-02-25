import client.Player;
import common.Point;

public class SequentialPlayer extends Player {
  
  @Override
  protected void newGame() {

  }

  @Override
  protected Point turn(int nTurn) {
    enemy.dumpBoard();
    int x = (nTurn-1) / boardSize;
    int y = (nTurn-1) % boardSize;
    return new Point(x, y);
  }

}
