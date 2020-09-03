package simulation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class N11559 {
	static char[][] map = new char[12][6]; // 게임판
	static int[][] dir = {{1,0}, {0,1}, {-1,0}, {0,-1}}; // 퍼즐이 붙어있을 수 있는 방향
	static boolean[][] visit = new boolean[12][6]; // 이미 확인했던 퍼즐 체크
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		for(int r = 0; r < 12; r++)
			map[r] = br.readLine().toCharArray();
		
		int ans = 0;
		while(true) {
			for(int i = 0; i < 12; i++)
				Arrays.fill(visit[i], false); // visit 배열 초기화
			
			int cnt = playPuyo(); // 사라질 퍼즐 고르기
			if(cnt == 0) break; // 더 사라질 퍼즐없으면 종료
			else if(cnt > 0) ans++; // 있으면 점수 증가
			
			makePuyo(); // 퍼즐 아래로 이동
		}
		System.out.println(ans); // 정답 출력
	}
	
	private static void makePuyo() {
		for(int r = 10; r >= 0; r--) {
			for(int c = 0 ; c < 6; c++) {
				if(map[r][c] == '.') continue; // 퍼즐없으면 패스
				
				char temp = map[r][c]; // 현재 컬러 저장
				map[r][c] = '.'; // 퍼즐 없는 걸로 수정
				// 범위를 벗어나거나 퍼즐이 움직일 수 있을때까지 내리기
				while(r <= 10 && map[r+1][c] == '.') r++; 
				map[r][c] = temp; // 마지막 위치에 컬러로 변경
			}
		}
	}

	private static int playPuyo() {
		int cnt = 0;
		for(int r = 0; r < 12; r++) {
			for(int c = 0; c < 6; c++) {
				// 퍼즐이 없거나 이미 확인한 퍼즐인 경우 제외
				if(map[r][c] == '.' || visit[r][c]) continue; 
				
				if(findPuyo(r, c) >= 4) { // 붙어있는 퍼즐이 4개 이상이면
					deletePuyo(r, c); // 삭제
					cnt++; // 삭제 횟수 증가
				}
			}
		}
		return cnt; // 삭제 횟수 리턴
	}
	
	private static void deletePuyo(int r, int c) {
		char color = map[r][c]; // 현재 컬러 저장
		
		Queue<Pair> q = new LinkedList<>(); // bfs로 삭제할 퍼즐 찾기
		q.add(new Pair(r, c)); // 시작점 추가
		while(!q.isEmpty()) { // 붙어있는 퍼즐이 없을 때 까지
			Pair cur = q.poll();
			for(int i = 0; i < 4; i++) {
				int nr = cur.r + dir[i][0];
				int nc = cur.c + dir[i][1];
				
				// 범위와 색상 확인
				if(nr < 0 || nc < 0 || nr >= 12 || nc >= 6 || map[nr][nc] != color)
					continue;
				
				map[nr][nc] = '.'; // 다음 위치 삭제
				q.add(new Pair(nr, nc)); // 다음 위치에서 또 확인
			}
		}
	}
	
	private static int findPuyo(int r, int c) { // 삭제 함수와 유사
		int cnt = 1; // 붙어있는 퍼즐 갯수
		char color = map[r][c]; // 현재 위치 저장
		
		Queue<Pair> q = new LinkedList<>(); 
		q.add(new Pair(r, c));
		visit[r][c] = true; // 현재 위치 visit 체크
		while(!q.isEmpty()) {
			Pair cur = q.poll();
			for(int i = 0; i < 4; i++) {
				int nr = cur.r + dir[i][0];
				int nc = cur.c + dir[i][1];
				
				// 범위, 색상, 방문여부 확인
				if(nr < 0 || nc < 0 || nr >= 12 || nc >= 6 || 
						map[nr][nc] != color || visit[nr][nc]) continue;
				
				q.add(new Pair(nr, nc));
				visit[nr][nc] = true;
				cnt++; // 붙어있는 퍼즐 갯수 증가
			}
		}
		return cnt; // 붙어있는 퍼즐 갯수 리턴
	}
	
	private static class Pair{
		int r, c;
		Pair(int r, int c){
			this.r = r;
			this.c = c;
		}
	}
}
