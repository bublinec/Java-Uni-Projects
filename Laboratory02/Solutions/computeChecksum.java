int computeChecksum (String iban) {
	iban = iban.replaceAll(" ", "");
	
	String country = iban.substring (0, 2);
	int targetLength = -1;
	
	switch (country) {
		case "GB":
			targetLength = 22;
			break;

		case "GR":
			targetLength = 27;
			break;

		case "SA":
			targetLength = 24;
			break;

		case "CH":
			targetLength = 21;
			break;

		case "TR":
			targetLength = 26;
			break;

		default:
			System.out.println("Unknown country code: " + country);
			return -1;
	}
	if (iban.length() != targetLength) {
		System.out.println("Invalid IBAN length: " + iban.length());
		return -1;
	}
	
	iban = iban.substring(4) + iban.substring(0, 2) + "00";

	String digits = "";
	for (char c : iban.toCharArray()) {
		if (Character.isDigit (c)) {
			digits += c;
		} else if (Character.isLetter (c)) {
			digits += Character.getNumericValue(c);
		} else {
			System.out.println ("Invalid character in IBAN: " + c);
			return -1;
		}
	}
	
	int mod = new BigInteger(digits).mod(BigInteger.valueOf(97)).intValue();
	return 98 - mod;
}