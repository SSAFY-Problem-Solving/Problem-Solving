package day0903;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class BOJ_11559 {

	static int N = 12, M = 6, ans;
	static char[][] map;
	static boolean[][] visit;
	static int[] di = { -1, 1, 0, 0 };
	static int[] dj = { 0, 0, -1, 1 };
	static Stack<Point> removes;

	static class Point {
		int i, j;

		Point(int i, int j) {
			this.i = i;
			this.j = j;
		}
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		ans = 0;

		map = new char[N][];
		for (int i = 0; i < N; i++) {
			map[i] = br.readLine().toCharArray();
		}
		removes = new Stack<>();
		
		// 왼쪽 맨 밑에서 부터 오른쪽 위로 하나씩 탐색하면서 4개 이상 되는지 찾아보기
		search();
		
		System.out.println(ans);

	}

	static void search() {

		while (true) {
			// 실행시 마다 방문배열 새로 만들어준다.
			visit = new boolean[N][M];
			for (int i = N - 1; i >= 0; i--) {
				for (int j = 0; j < M; j++) {
					// 아직 방문하지 않은 곳일 때 그 점 기준 같은 색 BFS 탐색
					if (!visit[i][j] && map[i][j] != '.') {
						visit[i][j] = true;
						Queue<Point> queue = new LinkedList<>();
						queue.add(new Point(i, j));
						int sameCnt = 0;
						while (!queue.isEmpty()) {
							Point now = queue.poll();
							sameCnt++;
							// 일단 삭제 목록에 넣어두기 , 4개 안되면 다시 뺄 예정
							removes.push(now);
							for (int d = 0; d < 4; d++) {
								int ni = now.i + di[d];
								int nj = now.j + dj[d];
								if (ni >= 0 && ni < N && nj >= 0 && nj < M && !visit[ni][nj]
										&& map[ni][nj] == map[i][j]) {
									visit[ni][nj] = true;
									queue.add(new Point(ni, nj));
								}
							}
						}
						// 4가 안되면 스택에 넣은 녀석들 다시 뱉어낸다.
						if (sameCnt < 4) {
							for (int k = 0; k < sameCnt; k++) {
								removes.pop();
							}
						}
					}
				}
			}
			
			// 만약 removes 배열이 비어있다면 아무것도 터뜨릴 수 없으니 while문 종료
			if (removes.isEmpty()) {
				break;
			}

			// 배열전체 순회후 터뜨릴 녀석들 다 모은 녀석들을 터뜨리고 배열 재배치
			remove();
		}
	}

	static void remove() {
		// 스택 비우면서 해당 좌표 0으로 바꾸기
		while (!removes.isEmpty()) {
			Point p = removes.pop();
			map[p.i][p.j] = '0';
		}
		// 스택 다 비워지면 연쇄 1 추가
		ans++;
		
		// 배열 바닥으로 내리기
		relocation();
	}

	static void relocation() {
		// 매 열마다 0의 개수를 세고 0의 개수만큼 내리면서 0
		for (int j = 0; j < M; j++) {
			String str = "";
			// j열에서 0이 아닌 녀석들만 뽑아낸다.
			int zeroCnt = 0;
			for (int i = 0; i < N; i++) {
				if (map[i][j] != '0') {
					str += map[i][j];
				} else {
					zeroCnt++;
				}
			}
			// 0의 개수만큼 문자열의 크기가 줄어드므로 0의 개수 만큼 '.'을 붙인다.
			for (int i = 0; i < zeroCnt; i++) {
				str = '.' + str;
			}
			// 변경 내용을 다시 map에 저장
			for (int i = 0; i < N; i++) {
				map[i][j] = str.charAt(i);
			}
		}
	}
}
