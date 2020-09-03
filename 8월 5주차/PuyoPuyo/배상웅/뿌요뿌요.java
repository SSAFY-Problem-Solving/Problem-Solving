import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class 뿌요뿌요 {
	static int[] di = { 0, 0, 1, -1 };
	static int[] dj = { 1, -1, 0, 0 };
	static char[][] ar;
	static ArrayList<Point> alist;
	static boolean[][] visit;
	static boolean flag;
	static int result= 0;
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		ar = new char[12][6];
		// 입력 받는곳
		for (int i = 0; i < 12; i++) {
			String st = br.readLine();
			for (int j = 0; j < 6; j++) {
				ar[i][j] = st.charAt(j);
			}
		}
		// 여기서 부터 시작할건데
		while (true) {
			visit = new boolean[12][6];	// 먼저 매 반복마다 배열이 바뀌니깐 초기화
			flag=false;		// 뿌요뿌요 됬는지 확인하는 flag
			for (int i = 11; i >= 0; i--) {		// 밑에서부터
				for (int j = 0; j < 6; j++) {	
					if (ar[i][j] != '.') {		// 색깔이 있는 경우
						bfs(i, j, ar[i][j], 0);	// bfs
					}
				}
			}
			remove();	// 위에서 만들어진 배열을 중력 때려
			if(flag)result++;	// 중력때릴게 있었으면 ++
			if(!flag)break;	// 때릴게 없었으면 어차피 더 못움직이기때문에 bye bye
		}
		System.out.println(result);
	}

	// .을 없애주면서 밑으로 내려보내는 작업
	static void remove() {
		for (int i = 0; i < 6; i++) {	// 열별로 확인
            for (int j = 11; j > 0; j--) {
                if (ar[j][i] == '.') {	// 만약 .이 있으면
                    for (int k = j - 1; k >= 0; k--) {
                        if (ar[k][i] != '.') {	// 그 위에것도 .인지 확인
                            ar[j][i] = ar[k][i];	// .이 아닌경우 밑으로 내림
                            ar[k][i] = '.';	// 해당 위치는 . 으로 바뀜
                            break;
                            
                        }
                    }
                }
            }
        }
	}
	// 먼저 c는 color이고, 같은 color가 인접해있으면 .으로 바꿔주고 count를 올려줌.
	// 만약 카운트가 4미만이면 다시 .을 c로 바꿔줌
	// 카운트가 4이상이면 flag를 true
	static void bfs(int a, int b, char c, int count) {
		Point p = new Point(a, b);
		Queue<Point> que = new LinkedList<>();
		que.add(p);
		alist = new ArrayList<>();
		alist.add(p);		// 첫번째꺼 추가
		ar[a][b]= '.';		// .으로 바꿔주기
		count++;			// count++
		// 인접한거 찾기
		while (!que.isEmpty()) {
			Point tmp = que.poll();
			visit[tmp.x][tmp.y] = true;
			for (int i = 0; i < 4; i++) {
				int dii = di[i] + tmp.x;
				int djj = dj[i] + tmp.y;
				if (dii < 0 || dii >= 12 || djj < 0 || djj >= 6 || visit[dii][djj])
					continue;
				else {
					if (ar[dii][djj] == c) {
						que.add(new Point(dii, djj));
						alist.add(new Point(dii,djj));
						ar[dii][djj] = '.';
						count++;
					}
				}
			}
		}
		if(count<4) {		// 4미만이면 list에 추가 됬던 좌표들을 다시 순회하면서 c로 돌려주기
			for(Point o : alist) {
				ar[o.x][o.y]=c;
			}
		}
		else {			// 4이상이면 flag 
			flag= true;
		}
	}

	static class Point {
		int x;
		int y;

		Point(int x, int y) {
			this.x = x;
			this.y = y;
		}
	}
}
