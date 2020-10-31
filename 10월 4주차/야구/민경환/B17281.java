package day1101;

import java.io.*;
import java.util.StringTokenizer;

public class B17281 {

    static int N, R, MAX;
    static boolean[] visited;
    static int[] temp;
    static int[] order;
    static int[][] result;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        init(br);
        backtrack(0);

        bw.write(MAX + "");
        bw.flush();
    }

    private static void init(BufferedReader br) throws IOException {
        R = 8;
        visited = new boolean[R];
        temp = new int[R];
        order = new int[R + 1];

        N = Integer.parseInt(br.readLine().trim());
        result = new int[N][10];
        for (int i = 0; i < N; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine().trim());
            for (int j = 1; st.hasMoreTokens(); j++) {
                result[i][j] = Integer.parseInt(st.nextToken());
            }
        }
    }

    private static void backtrack(int cnt) {
        if (cnt == R) {
            createOrder();
            baseball();
            return;
        }

        for (int i = 0; i < R; i++) {
            if (visited[i]) continue;
            visited[i] = true;
            temp[cnt] = i;
            backtrack(cnt + 1);
            visited[i] = false;
        }
    }

    private static void baseball() {
        int idx = 0, out = 0, j = 0, ans = 0;
        boolean[] base = new boolean[4];
        while (idx != N) {
            int active = result[idx][order[j]];
            if (active == 0) out++;
            else if (active == 1) {
                for (int i = 3; i >= 0; i--) {
                    if (base[i]) {
                        base[i] = false;
                        if (i + 1 >= 3) ans++;
                        else base[i + 1] = true;
                    }
                }
                base[0] = true;
            } else if (active == 2) {
                for (int i = 3; i >= 0; i--) {
                    if (base[i]) {
                        base[i] = false;
                        if (i + 2 >= 3) ans++;
                        else base[i + 2] = true;
                    }
                }
                base[1] = true;
            } else if (active == 3) {
                for (int i = 3; i >= 0; i--) {
                    if (base[i]) {
                        base[i] = false;
                        if (i + 3 >= 3) ans++;
                        else base[i + 3] = true;
                    }
                }
                base[2] = true;
            } else if (active == 4) {
                for (int i = 0; i < 4; i++) {
                    if (base[i]) {
                        ans++;
                        base[i] = false;
                    }
                }
                ans++;
            }
            j++;
            if (j == 9) j = 0;
            if (out == 3) {
                base = new boolean[4];
                idx++;
                out = 0;
            }
        }
        MAX = Math.max(MAX, ans);
    }

    private static void createOrder() {
        for (int i = 0; i < 3; i++) {
            order[i] = temp[i] + 2;
        }
        order[3] = 1;
        for (int i = 3; i < R; i++) {
            order[i + 1] = temp[i] + 2;
        }
    }

}
