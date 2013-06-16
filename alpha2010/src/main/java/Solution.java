import java.util.HashSet;

class Solution {

  public int solution (int[] A) {
    final HashSet<Integer> seen = new HashSet<Integer>(A.length / 2);
    int result = 0;
    for(int i = 0; i < A.length ; i++){
      if(seen.add(A[i])) {
        result = i;
      }
    }
    return result;
  }
}