package day0827;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/*
 * 언제나 짝수번째는 0, 2, 4,... 숫자이고
 * 홀수번째는 연산자이다
 * 괄호를 아무 곳이나 넣어보면서 최댓값 찾아보기
 * 
 * 백준 틀림
 */
public class BOJ_16637 {

	static int N, ans;
	static String list;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());
		list = br.readLine();
		ans = 0;

		int A = list.charAt(0) - '0';
		// DFS이용
		calculate(1, A);

		System.out.println(ans);
	}

	static void calculate(int index, int A) {
		if (index >= N) {
			ans = Math.max(ans, A);
			return;
		}

		char oper = list.charAt(index);
		int B = list.charAt(index + 1) - '0';

		// 현재 연산자를 +라고 하면
		// 괄호 묶는 경우 A + (B * C) ...
		if (index + 3 <= N) {
			char oper2 = list.charAt(index + 2);
			int C = list.charAt(index + 3) - '0';
			int bind = operation(B, C, oper2);
			int next = operation(A, bind, oper);
			calculate(index + 4, next);
		}

		// 안 묶는 경우 A + B ...
		int next = operation(A, B, oper);
		calculate(index + 2, next);
	}

	static int operation(int a, int b, char oper) {
		switch (oper) {
		case '+':
			return a + b;
		case '*':
			return a * b;
		case '-':
			return a - b;
		}
		return 0;
	}

}
