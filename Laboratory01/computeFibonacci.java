// return Nth Fibonacci number
int computeFibonacci (int n){
    int previousResult = 1, lastResult = 1;
    for(int i=0;i < n-2; i++){
        int newResult = previousResult + lastResult;
        previousResult = lastResult;
        lastResult = newResult;
    }
    return lastResult;
}