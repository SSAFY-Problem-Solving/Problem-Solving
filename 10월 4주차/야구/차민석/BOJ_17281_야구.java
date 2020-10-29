package day1029;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class BOJ_17281_야구 {

	static int N, ans; // N: 이닝 수
	static int[][] results;
	static int[] order;
	static boolean[] select;
	static boolean[] base;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		ans = 0;
		N = Integer.parseInt(br.readLine());
		results = new int[N][9];
		for (int i = 0; i < N; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			for (int j = 0; j < 9; j++) {
				results[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		base = new boolean[3];

		// 1. 시합 전 순서 정하기 ( 1번 선수는 4번 타자 )
		order = new int[9];
		order[3] = 0;
		int[] temp = new int[8];
		select = new boolean[9];
		select[0] = true;
		perm(temp, 0);
		
		System.out.println(ans);
	}

	private static void perm(int[] temp, int idx) {
		if (idx == 8) {
			int tIdx = 0;
			for (int i = 0; i < 9; i++) {
				if (i == 3) {
					continue;
				}
				order[i] = temp[tIdx++];
			}

			playGame();
			return;
		}

		for (int i = 1; i < 9; i++) {
			if (!select[i]) {
				select[i] = true;
				temp[idx] = i;
				perm(temp, idx + 1);
				select[i] = false;
			}
		}
	}

	private static void playGame() {
		// 2. 정해진 순서로 게임하기
		int tempAns = 0;
		
		// 3. 이닝 수 만큼 loop
		int batter = 0; // 타순은 이닝이 넘어가도 초기화되지않고 순서를 유지해야 한다
		for (int n = 0; n < N; n++) {
			Arrays.fill(base, false); // 매 이닝마다 베이스 0으로 초기화

			// 4. 한 이닝은 out count 3이 될 때까지 진행
			int outCnt = 0;
			while (outCnt < 3) {
				
				int num = results[n][order[batter]];
				
				if (num == 0) {
					outCnt++;
				} else {
					tempAns += moveBase(num);
				}
				batter = (batter + 1) % 9;
			}
		}
		
		ans = Math.max(tempAns, ans);
	}

	private static int moveBase(int n) {
		int score = 0;
		boolean[] temp = new boolean[7];
		temp[n - 1] = true; // 안타 친 타자
		for (int i = 0; i < 3; i++) {
			temp[i + n] = base[i]; // 기존 베이스 타자들 진루
		}
		
		for (int i = 0; i < 3; i++) {
			base[i] = temp[i];
		}
		
		for (int i = 3; i < 7; i++) {
			if (temp[i]) {
				score++;
			}
		}
		return score;
	}
}
