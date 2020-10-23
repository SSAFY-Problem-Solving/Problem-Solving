package BOJ.search;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class N16137 {
	static int map[][], dir[][] = {{1,0}, {-1,0}, {0,1}, {0,-1}};
	static int N, M, ans = Integer.MAX_VALUE;
	// 오작교 지은적있는지 여부 , 위치값, 현재 위치에서 해당 방향으로 이동한 적 있는지 저장
	static boolean visit[][][][];
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		
		map = new int[N][N];
		visit = new boolean[2][N][N][4];
		for(int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			for(int j = 0; j < N; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		System.out.println(bfs());
	}
	
	private static int bfs() {
		Queue<Pos> q = new LinkedList<>();
		q.add(new Pos(0,0,false,0,0));
		Arrays.fill(visit[0][0][0], true); // 시작 위치 초기화
		
		while(!q.isEmpty()) {
			Pos cur = q.poll();
			
			for(int i = 0; i < 4; i++) { // 4방향으로 이동
				int nr = cur.r + dir[i][0];
				int nc = cur.c + dir[i][1];
				
				// 범위 밖이거나 해당위치 이동 중복이면 패스
				if(nr < 0 || nc < 0 || nr >= N || nc >= N || visit[cur.build][nr][nc][i]) continue;
				
				// 목적지 도착이면 종료
				if(nr == N-1 && nc == N-1) return cur.time + 1;
				
				// 육지면 이동
				if(map[nr][nc] == 1) { 
					q.add(new Pos(nr, nc, false, cur.build, cur.time+1));
					visit[cur.build][nr][nc][i] = true;
				}
				// 절벽이고 현재 위치가 오작교가 아니고 오작교를 지은 적이 없으면
				else if(map[nr][nc] == 0 && !cur.bridge && cur.build == 0) {
					if((cur.time+1) % M == 0) { // 주기에 맞으면 이동
						q.add(new Pos(nr, nc, true, 1, cur.time+1));
						visit[cur.build][nr][nc][i] = true;
					} else { // 주기에 안맞으면 대기
						q.add(new Pos(cur.r, cur.c, false, 0, cur.time+1));
					}
				}
				// 주기에 따라 나타나는 오작교이고 현재 위치가 오작교가 아니면
				else if(map[nr][nc] >= 2 && !cur.bridge) {
					if((cur.time+1)% map[nr][nc] == 0) { // 주기에 맞으면 이동
						q.add(new Pos(nr, nc, true, cur.build, cur.time+1));
						visit[cur.build][nr][nc][i] = true;
					} else { // 주기에 안 맞으면 대기
						q.add(new Pos(cur.r, cur.c, false, cur.build, cur.time+1));
					}
				}
			}
		}
		return 0;
	}
	
	private static class Pos{
		// bridge : 현재 오작교 위이면 true, 땅이면 false
		// build : 오작교를 세운 적이 있으면 1, 없으면 0
		int r, c, build, time;
		boolean bridge;
		
		Pos(int r, int c, boolean bridge, int build, int time){
			this.r = r;
			this.c = c;
			this.bridge = bridge;
			this.build = build;
			this.time = time;
		}
	}
}