package BOJ.search;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class N3967 {
	// 매직스타 원본 저장 배열 및 탐색을 위한 temp 배열
	static char[][] map = new char[5][9], temp = new char[5][9];
	static boolean[] used = new boolean[12]; // 사용된 숫자 체크
	static int[][] dir = {{1,-1}, {1,1}, {0,2}}; // 꼭지점에서 움직이는 방향
	static int[] dirNum = {0,1,2,1,0,2}; // 해당 꼭지점에서 움직여야 하는 방향 저장
	static int[][] start = {{0,4}, {0,4}, {1,1}, {1,1}, {1,7}, {3,1}}; // 꼭지점 위치값 순서대로 저장
	// 숫자가 저장되는 위치값 저장 배열
	static int[][] pos = {{0,4}, {1,1}, {1,3}, {1,5}, {1,7},
			{2,2}, {2,6}, {3,1}, {3,3}, {3,5}, {3,7}, {4,4}};
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int cnt = 12; // 숫자 들어가야 하는 곳 갯수
		for(int i = 0; i < 5; i++) {
			map[i] = br.readLine().toCharArray();
			for(int j = 0; j < 9; j++) {
				if(map[i][j] >= 'A' && map[i][j] <= 'L') {
					used[map[i][j] - 'A'] = true; // 사용된 숫자 체크
					cnt--; // 숫자 들어가있다고 체크
				}
			}
		}
		
		makeMagic(cnt, 0); // 매직스타 만들어보기
		
		// 매직스타 출력
		for(int i = 0; i < 5; i++) {
			for(int j = 0; j < 9; j++) {
				System.out.print(map[i][j]);
			}
			System.out.println();
		}
	}
	
	private static boolean makeMagic(int cnt, int idx) { // 넣어야 하는 갯수, 현재 확인해야할 위치 번호
		if(cnt == 0) { // 다 넣었으면
			for(int i = 0; i < 6; i++) { // 모든 꼭지점에서 해당 변 합이 26인지 확인
				if(chkSum(i) != 26) return false; // 아니면 매직스타 안되서 종료
			}
			return true; // 매직스타 완성
		}
		
		// 가지 치기를 위한 조건문
		if(idx == 5 && chkSum(2) != 26) return false; // 위치 5번에서 변 확인
		if(idx == 8 && chkSum(0) != 26) return false; // 위치 8번에서 변 확인
		if(idx == 11 && chkSum(1) != 26) return false; // 위치 11번에서 변1 확인
		if(idx == 11 && chkSum(5) != 26) return false; // 위치 11번에서 변2 확인
		
		// 현재 위치 확인
		int r = pos[idx][0], c = pos[idx][1];
		if(map[r][c] != 'x') { // 숫자가 이미 들어간 곳이면
			if(makeMagic(cnt, idx+1)) return true; // 다음 장소로 넘기기
		}
		else { // 숫자를 넣어야 하는 곳이면
			for(int i = 0; i < 12; i++) {
				if(used[i]) continue; // 이미 사용된 숫자면 패스
				
				used[i] = true;
				map[r][c] = (char) (i + 'A');
				// 현재 숫자 넣어보고 다음 장소 넘기기
				if(makeMagic(cnt-1, idx+1)) return true; 
				map[r][c] = 'x';
				used[i] = false;
			}
		}
		return false;
	}
	
	private static int chkSum(int idx) {
		int sum = 0;
		
		// 현재 위치에서
		int r = start[idx][0];
		int c = start[idx][1];
		for(int i = 0; i <= 3; i++) { // 확인해야하는 변 4번 가면서 합 확인
			int nr = r + dir[dirNum[idx]][0] * i;
			int nc = c + dir[dirNum[idx]][1] * i;

			sum += (map[nr][nc] - 'A' + 1);
			if(sum > 26) return 0; // 어차피 26안되니까 조기 종료
		}		
		return sum; // 합 값 리턴
	}
}
