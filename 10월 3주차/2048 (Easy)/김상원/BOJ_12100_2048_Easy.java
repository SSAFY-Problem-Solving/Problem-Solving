package BOJ;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class BOJ_12100_2048_Easy {
	static final int UP = 0;
	static final int DOWN = 1;
	static final int LEFT = 2;
	static final int RIGHT = 3;
	static int N, max;
	static int[][] map;
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		N = sc.nextInt();
		map = new int[N][N];
		for(int i=0; i<N; i++) {
			for(int j=0; j<N; j++) {
				map[i][j] = sc.nextInt();
			}
		}
		max = Integer.MIN_VALUE;
		play_game(0);
		System.out.println(max);
	}

	private static void play_game(int cnt) {
		int[][] temp_map = new int[N][N];
		for(int i=0; i<N; i++) {
			for(int j=0; j<N; j++) {
				temp_map[i][j] = map[i][j];
			}
		}
		if(cnt==5) {
			find_max();
			return;
		}
		for(int d=0; d<4; d++) {
			switch (d) {
			case UP:
				for(int i=0; i<N; i++) {
					Queue<Integer> q = new LinkedList<Integer>();
					for(int j=0; j<N; j++) {
						if(temp_map[j][i]!=0) {
							q.add(temp_map[j][i]);
							map[j][i] = 0;
						}
						else {
							map[j][i] = 0;
						}
					}
					int size = 0;
					while(!q.isEmpty()) {
						int p = q.poll();
						if(q.size()!=0 && q.peek()==p) {
							int merge = p * 2;
							q.poll();
							map[size][i] = merge;
							size++;
						}
						else {
							map[size][i] = p;
							size++;
						}
					}
				}
//				System.out.println("UP");
//				for(int i=0; i<N; i++) {
//					for(int j=0; j<N; j++) {
//						System.out.print(map[i][j]+" ");
//					}
//					System.out.println();
//				}
				break;
			case DOWN:
				for(int i=0; i<N; i++) {
					Queue<Integer> q = new LinkedList<Integer>();
					for(int j=N-1; j>=0; j--) {
						if(temp_map[j][i]!=0) {
							q.add(temp_map[j][i]);
							map[j][i] = 0;
						}
						else {
							map[j][i] = 0;
						}
					}
					int size = N-1;
					while(!q.isEmpty()) {
						int p = q.poll();
						if(q.size()!=0 && q.peek()==p) {
							int merge = p * 2;
							q.poll();
							map[size][i] = merge;
							size--;
						}
						else {
							map[size][i] = p;
							size--;
						}
					}
				}
//				System.out.println("DOWN");
//				for(int i=0; i<N; i++) {
//					for(int j=0; j<N; j++) {
//						System.out.print(map[i][j]+" ");
//					}
//					System.out.println();
//				}
				break;
			case LEFT:
				for(int i=0; i<N; i++) {
					Queue<Integer> q = new LinkedList<Integer>();
					for(int j=0; j<N; j++) {
						if(temp_map[i][j]!=0) {
							q.add(temp_map[i][j]);
							map[i][j] = 0;
						}
						else {
							map[i][j] = 0;
						}
					}
					int size = 0;
					while(!q.isEmpty()) {
						int p = q.poll();
						if(q.size()!=0 && q.peek()==p) {
							int merge = p * 2;
							q.poll();
							map[i][size] = merge;
							size++;
						}
						else {
							map[i][size] = p;
							size++;
						}
					}
				}
//				System.out.println("LEFT");
//				for(int i=0; i<N; i++) {
//					for(int j=0; j<N; j++) {
//						System.out.print(map[i][j]+" ");
//					}
//					System.out.println();
//				}
				break;
			case RIGHT:
				for(int i=0; i<N; i++) {
					Queue<Integer> q = new LinkedList<Integer>();
					for(int j=N-1; j>=0; j--) {
						if(temp_map[i][j]!=0) {
							q.add(temp_map[i][j]);
							map[i][j] = 0;
						}
						else {
							map[i][j] = 0;
						}
					}
					int size = N-1;
					while(!q.isEmpty()) {
						int p = q.poll();
						if(q.size()!=0 && q.peek()==p) {
							int merge = p * 2;
							q.poll();
							map[i][size] = merge;
							size--;
						}
						else {
							map[i][size] = p;
							size--;
						}
					}
				}
//				System.out.println("RIGHT");
//				for(int i=0; i<N; i++) {
//					for(int j=0; j<N; j++) {
//						System.out.print(map[i][j]+" ");
//					}
//					System.out.println();
//				}
				break;
			default:
				break;
			}
			play_game(cnt+1);
		}
		
	}

	private static void find_max() {
		for(int i=0; i<N; i++) {
			for(int j=0; j<N; j++) {
				if(map[i][j]>max) {
					max = map[i][j];
				}
			}
		}
		
	}
}
