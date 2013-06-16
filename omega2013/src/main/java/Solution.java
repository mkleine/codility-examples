import java.util.ArrayDeque;
import java.util.HashMap;

class Solution {

  private HashMap<Integer, Integer> mins;
  private ArrayDeque<Integer> diameters;

  private void computeMins(int[] A, int limit) {
    int elem = A.length > 0 ? A[0] : -1;
    diameters.push(elem);
    limit = A.length < limit ? A.length : limit;

    for(int i = 0 ; i < limit ; i++) {
      int c = A[i];
      if(elem > c) {
        mins.put(elem, i -1);
        elem = c;
        diameters.push(elem);
      }
    }
    mins.put(elem, limit);
  }

  public int solution ( int[] A,int[] B ) {
    mins = new HashMap<Integer, Integer>(B.length);
    diameters = new ArrayDeque<Integer>(B.length);
    computeMins(A, B.length);

    Integer diameter = diameters.pop();
    Integer diameterIndex = mins.get(diameter);
    int limit = diameterIndex;
    int i = 0;
    for(;i < limit; i++) {
      int elem = B[i];
      if(elem > diameter) {
        while(true) {
          if(diameters.isEmpty()) {
            return i;
          }
          diameter = diameters.pop();
          diameterIndex = mins.get(diameter);
          if(diameter >= elem) {
            limit = Math.min(limit,i + diameterIndex + 1);
            break;
          }
        }
      }
    }

    return i;
  }

}