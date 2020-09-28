// return Nth Fibonacci number
int computeFibonacci (int n){
    int previousResult = 1;
    int lastResult = 1;
    int newResult;
    for(int i=0;i < n-2; i++){
        newResult = previousResult + lastResult;
        previousResult = lastResult;
        lastResult = newResult;
    }
    return lastResult;
}