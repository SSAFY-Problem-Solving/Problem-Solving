package BOJ;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

/*  
 * 1. BFS 활용하여 현재 field에서 4개이상 붙어있는 블록을 찾아서 0으로 바꿔줌 
 * 2. 만약 BFS에서 0으로 바꾼적이 있다면 모든 블록을 아래로 내려줌
*/
public class BOJ_11559_PuyoPuyo {
	static char[][] field; // 뿌요뿌요 게임판
	static boolean[][] m_visit; // BFS 결과 안돼는 블록을 2번 탐색하지 않게 하기 위한 visit
	static int[] di = { -1, 1, 0, 0 }; // 행 delta
	static int[] dj = { 0, 0, -1, 1 }; // 열 delta
	static int result; // 결과값
	static boolean end; // 더이상 블록을 못 깨뜨리는지 확인해주는 boolean
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		field = new char[12][6];
		for (int i = 0; i < 12; i++) {
			String line = br.readLine();
			for (int j = 0; j < 6; j++) {
				field[i][j] = line.charAt(j);
			}
		}
		// input end
		
		result = 0;
		while (true) {
			m_visit = new boolean[12][6];
			for (int i = 0; i < 12; i++) {
				for (int j = 0; j < 6; j++) {
					if ((field[i][j] == 'R' || field[i][j] == 'B' || field[i][j] == 'P' || field[i][j] == 'Y'
							|| field[i][j] == 'G') && !m_visit[i][j]) {
						bfs(field[i][j], new Point(i, j)); // 색깔 블록을 만날경우 bfs 진행
					}
				}
			}
			// 1 웨이브 완료
			if (end) { // 1 웨이브 동안 field에 변화가 있었다면
				result++; // 결과값++
				goDown(); // 맵을 다음으로 바꿔줌
			} else {
				break; // 맵의 이동이 없다는 것은 더이상 깨뜨릴 블록이 없다는 것을 뜻함 그러므로 break
			}
		}
		System.out.println(result); // 결과값 출력
	}

	// 맵 이동 함수
	private static void goDown() {
		for(int i=11; i>=0; i--) {
			for (int j = 0; j < 6; j++) {
				while (true) {
					if (field[i][j] == 0) {
						for (int k = 0; k < i; k++) {
							field[i - k][j] = field[(i-1) - k][j];
						}
						field[0][j] = '.';
					} else {
						break;
					}
				}
			}
		}
		end = false;

	}

	// BFS start
	private static void bfs(char flag, Point p) {
		boolean[][] visit = new boolean[12][6]; // bfs 내에서 활동하는 visit
		Queue<Point> q = new LinkedList<>();
		ArrayList<Point> list = new ArrayList<>(); // 만약 블록 4개가 붙어있을 경우 좌표를 저장해 0으로 바꾸기 위해 list 사용
		q.add(p);
		list.add(p);
		visit[p.x][p.y] = true;
		int cnt = 1;
		while (!q.isEmpty()) {
			Point curr = q.poll();
			for (int d = 0; d < 4; d++) {
				int ni = curr.x + di[d];
				int nj = curr.y + dj[d];
				if (ni < 0 || ni >= 12 || nj < 0 || nj >= 6 || field[ni][nj] != flag || visit[ni][nj])
					continue;
				visit[ni][nj] = true;
				q.add(new Point(ni, nj));
				list.add(new Point(ni, nj));
				cnt++;
			}
		}
		if (cnt >= 4) { // 블록 4개 이상 붙어있을 경우에
			for (int i = 0; i < list.size(); i++) { // list에 저장되어 있는 좌표를 다 0으로 바꿔준다.
				field[list.get(i).x][list.get(i).y] = 0;
				end = true; // 블록이 깨졌으므로 field에 변화가 일어났다는 것을 알려줄 boolean
			}
		}
		else { // 붙어있는 블록이 4개 미만이라면
			for (int i = 0; i < list.size(); i++) {
				m_visit[list.get(i).x][list.get(i).y] = true; // 다음 탐색을 해도 똑같기 때문에 전역변수 visit을 통해 쓸데없는 방문을 줄여주기 위함
			}
		}
	}
	// BFS end

	static class Point {
		int x, y;

		public Point(int x, int y) {
			this.x = x;
			this.y = y;
		}
	}

}
