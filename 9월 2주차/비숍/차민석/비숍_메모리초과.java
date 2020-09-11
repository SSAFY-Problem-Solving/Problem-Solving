import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/*
 * 10x10이니 DFS 접근 ( 해당 위치에 놓고 안 놓고 )
 * 왼쪽 상단부터 놓으면서 놨을때 왼쪽 대각 아래, 오른쪽 대각 아래 모두 못 놓는 자리로 세팅
 * 끝까지 진행 후 총 비숍 놓은 개수랑 ans 비교
 */
public class Main {

	static int N, ans;
	static boolean[][] map;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());
		map = new boolean[N][N];
		int remain = 0;
		for (int i = 0; i < N; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			for (int j = 0; j < N; j++) {
				map[i][j] = st.nextToken().equals("1");
				if (map[i][j]) {
					remain++;
				}
			}
		}

		// i, j, num
		dfs(0, 0, 0, remain);

		System.out.println(ans);
	}

	static void dfs(int x, int y, int num, int remain) {
		if (num + remain <= ans) {
			return;
		}
		
		if (x == N - 1 && y == N - 1) {
			ans = Math.max(num, ans);
			return;
		}		

		// 비숍 넣을 수 없는 곳
		if (!map[x][y]) {
			next(x, y, num,remain);
		} else { // 넣을 수 있는 곳 인 경우
			// 안 넣고 넘기기
			next(x, y, num,remain - 1);
			
			// 해당 위치에 넣기
			int cnt = check(x, y, map);
			
			boolean[][] temp = deepCopy();
			
			updateMap(x, y);
			next(x, y, num + 1, remain - 1 - cnt);
			map = temp;
			
		}
	}
	
	static boolean[][] deepCopy() {
		boolean[][] temp = new boolean[N][N];
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				temp[i][j] = map[i][j];
			}
		}
		return temp;
	}

	static void next(int x, int y, int num, int remain) {
		if (y == N - 1) {
			dfs(x + 1, 0, num, remain);
		} else {
			dfs(x, y + 1, num, remain);
		}
	}
	
	// 이 좌표에 비숍을 놓으면 앞으로 놓을 공간이 얼마나 없어지는지 return
	static int check(int x, int y, boolean[][] arr) {
		int result = 0;
		int nx = x;
		int ny = y;

		while (true) {
			if (++nx < N && --ny >= 0) {
				if (arr[nx][ny]) {
					result++;
				}
			} else {
				break;
			}
		}
		
		nx = x; 
		ny = y;

		while (true) {
			if (++nx < N && ++ny < N) {
				if (arr[nx][ny]) {
					result++;
				}				
			} else {
				break;
			}
		}
		return result;
	}
	
	// 현재 좌표 기준 왼쪽 대각 아래, 오른쪽 대각 아래 전부 false로 만들기
	static void updateMap(int x, int y) {
		int nx = x;
		int ny = y;

		// 왼쪽 대각 아래 false 만들기
		while (true) {
			if (++nx < N && --ny >= 0) {
				if (map[nx][ny]) {
					map[nx][ny] = false;
				}
			} else {
				break;
			}
		}
		
		nx = x; 
		ny = y;

		// 오른쪽 대각 아래 false 만들기
		while (true) {
			if (++nx < N && ++ny < N) {
				if (map[nx][ny]) {
					map[nx][ny] = false;
				}				
			} else {
				break;
			}
		}
	}
}