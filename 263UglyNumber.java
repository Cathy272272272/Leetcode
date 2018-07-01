class 263UglyNumber {
    public boolean isUgly(int num) {
        if ( num == 0 ) return false;
        while ( true ) {
            if ( num % 2 == 0 ) num /= 2;
            else if ( num % 5 == 0 ) num /= 5;
            else if ( num % 3 == 0 ) num /= 3;
            else break;
        }
        return 1 == num;
    }
}