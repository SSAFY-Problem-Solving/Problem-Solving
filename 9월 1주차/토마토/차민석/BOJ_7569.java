package day0828;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

/*
 * 1은 익은 토마토 0은 안익은 토마토 -1은 토마토 X
 * 익은 토마토는 하루하루마다 동시에 퍼져나가기때문에 익은토마토를 먼저 큐에 넣는다.
 * 한번 퍼진 곳은 1로 변해서 0인 곳만 가게 한다면 visit배열이 필요 없다.
 * 가장 밑에부터 위에까지 주어지므로 가장 밑이 h: 0이다.
 * 
 * while문에서 무한루프... 인덱스 i,j,h 주의하기
 */
public class BOJ_7569 {

	// 위, 아래, 왼쪽, 오른쪽, 앞, 뒤
	static int[] dh = { 1, -1, 0, 0, 0, 0 };
	static int[] di = { 0, 0, 0, 0, 1, -1 };
	static int[] dj = { 0, 0, -1, 1, 0, 0 };

	static int M, N, H, ans;
	static int[][][] map;
	static Queue<Tomato> queue;

	static class Tomato {
		int h, i, j, d;

		Tomato(int h, int i, int j, int d) {
			this.h = h;
			this.i = i;
			this.j = j;
			this.d = d;
		}
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		M = Integer.parseInt(st.nextToken()); // 가로 col
		N = Integer.parseInt(st.nextToken()); // 세로 row
		H = Integer.parseInt(st.nextToken()); // 높이

		ans = 0;
		queue = new LinkedList<>();
		map = new int[H][N][M];
		for (int h = 0; h < H; h++) {
			for (int i = 0; i < N; i++) {
				st = new StringTokenizer(br.readLine());
				for (int j = 0; j < M; j++) {
					map[h][i][j] = Integer.parseInt(st.nextToken());
					// 익은 토마토면 큐에 넣기
					if (map[h][i][j] == 1) {
						queue.add(new Tomato(h, i, j, 0));
					}
				}
			}
		}

		bfs();

		System.out.println(ans);
	}

	static void bfs() {
		while (!queue.isEmpty()) {
			Tomato now = queue.poll();

			ans = now.d;

			for (int d = 0; d < 6; d++) {
				int nh = now.h + dh[d];
				int ni = now.i + di[d];
				int nj = now.j + dj[d];
				if (nh >= 0 && nh < H && ni >= 0 && ni < N && nj >= 0 && nj < M	&& map[nh][ni][nj] == 0) {
					map[nh][ni][nj] = 1;
					queue.add(new Tomato(nh, ni, nj, now.d + 1));
				}
			}
		}

		// while 끝나면 다 익든지 아니면 -1에 막혀서 안익은 토미토가 있든지 둘 중 하나다.
		// 배열 전체 탐색 해보고 최종 답 결정하기
		ans = isAllRotten() ? ans : -1;
	}

	static boolean isAllRotten() {
		for (int[][] box : map) {
			for (int[] row : box) {
				for (int value : row) {
					// 안익은 토마토 있는 경우
					if (value == 0) {
						return false;
					}
				}
			}
		}
		return true;
	}

}
