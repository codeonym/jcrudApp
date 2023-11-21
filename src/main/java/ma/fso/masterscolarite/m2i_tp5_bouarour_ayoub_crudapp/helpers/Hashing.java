package ma.fso.auth.m2i_tp3_ex2_bouarour_ayoub.utility;


import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Hashing {

    protected String hash_sha(String pwd, String algo){
        try {

            // INSTANCING
            MessageDigest md = MessageDigest.getInstance(algo);

            // ADDING THE PWD BYTES TO MD-INSTANCE
            md.update(pwd.getBytes(StandardCharsets.UTF_8));

            // HASHING THE PWD AND GETTING THE HASH BYTES
            byte [] hashedBytes = md.digest();

            // STRING BUILDER TO STORE THE HASH
            StringBuilder sb = new StringBuilder();

            // CONVERTING BYTES TO HEX
            for (byte b: hashedBytes){
                sb.append(String.format("%02x", b));
            }

            // RETURNING THE HASHED PWD
            return sb.toString();

        }catch(NoSuchAlgorithmException e){
            e.printStackTrace();

            // HASH FAILED
            return null;
        }
    }
    public String hash_sha256(String pwd){
        return hash_sha(pwd, "SHA-256");
    }
    public String hash_sha512(String pwd){
        return hash_sha(pwd, "SHA-512");
    }
    public String hash_sha1(String pwd){
        return hash_sha(pwd, "SHA-1");
    }
    public boolean password_verify(String pwd, String hash, String algo){

        // STR CMP BASED ON ALGO
        switch(algo){
            case "SHA-256":
                return this.hash_sha256(pwd).equals(hash);
            case "SHA-512":
                return this.hash_sha512(pwd).equals(hash);
            case "SHA-1":
                return this.hash_sha1(pwd).equals(hash);
        }
        // UNRECOGNIZED ALGO
        return false;
    }
    public boolean password_verify(String pwd, String hash) {

        // DEFAULT ALGO
        return this.password_verify(pwd, hash, "SHA-256");
    }
}
