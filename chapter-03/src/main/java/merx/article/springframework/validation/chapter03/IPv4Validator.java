package merx.article.springframework.validation.chapter03;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class IPv4Validator implements ConstraintValidator<IPv4, String> {
    private boolean allowReservedAddress;

    @Override
    public void initialize(IPv4 constraintAnnotation) {
        allowReservedAddress = constraintAnnotation.allowReservedAddress();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) return true;

        try {
            int[] octets = toOctets(value);

            return allowReservedAddress || isReservedAddress(octets);
        } catch (Exception e) {
            return false;
        }
    }

    /*
    This method can be implemented using regex, grouping as well.
    Since this is part of an article, think avoid regex would be easier to understand.
    */
    private int[] toOctets(String value) {
        // verify length, so won't do unnecessary split/parse after
        if (value.length() > "255.255.255.255".length()) throw new IllegalArgumentException(value);

        String[] strOctets = value.split(".");
        if (strOctets.length != 4) throw new IllegalArgumentException(value);

        int[] iOctets = new int[strOctets.length];
        for (int i = 0; i < strOctets.length; i++) {
            try {
                int iOctet = Integer.parseInt(strOctets[i]);
                if (!isValidOctet(iOctet)) throw new IllegalArgumentException(value);

                iOctets[i] = iOctet;
            } catch (NumberFormatException nfe) {
                throw new IllegalArgumentException(value);
            }
        }

        return iOctets;
    }

    private boolean isValidOctet(int octet) {
        return (0 <= octet) && (octet <= 255);
    }

    private boolean isReservedAddress(int[] octets) {
        /*
        The implementation... have fun :)
        https://en.wikipedia.org/wiki/Reserved_IP_addresses
        */

        return false;
    }
}
