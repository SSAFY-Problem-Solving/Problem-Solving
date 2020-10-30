package BOJ.simulation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;

public class N3190 {
	static int N;
	static int[][] map; // 0:없음, 1:사과, 2:뱀
	static List<Pos> move;
	static int[][] dir = {{0,1}, {1,0}, {0,-1}, {-1,0}};
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		N = Integer.parseInt(br.readLine());
		int size = Integer.parseInt(br.readLine());
		
		// 사과 위치 표시
		map = new int[N+1][N+1];
		for(int i = 0; i < size; i++) {
			st = new StringTokenizer(br.readLine());
			int r = Integer.parseInt(st.nextToken());
			int c = Integer.parseInt(st.nextToken());
			map[r][c] = 1;
		}
		
		// 뱀 머리 방향 바꾸는 조건
		size = Integer.parseInt(br.readLine());
		move = new ArrayList<>();
		for(int i = 0; i < size; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			int t = Integer.parseInt(st.nextToken());
			String dtype = st.nextToken();
			int d;
			if(dtype.equals("L")) d = -1;
			else d = 1;
			
			move.add(new Pos(t,d));
		}
		System.out.println(start());
	}
	
	public static int start() {
		int time = 0, curDir = 0;
		
		Queue<Pos> snake = new LinkedList<>(); // 뱀 몸이 존재하는 큐
		Pos head = new Pos(1,1);
		snake.add(head);
		map[1][1] = 2;
		
		while(true) {
			time++;
			// 다음머리 위치 계산
			int nr = head.a + dir[curDir][0];
			int nc = head.b + dir[curDir][1];
			
			// 벽이나 자기 몸에 닿으면 죽음
			if(nr <= 0 || nc <= 0 || nr > N || nc > N || map[nr][nc] == 2) break;
			
			// 살아있으면 머리 위치 갱신 후 바디에 넣기
			head = new Pos(nr, nc);
			snake.add(head);
			
			if(map[nr][nc] == 1) { // 사과가 있으면
				map[nr][nc] = 2;
			}else { // 사과가 없으면
				map[nr][nc] = 2;
				Pos tail = snake.poll();
				map[tail.a][tail.b] = 0; 
			}
			
			// 방향 바뀌는 조건 맞으면 방향 바꾸기
			for(Pos p : move) {
				if(p.a == time) {
					curDir = (curDir + p.b + 4) % 4;
				}
			}
		}
		return time;
	}
	
	public static class Pos{
		int a, b;
		Pos(int a, int b){
			this.a = a;
			this.b = b;
		}
	}
}
