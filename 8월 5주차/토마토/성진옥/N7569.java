package search;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class N7569 {
	static int N, M, H, tomatoRemain, ans; // 박스 사이즈, 익어야 하는 토마토 갯수, 날짜 체크
	static int map[][][]; // 토마토 저장 위치
	static int dir[][] = {{0,0,1}, {0,1,0}, {0,-1,0}, {0,0,-1}, {1,0,0}, {-1,0,0}}; // 확인해야하는 방향
	static Queue<Pos> q = new LinkedList<>(); // 익은 토마토 위치 저장
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");
		M = Integer.parseInt(st.nextToken());
		N = Integer.parseInt(st.nextToken());
		H = Integer.parseInt(st.nextToken());
		
		map = new int[H][N][M];
		tomatoRemain = 0;
		ans = 0;
		for(int h = 0; h < H; h++) {
			for(int r = 0; r < N; r++) {
				st = new StringTokenizer(br.readLine(), " ");
				for(int c = 0; c < M; c++) {
					map[h][r][c] = Integer.parseInt(st.nextToken());
					if(map[h][r][c] == 0) tomatoRemain++; // 아직 익지 않은 토마토 갯수 세기
					else if(map[h][r][c] == 1) { // 익은 토마토 위치 저장
						q.add(new Pos(h, r, c));
					}
				}
			}
		}
		
		while(!q.isEmpty() && tomatoRemain > 0) { // 익은 토마토가 남아있고 익지 않은 토마토가 있으면
			int size = q.size(); // 현재 익은 토마토 갯수 세기
			ans++; // 날짜 증가
			for(int i = 0; i < size; i++) { // 하루 동안 옆으로 영향을 끼치는 토마토 갯수 만큼
				Pos cur = q.poll();
				for(int j = 0; j < 6; j++) { // 한방향씩 확인해보기
					int nh = cur.h + dir[j][0];
					int nr = cur.r + dir[j][1];
					int nc = cur.c + dir[j][2];
					
					// 범위와 토마토 존재 여부 확인
					if(nh < 0 || nh >= H || nr < 0 || nr >= N || nc < 0 || nc >= M || map[nh][nr][nc] != 0) continue;
					
					map[nh][nr][nc] = 1; // 익은 토마토로 바꾸고
					tomatoRemain--; // 안익은 토마토 갯수 줄이고
					if(tomatoRemain == 0) break; // 만약 다익었으면 종료
					q.add(new Pos(nh, nr, nc)); // 다음 날 체크하기 위해 큐에 저장
				}
				if(tomatoRemain == 0) break; // 만약 다 익었으면 종료
			}
		}
		// 익은 토마토 확인이 종료 되었는데
		if(tomatoRemain != 0) System.out.println("-1"); // 익지 않은 토마토 남아있으면
		else System.out.println(ans); // 모든 토마토가 다 익었으면
	}
	
	static class Pos{
		int h, r, c;
		Pos(int h, int r, int c){
			this.h = h;
			this.r = r;
			this.c = c;
		}
	}
}
