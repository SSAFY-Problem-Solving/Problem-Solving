import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class 비숍 {
	static int N;
	static int[][] ar;
	static int[] di = { 1, 1, -1, -1 };
	static int[] dj = { 1, -1, 1, -1 };
	static int result;
	/*
	 * 백트래킹 강의를 보고 감명을 받은 뒤 시도해보았음
	 * 
	 * 한 점에서 갈 수 있는 대각선 거리에 있는 점들을 모아서
	 * 그 중에서 가장 갈 수 있는 공간이 적은곳이어야 다른 곳에도 놓을 가능성이 많아진다.
	 * 
	 */
	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());
		StringTokenizer st;
		ar = new int[N][N];
		result = 0;
		// 입력 받는 곳
		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 0; j < N; j++) {
				ar[i][j] = Integer.parseInt(st.nextToken());
			}
		}

		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				// 아직 방문 되지 않은곳인 경우를 찾는다.
				if (ar[i][j] == 1) {
					Point tmp = new Point(i, j, 0);
					ArrayList<Point> arl = new ArrayList<>(); 
					
					// 해당 점에서 대각선상에 있는 유효한 좌표들을 ArrayList에 담는다.
					arl = find_next(tmp);

					// 해당 점에서 몇개의 유효공간을 포함하고 있는지 저장해둔다. 
					tmp.val = arl.size();
					
					Point min_point = tmp;
					// 포함하고 있는 유효공간이 가장 적은 것을 선택하는 부분
					for(Point p: arl) {
						ArrayList<Point> arl1 = new ArrayList<>();
						// 위에서 받아온 ArrayList들을 모두 방문해서 각 포인트마다 유효공간을 얼마나 지나갈 수 있는 지 확인한다.
						arl1 = find_next(p);
						p.val += arl1.size();
						
						// 그래서 가장 적게 유효공간을 포함하고 있는 점을 비숍을 놓을 수 있는 포인트로 지정한다.
						if(min_point.val > p.val) {
							min_point = p;
							arl = arl1;
						}
					}
					
					// 그래서 유효공간을 가장 적게 포함하고 있는 점에는 2를 표시해두고
					ar[min_point.x][min_point.y] = 2;
					arl.add(tmp);
					// 해당 포인트에 놨을때 못지나가는 대각선 방향들은 모두 0으로 해둔다.
					for(Point p: arl) {
						if(ar[p.x][p.y]==1) {
							ar[p.x][p.y] = 0;
						}
					}
					
					result++;
				}
			}
		}
		System.out.println(result);
	}
	// p 라는 포인트에서 대각선 방향에 있는 유효한 점들을 ArrayList로 모아준다
	static ArrayList<Point> find_next(Point p) {
		ArrayList<Point> arl = new ArrayList<>();
		for (int i = 0; i < 4; i++) {
			int dii = p.x;
			int djj = p.y;
			while (true) {
				dii += di[i];
				djj += dj[i];
				if (dii < 0 || dii >= N || djj < 0 || djj >= N)
					break;
				if (ar[dii][djj] == 1) {
					arl.add(new Point(dii, djj, 0));
				}
			}
		}
		return arl;
	}

	static class Point {
		int x;
		int y;
		int val;

		Point(int x, int y, int val) {
			this.x = x;
			this.y = y;
			this.val = val;
		}

		@Override
		public String toString() {
			return "Point [x=" + x + ", y=" + y + ", val=" + val + "]";
		}
	}
}
