boolean checkBarcode (String barcode) {
	if (barcode.length() != 8 && barcode.length() != 13) {
		// Wrong length
		return false;
	}
	
	char digitChar = barcode.charAt(barcode.length() - 1);
	if (!Character.isDigit(digitChar)) {
		// Check digit is not a digit
		return false;
	}
	
	char[] barcodeChars = barcode.substring(0, barcode.length() - 1).toCharArray();
	
	int sum = 0;
	boolean odd = true;
	for (int i = barcodeChars.length - 1; i >= 0; i--) {
		if (!Character.isDigit (barcodeChars[i])) {
			// One character is not a digit
			return false;
		}
		
		int value = Character.getNumericValue (barcodeChars[i]);
		if (odd) {
			sum += 3 * value;
		} else {
			sum += value;
		}
		odd = !odd;
	}
	
	int result = 10 - (sum % 10);
	if (result == 10) {
		result = 0;
	}
	
	// See if computed check digit matches last digit of barcode
	return result == Character.getNumericValue(digitChar);
}