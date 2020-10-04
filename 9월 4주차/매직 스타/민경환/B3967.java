package BOJ;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class B3967 {

    static class Point {
        int i, j;

        public Point(int i, int j) {
            this.i = i;
            this.j = j;
        }
    }
    static char[][] map;
    static boolean[] visited;
    static List<Point> lst;
    static int R;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        init(br);
        backtrack(0);
    }

    private static void backtrack(int idx) throws IOException {
        if (idx == R) {
            if (isValid()) {
                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < 5; i++) {
                    for (int j = 0; j < 9; j++) {
                        sb.append(map[i][j]);
                    }
                    sb.append("\n");
                }
                bw.write(sb.toString().trim());
                bw.flush();
                System.exit(0);
            }
            return;
        }

        for (int i = 0; i < 12; i++) {
            if (visited[i]) continue;
            Point cur = lst.get(idx);
            map[cur.i][cur.j] = (char) ('A' + i);
            visited[i] = true;
            backtrack(idx + 1);
            map[cur.i][cur.j] = '.';
            visited[i] = false;
        }
    }

    private static boolean isValid() {
        int r1 = (map[0][4] - 'A') + (map[1][3] - 'A') + (map[2][2] - 'A') + (map[3][1] - 'A');
        if (r1 != 22) return false;

        int r2 = (map[0][4] - 'A') + (map[1][5] - 'A') + (map[2][6] - 'A') + (map[3][7] - 'A');
        if (r2 != 22) return false;

        int r3 = (map[1][1] - 'A') + (map[1][3] - 'A') + (map[1][5] - 'A') + (map[1][7] - 'A');
        if (r3 != 22) return false;

        int r4 = (map[3][1] - 'A') + (map[3][3] - 'A') + (map[3][5] - 'A') + (map[3][7] - 'A');
        if (r4 != 22) return false;

        int r5 = (map[1][1] - 'A') + (map[2][2] - 'A') + (map[3][3] - 'A') + (map[4][4] - 'A');
        if (r5 != 22) return false;

        int r6 = (map[1][7] - 'A') + (map[2][6] - 'A') + (map[3][5] - 'A') + (map[4][4] - 'A');

        return r6 == 22;
    }

    private static void init(BufferedReader br) throws IOException {
        map = new char[5][9];
        visited = new boolean[12];
        lst = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            String str = br.readLine().trim();
            for (int j = 0; j < 9; j++) {
                char ch = str.charAt(j);
                map[i][j] = ch;
                if (ch == 'x') {
                    lst.add(new Point(i, j));
                } else {
                    if (ch == '.') continue;
                    visited[ch - 65] = true;
                }
            }
        }

        R = lst.size();
    }

}
