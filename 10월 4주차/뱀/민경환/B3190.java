package BOJ;

import java.io.*;
import java.util.*;

public class B3190 {

    static Map<Integer, String> info;
    static int N, K, L, dir;
    static int[][] map;
    static int[] di = {-1, 1, 0, 0};
    static int[] dj = {0, 0, -1, 1};
    static class Point {
        int i;
        int j;

        public Point(int i, int j) {
            this.i = i;
            this.j = j;
        }

        @Override
        public String toString() {
            return "(" + i + ", " + j + ")";
        }
    }
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        init(br);

        Deque<Point> deque = new LinkedList<>();

        int time = 0;
        int i = 1, j = 1;
        deque.push(new Point(i, j));

        dir = 3;
        while (true) {
            time++;

            int ni = i + di[dir];
            int nj = j + dj[dir];

            if (ni > N || ni < 1 || nj > N || nj < 1) break;
            boolean flag = false;
            for (Point point : deque) {
                if (point.i == ni && point.j == nj) {
                    flag = true;
                    break;
                }
            }

            if (flag) break;

            deque.addFirst(new Point(ni, nj));

            // 사과인지 검사
            if (map[ni][nj] == 1) {
                map[ni][nj] = 0;
            } else {
                deque.removeLast();
            }

            // 방향 전환 하는지 검사
            if (info.get(time) != null) {
                switch (dir) {
                    case 0 : { // 상
                        switch (info.get(time)) {
                            case "D" : dir = 3; break;
                            case "L" : dir = 2; break;
                        }
                    } break;
                    case 1 : { // 하
                        switch (info.get(time)) {
                            case "D" : dir = 2; break;
                            case "L" : dir = 3; break;
                        }
                    } break;
                    case 2 : { // 좌
                        switch (info.get(time)) {
                            case "D" : dir = 0; break;
                            case "L" : dir = 1; break;
                        }
                    } break;
                    case 3 : { // 우
                        switch (info.get(time)) {
                            case "D" : dir = 1; break;
                            case "L" : dir = 0; break;
                        }
                    } break;
                }
            }

            i = ni;
            j = nj;
        }

        bw.write(time + "");
        bw.flush();
    }

    private static void init(BufferedReader br) throws IOException {
        N = Integer.parseInt(br.readLine().trim());
        map = new int[N + 1][N + 1];
        K = Integer.parseInt(br.readLine().trim());
        while (K-- > 0) {
            StringTokenizer st = new StringTokenizer(br.readLine().trim());
            int row = Integer.parseInt(st.nextToken());
            int col = Integer.parseInt(st.nextToken());
            map[row][col] = 1;
        }
        L = Integer.parseInt(br.readLine().trim());
        info = new HashMap<>();
        while (L-- > 0) {
            StringTokenizer st = new StringTokenizer(br.readLine().trim());
            int X = Integer.parseInt(st.nextToken());
            String D = st.nextToken();
            info.put(X, D);
        }
    }
}