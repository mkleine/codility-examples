public class Alpha2010 {
  public static void main(String[] args) {
    Solution s = new Solution();
    int solution = s.solution(new int[]{2, 1, 1, 1, 0, 5, 2});
    if(solution != 5) {
      throw new RuntimeException("#1");
    }
    solution = s.solution(new int[]{5});
    if(solution != 0) {
      throw new RuntimeException("#2");
    }
    solution = s.solution(new int[]{2,2});
    if(solution != 0) {
      throw new RuntimeException("#3");
    }
  }
}
