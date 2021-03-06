/* i-j is like a passsword to represent an overlap position
 * Intuition:
If we do brute force, we have 2N horizontal possible sliding, 2N vertical sliding and N^2 to count overlap area.
We get O(N^4) solution and it may get accepted.
But we waste out time on case of sparse matrix.

Explanation:
Assume index in A and B is [0, N * N -1].

Loop on A, if value == 1, save a coordinates i / N * 100 + i % N to LA.
Loop on B, if value == 1, save a coordinates i / N * 100 + i % N to LB.
Loop on combination (i, j) of LA and LB, increase count[i - j] by 1.
If we slide to make A[i] orverlap B[j], we can get 1 point.
Loop on count and return max values.
I use a 1 key hashmap. Assume ab for row and cd for col, I make it abcd as coordinate.
For sure, hashmap with 2 keys will be better for understanding.

Time Complexity:
O(AB)

C++:

    int largestOverlap(vector<vector<int>>& A, vector<vector<int>>& B) {
        vector<int> LA, LB;
        int N = A.size();
        unordered_map<int, int> count;
        for (int i = 0; i < N * N; ++i) if (A[i / N][i % N] == 1) LA.push_back(i / N * 100 + i % N);
        for (int i = 0; i < N * N; ++i) if (B[i / N][i % N] == 1) LB.push_back(i / N * 100 + i % N);
        for (int i: LA) for (int j: LB) count[i - j]++;
        int res = 0;
        for (auto it: count) res = max(res, it.second);
        return res;
    }
Java:

    public int largestOverlap(int[][] A, int[][] B) {
        int N = A.length;
        List<Integer> LA = new ArrayList<>();
        List<Integer> LB = new ArrayList<>();
        HashMap<Integer, Integer> count = new HashMap<>();
        for (int i = 0; i < N * N; ++i) if (A[i / N][i % N] == 1) LA.add(i / N * 100 + i % N);
        for (int i = 0; i < N * N; ++i) if (B[i / N][i % N] == 1) LB.add(i / N * 100 + i % N);
        for (int i : LA) for (int j : LB)
                count.put(i - j, count.getOrDefault(i - j, 0) + 1);
        int res = 0;
        for (int i : count.values()) res = Math.max(res, i);
        return res;
    }
Python:

    def largestOverlap(self, A, B):
        N = len(A)
        LA = [i / N * 100 + i % N for i in xrange(N * N) if A[i / N][i % N]]
        LB = [i / N * 100 + i % N for i in xrange(N * N) if B[i / N][i % N]]
        c = collections.Counter(i - j for i in LA for j in LB)
        return max(c.values() or [0])
Update 2018-05-15 about i / N * 100 + i % N
I find many people discuss it, so I update this explanantion.

1.why 100?
100 is big enough and very clear.
For example, If I slide 13 rows and 19 cols, it will be 1319.

why not 30?
30 is not big enough.
For example: 409 = 13 * 30 + 19 = 14 * 30 - 11.
409 can be taken as sliding "14 rows and -11 cols" or "13 rows and 19 cols" at the same time.

How big is enough?
Bigger than 2N-1. Bigger than 2N-1. Bigger than 2N-1.

Can we replace i / N * 100 + i % N by i?
No, it's wrong for simple test case [[0,1],[1,1]], [[1,1],[1,0]
*/
class 835ImageOverlap {
    public int largestOverlap(int[][] A, int[][] B) {
        int n = A.length;
        int max = 0, count1, count2;
        for ( int r = 0; r < n; r++ ) {
            for ( int c = 0; c < n; c++ ) {
                count1 = 0;
                count2 = 0;
                for ( int i = 0; i < n - r; i++ ) {
                    for ( int j = 0; j < n - c; j++ ) {
                        if ( A[i][j] == 1 && B[i+r][j+c] == 1 ) count1++;
                        if ( B[i][j] == 1 && A[i+r][j+c] == 1 ) count2++;
                    }
                }
                max = Math.max(Math.max(count1, count2), max);
            }
        }
        return max;
    }
}