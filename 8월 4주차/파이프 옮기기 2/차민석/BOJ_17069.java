package day0827;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

/*
 * 맵의 가로, 세로, 대각선으로 온 경우의 수를 넣기
 * 
 * 시간 초과
 */
public class BOJ_17069 {
	
	static int N;
	static int[][][] map;
	static int[] di = { 0, 1, 1 }; // 가로, 세로, 대각선
	static int[] dj = { 1, 0, 1 };
		
	static class Point {
		int i, j, shape; // 좌표, shape 0:가로 1:세로 2:대각
		Point(int i, int j, int shape) {
			this.i = i;
			this.j = j;
			this.shape = shape;
		}
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());
		map = new int[N][N][3];
		
		for (int i = 0; i < N; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			for (int j = 0; j < N; j++) {
				int num = Integer.parseInt(st.nextToken());
				if (num == 0) {
					int[] temp = { 0, 0, 0 };
					map[i][j] = temp;					
				} else { // num == 1 ( 벽 )
					map[i][j] = null;
				}
			}
		}
		
		bfs();
		
		int ans = 0;
		for (int i = 0; i < 3; i++) {
			ans += map[N-1][N-1][i];
		}
		System.out.println(ans);
		
	}
	
	static void bfs() {
		Queue<Point> queue = new LinkedList<>();
		queue.add(new Point(0, 1, 0));
		map[0][1][0] = 1;
		while (!queue.isEmpty()) {
			Point now = queue.poll();
			
			boolean checkRD = true;
			for (int d = 0; d < 3; d++) {
				int ni = now.i + di[d];
				int nj = now.j + dj[d];
				if (ni < 0 || ni >= N || nj < 0 || nj >= N || map[ni][nj] == null) {
					checkRD = false;
				} else if ((now.shape == d || now.shape == 2) && d != 2) {
					queue.add(new Point(ni, nj, d));
					map[ni][nj][d] += map[now.i][now.j][now.shape];
				}
			}
			if (checkRD) {
				queue.add(new Point(now.i + 1, now.j + 1, 2));
				map[now.i + 1][now.j + 1][2] += map[now.i][now.j][now.shape];
			}			
		}
	}
}
