import java.util.*;

import client.Player;
import common.Point;

//テスト用プレイヤーその6
//とりあえず1マス飛ばしで
//とりあえず当たったら周りをせめるで
public class TestPlayer06 extends Player {

	private int boardSize;
	private Set<String> log;
	private Set<String> hitLog;
	private int turnOffset;
	private Point prevHitPoint;
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
		hitLog = new HashSet<String>();
		turnOffset = 0;
		prevHitPoint = null;
		prevPoint = null;
		isExp = false;
		prevExpPoint = null;
		expStack = new Stack<Point>();
	}

	@Override
	protected Point turn(int nTurn) {
		nTurn--;
		enemy.dumpBoard();
		//System.out.println("turn[" + nTurn + "]");
		//次に打ち込む座標を計算
		Point next = null;
		if(enemy.getLastOpen() || isExp) {
			next = getNext_Open(nTurn);
		} else {
			next = getNext_Close(nTurn);
		}
		prevPoint = next;
		//ログに残しておく
		log.add(pointToStr(next));
		return next;
	}

	//開いた時の座標
	private Point getNext_Open(int nTurn) {
		if(!isExp) {
			isExp = true;
			prevHitPoint = prevPoint;
			expIndex = 1;
			dirIndex = 0;
		} else {
			if(enemy.getLastOpen()) {
				hitLog.add(pointToStr(prevPoint));
				expIndex++;
			} else {
				expIndex = 1;
				dirIndex++;
			}
		}

		Point next = null;
		while(true) {
			if(dirIndex > 3) {
				isExp = false;
				return getNext_Close(nTurn);
			}
			next = new Point(
				prevHitPoint.getX() + expDir[dirIndex].getX() * expIndex,
				prevHitPoint.getY() + expDir[dirIndex].getY() * expIndex);
			//はみ出し確認
			if((next.getX() < 0 || boardSize <= next.getX()) ||
				(next.getY() < 0 || boardSize <= next.getY())) {
				expIndex = 1;
				dirIndex++;
				continue;
			}
			//探索距離確認
			if(expIndex > 5) {
				dirIndex++;
				continue;
			}
			//あたり重複確認
			if(hitLog.contains(pointToStr(next))) {
				expIndex++;
				continue;
			}
			//重複確認
			if(log.contains(pointToStr(next))) {
				expIndex = 1;
				dirIndex++;
				continue;
			}
			break;
		}
		turnOffset--;
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
				(((turn / 5) + (turn / 50 % 2)) % 10),
				(turn % 5 * 2) + (turn / 5 % 2));
			//重複確認
			if(!log.contains(pointToStr(p))) {
				break;
			} else {
				turnOffset++;
			}
		}
		return p;
	}

	private String pointToStr(Point p) {
		return p.getX() + "," + p.getY(); 
	}
}