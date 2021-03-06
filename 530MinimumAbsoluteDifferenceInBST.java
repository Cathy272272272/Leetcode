/*
 * The most common idea is to first inOrder traverse the tree and compare the delta between each of the adjacent values. It's guaranteed to have the correct answer because it is a BST thus inOrder traversal values are sorted.

Solution 1 - In-Order traverse, time complexity O(N), space complexity O(1).

public class Solution {
    int min = Integer.MAX_VALUE;
    Integer prev = null;
    
    public int getMinimumDifference(TreeNode root) {
        if (root == null) return min;
        
        getMinimumDifference(root.left);
        
        if (prev != null) {
            min = Math.min(min, root.val - prev);
        }
        prev = root.val;
        
        getMinimumDifference(root.right);
        
        return min;
    }
    
}
What if it is not a BST? (Follow up of the problem) The idea is to put values in a TreeSet and then every time we can use O(lgN) time to lookup for the nearest values.

Solution 2 - Pre-Order traverse, time complexity O(NlgN), space complexity O(N).

public class Solution {
    TreeSet<Integer> set = new TreeSet<>();
    int min = Integer.MAX_VALUE;
    
    public int getMinimumDifference(TreeNode root) {
        if (root == null) return min;
        
        if (!set.isEmpty()) {
            if (set.floor(root.val) != null) {
                min = Math.min(min, root.val - set.floor(root.val));
            }
            if (set.ceiling(root.val) != null) {
                min = Math.min(min, set.ceiling(root.val) - root.val);
            }
        }
        
        set.add(root.val);
        
        getMinimumDifference(root.left);
        getMinimumDifference(root.right);
        
        return min;
    }
}
*/
/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */
class 530MinimumAbsoluteDifferenceInBST {
    public int getMinimumDifference(TreeNode root) {
        return (int)(find(root));
    }
    public double find( TreeNode root) {
        if ( root == null ) return Double.POSITIVE_INFINITY;
        double leftMax, rightMin, leftNodeMin, rightNodeMin, rootMin;
        TreeNode left = root.left;
        if ( left == null ) leftMax = Double.NEGATIVE_INFINITY;
        else {
            while ( left.right != null ) left = left.right;
            leftMax = left.val;
        }
        TreeNode right = root.right;
        if ( right == null ) rightMin = Double.POSITIVE_INFINITY;
        else {
            while ( right.left != null ) right = right.left; 
            rightMin = right.val;
        }
        leftNodeMin = find(root.left); 
        rightNodeMin = find(root.right);
        rootMin = Math.min(root.val - leftMax, rightMin - root.val);
        return Math.min(leftNodeMin, Math.min(rightNodeMin, rootMin));
    }
}