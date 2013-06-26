import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Stack;

class Solution {

  private boolean runTests;
  private int[] partitions;

  public Solution(){this(false);}
  public Solution(boolean runTests) {
    this.runTests = runTests;
    if(runTests){
      final int l = indexOf(2 << 26, 2 << 28, 4);
      if(l != 2<<29) throw new RuntimeException("long: "+ l);
      int solution = solution(4, new int[]{0}, new int[]{0}, new int[]{0});
      if(solution != -1) throw new RuntimeException("a: "+solution);
      solution = solution(4, new int[]{0, 1, 1, 2, 3, 2, 1, 0, 0}, new int[]{0, 1, 1, 1, 2, 2, 3, 1, 0}, new int[]{0, 1, 0, 0, 0, 1, 1, 0, 1});
      if(solution != 8) throw new RuntimeException("example: "+ solution);
    }
  }

  private void log(Object ... args) {
    if(this.runTests) {
      StringBuilder stringBuilder = new StringBuilder();
      for(Object o : args) {
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

  void initPartitions(int numElements) {
    partitions = new int [numElements];
    for (int i = 0; i < partitions.length; i++) {
      partitions[i] = -1;
    }
  }

  public void union(int a, int b) {
    final int root_a = find(a);
    final int root_b = find(b);

    if (root_a != root_b) {
      if (partitions[root_b] < partitions[root_a]) {
        partitions[root_b] += partitions[root_a];
        partitions[root_a] = root_b;
      } else {
        partitions[root_a] += partitions[root_b];
        partitions[root_b] = root_a;
      }
    }
  }

  public int find(int x) {
    if(partitions[x] >= 0) {
      // find, set and return new root
      partitions[x] = find(partitions[x]);
      return partitions[x];
    }
    return x;
  }

  public int solution ( int N, int[] A, int[] B, int[] C ) {
    final int N_square = N * N;
    initPartitions(N_square);

    final Stack<List<Integer>> edges = computeFinalState(N, A, B, C);

    final int endNodeId = N_square - 1;
    if(find(0) == find(endNodeId)) {
      // the burn-outs didn't cut start from end node
      return -1;
    }

    while(!edges.isEmpty()) {
      final List<Integer> edge = edges.pop();
      log("re-connecting edge", edge);

      union(edge.get(0), edge.get(1));
      if(find(0) == find(endNodeId)) {
        log("same partition");
        return edges.size() +1;
      }
    }
    throw new IllegalStateException("initial grid was not connected");
  }

  private Stack<List<Integer>> computeFinalState(int N, int[] A, int[] B, int[] C) {
    // edges are lists of size 2
    HashSet<List<Integer>> cut = new HashSet<List<Integer>>(A.length);
    Stack<List<Integer>> edges = new Stack<List<Integer>>();
    for(int i = 0; i < A.length; i++) {
      /**
       * Initially, all the wires conduct the current, but the wires burn out at a rate of one per second. The burnouts are described by three zero-indexed arrays of integers, A, B and C, each of size M. For each moment T (0 ≤ T < M), in the T-th second the wire between nodes (A[T], B[T]) and:
       (A[T], B[T] + 1), if C[T] = 0 or
       (A[T] + 1, B[T]), if C[T] = 1
       */
      boolean incFirst = C[i] == 1;
      final int x = A[i];
      final int y = B[i];
      final int startId = indexOf(x, y, N);
      final int endId = indexOf(incFirst ? x + 1 : x, incFirst ? y : y + 1, N);
      final List<Integer> edge = Arrays.asList(startId, endId);
      cut.add(edge);
      edges.push(edge);
    }

    // compute partitions
    final int limit = N - 1;
    for(int i = 0; i < N; i++) {
      for(int j = 0; j < N; j++) {
        final int startId = indexOf(i, j, N);
        if(j < limit) {
          final int endId = indexOf(i, j + 1, N);
          if(!cut.contains(Arrays.asList(startId, endId))) {
            union(startId, endId);
            log("partitions after adding edge (", startId, "/", endId, "): ", partitions);
          }
        }
        if(i < limit) {
          final int endId = indexOf(i + 1, j, N);
          if(!cut.contains(Arrays.asList(startId, endId))) {
            union(startId, endId);
            log("partitions after adding edge (", startId, "/", endId, "): ", partitions);
          }
        }
      }
    }
    return edges;
  }

  /**
   * compute the index of a node (x,y)
   */
  static int indexOf(int x, int y, int N) {
    final long result = ((long) x) * (long) N + y;
    if(result > Integer.MAX_VALUE){
      throw new IllegalStateException("arithmetic overflow: " + result + " > " + Integer.MAX_VALUE);
    }
    if(result < 0) {
      throw new IllegalStateException("result is negative: "+ result);
    }
    return (int) result;
  }

}
