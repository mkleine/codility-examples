import java.util.Arrays;
import java.util.Comparator;

class Beta2010 {
  public static void main(String[] args) {
    new Solution(true);
  }
}

class Solution {

  /**
   * The function should return −1 if the number of intersecting pairs exceeds 10,000,000.
   */
  private static final int UPPER_LIMIT = 10000000;
  private final boolean runTests;

  @SuppressWarnings("UnusedDeclaration") // this constructor is used by codility
  public Solution(){this(false);}
  public Solution(boolean runTests) {
    this.runTests = runTests;
    if(this.runTests){
      int result = solution(new int[]{1, 5, 2, 1, 4, 0});
      if (result != 11) {
        throw new RuntimeException("a: " + result);
      }
    }
  }

  public int solution (int[] A) {
    long[][] pairs = new long[A.length][2];

    for(int i = 0; i < A.length; i++) {
      long lower = (long)i - (long)A[i];
      long upper = (long)i + (long)A[i];
      if(this.runTests) {
        System.out.println(lower + " / " + upper);
      }
      pairs[i] = new long[] {lower, upper};
    }

    Arrays.sort(pairs,new Comparator<long[]>() {
      @Override
      public int compare(long[] o1, long[] o2) {
        Long l1 = o1[0];
        Long l2 = o2[0];
        return l1.compareTo(l2);
      }
    });

    if(this.runTests) {
      System.out.println("sorted:");
      for(int i = 0; i < pairs.length; i++)
        System.out.println(pairs[i][0] + " / " + pairs[i][1]);
    }

    int result = 0;
    for(int i = 0; i < pairs.length; i++) {
      long upper = pairs[i][1];
      for(int j = i + 1; j < pairs.length ; j++) {
        long lower = pairs[j][0];
        if(lower <= upper) {
          result++; // found intersection
        } else {
          break; // don't intersect
        }
      }
      if(result > UPPER_LIMIT) {
        break;
      }
    }

    if(result > UPPER_LIMIT) {
      return -1;
    }

    return result;
  }

}
