package backjoon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class N12100 {
	static int N, maxValue = 0;
	static int[][] map, origin;
	static Queue<Integer> oldLine, newLine;
	static int[] order = new int[5];

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());

		StringTokenizer st;
		origin = new int[N][N];
		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 0; j < N; j++) {
				origin[i][j] = Integer.parseInt(st.nextToken());
				maxValue = Math.max(maxValue, origin[i][j]);
			}
		}
		setOrder(0);
		System.out.println(maxValue);
	}

	public static void setOrder(int cnt) { // 움직이는 방향 순서 정하기(중복순열)
		if (cnt == 5) {
			map = new int[N][N];
			for (int i = 0; i < N; i++) {
				for (int j = 0; j < N; j++)
					map[i][j] = origin[i][j];
			}

			for (int d : order)
				playGame(d);
			
			return ;
		}

		for (int i = 0; i < 4; i++) {
			order[cnt] = i;
			setOrder(cnt + 1);
		}
	}

	public static void playGame(int d) { // 정해진 순서대로 움직이기
		oldLine = new LinkedList<>();
		newLine = new LinkedList<>();

		if (d == 0) {
			for (int c = 0; c < N; c++) {
				for (int r = 0; r < N; r++) {
					if (map[r][c] != 0) {
						oldLine.add(map[r][c]);
						map[r][c] = 0;
					}
				}
				moveGame();

				int r = 0;
				while (!newLine.isEmpty()) {
					map[r++][c] = newLine.poll();
				}
			}
		} else if (d == 1) {
			for (int c = 0; c < N; c++) {
				for (int r = N - 1; r >= 0; r--) {
					if (map[r][c] != 0) {
						oldLine.add(map[r][c]);
						map[r][c] = 0;
					}
				}
				moveGame();

				int r = N - 1;
				while (!newLine.isEmpty()) {
					map[r--][c] = newLine.poll();
				}
			}
		} else if (d == 2) {
			for (int r = 0; r < N; r++) {
				for (int c = 0; c < N; c++) {
					if (map[r][c] != 0) {
						oldLine.add(map[r][c]);
						map[r][c] = 0;
					}
				}
				moveGame();

				int c = 0;
				while (!newLine.isEmpty()) {
					map[r][c++] = newLine.poll();
				}
			}
		} else if (d == 3) {
			for (int r = 0; r < N; r++) {
				for (int c = N - 1; c >= 0; c--) {
					if (map[r][c] != 0) {
						oldLine.add(map[r][c]);
						map[r][c] = 0;
					}
				}
				moveGame();

				int c = N - 1;
				while (!newLine.isEmpty()) {
					map[r][c--] = newLine.poll();
				}
			}
		}
	}

	public static void moveGame() { // 한줄씩 떼와서 합치기
		boolean mergy = false;
		int past = -1, now = -1;

		while (!oldLine.isEmpty()) {
			if (past == -1) {
				past = oldLine.poll();
				mergy = false;
				continue;
			}

			now = oldLine.poll();
			if (past == now && !mergy) {
				mergy = true;
				newLine.add(past * 2);
				maxValue = Math.max(maxValue, past*2);
				past = -1;
				now = -1;
			} else {
				newLine.add(past);
				past = now;
				now = -1;
				mergy = false;
			}
		}
		if (past != -1)
			newLine.add(past);
	}
}
