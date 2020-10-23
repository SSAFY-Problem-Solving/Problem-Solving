package BOJ;

import java.io.*;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class B12100 {

    static int N, MAX;
    static int[][] map, copy;
    static int[] visited;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        init(br);

        backtrack(0);

        bw.write(MAX + "");
        bw.flush();
    }

    // 중복 순열
    private static void backtrack(int cnt) {
        if (cnt == 5) {
            for (int i = 0; i < 5; i++) {
                switch (visited[i]) {
                    case 0 : right(); break; // 동
                    case 1 : left(); break; // 서
                    case 2 : down(); break; // 남
                    case 3 : up(); break; // 북
                }
                // printMap(copy);
            }

            MAX = Math.max(MAX, maxValue());

            // 맵 복구
            copyMap();
            System.exit(0);
        }

        for (int i = 0; i < 4; i++) {
            visited[cnt] = i;
            backtrack(cnt + 1);
        }
    }

    // 이동해서 얻은 값 중 최대 값 찾기
    private static int maxValue() {
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                max = Math.max(max, copy[i][j]);
            }
        }
        return max;
    }

    // 동
    private static void right() {
        Queue<Integer> queue = new LinkedList<>();

        for (int i = 0; i < N; i++) {
            for (int j = N - 1; j >= 0; j--) {
                if (copy[i][j] != 0) queue.offer(copy[i][j]);
                copy[i][j] = 0;
            }

            System.out.println("queue => " + queue);

            int j = N - 1;
            while (!queue.isEmpty()) {
                int poll = queue.poll();
                System.out.println("j => " + j);
                System.out.println("poll => " + poll);
                if (copy[i][j] == 0) {
                    copy[i][j] = poll;
                } else if (copy[i][j] == poll) {
                    copy[i][j] = poll * 2;
                    j--;
                } else {
                    j--;
                    copy[i][j] = poll;
                }
                printCopy();
                System.out.println("result j => " + j);
            }

        }
    }

    public static void printCopy() {
        System.out.println("====================");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                System.out.printf("%4d", copy[i][j]);
            }
            System.out.println();
        }
        System.out.println("====================");
    }

    // 서
    private static void left() {
        Queue<Integer> queue = new LinkedList<>();
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (copy[i][j] != 0) queue.offer(copy[i][j]);
                copy[i][j] = 0;
            }

            int j = 0;
            while (!queue.isEmpty()) {
                int poll = queue.poll();
                if (copy[i][j] == 0) {
                    copy[i][j] = poll;
                } else if (copy[i][j] == poll) {
                    copy[i][j] = poll * 2;
                    j++;
                } else {
                    j++;
                    copy[i][j] = poll;
                }
            }
        }
    }

    // 남
    private static void down() {
        Queue<Integer> queue = new LinkedList<>();
        for (int i = 0; i < N; i++) {
            for (int j = N - 1; j >= 0; j--) {
                if (copy[j][i] != 0) queue.offer(copy[j][i]);
                copy[j][i] = 0;
            }

            int j = N - 1;
            while (!queue.isEmpty()) {
                int poll = queue.poll();
                if (copy[j][i] == 0) {
                    copy[j][i] = poll;
                } else if (copy[j][i] == poll) {
                    copy[j][i] = poll * 2;
                    j--;
                } else {
                    j--;
                    copy[j][i] = poll;
                }
            }
        }
    }

    // 북
    private static void up() {
        Queue<Integer> queue = new LinkedList<>();
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (copy[j][i] != 0) queue.offer(copy[j][i]);
                copy[j][i] = 0;
            }

            int j = 0;
            while (!queue.isEmpty()) {
                int poll = queue.poll();
                if (copy[j][i] == 0) {
                    copy[j][i] = poll;
                } else if (copy[j][i] == poll) {
                    copy[j][i] = poll * 2;
                    j++;
                } else {
                    j++;
                    copy[j][i] = poll;
                }
            }
        }
    }

    private static void copyMap() {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                copy[i][j] = map[i][j];
            }
        }
    }

    private static void init(BufferedReader br) throws IOException {
        N = Integer.parseInt(br.readLine().trim());
        map = new int[N][N];
        copy = new int[N][N];
        visited = new int[5];
        for (int i = 0; i < N; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine().trim());
            for (int j = 0; j < N; j++) {
                int v = Integer.parseInt(st.nextToken());
                map[i][j] = v;
                copy[i][j] = v;
            }
        }

        MAX = Integer.MIN_VALUE;
    }

}
