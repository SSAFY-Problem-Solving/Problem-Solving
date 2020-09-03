package BOJ;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;


/*  
 * 1. 3차원 배열로 문제를 풀 수 있음
 * 2. BFS 활용
 * 3. delta 탐색을 6방향을 진행
*/
public class BOJ_7569_토마토 {
	static int N, M, H; // 세로, 가로, 높이
	static int[][][] box; // 토마토 상자
	// 위, 아래, 왼쪽, 오른쪽, 앞, 뒤
	static int[] di = {-1, 1 ,0, 0, 0, 0}; // 행 delta
	static int[] dj = {0, 0, -1, 1, 0, 0}; // 열 delta
	static int[] dk = {0, 0, 0, 0, -1, 1}; // 높이 delta
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		M = Integer.parseInt(st.nextToken());
		N = Integer.parseInt(st.nextToken());
		H = Integer.parseInt(st.nextToken());
		box = new int[N][M][H];
		Queue<Point> q = new LinkedList<>();
		for (int k = 0; k < H; k++) {
			for (int i = 0; i < N; i++) {
				st = new StringTokenizer(br.readLine());
				for (int j = 0; j < M; j++) {
					int tomato = Integer.parseInt(st.nextToken());
					box[i][j][k] = tomato;
					if(tomato==1) {
						q.add(new Point(i, j, k)); // 토마토 위치를 queue에 저장
					}
				}
			}
		}
		// input end
		
		
		// BFS start
		int cnt=0;
		while(!q.isEmpty()) {
			boolean flag = false;
			int size = q.size();
			for(int i=0; i<size; i++) {
				Point p = q.poll();
				for(int d=0; d<6; d++) {
					int ni = p.x+di[d];
					int nj = p.y+dj[d];
					int nk = p.z+dk[d];
					if(ni<0 || ni>=N || nj<0 || nj>=M || nk<0 || nk>=H || box[ni][nj][nk]!=0) continue;
					box[ni][nj][nk] = 1;
					q.add(new Point(ni, nj, nk));
					flag = true;
				}
			}
			if(flag) {
				cnt++;
			}
		}
		// BFS end
		
		// 토마토가 모두 익지 못하는 경우 찾기
		for (int k = 0; k < H; k++) {
			for (int i = 0; i < N; i++) {
				for (int j = 0; j < M; j++) {
					if(box[i][j][k]==0) {
						System.out.println(-1);
						return;
					}
				}
			}
		}
		
		System.out.println(cnt); // 토마토가 모두 익었을 경우 최소 일 수 출력
		
	}
	static class Point{
		int x, y, z;
		public Point(int x, int y, int z) {
			this.x = x;
			this.y = y;
			this.z = z;
		}
	}
}
