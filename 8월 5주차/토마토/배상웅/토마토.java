import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class 토마토 {
	static int[] dx = { -1, 1, 0, 0, 0, 0 };		// 6방향
	static int[] dy = { 0, 0, -1, 1, 0, 0 };
	static int[] dz = { 0, 0, 0, 0, -1, 1 };

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		int m = Integer.parseInt(st.nextToken());
		int n = Integer.parseInt(st.nextToken());
		int h = Integer.parseInt(st.nextToken());

		Queue<Point> queue = new LinkedList<Point>();
		int[][][] ar = new int[h][n][m];
		boolean[][][] visit = new boolean[h][n][m];

		for (int k = 0; k < h; k++) {
			for (int i = 0; i < n; i++) {
				st = new StringTokenizer(br.readLine());
				for (int j = 0; j < m; j++) {
					ar[k][i][j] = Integer.parseInt(st.nextToken());
					if (ar[k][i][j] == 1) {
						queue.add(new Point(k, i, j));				// 토마토가 있는 부분 따로 빼두기
						visit[k][i][j] =true;
					}
					else if(ar[k][i][j]==-1) {					// 벽
						visit[k][i][j] = true;
					}
					
				}
			}
		}

		int result = 0;
		// bfs를 통해 인접한 부분 감염(?), result를 통해 일 수 세기
		while (true) {
			int size = queue.size();
			if (size == 0)
				break;
			result++;
			for (int s = 0; s < size; s++) {
				Point p = queue.poll();
				int x = p.x;
				int y = p.y;
				int z = p.z;
				for (int i = 0; i < 6; i++) {
					int dxx = x + dx[i];
					int dyy = y + dy[i];
					int dzz = z + dz[i];
					if (dxx < 0 || dxx >= n || dyy < 0 || dyy >= m || dzz < 0 || dzz >= h)
						continue;
					if (ar[dzz][dxx][dyy]== 0 && !visit[dzz][dxx][dyy]) {
						queue.add(new Point(dzz, dxx, dyy));
						visit[dzz][dxx][dyy] = true;

					}
				}
			}
		}
		result--; // 일단 추가하고 세서 한개 빼줌
		
		
		for (int k = 0; k < h; k++) {
			for (int i = 0; i < n; i++) {
				for (int j = 0; j < m; j++) {
					if (ar[k][i][j] == 0 && !visit[k][i][j]) {		// 방문 못한 부분 있으면 익을 수 없다는 것을 뜻하기 때문에
						result = -1;							// result -1
					}
				}
			}
		}

		System.out.println(result);

	}

	static class Point {
		int z;
		int x;
		int y;

		Point(int z, int x, int y) {
			this.z = z;
			this.x = x;
			this.y = y;
		}
	}
}