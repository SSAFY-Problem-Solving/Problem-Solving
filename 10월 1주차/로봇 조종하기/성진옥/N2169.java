package BOJ.dynamic;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class N2169 {
	static int map[][], dp[][][];
	static int N, M;
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		
		map = new int[N][M];
		dp = new int[N][M][3]; // 0: 아래로 감, 1: 오른쪽으로 감, 2: 왼쪽으로 감
		for(int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			for(int j = 0; j < M; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		
		// 시작 지점은 아래, 오른쪽방향으로 이동가능. 왼쪽으로는 못감
		dp[0][0][0] = map[0][0];
		dp[0][0][1] = map[0][0];
		dp[0][0][2] = Integer.MIN_VALUE;
		
		for(int j = 1; j < M; j++) {
			dp[0][j][0] = Integer.MIN_VALUE; // 맨 윗줄은 위에서 내려오는 애들 없음
			dp[0][j][1] = dp[0][j-1][1] + map[0][j]; // 맨 윗줄은 오른쪽으로 가는 애들 누적값으로 초기화
			dp[0][j][2] = Integer.MIN_VALUE; // 맨 윗줄은 왼쪽으로 오는 애들 없음
		}
		
		// 한줄씩 아래로 내려가면서 계산
		for(int i = 1; i < N; i++) {
			// 위에서 아래로 내려오는 애들 값 비교해서 가장 큰 값 + 현재 가치
			for(int j = 0; j < M; j++) {
				dp[i][j][0] = Math.max(dp[i-1][j][0], dp[i-1][j][1]);
				dp[i][j][0] = Math.max(dp[i][j][0], dp[i-1][j][2]);
				dp[i][j][0] += map[i][j];
			}
			
			// 왼쪽에서 오른쪽으로 가는 애들 값 비교해서 가장 큰 값 + 현재 가치
			dp[i][0][1] = Integer.MIN_VALUE;
			for(int j = 1; j < M; j++)
				dp[i][j][1] = Math.max(dp[i][j-1][0], dp[i][j-1][1]) + map[i][j];
			
			// 오른쪽에서 왼쪽으로 가는 애들 값 비교해서 가장 큰 값 + 현재 가치
			dp[i][M-1][2] = Integer.MIN_VALUE;
			for(int j = M-2; j >= 0; j--)
				dp[i][j][2] = Math.max(dp[i][j+1][0], dp[i][j+1][2]) + map[i][j];
		}
		// 맨 아래 칸은 아래로 오거나 오른쪽으로 오거나 이므로 둘중 큰 값 출력
		System.out.println(Math.max(dp[N-1][M-1][0], dp[N-1][M-1][1]));
	}
}
