import java.util.Random;

import client.Player;
import common.Point;

//テスト用プレイヤー
public class TestPlayer01 extends Player {
	private Random rand;

	@Override
	protected void newGame() {
		rand = new Random();
	}

	@Override
	protected Point turn(int nTurn) {
		enemy.dumpBoard();
		int x = rand.nextInt(boardSize);
		int y = rand.nextInt(boardSize);

		return new Point(x, y);
	}
}