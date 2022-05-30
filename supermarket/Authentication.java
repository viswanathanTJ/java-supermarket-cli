package supermarket;

public class Authentication {
    public String encrypt(String s) {
        String encryptedString = "";
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == 'z') {
                encryptedString += 'a';
            } else if (s.charAt(i) == 'Z') {
                encryptedString += 'A';
            } else if (s.charAt(i) == '9') {
                encryptedString += '0';
            } else {
                int temp = s.charAt(i) + 1;
                char ctemp = (char) temp;
                encryptedString += ctemp;
            }
        }
        return encryptedString;
    }

    public String decrypt(String s) {
        String decryptedString = "";
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == 'a') {
                decryptedString += 'z';
            } else if (s.charAt(i) == 'A') {
                decryptedString += 'Z';
            } else if (s.charAt(i) == '0') {
                decryptedString += '9';
            } else {
                int temp = s.charAt(i) - 1;
                char ctemp = (char) temp;
                decryptedString += ctemp;
            }
        }
        return decryptedString;
    }

}
