// get array of primes lower than n
int [] getPrimesArr(int n){
    int [] primes = new int[n];
    int j; 
    double sqrt_i;
    boolean i_is_prime;
    for(int i = 2; i<n; i++){
        // divide i by all numbers between 2 and it's sqare root to check if it is prime
        j = 2;
        sqrt_i = Math.sqrt(i);
        i_is_prime = true;
        while (i_is_prime && j <= sqrt_i){
            // if divisible by j, then not prime
            if(i % j == 0){
                i_is_prime = false;
            }
            j++;
        }
        // if prime, add to the primes array
        if(i_is_prime){
            primes[i-2] = i;
        }
    }
    return primes;
}



// print primes lower than n