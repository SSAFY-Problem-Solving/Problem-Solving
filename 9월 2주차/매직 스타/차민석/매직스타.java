import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

/*
 * 'A'부터 'L'까지 알파벳
 * A = 1, ~ L = 12 -> int LNum = 'L' - 'A' + 1;
 * 숫자 4개로 이루어진 줄의 숫자를 모두 합하면 26이 된다.
....0....
.1.2.3.4.
..5...6..
.7.8.9.10.
....11....
모든 선마다 각자의 수식을 만족해야한다.
static int[][] magicCalc = { 
{ 0, 2, 5, 7 }, 
{ 0, 3, 6, 10 }, 
{ 1, 2, 3, 4 }, 
{ 1, 5, 8, 11 }, 
{ 4, 6, 9, 11 },
{ 7, 8, 9, 10 } 
}; 여섯가지 경우를 만족하도록 숫자를 고르면 된다.
 *
 * 12개 중 처음에 수가 채워진 갯수를 제외해서 N개 중 N개를 뽑는 순열을만든다
 * 그 때마다 성립하는지 확인한다. 순열의 순서는 맨 위부터 알파벳 사전순으로 넣어서
 * 정답이 나오면 더 이상 볼 필요 없도록 한다.
 */
public class Main {

	static char[][] map;
	static int[] magicStar;
	static boolean[] select;
	static int[] order;
	static boolean isEnd;
	static int[][] magicCalc = { { 0, 2, 5, 7 }, { 0, 3, 6, 10 }, { 1, 2, 3, 4 }, { 1, 5, 8, 11 }, { 4, 6, 9, 11 },
			{ 7, 8, 9, 10 } };

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		select = new boolean[13];
		magicStar = new int[12];
		int mIdx = 0;
		int preSelect = 0;

		map = new char[5][9];
		for (int i = 0; i < 5; i++) {
			String s = br.readLine();
			for (int j = 0; j < 9; j++) {
				map[i][j] = s.charAt(j);
				if (map[i][j] != '.') {
					if (map[i][j] == 'x') {
						magicStar[mIdx++] = 0;
					} else {
						int num = map[i][j] - 'A' + 1;
						magicStar[mIdx++] = num;
						select[num] = true;
						preSelect++;
						map[i][j] = 'x'; // x부분 전체를 덮어 씌울 예정
					}
				}
			}
		}

		order = new int[12 - preSelect];

		perm(0);

		StringBuilder sb = new StringBuilder();
		for (char[] row : map) {
			for (char ch : row) {
				sb.append(ch);
			}
			sb.append("\n");
		}
		System.out.println(sb);
	}

	static void perm(int cnt) {
		if (isEnd) {
			return;
		}
		
		if (cnt == order.length) {
			if (!isEnd && isMagicStar()) {
				isEnd = true;
			}
			return;
		}

		for (int i = 1; i <= 12; i++) {
			if (!select[i]) {
				select[i] = true;
				order[cnt] = i;
				perm(cnt + 1);
				select[i] = false;
			}
		}
	}

	static boolean isMagicStar() {
		int[] temp = magicStar.clone();

		for (int num : order) {
			for (int i = 0; i < 12; i++) {
				if (temp[i] == 0) {
					temp[i] = num;
					break;
				}
			}
		}

		for (int[] nums : magicCalc) {
			if (!isSum26(temp, nums[0], nums[1], nums[2], nums[3])) {
				return false;
			}
		}

		for (int num : temp) {
			makeMagicStar(num);
		}
		return true;
	}

	static boolean isSum26(int[] temp, int a, int b, int c, int d) {
		return temp[a] + temp[b] + temp[c] + temp[d] == 26; 
	}
	
	static void makeMagicStar(int num) {
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 9; j++) {
				if (map[i][j] == 'x') {
					map[i][j] = (char) ('A' - 1 + num);
					return;
				}
			}
		}
	}
}