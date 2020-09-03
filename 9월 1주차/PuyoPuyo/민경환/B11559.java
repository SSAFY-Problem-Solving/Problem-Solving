package BOJ;

import java.io.*;
import java.util.*;

public class B11559 {

    static class Point {
        int i, j;

        public Point(int i, int j) {
            this.i = i;
            this.j = j;
        }

        public int getI() {
            return i;
        }
    }

    static int R = 12, C = 6, cnt;
    static char[][] map;
    static boolean[][] visited;
    static List<Point> lst;
    static int[] di = {-1, 1, 0, 0};
    static int[] dj = {0, 0, -1, 1};

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        init(br);

        // 뿌요가 터질 수 없을 때 까지 반복
        while (true) {
            visited = new boolean[R][C];
            lst = new ArrayList<>();
            for (int i = 0; i < R; i++) {
                for (int j = 0; j < C; j++) {
                    // 뿌요일 경우 4개 이상의 뿌요가 연결되어 있는지 bfs를 돌린다.
                    if (map[i][j] != '.' && !visited[i][j]) {
                        bfs(i, j);
                    }
                }
            }
            // 터질 수 있는 뿌요가 존재할 경우(lst에 하나라도 좌표가 존재하면) 터트린다.
            if (lst.size() > 0) boom();
            else break;
        }

        bw.write(String.valueOf(cnt));
        bw.flush();
    }

    // 뿌요를 터트리는 함수
    private static void boom() {
        // 터트릴 뿌요의 모든 좌표를 정렬한다.
        lst.sort(Comparator.comparingInt(Point::getI));
        // 좌표를 반복하면서,
        for (Point p : lst) {
            // row 만큼 위에서 아래로 내린다.
            for (int row = p.i; row > 0; row--) {
                map[row][p.j] = map[row - 1][p.j];
            }
            // 맨 위의 좌표는 자동으로 내려오지 않으므로 맨 위는 벽으로 바꾼다.
            map[0][p.j] = '.';
        }
        // 터트렸으니 카운트를 올린다.
        cnt++;
    }

    private static void bfs(int i, int j) {
        Queue<Point> queue = new LinkedList<>();
        List<Point> temp = new ArrayList<>();

        queue.offer(new Point(i, j));
        temp.add(new Point(i, j));

        while (!queue.isEmpty()) {
            Point poll = queue.poll();
            int ci = poll.i;
            int cj = poll.j;
            visited[ci][cj] = true;

            for (int d = 0; d < 4; d++) {
                int ni = ci + di[d];
                int nj = cj + dj[d];
                if (ni >= R || ni < 0 || nj >= C || nj < 0 || map[ni][nj] == '.' || visited[ni][nj] || map[ci][cj] != map[ni][nj]) continue;
                visited[ni][nj] = true;
                queue.offer(new Point(ni, nj));
                // 연결된 뿌요를 리스트에 추가한다.
                temp.add(new Point(ni, nj));
            }
        }

        // 터트릴 뿌요가 4개 이상인 경우, 진짜 터트리는 리스트에 더한다.
        if (temp.size() >= 4) lst.addAll(temp);
    }

    // 입력
    private static void init(BufferedReader br) throws IOException {
        map = new char[R][C];
        for (int i = 0; i < R; i++) map[i] = br.readLine().toCharArray();
    }

}

/*
test_case

......
......
......
......
......
......
......
G.....
GY....
GYG...
RRYG..
RRYGG.

......
......
......
......
......
......
......
......
RYRRRR
GYGYGY
RRYGRY
RRYGGY

......
......
......
......
......
......
......
......
.Y....
.YG...
GRYG..
RRYGG.

Y...YR
B.RGGY
R.GGYY
G.RYGR
YGYGRR
YBRYGY
RRYYGY
YYRBRB
YRBGBB
GBRBGR
GBRBGR
GBRBGR
 */