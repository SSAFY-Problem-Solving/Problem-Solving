package day1029;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

public class BOJ_3190_뱀 {
	
	static int N, K, L, ans, dir;
	static int[][] map;
	static int[] di = { -1, 1, 0, 0 }; // 상, 하, 좌, 우
	static int[] dj = { 0, 0, -1, 1 };
	static Map<Integer, Character> dirMap;
	static List<Point> snake;	
		
	public static class Point {
		int i, j;
		Point(int i, int j) {
			this.i = i;
			this.j = j;
		}
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());
		K = Integer.parseInt(br.readLine());
		
		map = new int[N + 1][N + 1];
		StringTokenizer st = null;
		for (int k = 0; k < K; k++) {
			st = new StringTokenizer(br.readLine());
			int r = Integer.parseInt(st.nextToken());
			int c = Integer.parseInt(st.nextToken());
			map[r][c] = 1;
		}

		dirMap = new HashMap<>();
		L = Integer.parseInt(br.readLine());
		for (int l = 0; l < L; l++) {
			st = new StringTokenizer(br.readLine());
			int key = Integer.parseInt(st.nextToken());
			char value = st.nextToken().charAt(0);	
			dirMap.put(key, value);
		}
		
		snake = new LinkedList<>();
		dir = 3;
		ans = 0;
		play();
		
		System.out.println(ans);
	}
	
	private static void play() {
		// 뱀의 몸을 2로 넣어보기
		map[1][1] = 2;
		snake.add(new Point(1, 1));
		
		while (true) {			
			ans++;
			
			int ni = snake.get(0).i + di[dir];
			int nj = snake.get(0).j + dj[dir];
			
			if (ni <= 0 || ni > N || nj <= 0 || nj > N || map[ni][nj] == 2) {
				break;
			}
			
			if (map[ni][nj] == 0) { // 사과가 없을 때
				map[snake.get(snake.size() - 1).i][snake.get(snake.size() - 1).j] = 0; // 꼬리 부분 0으로
				snake.remove(snake.size() - 1); // 꼬리 삭제
				
			}
			map[ni][nj] = 2; 
			snake.add(0, new Point(ni, nj)); // 머리에 새로운 좌표 추가
			
			if (dirMap.containsKey(ans)) {
				rotate(dirMap.get(ans));
			}
		}
		
	}

	public static void rotate(char ch) {
		if ((dir == 3 && ch == 'L') || (dir == 2 && ch == 'D')) {
			dir = 0;
		} else if ((dir == 3 && ch == 'D') || (dir == 2 && ch == 'L')) {
			dir = 1;
		} else if ((dir == 0 && ch == 'L') || (dir == 1 && ch == 'D')) {
			dir = 2;
		} else if ((dir == 0 && ch == 'D') || (dir == 1 && ch == 'L')) {
			dir = 3;
		} 
	}
}
