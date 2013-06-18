import java.util.Stack;

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
    Tree tree = new Tree(5, new Tree(8, new Tree(12), new Tree(2)), new Tree(9, new Tree(8, new Tree(2), null), new Tree(4, null, new Tree(5))));
    int s = solution(tree);
    if(s != 7) throw new RuntimeException("a: "+s);
    log("solution a: ", s);

    s = solution(new Tree(10));
    if(s != 0) throw new RuntimeException("b: "+s);
    log("solution b: ", s);
  }

  final class PathElem {
    final int min;
    final int max;
    final Tree T;

    public PathElem(Tree t) {
      this(t, t.x, t.x);
    }

    public PathElem(Tree t, int min, int max) {
      T = t;
      this.min = Math.min(t.x, min);
      this.max = Math.max(t.x, max);
      log("creating path elem", t.x, this.min, this.max);
    }
  }

  public int solution (Tree T) {

    final Stack<PathElem> stack = new Stack<PathElem>();
    int amplitude = 0;

    PathElem P = new PathElem(T);
    while(P != null) {
      T = P.T;
      int currentMin = P.min;
      int currentMax = P.max;

      final Tree l = T.l;
      final Tree r = T.r;

      if(l != null) { // walk left

        stack.push(P);
        T.l = null; // make sure not to walk left again
        P = new PathElem(l, currentMin, currentMax);

      } else if (r != null) { // walk right

        P = new PathElem(r, currentMin, currentMax);

      } else { // leaf

        P = stack.empty() ? null : stack.pop();

      }

      amplitude = Math.max(amplitude, currentMax - currentMin);
      log("current amplitude", amplitude);

    }

    return amplitude;
  }

}

class Test02 {

  public static void main(String[] args) {
    new Solution(true);
  }
}

final class Tree {
  public int x;
  public Tree l;
  public Tree r;

  Tree(int x, Tree l, Tree r) {
    this.x = x;
    this.l = l;
    this.r = r;
  }

  public Tree() {}

  public Tree(int i) {
    x = i;
  }
}