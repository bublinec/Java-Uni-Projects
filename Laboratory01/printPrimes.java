// print primes lower than n
void printPrimes (int max){
    int f; 
    double iSqrt;
    boolean iIsPrime;
    for(int i = 2; i<max; i++){
        // divide i by all numbers between 2 and it's sqare root to check if it is prime
        iIsPrime = true;
        f = 2;
        iSqrt = Math.sqrt(i);
        while (iIsPrime && f <= iSqrt){
            // if divisible by f, then not prime
            if(i % f == 0){
                iIsPrime = false;
            }
            f++;
        }
        // if prime print
        if(iIsPrime){
            System.out.println(i + " is prime");
        }
    }
}