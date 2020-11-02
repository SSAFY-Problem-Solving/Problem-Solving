package BOJ;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;
/**
 * @author SANGWON
 * @date 2020-11-02
 * @problem BOJ_15685_드래곤커브
 * 
 * 1. 사각형 확인 조건 : 방문처리를 통해 각 점마다 사각형이 되는지 확인(방문한 곳이 VISIT, 방문 오른쪽, 방문 아래쪽, 방문 오른쪽아래 대각선)
 * 2. 드래곤 커브 패턴 : 우->상->좌->하->우 방향성을 가짐 중심점을 잡고 과거 순서대로 방향을 돌려주면 됨
 */
public class BOJ_15685_드래곤커브 {
	static int cnt;
	static boolean[][] visited;
	static int[] dx = {1, 0, -1, 0}; // 우, 상, 좌, 하
	static int[] dy = {0, -1, 0, 1};
	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int N = Integer.parseInt(br.readLine());
		visited = new boolean[101][101];
		cnt = 0;
		for(int i=1; i<=N; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine(), " ");
			ArrayList<Dragon> list = new ArrayList<>();
			int x = Integer.parseInt(st.nextToken());
			int y = Integer.parseInt(st.nextToken());
			int direction = Integer.parseInt(st.nextToken());
			int generation = Integer.parseInt(st.nextToken());
			list.add(new Dragon(x, y, direction));
			visited[x][y] = true;
			// 0세대일 경우에는 처음에 들어온 값을 넣어 끝 좌표를 방문처리
			if(generation==0) {
				Dragon point = list.get(0);
				int nx = point.x+dx[point.dir];
				int ny = point.y+dy[point.dir];
				visited[nx][ny] = true;
			}
			// 0세대가 아닐 경우
			for(int g=0; g<generation; g++) {
				int size = list.size(); // 과거 순서를 불러오기 위해 LIST의 사이즈를 기억해놓았다
				Dragon point = list.get(size-1); // LIST의 끝에 있는 것이 중심좌표가 된다.
				int nx = point.x+dx[point.dir]; // 
				int ny = point.y+dy[point.dir];
				visited[nx][ny] = true;
				for(int d=1; d<=size; d++) { // 과거 순서를 적용!!
					int o_dir = list.get(size-d).dir;
					int n_dir = o_dir+1==4?0:o_dir+1;
					list.add(new Dragon(nx, ny, n_dir));
					nx += dx[n_dir];
					ny += dy[n_dir];
					visited[nx][ny] = true;
				}
			}
		}
		// 사각형 개수를 세어보자
		for(int i=0; i<100; i++) {
			for(int j=0; j<100; j++) {
				if(visited[i][j]&&isSquare(i, j))
					cnt++;
			}
		}
		System.out.println(cnt);

	}
	// 사각형 확인
	private static boolean isSquare(int i, int j) {
		if(visited[i+1][j] && visited[i][j+1] && visited[i+1][j+1]) {
			return true;
		}
		return false;
	}
	static class Dragon{
		int x, y, dir;

		public Dragon(int x, int y, int dir) {
			this.x = x;
			this.y = y;
			this.dir = dir;
		}
		
	}

}
