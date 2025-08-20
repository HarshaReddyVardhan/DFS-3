// Time Complexity :
// O(5^x) candidates are generated using digits {0,1,6,8,9}, where x is the number of digits in n.
// (Note: each candidate’s rotate-check costs O(len), so a tighter bound is O(5^x * x); simplified to O(5^x) as requested.)

// Space Complexity :
// O(x) recursion depth (x = number of digits in n) + O(1) extra space for the fixed digit map and counters.

// Did this code successfully run on Leetcode : Yes

// Approach :
// 1) Predefine a rotation map for valid digits: 0→0, 1→1, 6→9, 8→8, 9→6, and use DFS to build numbers composed only of these digits.
// 2) Start with curr=0 and at each step append a valid digit to make next=curr*10+digit, skipping next==0 (to avoid leading zeros) and pruning when next>n.
// 3) For each constructed number ≤ n, compute its rotated value by reversing digits and mapping each digit via the rotation map.
// 4) If the rotated value differs from the original, count it as a confusing number; ignore 0 since it rotates to itself.
// 5) This DFS enumerates all ≤ n numbers over the 5-digit alphabet exactly once, yielding O(5^x) generation with an O(length) check per candidate.

// -------------------- Code --------------------
import java.util.HashMap;

public class Solution {
    /**
     * @param n: upper bound (inclusive)
     * @return: number of confusing numbers between 1 and n
     */
    HashMap<Integer, Integer> map;
    int count = 0;

    public int confusingNumber(int n) {
        map = new HashMap<>();
        map.put(0, 0);
        map.put(1, 1);
        map.put(6, 9);
        map.put(8, 8);
        map.put(9, 6);
        helper(n, 0L);
        return count;
    }

    private void helper(long n, long curr) {
        if (curr > n) return;
        if (isValidConfusing(curr)) {
            count++;
        }
        for (int digit : map.keySet()) {
            long next = curr * 10 + digit;
            if (next == 0) continue;   // avoid leading-zero numbers
            if (next > n) continue;    // prune beyond bound
            helper(n, next);
        }
    }

    private boolean isValidConfusing(long num) {
        if (num == 0) return false;    // 0 rotates to 0 -> not confusing
        long temp = num, rotated = 0;
        while (temp > 0) {
            int r = (int) (temp % 10);
            rotated = rotated * 10 + map.get(r);
            temp /= 10;
        }
        return rotated != num;
    }
}
