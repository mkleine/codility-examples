import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;

public class Gamma2011 {

  final SystemOutLog log = new SystemOutLog();

  public static void main(String[] args) {
    boolean runTests = true;
    final Gamma2011 gamma2011 = new Gamma2011();
    if(runTests)
      gamma2011.runTests();
    else {
      gamma2011.generateTestCase();
    }
  }

  void log(Object... args) {
    log.log(args);
  }

  void generateTestCase() {
    final char[] alphabet = {'a','b','c'};
    final Solution solution = new Solution();
    for(int length = 4, attempts = 0; attempts < 100000; length = attempts / length > 1.8 ? length +1 : length, attempts = attempts > 2* length ? 0 : attempts  +1) {
      StringBuilder builder = new StringBuilder(length);
      for(int i = 0; i < length; i++) {
        int index = (int)Math.floor(Math.random()* alphabet.length);
        builder.append(alphabet[index]);
      }
      final String input = builder.toString();
      //log("checking ", input);
      final int result = solution.solution(input);
      final int expectedResult = new Solution1().solution(input);
      if(result != expectedResult) {
        log("---------------------------------------------------");
        log("-------------------------FAIL----------------------");
        log("---------------------------------------------------");
        log(input);
        log("expected: " + expectedResult);
        log("result: " + result);
        System.exit(1);
      }
      //log("result: " + result);
    }
  }

