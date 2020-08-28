package day0827;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class BOJ_17069_2 {
	
	static int N;
	static int[][][] map; // 0:가로, 1:세로, 2:대각
	static int[] di = { 0, -1, -1 };
	static int[] dj = { -1, 0, -1 };

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());
		map = new int[N][N][3];
		
		for (int i = 0; i < N; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			for (int j = 0; j < N; j++) {
				if (st.nextToken().equals("0")) {
					int[] temp = { 0, 0, 0 };
					map[i][j] = temp;					
				} else { // num == 1 ( 벽 )
					int[] temp = { -1, -1, -1 };
					map[i][j] = temp;		
				}
			}
		}
		
		// 가로와 대각을 1로 놔준다.
		map[0][1][0] = 1;
		map[0][1][2] = 1; 
		
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				if (i == 0 && j == 0) {
					continue;
				}
				if (map[i][j][0] == -1) {
					continue;
				}
				
				// 1. (i, j-1) 가로 파이프
				int left = 0;
				{
					int ni = i;
					int nj = j - 1;
					if (ni >= 0 && nj >= 0 && map[ni][nj][0] != -1) {
						left = map[ni][nj][0];
					}
				}
				// 2. (i-1, j) 세로 파이프
				int up = 0;
				{
					int ni = i - 1;
					int nj = j;
					if (ni >= 0 && nj >= 0 && map[ni][nj][1] != -1) {
						up = map[ni][nj][1];
					}
				}
				// 3. (i-1, j-1) 대각 파이프
				int leftUp = 0;
				{
					int ni = i - 1;
					int nj = j - 1;
					if (ni >= 0 && nj >= 0 && map[ni][nj][2] != -1 && left != 0 && up != 0) {
						leftUp = map[ni][nj][2];
					}
				}
				
				// 현재 의 가로는 가로 + 대각
				map[i][j][0] = left + leftUp;
				// 세로는 세로 + 대각
				map[i][j][1] = up + leftUp;
				// 대각은 가로 + 세로 + 대각
				map[i][j][2] = left + up + leftUp;
				
			}
		}
		
		int ans = 0;
		for (int i = 0; i < 3; i++) {
			ans += map[N-1][N-1][i];
		}
		System.out.println(ans);
	}

}
