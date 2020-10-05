boolean isNumeric(String str) { 
// return true if the string is numeric, false otherwise
    try {  
      Double.parseDouble(str);  
      return true;
    } catch(NumberFormatException e){  
      return false;  
    }  
  }

boolean checkBarcode (String barcode){
// return true if barcode is valid, false otherwise
    int len = barcode.length();
    // CHECK IF THE BARCODE IS VALID LENGTH
    if (!(len == 8 || len == 13)){
        return false;
    }
    // CHECK IF THE BARCDE IS NUMERIC
    if (!isNumeric(barcode)){
        return false;
    }

    // CHECK THE SUM
    // compute the check digit
    int checkSum = 0, partialSum = 0;
    boolean weightThree = true;
    for (int i=len-2; i >= 0; i--){
        // compute partial sum
        partialSum = Character.getNumericValue(barcode.charAt(i));
        if (weightThree){
            partialSum = 3 * partialSum;
        }
        // add partial sum to the checkSum and switch the weightThree flag
        checkSum += partialSum;
        weightThree = !weightThree;
    }
    int checkDigit = 10 - checkSum % 10;
    // compare check digit with the actual check digit
    if (checkDigit != Character.getNumericValue(barcode.charAt(len - 1))){
        return false;
    }

    return true;
}