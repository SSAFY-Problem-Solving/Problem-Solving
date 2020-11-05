package BOJ;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class BOJ_14500_테트로미노 {
	static int N, M, max;
	static int[] comb;
	static boolean[][] visit;
	static int[] di = { -1, 1, 0, 0 }; // 상, 하, 좌, 우
	static int[] dj = { 0, 0, -1, 1 };
	static int[][] map;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		map = new int[N][M];
		max = 0;
		comb = new int[3];
		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			for (int j = 0; j < M; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		visit = new boolean[N][M];
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < M; j++) {
				visit[i][j] = true;
				check_one(i, j, 0, map[i][j]);
				visit[i][j] = false;
				check_two(i, j, map[i][j], 0, 0);
			}
		}
		System.out.println(max);
	}
	
	// ㅗ모양을 제외한 모든 모양 탐색 
	private static void check_one(int a, int b, int cnt, int sum) {
		if (cnt == 3) {
			max = Math.max(max, sum); // 시작점은 이미 포함시켰기 때문에 3방향 탐색 후 최댓값 비교
			return;
		}
		for (int i = 0; i < 4; i++) {
			int ni = a + di[i];
			int nj = b + dj[i];
			if (ni >= 0 && ni < N && nj >= 0 && nj < M) {
				if (visit[ni][nj])
					continue;
				visit[ni][nj] = true;
				check_one(ni, nj, cnt + 1, sum + map[ni][nj]);
				visit[ni][nj] = false;
			}

		}
	}

	// ㅗ모양 탐색
	private static void check_two(int a, int b, int sum, int cnt, int start) {
		if (cnt == 3) {
			for (int i = 0; i < 3; i++) {
				int ni = a + di[comb[i]];
				int nj = b + dj[comb[i]];
				if (ni >= 0 && ni < N && nj >= 0 && nj < M) {
					sum += map[a + di[comb[i]]][b + dj[comb[i]]];
				}
			}
			max = Math.max(max, sum);
			return;
		}
		for (int i = start; i < 4; i++) {
			comb[cnt] = i;
			check_two(a, b, sum, cnt + 1, i + 1);
		}
	}
}
