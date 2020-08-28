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
					map[i][j] = null;
				}
			}
		}
		
		map[0][1][0] = 1;
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				
				for (int d = 0; d < 3; d++) {
					int ni = i + di[d];
					int nj = j + dj[d];
					if (ni >= 0 && ni < N && nj >= 0 && nj < N) {
						// 가로는 가로와 대각의 합이다
						
						map[i][j][0] += map[ni][nj][d];
					}
				}
				
				// 세로는 
			}
		}
	}

}
