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
    int rev = bitrev(25);
    if(rev != 19) throw new RuntimeException("bitrev(25):" + rev);

    rev = bitrev(26);
    if(rev != 11) throw new RuntimeException("bitrev(26):" + rev);

    rev = bitrev(11);
    if(rev != 13) throw new RuntimeException("bitrev(11):" + rev);

    rev = bitrev(10);
    if(rev != 5) throw new RuntimeException("bitrev(10):" + rev);

    rev = bitrev(5);
    if(rev != 5) throw new RuntimeException("bitrev(5):" + rev);

    int s = solution(50);
    if(s != 10) throw new RuntimeException("50: "+s);

    s = solution(286);
    if(s != 22) throw new RuntimeException("286: "+s);

    s = solution(3245);
    if(s != 55) throw new RuntimeException("3245: "+s);
  }

  public int solution (int A) {
    /**
     * Iterate over integers up to sqrt(A) and check if we got a binary root.
     * Also check if factor f2 in (f1 * f2 = A) is a binary root but beware that it is possible
     * that there are smaller ones
     */
    int limit = (int) Math.floor(Math.sqrt(A));
    int f1 = 1;
    int result = -1;
    while(f1 < limit) {
      if(0 == A % f1) {
        final int f2 = A / f1;
        if(bitrev(f1)*f1 == A) {
          return f1;
        } else if(bitrev(f2) == f1) {
          // this one might not be the smallest binary root, so don't return immediately
          final int oldResult = result;
          result = f2;
          if(oldResult > 0) {
            result = Math.min(result, oldResult);
          }
        }
      }
      f1 ++;
    }
    return result;
  }

  private int bitrev(final int candidate) {
    int result = 0;
    int tmp = candidate;
    while(tmp > 0) {
      log("bitrev", tmp, result);
      result <<= 1;
      result += tmp %2;
      tmp >>= 1;
    }
    log("bitrev(", candidate, ")=", result);
    return result;
  }

}

class Test03{

  public static void main(String[] args) {
    new Solution(true);
  }
}