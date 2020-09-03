package BOJ;

import java.io.*;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class B7569 {

    static class Point {
        int i, j, h;

        public Point(int i, int j, int h) {
            this.i = i;
            this.j = j;
            this.h = h;
        }

        @Override
        public String toString() {
            return "(" + i + ", " + j + ", " + h + ")";
        }
    }
    static int M, N, H;
    static int[][][] map;
    static int[][][] dist;
    static int[] di = {0, 0, 0, 0, 1, -1};
    static int[] dj = {0, -1, 1, 0, 0, 0};
    static int[] dh = {1, 0, 0, -1, 0, 0};
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        StringTokenizer st = new StringTokenizer(br.readLine().trim());
        M = Integer.parseInt(st.nextToken());
        N = Integer.parseInt(st.nextToken());
        H = Integer.parseInt(st.nextToken());

        map = new int[N][M][H];
        dist = new int[N][M][H];
        Queue<Point> queue = new LinkedList<>();
        for (int h = 0; h < H; h++) {
            for (int i = 0; i < N; i++) {
                st = new StringTokenizer(br.readLine().trim());
                for (int j = 0; j < M; j++) {
                    map[i][j][h] = Integer.parseInt(st.nextToken());
                    if (map[i][j][h] == 1) queue.offer(new Point(i, j, h));
                }
            }
        }

        int ans = bfs(queue);
        ans = isValid() ? ans : -1;

        bw.write(String.valueOf(ans));
        bw.flush();
    }

    private static boolean isValid() {
        for (int h = 0; h < H; h++) {
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < M; j++) {
                    if (map[i][j][h] == 0) return false;
                }
            }
        }
        return true;
    }

    private static int bfs(Queue<Point> queue) {
        int ans = 0;
        while (!queue.isEmpty()) {
            int size = queue.size();
            while (size-- > 0) {
                Point cur = queue.poll();
                int ci = cur.i;
                int cj = cur.j;
                int ch = cur.h;
                for (int d = 0; d < 6; d++) {
                    int ni = ci + di[d];
                    int nj = cj + dj[d];
                    int nh = ch + dh[d];
                    if (ni >= N || ni < 0 || nj >= M || nj < 0 || nh >= H || nh < 0 || map[ni][nj][nh] == 1 || map[ni][nj][nh] == -1) continue;
                    map[ni][nj][nh] = 1;
                    dist[ni][nj][nh] = dist[ci][cj][ch] + 1;
                    ans = dist[ni][nj][nh];
                    queue.offer(new Point(ni, nj, nh));
                }
            }
        }
        return ans;
    }

}
