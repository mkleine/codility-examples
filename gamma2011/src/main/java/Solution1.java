import java.util.ArrayList;

public class Solution1 {

  private static final int LIMIT = 100000000;

 int solution(String A) {
    int result = 0;

    final int length = A.length();
    ArrayList<Integer> rootIndexes = new ArrayList<Integer>();
    if (length > 1) {
      char last = 0;
      char beforeLast = 0;
      for (int i = 0; i < length; i++) {
        final Character current = A.charAt(i);

        for (int j = rootIndexes.size() - 1; j > -1; j--) {
          final int rootIndex = rootIndexes.get(j);
          int index = rootIndex - i;
          if (index < 0) {
            // out of range, don't need to look further
            rootIndexes.remove(j);
          } else {
            if (current == A.charAt(index)) {
              result++;
            } else {
              // current root is no longer root
              rootIndexes.remove(j);
            }
          }
        }

        if (beforeLast == current) {
          // odd palindrome
          int currentRootIndex = 2 * i - 2;
          rootIndexes.add(currentRootIndex);
          result++;
        }

        if (last == current) {
          // even palindrome
          int currentRootIndex = 2 * i - 1;
          rootIndexes.add(currentRootIndex);
          result++;
        }

        beforeLast = last;
        last = current;

        if (result > LIMIT) {
          return -1;
        }
      }
    }

    return result;
  }

}
