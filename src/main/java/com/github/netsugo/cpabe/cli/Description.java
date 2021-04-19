package com.github.netsugo.cpabe.cli;

public class Description {
    public final static String decrypted = "plain file path";
    public final static String encrypted = "encrypted file path";
    public final static String publicKey = "public key file path";
    public final static String privateKey = "private key file path";
    public final static String master = "master key file path";
    public final static String policy = "policy text";
    public final static String attr = "attribute text";
    public final static String policyExample = "Format: \"value1 value2 ... MofN (Postfix)\"";
    public final static String attrExample = "Format: \"value1 value2 ...\"";

    public static class Command {
        public final static String setup = "Generate public key and master key.";
        public final static String keygen = "Generate private key.";
        public final static String encrypt = "Encrypt plain(decrypted) data.";
        public final static String decrypt = "Decrypt encrypted data.";
    }
}