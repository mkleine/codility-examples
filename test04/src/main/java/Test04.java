import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

class Solution {

  private final boolean runTests;

  @SuppressWarnings("UnusedDeclaration") // this constructor is used by codility
  public Solution(){this(false);}
  public Solution(boolean runTests) {
    this.runTests = runTests;
    if(this.runTests){
      runMyTests();
    }
  }

  private void log(Object ... args) {
     if(this.runTests) {
       StringBuilder stringBuilder = new StringBuilder();
       for(Object o : args) {
         stringBuilder.append(o).append(" ");
       }
       System.out.println(stringBuilder.toString());
     }
  }

  private void runMyTests() {
    int s = solution(6, new int[]{1,8,-3,0,1,3,-2,4,5});
    if(s != 7) throw new RuntimeException("a: "+s);

    s = solution(2, new int[] {1,1});
    if(s != 3) throw new RuntimeException("b: "+s);

    s = solution(3, new int[] {1,1});
    if(s != 0) throw new RuntimeException("b: "+s);
  }

  public int solution(int K, int[] A) {
    int result = 0;

    HashMap<Long, Set<Integer>> map = new HashMap<Long, Set<Integer>>(A.length);
    for(int i = 0; i < A.length; i++) {
      long expected = K - A[i];
      if(!map.containsKey(expected)) {
        map.put(expected, new HashSet<Integer>());
      }
      map.get(expected).add(i);
      log("expecting", expected, map);
    }
    log("expecting values", map);

    for(int i = 0; i < A.length; i++) {
      final long item = A[i];
      if(map.containsKey(item)) {
        final Set<Integer> integers = map.get(item);
        result += integers.size();
        integers.remove(i);
        log("found value", item, "remaining", map, "result", result);
      }
    }

    return result;
  }

}

class Test04{

  public static void main(String[] args) {
    new Solution(true);
  }
}