  void runTests() {
    final long start = System.currentTimeMillis();

    final Solution solution = new Solution(log);
    // ababbbb
    int[] ints = solution.shrinkAdjacentRootIndexes(new ArrayList<Integer>(asList(2, 4, 7, 8, 9, 10, 11)), 2, 7);
    expect("count", 1, ints[0]);
    expect("distance", 2, ints[1]);
    expect("new root", 9, ints[2]);

    runTest("bcccb", 4);
    runTest("abbbac", 4);
    runTest("cabbba", 4);
    runTest("abbbba", 7) ;
    runTest("accaccca", 8);
    runTest("aababaccbbabaccbcab", 11);
    runTest("aaaaabbababcbab", 20);

    runTest("a", 0);
    runTest("aa", 1);
    runTest("aaa", 3);
    runTest("aaaa", 6); // 5 + 1
    runTest("aaaaa", 10); // 7 + 3
    runTest("aaaaaa", 15); // 9 + 6
    runTest("aaaaaaa", 21); // 11 + 10
    runTest("aaaaaaaa", 28); // 13 + 15
    runTest("cbaaacc", 4);
    runTest("aaaabbbbbbbaa", 30);    // <-
    runTest("aaaabbbbbbbbaa", 37);    // <-
    runTest("aaaabbbbccccdddd", 24);
    runTest("aaaabbbbaaaabbbb", 32);
    runTest("aaaabbbbaaaabbbbaa", 37);
    runTest("aaaabbbbaaaabbbbaaaabb", 51);
    runTest("aaaabbbbaaaabbbbbbbbaa", 57);
    runTest("abc", 0);
    runTest("baab", 2);
    runTest("baababa", 6);
    runTest("aba", 1);
    runTest("abab", 2);
    runTest("ababa", 4);
    runTest("ababab", 6);
    runTest("abababa", 9);
    runTest("abababab", 12);
    runTest("ababababa", 16);
    runTest("ababababab", 20);
    runTest("abababababa", 25);
    runTest("baabbaab", 8);
    runTest("abababababababababababababababababababababababababababababababababababababababababababababababababababab" +
            "abababababababababababababababababababababababababababababababababababababababababababababababababababab" +
            "abababababababababababababababababababababababababababababababababababababababababababababababababababab" +
            "abababababababababababababababababababababababababababababababababababababababababababababababababababab" +
            "abababababababababababababababababababababababababababababababababababababababababababababababababababab" +
            "abababababababababababababababababababababababababababababababababababababababababababababababababababab" +
            "abababababababababababababababababababababababababababababababababababababababababababababababababababab" +
            "ababababababababababababababababababababababababababababababababababababababababab", 163620);
    runTest("aabbaabbaabbaabbaabbaabbaabbaabbaabbaabbaabbaabbaabbaabbaabbaabbaabbaabbaabbaabbaabbaabbaabbaabbaabbaabb" +
            "aabbaabbaabbaabbaabbaabbaabbaabbaabbaabbaabbaabbaabbaabbaabbaabbaabbaabbaabbaabbaabbaabbaabbaabbaabbaabb" +
            "aabbaabbaabbaabbaabbaabbaabbaabbaabbaabbaabbaabbaabbaabbaabbaabbaabbaabbaabbaabbaabbaabbaabbaabbaabbaabb" +
            "aabbaabbaabbaabbaabbaabbaabbaabbaabbaabbaabbaabbaabbaabbaabbaabbaabbaabbaabbaabbaabbaabbaabbaabbaabbaabb" +
            "aabbaabbaabbaabbaabbaabbaabbaabbaabbaabbaabbaabbaabbaabbaabbaabbaabbaabbaabbaabbaabbaabbaabbaabbaabbaabb" +
            "aabbaabbaabbaabbaabbaabbaabbaabbaabbaabbaabbaabbaabbaabbaabbaabbaabbaabbaabbaabbaabbaabbaabbaabbaabbaabb" +
            "aabbaabbaabbaabbaabbaabbaabbaabbaabbaabbaabbaabbaabbaabbaabbaabbaabbaabbaabbaabbaabbaabbaabbaabbaabbaabb" +
            "aabbaabbaabbaabbaabbaabbaabbaabbaabbaabbaabbaabbaabbaabbaabbaabbaabbaabbaabbaabbaabbaa", 82825);
    runTest("aaaabbbbaaaabbbbaaaabbbbaaaabbbbaaaabbbbaaaabbbbaaaabbbbaaaabbbbaaaabbbbaaaabbbbaaaabbbbaaaabbbbaaaabbbb" +
            "aaaabbbbaaaabbbbaaaabbbbaaaabbbbaaaabbbbaaaabbbbaaaabbbbaaaabbbbaaaabbbbaaaabbbbaaaabbbbaaaabbbbaaaabbbb" +
            "aaaabbbbaaaabbbbaaaabbbbaaaabbbbaaaabbbbaaaabbbbaaaabbbbaaaabbbbaaaabbbbaaaabbbbaaaabbbbaaaabbbbaaaabbbb" +
            "aaaabbbbaaaabbbbaaaabbbbaaaabbbbaaaabbbbaaaabbbbaaaabbbbaaaabbbbaaaabbbbaaaabbbbaaaabbbbaaaabbbbaaaabbbb" +
            "aaaabbbbaaaabbbbaaaabbbbaaaabbbbaaaabbbbaaaabbbbaaaabbbbaaaabbbbaaaabbbbaaaabbbbaaaabbbbaaaabbbbaaaabbbb" +
            "aaaabbbbaaaabbbbaaaabbbbaaaabbbbaaaabbbbaaaabbbbaaaabbbbaaaabbbbaaaabbbbaaaabbbbaaaabbbbaaaabbbbaaaabbbb" +
            "aaaabbbbaaaabbbbaaaabbbbaaaabbbbaaaabbbbaaaabbbbaaaabbbbaaaabbbbaaaabbbbaaaabbbbaaaabbbbaaaabbbbaaaabbbb" +
            "aaaabbbbaaaabbbbaaaabbbbaaaabbbbaaaabbbbaaaabbbbaaaabbbbaaaabbbbaaaabbbbaaaabbbb", 41612);
    runTest("aaaabbbbaaaabbbbaaaabbbbaaaabbbbaaaabbbbaaaabbbbaaaabbbbaaaabbbbaaaabbbbaaaabbbbaaaabbbbaaaabbbbaaaabbbb" +
            "aaaabbbbaaaabbbbaaaabbbbaaaabbbbaaaabbbbaaaabbbbaaaabbbbaaaabbbbaaaabbbbaaaabbbbaaaabbbbaaaabbbbaaaabbbb" +
            "aaaabbbbaaaabbbbaaaabbbbaaaabbbbaaaabbbbaaaabbbbaaaabbbbaaaabbbbaaaabbbbaaaabbbbaaaabbbbaaaabbbbaaaabbbb" +
            "aaaabbbbaaaabbbbaaaabbbbaaaabbbbaaaabbbbaaaabbbbaaaabbbbaaaabbbbaaaabbbbaaaabbbbaaaabbbbaaaabbbbaaaabbbb" +
            "aaaabbbbaaaabbbbaaaabbbbaaaabbbbaaaabbbbaaaabbbbaaaabbbbaaaabbbbaaaabbbbaaaabbbbaaaabbbbaaaabbbbaaaabbbb" +
            "aaaabbbbaaaabbbbaaaabbbbaaaabbbbaaaabbbbaaaabbbbaaaabbbbaaaabbbbaaaabbbbaaaabbbbaaaabbbbaaaabbbbaaaabbbb" +
            "aaaabbbbaaaabbbbaaaabbbbaaaabbbbaaaabbbbaaaabbbbaaaabbbbaaaabbbbaaaabbbbaaaabbbbaaaabbbbaaaabbbbaaaabbbb" +
            "aaaabbbbaaaabbbbaaaabbbbaaaabbbbaaaabbbbaaaabbbbaaaabbbbaaaabbbbaaaabbbbaaaabbbbbbbb", 41634);
    runTest("aaaabbbbaaaabbbbaaaabbbbaaaabbbbaaaabbbbaaaabbbbaaaabbbbaaaabbbbaaaabbbbaaaabbbbaaaabbbbaaaabbbbaaaabbbb" +
            "aaaabbbbaaaabbbbaaaabbbbaaaabbbbaaaabbbbaaaabbbbaaaabbbbaaaabbbbaaaabbbbaaaabbbbaaaabbbbaaaabbbbaaaabbbb" +
            "aaaabbbbaaaabbbbaaaabbbbaaaabbbbaaaabbbbaaaabbbbaaaabbbbaaaabbbbaaaabbbbaaaabbbbaaaabbbbaaaabbbbaaaabbbb" +
            "aaaabbbbaaaabbbbaaaabbbbaaaabbbbaaaabbbbaaaabbbbaaaabbbbaaaabbbbaaaabbbbaaaabbbbaaaabbbbaaaabbbbaaaabbbb" +
            "aaaabbbbaaaabbbbaaaabbbbaaaabbbbaaaabbbbaaaabbbbaaaabbbbaaaabbbbaaaabbbbaaaabbbbaaaabbbbaaaabbbbaaaabbbb" +
            "aaaabbbbaaaabbbbaaaabbbbaaaabbbbaaaabbbbaaaabbbbaaaabbbbaaaabbbbaaaabbbbaaaabbbbaaaabbbbaaaabbbbaaaabbbb" +
            "aaaabbbbaaaabbbbaaaabbbbaaaabbbbaaaabbbbaaaabbbbaaaabbbbaaaabbbbaaaabbbbaaaabbbbaaaabbbbaaaabbbbaaaabbbb" +
            "aaaabbbbaaaabbbbaaaabbbbaaaabbbbaaaabbbbaaaabbbbaaaabbbbaaaabbbbaaaabbbbaaaabbbbbbbbaa", 41637);
    log("-------------------------------------------");
    log("total millis:", System.currentTimeMillis() - start);
  }

  private void runTest(String input, int expectedResult) {
    log("-------------------------------------------");
    log("testing", input, "expecting", expectedResult);
    final long start = System.currentTimeMillis();
    Solution solution = new Solution(log);
    if (input.length() > 20) {
      // turn logging off for big tests
      solution = new Solution();
    }
    int result = solution.solution(input);
    log("took ", System.currentTimeMillis() - start, " millis");
    expect(input, expectedResult, result);
  }

  private void expect(String message, int expectedResult, int result) {
    if (result != expectedResult) {
      throw new RuntimeException(message + ": " + result);
    }
  }

  class SystemOutLog extends Solution.Log {
    void log(Object... args) {
      StringBuilder stringBuilder = new StringBuilder();
      for (Object o : args) {
        if(o.getClass().isArray()) {
          final int length = Array.getLength(o);
          List<Object> list = new ArrayList<Object>(length);
          for(int i = 0; i < length; i++) {
            list.add(Array.get(o, i));
          }
          o = list;
        }
        stringBuilder.append(o).append(" ");
      }
      System.out.println(stringBuilder.toString());
    }
  }
}
