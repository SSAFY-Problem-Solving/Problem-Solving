package BOJ;

import java.io.*;
import java.util.*;

public class B5022 {

    static class Point {
        int i, j;

        public Point(int i, int j) {
            this.i = i;
            this.j = j;
        }

        @Override
        public String toString() {
            return "(" + i + ", " + j + ")";
        }
    }

    static int N, M;
    static int[][] map;
    static int[][] dist;
    static Point A1, A2, B1, B2;
    static int[] di = {-1, 1, 0, 0};
    static int[] dj = {0, 0, -1, 1};

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        init(br);

        dist[B1.i][B1.j] = -3;
        dist[B2.i][B2.j] = -3;
        int a = bfs(A1, A2);
        reverse(A2);
        int b = bfs(B1, B2);

        String result1;
        if (a == -1 || b == -1) {
            result1 = "IMPOSSIBLE";
        } else {
            result1 = String.valueOf(a + b);
        }

        init2();

        dist[A1.i][A1.j] = -3;
        dist[A2.i][A2.j] = -3;
        int c = bfs(B1, B2);
        reverse(B2);
        int d = bfs(A1, A2);

        String result2;
        if (c == -1 || d == -1) {
            result2 = "IMPOSSIBLE";
        } else {
            result2 = String.valueOf(c + d);
        }

        String result;
        if (result1.equals("IMPOSSIBLE") && result2.equals("IMPOSSIBLE")) {
            result = "IMPOSSIBLE";
        } else if (!result1.equals("IMPOSSIBLE") && result2.equals("IMPOSSIBLE")) {
            result = result1;
        } else if (result1.equals("IMPOSSIBLE") && !result2.equals("IMPOSSIBLE")) {
            result = result2;
        } else {
            result = String.valueOf(Math.min(Integer.parseInt(result1), Integer.parseInt(result2)));
        }

        bw.write(result);
        bw.flush();
    }

    private static void init2() {
        map = new int[M + 1][N + 1];
        dist = new int[M + 1][N + 1];
        for (int i = 0; i < M + 1; i++) {
            for (int j = 0; j < N + 1; j++) {
                dist[i][j] = -1;
            }
        }
    }

    private static void reverse(Point P) {
        List<Point> lst = new ArrayList<>();
        Queue<Point> queue = new LinkedList<>();
        lst.add(P);
        queue.offer(P);

        while (!queue.isEmpty()) {
            Point cur = queue.poll();
            int ci = cur.i;
            int cj = cur.j;
            for (int d = 0; d < 4; d++) {
                int ni = ci + di[d];
                int nj = cj + dj[d];
                if (ni < 0 || ni >= M + 1 || nj < 0 || nj >= N + 1) continue;
                if (dist[ci][cj] == dist[ni][nj] + 1) {
                    lst.add(new Point(ni, nj));
                    queue.offer(new Point(ni, nj));
                    break;
                }
            }
        }

        for (Point p : lst) {
            dist[p.i][p.j] = -2;
        }

        for (int i = 0; i < M + 1; i++) {
            for (int j = 0; j < N + 1; j++) {
                if (dist[i][j] == -2) continue;
                dist[i][j] = -1;
            }
        }
    }

    private static void printDist() {
        for (int i = 0; i < M + 1; i++) {
            for (int j = 0; j < N + 1; j++) {
                System.out.printf("%5d", dist[i][j]);
            }
            System.out.println();
        }
        System.out.println("\n");
    }

    private static int bfs(Point S, Point E) {
        Queue<Point> queue = new LinkedList<>();
        queue.offer(S);
        dist[S.i][S.j] = 0;

        while (!queue.isEmpty()) {
            Point cur = queue.poll();
            int ci = cur.i;
            int cj = cur.j;
            if (ci == E.i && cj == E.j) {
                return dist[ci][cj];
            }
            for (int d = 0; d < 4; d++) {
                int ni = ci + di[d];
                int nj = cj + dj[d];
                if (ni < 0 || ni >= M + 1 || nj < 0 || nj >= N + 1 || dist[ni][nj] != -1) continue;
                dist[ni][nj] = dist[ci][cj] + 1;
                queue.offer(new Point(ni, nj));
            }
        }

        return -1;
    }

    private static void init(BufferedReader br) throws IOException {
        StringTokenizer st = new StringTokenizer(br.readLine().trim());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        init2();
        st = new StringTokenizer(br.readLine().trim());
        int x = Integer.parseInt(st.nextToken());
        int y = Integer.parseInt(st.nextToken());
        A1 = new Point(y, x);
        st = new StringTokenizer(br.readLine().trim());
        x = Integer.parseInt(st.nextToken());
        y = Integer.parseInt(st.nextToken());
        A2 = new Point(y, x);
        st = new StringTokenizer(br.readLine().trim());
        x = Integer.parseInt(st.nextToken());
        y = Integer.parseInt(st.nextToken());
        B1 = new Point(y, x);
        st = new StringTokenizer(br.readLine().trim());
        x = Integer.parseInt(st.nextToken());
        y = Integer.parseInt(st.nextToken());
        B2 = new Point(y, x);
    }

}
