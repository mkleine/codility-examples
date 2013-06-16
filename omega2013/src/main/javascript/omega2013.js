var mins = {};
var diameters = [];
function computeMins(A, limit) {

  var elem = A.length > 0 ? A[0] : -1;
  diameters.push(elem);
  limit = A.length < limit ? A.length : limit;

  for(var i = 1 ; i < limit; i ++){
    var currentElem = A[i];
    if(elem > currentElem){
      // the disk's place is above the current index
      mins[elem] = i -1;
      elem = currentElem;
      diameters.push(elem);
    }
  }
  mins[elem] = limit;
}

function solution ( A,B ) {

  computeMins(A, B.length);

  var diameter = diameters.pop();
  var diameterIndex = mins[diameter];
  var limit = diameterIndex;

  var i = 0;
  for( ; i < limit; i++){
    var elem = B[i];

    //console.log("checking (elem, index, diameter, diameterIndex)", elem, i, diameter, diameterIndex);

    if(elem <= diameter){
      // the disk fits
      //console.log("index",i,"ok");
    } else {
      while(true) {
        // disk blocked at diameterIndex, so check next diameter
        diameter = diameters.pop();
        if(diameter === undefined) {
          // no more diameters known
          return i;
        }
        diameterIndex = mins[diameter];
        if(diameter >= elem){
          //console.log("elem",elem,"fits at", diameterIndex,diameter);
          limit = Math.min(limit, i + diameterIndex +1);
          //console.log("decreasing limit to", limit);
          break;
        }
      }
    }
  }
  return i;
}

function testMe(){
  if(solution([5, 6, 4, 3, 6, 2, 3], [2, 3, 5, 2, 4]) != 4) throw new Error("1");
  if(solution([4,4,4], [2, 3, 5, 2, 4]) != 2) throw new Error("2");
  if(solution([4,0,10], [2, 3, 5, 2, 4]) != 1) throw new Error("3");
}

//testMe()