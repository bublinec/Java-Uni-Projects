// print primes lower than n
void printPrimes (int max){
    int f; 
    double sqrt_i;
    boolean i_is_prime;
    for(int i = 2; i<max; i++){
        // divide i by all numbers between 2 and it's sqare root to check if it is prime
        i_is_prime = true;
        f = 2;
        sqrt_i = Math.sqrt(i);
        while (i_is_prime && f <= sqrt_i){
            // if divisible by f, then not prime
            if(i % f == 0){
                i_is_prime = false;
            }
            f++;
        }
        // if prime print
        if(i_is_prime){
            System.out.println(i + " is prime");
        }
    }
}