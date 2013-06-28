import java.util.Arrays;

class Solution {

  private final boolean runTests;
  static final int UPPER_BOUND = 100 * 1000 * 1000;

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
    int s = solution(new int[] {1});
    if(s != 0) throw new RuntimeException("b: "+s);

    s = solution(new int[] {1,1});
    if(s != 1) throw new RuntimeException("c: "+s);

    s = solution(new int[] {1,2});
    if(s != 1) throw new RuntimeException("d: "+s);

    s = solution(new int[] {1,2,3,4});
    if(s != 3) throw new RuntimeException("e: "+s);

    s = solution(new int[] {1,1,1,2,2,2});
    if(s != 15) throw new RuntimeException("15 (1): "+s);

    s = solution(new int[] {1,1,1,2,2,3,3});
    if(s != 15) throw new RuntimeException("15 (2): "+s);

    s = solution(new int[] {1,2,2,3,3});
    if(s != 8) throw new RuntimeException("8: "+s);

    s = solution(new int[] {1,1,1,2,2,2,2,3,3,3});
    if(s != 36) throw new RuntimeException("36: "+s);

    s = solution(new int[] {0,3,3,7,5,3,11,1});
    if(s != 12) throw new RuntimeException("a: "+s);

    s = solution(new int[] {0,1,1,1,2,2,2,5});
    if(s != 21) throw new RuntimeException("21: "+s);

    System.out.println("running big test ...");
    final int upperBound = UPPER_BOUND + 2;
    int[] array = new int[upperBound];
    for(int i = 0; i < upperBound; i++){
      array[i] = i;
    }
    s = new Solution().solution(array); // run this test without logging
    if(s != -1) throw new RuntimeException("big: "+s);
    System.out.println("tests ok");
  }

  public int solution(int[] A){
    log();
    log("------------------------------------");

    int result = 0;

    // we're not interested in 'pairs', we only have to compare the actual values, so let's sort the input
    Arrays.sort(A);

    // single element array has no pairs
    if(A.length > 1) {
      // let's count adjacent values and sum them up
      int counter = 0;
      int lastCounter = 0;
      int current = A[0];

      for(int i = 0; i < A.length; i++) {
        final int elem = A[i];
        if(elem == current) {
          counter++;
        } else {
          current = elem;
          // add temp result
          result += fragmentSum(counter, lastCounter);
          if(result > UPPER_BOUND) {
            return -1;
          }
          // measure next bulk
          lastCounter = counter;
          counter = 1;
        }
        log("result for elem", elem, "is", result);
      }

      // add temp result
      result += fragmentSum(counter, lastCounter);
      log("result is", result);
      if(result > UPPER_BOUND) {
        return -1;
      }
    }
    return result;
  }

  private int fragmentSum(long counter, long lastCounter) {
      // the total of pairs that we sum up
    long sum = counter + lastCounter;
    long tmpResult = (sum * (sum - 1)) / 2;

      // make sure not to count 'lastCounter' twice
    if(lastCounter > 1) {
      tmpResult -= (lastCounter*(lastCounter -1))/2;
    }
    log("adding sum",tmpResult, "for counter", counter, "and last counter", lastCounter);
    // computation of sum shouldn't overflow because of long, but the total is bounded anyway
    return (int)tmpResult;
  }

}

class Test05{

  public static void main(String[] args) {
    new Solution(true);
  }
}
