public class Omega2013 {
  public static void main(String[] args) {
    Solution s = new Solution();
    if(s.solution(new int[]{5, 6, 4, 3, 6, 2, 3}, new int[]{2, 3, 5, 2, 4}) != 4) throw new Error("1");
    if(s.solution(new int[]{4,4,4}, new int[]{2, 3, 5, 2, 4}) != 2) throw new Error("2");
    if(s.solution(new int[]{4,0,10}, new int[]{2, 3, 5, 2, 4}) != 1) throw new Error("3");
  }
}
