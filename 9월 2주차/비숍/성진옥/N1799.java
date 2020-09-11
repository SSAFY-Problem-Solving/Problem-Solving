package BOJ.search;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class N1799 {
	static int N, maxCnt = 0, spaceCnt = 0;;
	static int[][] map, chkD = {{-1,-1}, {-1,1}, {1,-1}, {1,1}};
	
	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());
		
		map = new int[N][N];
		StringTokenizer st;
		for(int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			for(int j = 0; j < N; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
				if(map[i][j] == 1) spaceCnt++;
			}
		}
		
		for(int r = 0; r < N; r++) {
			for(int c = 0; c < N; c++) {
				if(map[r][c] == 0) continue;
				mapChk(r, c, 1);
			}
		}
		System.out.println(maxCnt);
	}
	
	private static void mapChk(int r, int c, int cnt) {
		spaceCnt--;
		
		if(cnt+spaceCnt < maxCnt) {
			spaceCnt++;
			return ;
		}
		
		maxCnt = Math.max(maxCnt, cnt);
		map[r][c] = 2;
		int nc = c+1;
		for(int nr = r; nr < N; nr++) {
			for(; nc < N; nc++) {
				if(map[nr][nc] == 1 && chkPos(nr, nc)) {
					mapChk(nr, nc, cnt+1);
				}
			}
			nc = 0;
		}
		map[r][c] = 1;
		spaceCnt++;
		return ;
	}
	
	private static boolean chkPos(int r, int c) {
		for(int i = 0; i < 4; i++) {
			int nr = r + chkD[i][0], nc = c + chkD[i][1];
			while(nr >= 0 && nc >= 0 && nr < N && nc < N) {
				if(map[nr][nc] == 2) return false;
				nr += chkD[i][0];
				nc += chkD[i][1];
			}
		}
		return true;
	}
}
