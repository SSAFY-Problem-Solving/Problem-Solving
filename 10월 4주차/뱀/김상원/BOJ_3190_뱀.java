package BOJ;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;
/*
 * 1. 뱀의 이동경로를 머리와 꼬리로 나누어서 진행하였다.
 * 2. 뱀을 visit함수를 통해 true인 곳이 뱀의 몸통이다.
 * 3. 사과를 먹었을 때랑 그냥 이동할 때만 잘 고려해주면 됀다.
 * 4. di, dj 방향 잘 고려하면 코드 간결해질 수 있다.
 */
public class BOJ_3190_뱀 {
	static int N, K, L;
	static int[][] field;
	static boolean[][] visit;
	static int[] di = { -1, 0, 1, 0 }; // 상, 우, 하, 좌
	static int[] dj = { 0, 1, 0, -1 };
	static final int UP = 0;
	static final int RIGHT = 1;
	static final int DOWN = 2;
	static final int LEFT = 3;

	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());
		field = new int[N + 1][N + 1];
		visit = new boolean[N + 1][N + 1];
		K = Integer.parseInt(br.readLine());
		StringTokenizer st;
		for (int i = 0; i < K; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			int x = Integer.parseInt(st.nextToken());
			int y = Integer.parseInt(st.nextToken());
			field[x][y] = 1;
		}
		L = Integer.parseInt(br.readLine());
		Queue<Direction> q = new LinkedList<>(); // 방향이 바뀌는 부분을 queue를 이용해 저장하였음
		Queue<Integer> end_q = new LinkedList<>(); // 꼬리방향을 queue로 따로 관리해줬음
		for (int i = 0; i < L; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			int idx = Integer.parseInt(st.nextToken());
			String dir = st.nextToken();
			q.add(new Direction(idx, dir));
		}
		Point front = new Point(1, 1); // 뱀 머리
		Point end = new Point(1, 1); // 뱀 꼬리
		visit[1][1] = true;
		int dir = RIGHT; // 처음 뱀의 방향
		int time = 0; // 이동횟수
		while (true) {
//			뱀 이동경로 찍어보기
//			System.out.println(time);
//			for (int i = 1; i <= N; i++) {
//				for (int j = 1; j <= N; j++) {
//					if (visit[i][j]) {
//						System.out.print("ㅁㅁㅁㅁ ");
//					} else {
//						System.out.print(visit[i][j] + " ");
//					}
//				}
//				System.out.println();
//			}
//			System.out.println("-----------------------------------");
			String flag = "S"; // 기본 flag는 S라는 값을 줘서 정해진 방향으로 가도록 하였음
			if (!q.isEmpty() && q.peek().index == time) { // queue를 peek한 값을 통해 방향전환을 시켜줌 
				flag = q.poll().dir; // L인지 D인지
			}
			if (flag.equals("D")) {
				dir = (dir + 1 > 3) ? 0 : dir + 1; // 오른쪽 90도
			} else if (flag.equals("L")) {
				dir = (dir - 1 < 0) ? 3 : dir - 1; // 왼쪽 90도
			} else {
				front.x = front.x + di[dir]; // 정해진 방향으로 이동
				front.y = front.y + dj[dir];
				if (front.x > N || front.x <= 0 || front.y > N || front.y <= 0 || visit[front.x][front.y]) { // 맵 벗어나면 게임 끝
					break;
				}
				if (field[front.x][front.y] == 1) {
					end_q.add(dir); // 꼬리 방향 queue에 추가
					visit[front.x][front.y] = true;
					field[front.x][front.y] = 0; // ***** 사과 먹었으면 초기화  *****
				} else {
					visit[end.x][end.y] = false;
					visit[front.x][front.y] = true;
					end_q.add(dir);  // 꼬리 방향 queue에 추가
					int end_dir = end_q.poll();
					end.x = end.x + di[end_dir];
					end.y = end.y + dj[end_dir];
				}
				time++;
			}
		}
		System.out.println(time + 1);
	}

	static class Point {
		int x, y;

		public Point(int x, int y) {
			this.x = x;
			this.y = y;
		}
	}

	static class Direction {
		int index;
		String dir;

		public Direction(int index, String dir) {
			this.index = index;
			this.dir = dir;
		}

	}
}
