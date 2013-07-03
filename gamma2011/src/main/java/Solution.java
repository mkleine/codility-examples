import java.util.ArrayList;

class Solution {

  private static final int LIMIT = 100000000;
  private final boolean runTests;

  public Solution(){this(false);}
  public Solution(boolean runTests) {
    this.runTests = runTests;
    if(this.runTests){
      runTest("abc", 0);
      runTest("aaa", 3);
      runTest("aaaa", 6);
      runTest("baab", 2);
      runTest("baababa", 6);
      runTest("baabbaab", 8);
      runTest("abababababababababababababababababababababababababababababababababababababababababababababababababababab" +
              "abababababababababababababababababababababababababababababababababababababababababababababababababababab" +
              "abababababababababababababababababababababababababababababababababababababababababababababababababababab" +
              "abababababababababababababababababababababababababababababababababababababababababababababababababababab" +
              "abababababababababababababababababababababababababababababababababababababababababababababababababababab" +
              "abababababababababababababababababababababababababababababababababababababababababababababababababababab" +
              "abababababababababababababababababababababababababababababababababababababababababababababababababababab" +
              "ababababababababababababababababababababababababababababababababababababababababab",163620);
    }
  }

  private void runTest(String input, int expectedResult) {
    log("-------------------------------------------");
    log("testing", input, "expecting",expectedResult);
    final long start = System.currentTimeMillis();
    Solution solution = this;
    if(input.length() > 10) {
      // turn logging of for big tests
      solution = new Solution();
    }
    int result = solution.solution(input);
    log("took ", System.currentTimeMillis() -start, " millis");
    if (result != expectedResult) {
      throw new RuntimeException(input + ": "+ result);
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

  public int solution (String A) {
    int result = 0;

    final int length = A.length();
    ArrayList<Integer> rootIndexes = new ArrayList<Integer>();
    if(length > 1) {
      char last = 0;
      char beforeLast = 0;
      for(int i = 0; i < length; i++) {
        final Character current = A.charAt(i);
        log("checking char", current, "at index", i);

          for(int j = rootIndexes.size() -1; j > -1; j--) {
            final int rootIndex = rootIndexes.get(j);
            int index = rootIndex - i;
            log("checking root index", rootIndex, "i", i, "index", index);
            if(index < 0) {
              // out of range, don't need to look further
              log("discarding root index", rootIndex, "at index", j);
              rootIndexes.remove(j);
            } else {
              if( current == A.charAt(index)) {
                log("found", index %2 == 1 ? "even" : "odd","palindrome rooted at", rootIndex, ":", A.substring(index, i+1));
                result ++;
              } else {
                // current root is no longer root
                log("discarding root index", rootIndex, "at index", j);
                rootIndexes.remove(j);
              }
            }
        }

        if(beforeLast == current) {
          // odd palindrome
          int currentRootIndex = 2*i - 2;
          rootIndexes.add(currentRootIndex);
          result ++;
          log("found odd root index", currentRootIndex, result);
        }

        if(last == current) {
          // even palindrome
          int currentRootIndex = 2*i -1;
          rootIndexes.add(currentRootIndex);
          result ++;
          log("found even root index", currentRootIndex, result);
        }

        beforeLast = last;
        last = current;

        if(result > LIMIT) {
          return  -1;
        }
      }
    }

    return result;
  }

}
