import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class N14500 {
	static int[][] chkShape = {{0,1,2,3,4},{0,1,2,3},{1,3},{1,3},{1,2}};
	static int[][][] dir = {{{0,1}, {0,2}}, {{0,1}, {1,1}}, {{1,0}, {1,1}}, {{0,1}, {1,0}}, {{1,0}, {1,-1}}};
	static int[][] map;
	static int N, M;
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		
		map = new int[N][M];
		for(int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			for(int j = 0; j < M; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		System.out.println(cal());
	}
	
	public static int cal() {
		int max = 0;
		
		for(int i = 0; i < 4; i++) {
			max = Math.max(max, calMax(i));
			rotateMap();
		}
		reverse();
		for(int i = 0; i < 4; i++) {
			max = Math.max(max, calMax(i));
			rotateMap();
		}
		max = Math.max(max, calMax(4));
		
		return max;
	}

	public static int calMax(int idx) {
		int max = 0;
		
		for(int r = 0; r < N; r++) {
			for(int c = 1; c < M; c++) {
				for(int shape : chkShape[idx]) {
					int sum = map[r][c] + map[r][c-1];
					for(int d = 0; d < 2; d++) {
						int nr = r + dir[shape][d][0];
						int nc = c + dir[shape][d][1];
						
						if(nr >= N || nc >= M || nr < 0 || nc < 0) {
							sum = 0;
							break;
						}
						sum += map[nr][nc];
					}
					max = Math.max(max, sum);
				}
			}
		}
		return max;
	}
	
	private static void rotateMap() {
		int[][] temp = new int[N][M];
		
		for(int i = 0; i < N; i++) {
			for(int j = 0; j < M; j++) {
				temp[i][j] = map[i][j];
			}
		}
		
		map = new int[M][N];
		
		for(int i = 0; i < N; i++) {
			for(int j = 0; j < M; j++) {
				map[M-j-1][i] = temp[i][j];
			}
		}
		
		int t = N;
		N = M;
		M = t;
	}

	private static void reverse() {
		int[][] temp = new int[N][M];
		
		for(int i = 0; i < N; i++) {
			for(int j = 0; j < M; j++) {
				temp[i][j] = map[i][j];
			}
			for(int j = 0; j < M; j++) {
				map[i][j] = temp[i][M-j-1];
			}
		}
	}
}
