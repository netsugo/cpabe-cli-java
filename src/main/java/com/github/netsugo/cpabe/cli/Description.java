package com.github.netsugo.cpabe.cli;

public class Description {
    public final static String decrypted = "plain file";
    public final static String encrypted = "encrypted file";
    public final static String publicKey = "public key file";
    public final static String privateKey = "private key file";
    public final static String master = "master key file";
    public final static String policy = "policy text";
    public final static String attr = "attribute text";
    public final static String policyExample = "example: ";
    public final static String attrExample = "example: ";

    public static class Command {
        public final static String setup = "generate public key and master key";
        public final static String keygen = "generate private key";
        public final static String encrypt = "encrypt plain(decrypted) file";
        public final static String decrypt = "decrypt encrypted file";
    }
}