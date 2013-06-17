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
    int s = solution(new int[]{1, 2, 3});
    if(s != -1) throw new RuntimeException("a: "+s);

    s = solution(new int[] {-7, 1, 5, 2, -4, 3,0});
    if(s != 3) throw new RuntimeException("b: "+s);
  }

  public int solution ( int[] A ) {

    long[] leftSums = new long[A.length];
    long[] rightSums = new long[A.length];
    for(int i = 0;i < A.length; i ++){
      int inverseIndex = A.length - (i +1);
      if(i > 0) {
        leftSums[i] = leftSums[i-1] + A[i-1];
        rightSums[inverseIndex] = rightSums[inverseIndex +1] + A[inverseIndex +1];
      } else {
        leftSums[i] = 0;
        rightSums[inverseIndex] = 0;
      }
      log("A[",i,"]", A[i], "lefSums", leftSums[i]);
      log("A[",inverseIndex,"]", A[inverseIndex], "rightSums", rightSums[inverseIndex]);
    }

    for(int i = 0; i < leftSums.length; i++) {
      long elem = leftSums[i];
      if(elem == rightSums[i]) {
        return i;
      }

    }

    return -1;
  }

}

class Equi {

  public static void main(String[] args) {
    new Solution(true);
  }
}