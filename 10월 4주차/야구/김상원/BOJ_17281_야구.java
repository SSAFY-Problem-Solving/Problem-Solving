package BOJ;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

/*
 * 주의사항 : 
 * 1. 1번으로 입력받은 타자는 4번타석 고정 => 선수가 9명인데 1명이 고정이니 8!개의 경우의 수가 나올 것임
 * 2. 이닝이 끝나도 타석 순서는 1번으로 바뀌는게 아니라 전 이닝의 타순에서 이어져야 함
 * 풀이 : 완전탐색으로 타자 순서를 8!개 만들어서 최고의 점수가 내는 조합을 구함
 */
public class BOJ_17281_야구 {
	static int N, score, max;
	static int[] player;
	static int[][] input;
	static boolean[] visit;
	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());
		input = new int[10][N+1];
		player = new int[10];
		visit = new boolean[10];
		for(int i=1; i<=N; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine(), " ");
			for(int j=1; j<=9; j++) {
				int idx = Integer.parseInt(st.nextToken());
				if(j==4) {
					input[j][i] = input[1][i];
					input[1][i] = idx;
				}
				else {
					input[j][i] = idx; 
				}
			}
		}
		max = Integer.MIN_VALUE;
		player_case(0);
		System.out.println(max);
		
	}
	
	// 8!개의 타석 순서의 경우의 수를 구해주는 메소드
	private static void player_case(int cnt) {
		if(cnt==8) {
			player[4] = 4;
//			System.out.println(Arrays.toString(player));
			score = 0;
			play_game();
			max = Math.max(max, score);
			return;
		}
		else {
			for(int i=1; i<=8; i++) {
				if(!visit[i]) {
					if(cnt>=3) {
						if(i==4) {
							player[cnt+2] = 9;
						}
						else {
							player[cnt+2] = i;						
						}
					}
					else {
						if(i==4) {
							player[cnt+1] = 9;
						}
						else {
							player[cnt+1] = i;
						}
					}
					visit[i] = true;
					player_case(cnt+1);
					visit[i] = false;
				}
			}
		}
		
	}
	
	// 8!개의 경우의 수를 가지고 게임을 진행
	private static void play_game() {
		Queue<Integer> q = new LinkedList<>();
		int start = 1;
		for(int i=1; i<=N; i++) {
			int out = 0;
			q.clear();
			while(true) {
				if(out==3)
					break;
				int hit = input[player[start]][i]; 
				if(hit==0) { // 아웃일때
					out++;
				} else if(hit==4) { // 홈런일때
					score += q.size()+1;
					q.clear();
				} else { // 1, 2, 3루타일때
					if(q.isEmpty()) {
						q.add(hit);
					}
					else {
						int size = q.size();
						for(int h=0; h<size; h++) {
							int p = q.poll();
							if(p+hit>=4) {
								score += 1;
							} else {
								q.add(p+hit);
							}
						}
						q.add(hit);
					}
				}
				start = (start+1)==10?1:(start+1);
			}
		}
	}
}
