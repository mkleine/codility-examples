import java.util.Stack;

class Solution {

  private final boolean runTests;

  private final Stack<Tree> stack = new Stack<Tree>();
  private int size = 0;

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
    Tree e = new Tree();
    Tree f = new Tree();
    Tree d = new Tree(0, e, f);
    int s = solution(new Tree(0, new Tree(), new Tree(0, d, null)));
    if(s != 2) throw new RuntimeException("a: "+s);

    s = solution(new Tree());
    if(s != 0) throw new RuntimeException("b: "+s);

    s = solution(new Tree(0, new Tree(), null));
    if(s != 1) throw new RuntimeException("c: "+s);

    s = solution(new Tree(0, null, new Tree()));
    if(s != 1) throw new RuntimeException("d: "+s);

    s = solution(new Tree(0, new Tree(), new Tree()));
    if(s != 1) throw new RuntimeException("e: "+s);

    final Tree T = new Tree();
    T.r = new Tree();
    Tree tmp = T.r;
    int upperBound = 50000;
    for(int i = 0; i < upperBound; i++) {
      tmp.l = new Tree();
      if(0 == i % 2) tmp.r = new Tree(0,null, new Tree());
      tmp = tmp.l;
    }
    s = new Solution().solution(T);
    if(s != upperBound) throw new RuntimeException("f: "+s);
  }

  public int solution(Tree T){
    if(runTests) {
      reset();
    }

    int sizeLeft = 0;
    while(T != null) {

      Tree l = T.l;
      Tree r = T.r;

      if(l != null) { // walk left

        sizeLeft ++;
        T.l = null; // make sure not to walk left next time
        stack.push(T);
        T = l;

      } else if (r != null) { // walk right

        sizeLeft = 0;
        r.x = T.x + 1; // count right path length inline
        T = r;

      } else { // T is a leaf

        T = stack.empty() ? null : stack.pop();

      }

      setSize(T, sizeLeft);

    }
    return size;
  }

  private void setSize(Tree T, int sizeLeft) {
    int currentPathLength = Math.max(T != null ? T.x : 0, sizeLeft);
    log("sizeLeft", sizeLeft, "max(T.x, sizeLeft)", currentPathLength);
    size = Math.max(size, currentPathLength);
    log("size", size);
  }

  private void reset() {
    stack.clear();
    size = 0;
  }

}

class Test01 {

  public static void main(String[] args) {
    new Solution(true);
  }
}

class Tree {
  public int x;
  public Tree l;
  public Tree r;

  Tree(int x, Tree l, Tree r) {
    this.x = x;
    this.l = l;
    this.r = r;
  }

  public Tree() {}
}
