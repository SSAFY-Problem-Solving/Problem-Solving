package day1023;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Stack;
import java.util.StringTokenizer;

/*
 * 상, 하, 좌, 우 로 이동시키면 내부의 블록들이 같은 값을 가지는 경우 하나로 합쳐진다
 * 한 번의 이동에서 한번 합쳐진 블록은 다시 합쳐지지 않는다
 * 블록 이동지 모두 해당 방향으로 쏠린다 ( 보드판을 기울이는 느낌 )
 * 최대 5번 이동시켜서 얻을 수 있는 가장 큰 불록 출력?
 * 5^5번의 경우의 수가 생긴다 그때마다의 가장 큰 블록을 비교 ( 상, 하, 좌, 우)
 */
public class BOJ_12100 {

	static int N, ans;
	static int[][] map, temp;
	
	static int[] order;
	static Stack<Integer> stack;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		ans = 0;
		N = Integer.parseInt(br.readLine());
		stack = new Stack<>();
		map = new int[N][N];
		for (int i = 0; i < N; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			for (int j = 0; j < N; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
			}
		}

		// 1. 경우의 수 만들기 ( 0번, 1번 ... 5번 이동 )
		order = new int[5]; // 최대 5번 이동
		for (int i = 0; i <= 5; i++) {
			perm(i, 0);
		}
		
		System.out.println(ans);

	}

	private static void perm(int r, int idx) {
		if (idx == r) {
			// 2. 해당 경우의 수 배열을 보고 보드 기울이기
			play();
			return;
		}

		for (int i = 1; i <= 4; i++) {
			order[idx] = i;
			perm(r, idx + 1);
		}
	}

	private static int[][] deepCopy() {
		int[][] arr = new int[N][N];
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				arr[i][j] = map[i][j];
			}
		}
		return arr;
	}

	private static void play() {
		temp = deepCopy();

		// dir이 0이 아니면 기울이기 
		for (int dir : order) {
			if (dir != 0) {
				tilt(dir);
			}
		}
		
		int tempAns = 0;
		for (int[] row : temp) {
			for (int num : row) {
				tempAns = Math.max(num, tempAns);
			}
		}
		
		ans = Math.max(tempAns, ans);
		
	}
	
	// 상, 하, 좌, 우 한번에 하기 위한 작업
	private static int[] convert(int dir, int i, int j) {
		int[] arr = new int[2];
		if (dir == 1) { // 상
			arr[0] = j;
			arr[1] = i;
		} else if (dir == 2) { // 하
			arr[0] = j;
			arr[1] = N - 1 - i;
		} else if (dir == 3) { // 좌
			arr[0] = i;
			arr[1] = j;
		} else if (dir == 4) { // 우
			arr[0] = i;
			arr[1] = N - 1 - j;
		}
		return arr;
	}
	
	private static void tilt(int dir) {
		int x = 0;
		int y = 0;
		
		for (int i = 0; i < N; i++) {
			// 2. stack으로 연산
			boolean isSum = false;
			for (int j = 0; j < N; j++) {
				int[] arr = convert(dir, i, j);
				x = arr[0];
				y = arr[1];
				
				int num = temp[x][y];
				if (num != 0) {
					if (!stack.isEmpty() && !isSum && num == stack.peek()) {
						stack.pop();
						stack.push(num * 2);
						isSum = true;
					} else {
						stack.push(num);
						isSum = false;
					}
				}
			}

			// 3. 위로 당기는 것이므로 stack의 원소갯수가 N이 될때까지 0을 push
			while (stack.size() < N) {
				stack.push(0);
			}

			// 4. 맨 밑의 행부터 위로 채운다
			for (int j = N - 1; j >= 0; j--) {
				int[] arr = convert(dir, i, j);
				x = arr[0];
				y = arr[1];
				temp[x][y] = stack.pop();
			}
		}
		
	}
	
}
