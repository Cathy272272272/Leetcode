/* So long...... but faster......He means just record the calculations used......
 * The dp[i][j] saves how much more scores that the first-in-action player will get from i to j than the second player. First-in-action means whomever moves first. You can still make the code even shorter but I think it looks clean in this way.

public boolean PredictTheWinner(int[] nums) {
    int n = nums.length;
    int[][] dp = new int[n][n];
    for (int i = 0; i < n; i++) { dp[i][i] = nums[i]; }
    for (int len = 1; len < n; len++) {
        for (int i = 0; i < n - len; i++) {
            int j = i + len;
            dp[i][j] = Math.max(nums[i] - dp[i + 1][j], nums[j] - dp[i][j - 1]);
        }
    }
    return dp[0][n - 1] >= 0;
}
Here is the code for O(N) space complexity:


public boolean PredictTheWinner(int[] nums) {
    if (nums == null) { return true; }
    int n = nums.length;
    if ((n & 1) == 0) { return true; } // Improved with hot13399's comment.
    int[] dp = new int[n];
    for (int i = n - 1; i >= 0; i--) {
        for (int j = i; j < n; j++) {
            if (i == j) {
                dp[i] = nums[i];
            } else {
                dp[j] = Math.max(nums[i] - dp[j], nums[j] - dp[j - 1]);
            }
        }
    }
    return dp[n - 1] >= 0;
}

Edit : Since I have some time now, I will explain how I come up with this solution step by step:

1, The first step is to break the question into the sub-problems that we can program. From the question, the winning goal is that "The player with the maximum score wins". So one way to approach it is that we may want to find a way to maximize player 1's sum and check if it is greater than player 2's sum (or more than half of the sum of all numbers). Another way, after noting that the sum of all numbers is fixed, I realized that it doesn't matter how much player 1's total sum is as long as the sum is no less than player 2's sum. No matter how, I think we can easily recognize that it is a recursive problem where we may use the status on one step to calculate the answer for the next step. It is a common way to solve game problems. So we may start with using a brutal force recursive method to solve this one.

2, However, we always want to do better than brutal force. We may easily notice that there will be lots of redundant calculation. For example, "player 1 picks left, then player 2 picks left, then player 1 picks right, then player 2 picks right" will end up the same as "player 1 picks right, then player 2 picks right, then player 1 picks left, then player 2 picks left". So, we may want to use dynamic programming to save intermediate states.

3, I think it will be easy to think about using a two dimensional array dp[i][j] to save all the intermediate states. From step 1, we may see at least two ways of doing it. It just turned out that if we choose to save how much more scores that the first-in-action player will earn from position i to j in the array (as I did), the code will be better in a couple of ways.

4, After we decide that dp[i][j] saves how much more scores that the first-in-action player will get from i to j than the second player, the next step is how we update the dp table from one state to the next. Going back to the question, each player can pick one number either from the left or the right end of the array. Suppose they are picking up numbers from position i to j in the array and it is player A's turn to pick the number now. If player A picks position i, player A will earn nums[i] score instantly. Then player B will choose from i + 1 to j. Please note that dp[i + 1][j] already saves how much more score that the first-in-action player will get from i + 1 to j than the second player. So it means that player B will eventually earn dp[i + 1][j] more score from i + 1 to j than player A. So if player A picks position i, eventually player A will get nums[i] - dp[i + 1][j] more score than player B after they pick up all numbers. Similarly, if player A picks position j, player A will earn nums[j] - dp[i][j - 1] more score than player B after they pick up all numbers. Since A is smart, A will always choose the max in those two options, so:
dp[i][j] = Math.max(nums[i] - dp[i + 1][j], nums[j] - dp[i][j - 1]);

5, Now we have the recursive formula, the next step is to decide where it all starts. This step is easy because we can easily recognize that we can start from dp[i][i], where dp[i][i] = nums[i]. Then the process becomes a very commonly seen process to update the dp table. I promise that this is a very useful process. Everyone who is preparing for interviews should get comfortable with this process:
Using a 5 x 5 dp table as an example, where i is the row number and j is the column number. Each dp[i][j] corresponds to a block at row i, column j on the table. We may start from filling dp[i][i], which are all the diagonal blocks. I marked them as 1. Then we can see that each dp[i][j] depends only on dp[i + 1][j] and dp[i][j - 1]. On the table, it means each block (i, j) only depends on the block to its left (i, j - 1) and to its down (i + 1, j). So after filling all the blocks marked as 1, we can start to calculate those blocks marked as 2. After that, all blocks marked as 3 and so on.
0_1488092542752_dp.jpg
So in my code, I always use len to denote how far the block is away from the diagonal. So len ranges from 1 to n - 1. Remember this is the outer loop. The inner loop is all valid i positions. After filling all the upper side of the table, we will get our answer at dp[0][n - 1] (marked as 5). This is the end of my code.

However, if you are interviewing with a good company, they may challenge you to further improve your code, probably in the aspect of space complexity. So far, we are using a n x n matrix so the space complexity is O(n^2). It actually can be improved to O(n). That can be done by changing our way of filling the table. We may use only one dimensional dp[i] and we start to fill the table at the bottom right corner where dp[4] = nums[4]. On the next step, we start to fill the second to the last line, where it starts from dp[3] = nums[3]. Then dp[4] = Math.max(nums[4] - dp[3], nums[3] - dp[4]). Then we fill the third to the last line where dp[2] = nums[2] and so on... Eventually after we fill the first line and after the filling, dp[4] will be the answer.

On a related note, whenever we do sum, subtract, multiply or divide of integers, we might need to think about overflow. It doesn't seem to be a point to check for this question. However, we may want to consider using long instead of int for some cases. Further, in my way of code dp[i][j] roughly varies around zero or at least it doesn't always increases with approaching the upper right corner. So it will be less likely to overflow.
*/

class 486PredicttheWinner {
    public boolean PredictTheWinner(int[] nums) {
        int n = nums.length;
        if ( n % 2 == 0 || n == 1 ) return true;
        if ( win(nums) >= 0 ) return true;
        return false;
    }
    public int win(int[] nums) {
        int n = nums.length;
        if ( n == 3 ) return nums[0] + nums[2] - nums[1];
        int win1, win2, win3;
        win1 = win(Arrays.copyOfRange(nums,2,n));
        win2 = win(Arrays.copyOfRange(nums, 1, n-1));
        win3 = win(Arrays.copyOfRange(nums, 0, n-2));
        return Math.max(Math.min(nums[0] - nums[1] + win1, nums[0] - nums[n-1] + win2), Math.min(nums[n-1] - nums[n - 2] + win3, nums[n-1] - nums[0] + win2));
    }
}