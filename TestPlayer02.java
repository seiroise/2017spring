import java.util.*;

import client.Player;
import common.Point;

//テスト用プレイヤーその2
//とりあえず1マス飛ばしで
public class TestPlayer02 extends Player {
	private Random rand;

	@Override
	protected void newGame() {
		rand = new Random();
	}

	@Override
	protected Point turn(int nTurn) {
		System.out.println("turn[" + nTurn + "]");
		return CalcNext(nTurn - 1);
	}

	//次に打つ座標
	private Point CalcNext(int nTurn) {
		return new Point(
			(nTurn % 5 * 2) + (nTurn / 5 % 2),
			(((nTurn / 5) + (nTurn / 50 % 2)) % 10));
	}
}