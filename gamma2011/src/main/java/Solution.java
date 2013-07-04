import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Solution {

  private static final int LIMIT = 100000000;
  private final Log log;

  public Solution() {
    this(null);
  }

  public Solution(Log log) {
    this.log = log;
  }

  public int solution(String A) {
    int result = 0;

    final int length = A.length();
    final ArrayList<Integer> rootIndexes = new ArrayList<Integer>();
    if (length > 1) {
      char last = 0;
      char beforeLast = 0;
      for (int i = 0; i < length; i++) {
        final Character current = A.charAt(i);

        if (beforeLast == current) {
          // odd palindrome
          int currentRootIndex = 2 * i - 2;
          rootIndexes.add(currentRootIndex);
        }

        if (last == current) {
          // even palindrome
          int currentRootIndex = 2 * i - 1;
          rootIndexes.add(currentRootIndex);
        }

        beforeLast = last;
        last = current;

        if (rootIndexes.size() > LIMIT) {
          return -1;
        }
      }

      result = rootIndexes.size();
      final HashMap<Integer,Integer> rootIndexesWithDistance = new HashMap<Integer, Integer>();

      // combine and count adjacent rootIndex
      int start = -1, stepWidth = -1;
      for(int i = 0, r0, r1 = -2, r2 = -5; i < rootIndexes.size(); i++, stepWidth = r0 - r1, r2 = r1, r1 = r0) {
        r0 = rootIndexes.get(i);
        if((r1 + 1 == r0 && r2 + 1 == r1) || (r1 + 2 == r0 && r2 + 2 == r1)) {
          if(-1 == start) {
            start = i - 2;
          }
        } else if(-1 < start) {
          final int[] ints = stepWidth == 1
                  ? shrinkAdjacentRootIndexes(rootIndexes, start, i)
                  : shrinkOddPalindromes(rootIndexes, start, i);
          result += ints[0];
          if(ints[1] > 0) {
            rootIndexesWithDistance.put(ints[2], ints[1]);
          }
          i = start -1;
          r0 = -1;
          start = -1;
        }
      }

      if(-1 < start) {
        final int[] ints = stepWidth == 1
                ? shrinkAdjacentRootIndexes(rootIndexes, start, rootIndexes.size())
                : shrinkOddPalindromes(rootIndexes, start, rootIndexes.size());
        result += ints[0];
        if(ints[1] > 0) {
          rootIndexesWithDistance.put(ints[2], ints[1]);
        }
      }
      log("remaining root indexes", rootIndexes, rootIndexesWithDistance);

      if (result > LIMIT) {
        return -1;
      }

      for(Integer integer : rootIndexes) {
        final int offset = 1 == integer % 2 ? 3 : 4;
        log("#1 searching for more palindromes with root",integer,"-- current result is", result);
        log("#1 starting with indexes", (integer - offset)/2, "and", (integer + offset)/2, "while length is", length);
        for(int lower = (integer - offset)/2, upper = (integer + offset)/2 ; lower > -1 && upper < length; lower --, upper ++) {
          final char lowerChar = A.charAt(lower);
          final char upperChar = A.charAt(upper);
          if(lowerChar == upperChar) {
            result++;
          } else {
            log("#1 upperChar", upperChar, "doesn't match", lowerChar, "for root",integer, "at distance", integer - lower);
            break;
          }
        }
      }

      if (result > LIMIT) {
        return -1;
      }

      for(Map.Entry<Integer,Integer> entry : rootIndexesWithDistance.entrySet()) {
        final Integer integer = entry.getKey();
        final Integer distance = entry.getValue();
        final int offset = distance + (distance % 2 == integer % 2 ? 4 : 3);
        log("#2 searching for more palindromes with root",integer,"at distance", distance,"-- current result is", result);
        log("#2 starting with indexes", (integer -offset)/2, "and", (integer + offset )/2, "while length is", length);
        for(int lower = (integer - offset)/2, upper = (integer + offset)/2; lower > -1 && upper < length; lower --, upper ++) {
          final char lowerChar = A.charAt(lower);
          final char upperChar = A.charAt(upper);
          log("#2 comparing lowerChar",lowerChar,"at index", lower, "with upperChar", upperChar, "at index", upper,"-- current result is", result);
          if(lowerChar == upperChar) {
            result++;
          } else {
            log("#2 upperChar", upperChar, "doesn't match", lowerChar, "for root",integer, "at distance", integer - lower);
            break;
          }
        }
      }
    }
    return result > LIMIT ? -1 : result;
  }

  int[] shrinkOddPalindromes(ArrayList<Integer> rootIndexes, int start, int end) {
    final List<Integer> integers = rootIndexes.subList(start, end);
    final Integer endIndex = rootIndexes.get(end - 1);
    final Integer startIndex = integers.get(0);
    final int distance = (endIndex - startIndex )/2;
    final int size = integers.size();
    integers.clear();
    int[] result = {0, 0, startIndex + distance};
    if(1 == size % 2) {
      // 0 1 4 9 16 25  .... n^2
      result[0] = ((size -1) / 2);
      result[0] *= result[0];
      result[1] = distance;
    } else  {
      // 0 0 2 6 12 20 30 .... (n-1)*n
      result[0] = (size / 2);
      result[0] *= (result[0] -1);
    }
    log("number of new covered palindromes", result, "for root indexes from", startIndex, "to", endIndex, "replaced with new root", startIndex + distance);
    log("result", result, "new root indexes:", rootIndexes);
    return result;
  }

  int[] shrinkAdjacentRootIndexes(List<Integer> rootIndexes, int start, int end) {
    final List<Integer> integers = rootIndexes.subList(start, end);
    final Integer endIndex = rootIndexes.get(end - 1);
    final Integer startIndex = integers.get(0);
    final int distance = (endIndex - startIndex )/2;
    int[] result = {(distance) * (distance -1) / 2, distance, startIndex + distance};
    integers.clear();
    log("number of new covered adjacent palindromes ", result, "for root indexes from", startIndex, "to", endIndex, "replaced with new root", startIndex + distance);
    log("result", result, "new root indexes:", rootIndexes);
    return result;
  }

  void log(Object... args) {
    if(log != null) {
      log.log(args);
    }
  }

  static class Log {
    void log(Object ... obj){}
  }

}
