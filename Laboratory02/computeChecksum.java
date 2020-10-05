int computeChecksum (String iban){
// return the check sum of the iban, return -1 if invalid and log the error
    iban = iban.replaceAll(" ", "").toUpperCase();
    int len = iban.length();
    
    // check if iban is valid length for given country
    String countryCode = iban.substring(0, 2);
    boolean ibanIsValid = true;
    switch(countryCode) {
        case "GB":
          if (len != 22){ibanIsValid = false;}
          break;
        case "GR":
          if (len != 27){ibanIsValid = false;}
          break;
        case "SA":
          if (len != 24){ibanIsValid = false;}
          break;
        case "CH":
          if (len != 21){ibanIsValid = false;}
          break;
        case "TR":
          if (len != 26){ibanIsValid = false;}
          break;
        default:
          System.out.println("Unknown country code: " + countryCode);
          return -1;
    }
    if (!ibanIsValid) {
        System.out.println("Invalid IBAN length: " + len);
        return -1;
    }

    // rearange iban
    iban = iban.substring(4) + (countryCode + "00");

    // get digits 
    String digits = "";
    for (int i = 0; i < len; i++) {
        char curChar = iban.charAt(i);
        // check if the character is valid
        if (!(Character.isDigit(curChar) || Character.isLetter(curChar))){
            System.out.println("Invalid character in IBAN: " + curChar);
            return -1;
        }
        digits += Integer.toString(Character.getNumericValue(curChar));
    }
    
    // get the check sum
    int DIVISOR = 97, SUBTRACT_VALUE = 98;
    int mod = new BigInteger(digits).mod(BigInteger.valueOf(DIVISOR)).intValue();
    int checkSum = SUBTRACT_VALUE - mod
    return checkSum;
}