// Went to Brian Yang's office hours
import java.util.*;

public class A3Q1 {

	static int totalFood = 0;
	static int rows;
	static int cols;
	static List<int[]> entrances = new ArrayList<>();

	static int[] xdirec = {0, 1, 0, -1}; // right, down, left, up moving through rows
	static int[] ydirec = {1, 0, -1, 0}; // right down left up moving through columns


	public static int[] saving_frogs(String[][] board) {
		int[] ans = new int[2];

		// scans the whole grid to know total number of food and where each entrance coordinate is
		gridScan(board);

		// to know if a frog ate food or not. number of true is minimum frogs
		boolean[] frogUsed = new boolean[entrances.size()];

		int frogsUsed = 0;
		// food eaten so far
		int collectedFood = 0;
		

		// add each entrance coordinate to queue and start bfs from there
		for(int e = 0; e< entrances.size(); e++){
			int[] entrance = entrances.get(e);

			int currentFood = bfs(board, entrance);
			if (currentFood> 0){
				frogsUsed++;
				collectedFood += currentFood;
			}
			if (collectedFood == totalFood){
				break;
			}
		}
		
		ans[1] = totalFood - collectedFood;
		ans[0] = frogsUsed;

		return ans;
	}

	public static int bfs(String[][] board, int[] entrance){
		int foodEaten = 0;

		// to know if a cell was visited. helps end bfs earlier
		boolean[][] visited = new boolean[rows][cols];
		// queue for iterative bfs
		Queue<int[]> queue = new LinkedList<>();

		int r = entrance[0];
		int c = entrance[1];

		visited[r][c] = true;
		queue.offer(entrance);

		while(!queue.isEmpty()) {
			// remove first entrance from queue
			int[] cell = queue.poll();
			int ur = cell[0];
			int uc = cell[1];

			for (int[] item : queue) {
				System.out.println(Arrays.toString(item) + " " + "before polling");
			}
			System.out.println();

			for(int[] item : queue){
				System.out.print(Arrays.toString(item)+ " ");
			}
			System.out.println();

			
			// at a food cell. eat it, update board
			if (board[ur][uc].equals(".")) {
				foodEaten++;
				board[ur][uc] = "\\";
				// frog was used
				//frogUsed[frogID] = true;
			}

			// iterate through all possible directions
			for (int d = 0; d < 4; d++) {
				int vx = ur + xdirec[d];
				int vy = uc + ydirec[d];

				// bounds check
				if (vx < 0 || vx >= rows) {
					continue;
				}
				if (vy < 0 || vy >= cols) {
					continue;
				}

				String neighbor = board[vx][vy];
				// probably useless
				if (isEntrance(neighbor)) {
					continue;
				}
				// last check, then add to queue

				// can go in this direction, so add to queue
				if (neighbor.equals("X")) {continue;}
				if(!visited[vx][vy]){
					visited[vx][vy] = true;
					queue.offer(new int[]{vx, vy});
					}
				
			}
		}

		return foodEaten;

	}

	public static void gridScan(String[][] board){
		entrances.clear();
		totalFood = 0;
		rows = board.length;
		cols = board[0].length;
		for(int i = 0; i <rows; i++){
			for(int j =0; j<cols; j++){
				if(board[i][j].equals(".")){
					totalFood += 1;
				}
				else if (isEntrance(board[i][j])){
					int[] entrance = new int[]{i,j};
					entrances.add(entrance);
				}

			}
		}
	}

	public static boolean isEntrance(String letter){
		return letter.length() == 1 && letter.charAt(0) >= 'A' && letter.charAt(0) <= 'W';
	}
}
