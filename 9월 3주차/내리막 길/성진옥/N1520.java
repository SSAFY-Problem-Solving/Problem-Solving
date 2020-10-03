package BOJ.search;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class N1520 {
	static int map[][], memo[][], dir[][] = {{1,0}, {0,1}, {-1,0}, {0,-1}};
	static int N, M;
	
	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");

		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		
		map = new int[N][M];
		memo = new int[N][M];
		
		for(int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			for(int j = 0; j < M; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
				memo[i][j] = -1; // memo 초기화
			}
		}
		
		System.out.println(func(0,0));
	}
	
	private static int func(int r, int c) {
		if(r == N-1 && c == M-1) { // 목적지 도착하면 memo 값 리턴
			memo[r][c] = 1;
			return memo[r][c];
		}
		
		int ret = 0; // 리턴되서 오는 값 저장 하여 비교할 변수
		for(int i = 0; i < 4; i++) {
			int nextR = r + dir[i][0];
			int nextC = c + dir[i][1];
			
			// 범위 밖이거나 이동할 곳이 현재 위치보다 낮은 곳이 아니면 패스
			if(nextR < 0 || nextC < 0 || nextR >= N || nextC >= M ||
					map[nextR][nextC] >= map[r][c]) continue;
			
			if(memo[nextR][nextC] != -1) { // 다음 이동할 곳이 중복 방문이면 그냥 경우의 수 더하기
				ret += memo[nextR][nextC];
			}else { // 다음 이동할 곳이 처음 방문한 곳이면
				memo[nextR][nextC] = func(nextR, nextC); // 그곳에서부터 도착지점까지 도달하는 경우의 수 저장하고
				ret+= memo[nextR][nextC]; // 경우의 수 더하기
			}
		}
		return ret;
	}
}
