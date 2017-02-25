import java.util.*;

import client.Player;
import common.Point;

//テスト用プレイヤーその3
//とりあえず1マス飛ばしで
//とりあえず当たったら周りをせめるで
public class TestPlayer03 extends Player {

	//探索スタックに積む要素
	private class ExpElem {
		public Point p;
		public int dirIndex;

		public ExpElem(Point p) {
			this.p = p;
			dirIndex = 0;
		}
	}

	private int boardSize;
	private Set<String> log;
	private int turnOffset;
	private Point prevPoint;

	//探索リソース
	private Point expDir[]= {
		new Point(1, 0),
		new Point(0, 1),
		new Point(-1, 0),
		new Point(0, -1)
	};
	private int dirIndex;
	private int expIndex;
	private boolean isExp;
	private Point prevExpPoint;
	private Stack<Point> expStack;

	@Override
	protected void newGame() {
		boardSize = common.Constants.BOARDSIZE;
		log = new HashSet<String>();
		turnOffset = 0;
		prevPoint = null;
		isExp = false;
		prevExpPoint = null;
		expStack = new Stack<Point>();
	}

	@Override
	protected Point turn(int nTurn) {
		nTurn--;
		System.out.println("turn[" + nTurn + "]");
		//次に打ち込む座標を計算
		Point next = null;
		if(enemy.getLastOpen() || isExp) {
			next = getNext_Open(nTurn);
		} else {
			prevPoint = next = getNext_Close(nTurn);
		}
		//ログに残しておく
		log.add(pointToStr(next));
		return next;
	}

	//開いた時の座標
	private Point getNext_Open(int nTurn) {
		//とりあえず4方向を見る(x+ と y- の方向を重視)
		if(!isExp) {
			isExp = true;
			expIndex = 1;
			dirIndex = 0;
		} else {
			//探索中で当たった場合はもう一度その方向へ進む
			if(enemy.getLastOpen()) {
				expIndex++;
			}
		}

		Point next = null;
		//次の座標を取得
		while(true) {
			if(dirIndex > 3) {
				isExp = false;
				return getNext_Close(nTurn);
			}
			next = new Point(
				prevPoint.getX() + expDir[dirIndex].getX() * expIndex,
				prevPoint.getY() + expDir[dirIndex].getY() * expIndex);
			//はみ出し確認 & 重複確認
			if((next.getX() < 0 || boardSize <= next.getX()) ||
				(next.getY() < 0 || boardSize <= next.getY()) ||
				log.contains(pointToStr(next))) {
				expIndex = 1;
				dirIndex++;
				continue;
			}
			break;
		}
		turnOffset--;
		System.out.println("--------Hit--------");
		return next;
	}

	//開かなかった時の座標
	private Point getNext_Close(int nTurn) {
		Point p = new Point(0, 0);
		int turn = 0;
		while(true) {
			//ターンのオフセット
			turn = nTurn + turnOffset;
			p = new Point(
				(turn % 5 * 2) + (turn / 5 % 2),
				(((turn / 5) + (turn / 50 % 2)) % 10));
			//重複確認
			if(!log.contains(pointToStr(p))) {
				break;
			} else {
				turnOffset++;
			}
		}
		System.out.println("Close : " + turnOffset);
		return p;
	}

	private String pointToStr(Point p) {
		return p.getX() + "," + p.getY(); 
	}
}