package backjoon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class N17281 {
	static int N; // 이닝 횟수
	static int MaxScore = 0; // 최대 점수
	static int[][] score; // 각 이닝별 선수 득점표
	static int[] hitterLine = new int[9]; // 선수들의 타순
	static boolean[] choice = new boolean[9]; // 순서가 정해진 선수 체크
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());
		score = new int[N][9];
		
		StringTokenizer st;
		for(int i = 0; i < N; i++) {
			 st = new StringTokenizer(br.readLine(), " ");
			 for(int j = 0; j < 9; j++) {
				 score[i][j] = Integer.parseInt(st.nextToken());
			 }
		}
		choice[0] = true;
		setLine(0); // 타순 정하기
		System.out.println(MaxScore);
	}
	
	public static void setLine(int idx) { // 타자순서 정하는 순열
		if(idx == 9) {
			MaxScore = Math.max(MaxScore, playGame());
			return;
		}
		
		if(idx == 3) {
			setLine(idx+1);
		}else {
			for(int i = 1; i < 9; i++) {
				if(choice[i]) continue;
				choice[i] = true;
				hitterLine[idx] = i;
				setLine(idx+1);
				choice[i] = false;
			}
		}
	}
	
	public static int playGame() { // 정한 타순으로 게임 진행하기
		int totalScore = 0; // 점수
		int nextHitterNum = 0; // 다음 순서 번호
		
		for(int i = 0; i < N; i++) {
			int[] stage = new int[4]; // 각 위치별 선수 체크(0:아웃, 1:1루, 2:2루, 3:3루)
			while(stage[0] < 3) {
				int nextHitter = hitterLine[nextHitterNum]; // 해당 순서의 타자 번호
				int nowScore = score[i][nextHitter]; // 타자의 점수
				if (nowScore == 0) { // 아웃이면 아웃 + 1
					stage[nowScore]++;
				}
				else { // 공을 쳤으면
					for (int j = 3; j > 0; j--) { // 3루부터 1루까지 확인하며
						if (stage[j] > 0) { // 타자 있으면 
							if (nowScore + j >= 4) { // 홈 들어왔으면 점수 +1
								totalScore++;
							} else { // 홈 못들어왔으면 다음 위치로 옮기기
								stage[nowScore + j] = stage[j];
							}
							stage[j] = 0; // 지난 위치는 초기화
						}
					}
					
					if(nowScore == 4) { // 홈런이면 점수 +1
						totalScore++;
					}else {
						stage[nowScore]++; // 방금 타자 위치 정하기
					}
				}
				nextHitterNum = (nextHitterNum + 1) % 9; // 다음 순서 번호
			}
		}
		return totalScore; // 현재 순서에서 낸 점수 리턴하기
	}

}
