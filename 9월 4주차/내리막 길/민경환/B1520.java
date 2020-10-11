package BOJ;

import java.io.*;
import java.util.StringTokenizer;

public class B1520 {

    static int N, M;
    static int[][] map, dp;
    static int[] di = {-1, 1, 0, 0};
    static int[] dj = {0, 0, -1, 1};
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        init(br);

        bw.write(dfs(0, 0) + "");
        bw.flush();
    }

    private static int dfs(int i, int j) {

        if (i == N - 1 && j == M - 1) {
            return 1;
        }

        if (dp[i][j] == -1) {
            dp[i][j] = 0;
            for (int d = 0; d < 4; d++) {
                int ni = i + di[d];
                int nj = j + dj[d];
                if (ni >= N || nj >= M || ni < 0 || nj < 0) continue;
                if (map[i][j] > map[ni][nj]) {
                    dp[i][j] += dfs(ni, nj);
                }
            }
        }

        return dp[i][j];
    }

    private static void init(BufferedReader br) throws IOException {
        StringTokenizer st = new StringTokenizer(br.readLine().trim());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        map = new int[N][M];
        dp = new int[N][M];
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine().trim());
            for (int j = 0; j < M; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
                dp[i][j] = -1;
            }
        }
    }

}
