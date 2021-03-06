/*
 * This is a classic backtracking problem.

Start row by row, and loop through columns. At each decision point, skip unsafe positions by using three boolean arrays.

Start going back when we reach row n.

Just FYI, if using HashSet, running time will be at least 3 times slower!

public class Solution {
    int count = 0;
    public int totalNQueens(int n) {
        boolean[] cols = new boolean[n];     // columns   |
        boolean[] d1 = new boolean[2 * n];   // diagonals \
        boolean[] d2 = new boolean[2 * n];   // diagonals /
        backtracking(0, cols, d1, d2, n);
        return count;
    }
    
    public void backtracking(int row, boolean[] cols, boolean[] d1, boolean []d2, int n) {
        if(row == n) count++;

        for(int col = 0; col < n; col++) {
            int id1 = col - row + n;
            int id2 = col + row;
            if(cols[col] || d1[id1] || d2[id2]) continue;
            
            cols[col] = true; d1[id1] = true; d2[id2] = true;
            backtracking(row + 1, cols, d1, d2, n);
            cols[col] = false; d1[id1] = false; d2[id2] = false;
        }
    }
}
*/
class 52NQueensII {
    int count = 0;
    List<Integer> list = new ArrayList<Integer>();
    public int totalNQueens(int n) {
        if ( n == 0 || n == 2 || n == 3 ) return 0;
        if ( n == 1 ) return 1;
        add(list, n, 0);
        return count;
        
    }
    public void add( List<Integer> list, int n, int pos ) {
        List<Integer> tmp = new ArrayList<Integer>(list);
        if ( pos < n ) {
            for ( int i = 0; i < n; i++ ) {
                boolean check = false;
                if ( !list.contains(i) ){
                    for ( int k = 0; k < list.size(); k++ ) {
                        if ( Math.abs(pos - k) == Math.abs(i - list.get(k)) ) {
                            check = true;
                            break;
                        }
                    }
                        if (!check) {
                            tmp.add(i);
                            add(tmp, n, pos + 1);
                            tmp.remove(tmp.size() - 1);
                        }
                }
            }
        }
        else {
            count++;
        }
    }
    
}