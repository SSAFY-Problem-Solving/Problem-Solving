package backjoon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class N17281 {
	static int N; // �̴� Ƚ��
	static int MaxScore = 0; // �ִ� ����
	static int[][] score; // �� �̴׺� ���� ����ǥ
	static int[] hitterLine = new int[9]; // �������� Ÿ��
	static boolean[] choice = new boolean[9]; // ������ ������ ���� üũ
	
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
		setLine(0); // Ÿ�� ���ϱ�
		System.out.println(MaxScore);
	}
	
	public static void setLine(int idx) { // Ÿ�ڼ��� ���ϴ� ����
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
	
	public static int playGame() { // ���� Ÿ������ ���� �����ϱ�
		int totalScore = 0; // ����
		int nextHitterNum = 0; // ���� ���� ��ȣ
		
		for(int i = 0; i < N; i++) {
			int[] stage = new int[4]; // �� ��ġ�� ���� üũ(0:�ƿ�, 1:1��, 2:2��, 3:3��)
			while(stage[0] < 3) {
				int nextHitter = hitterLine[nextHitterNum]; // �ش� ������ Ÿ�� ��ȣ
				int nowScore = score[i][nextHitter]; // Ÿ���� ����
				if (nowScore == 0) { // �ƿ��̸� �ƿ� + 1
					stage[nowScore]++;
				}
				else { // ���� ������
					for (int j = 3; j > 0; j--) { // 3����� 1����� Ȯ���ϸ�
						if (stage[j] > 0) { // Ÿ�� ������ 
							if (nowScore + j >= 4) { // Ȩ �������� ���� +1
								totalScore++;
							} else { // Ȩ ���������� ���� ��ġ�� �ű��
								stage[nowScore + j] = stage[j];
							}
							stage[j] = 0; // ���� ��ġ�� �ʱ�ȭ
						}
					}
					
					if(nowScore == 4) { // Ȩ���̸� ���� +1
						totalScore++;
					}else {
						stage[nowScore]++; // ��� Ÿ�� ��ġ ���ϱ�
					}
				}
				nextHitterNum = (nextHitterNum + 1) % 9; // ���� ���� ��ȣ
			}
		}
		return totalScore; // ���� �������� �� ���� �����ϱ�
	}

}
