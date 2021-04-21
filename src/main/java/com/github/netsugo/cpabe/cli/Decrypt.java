package com.github.netsugo.cpabe.cli;

import com.github.netsugo.cpabe.Cpabe2;
import picocli.CommandLine.Command;
import picocli.CommandLine.ExitCode;
import picocli.CommandLine.Option;

import java.io.*;
import java.util.Base64;
import java.util.concurrent.Callable;

@Command(name = "decrypt", mixinStandardHelpOptions = true, description = {Description.Command.decrypt})
public class Decrypt extends Base64EncDec implements Callable<Integer> {
    @Option(names = {"-i", "--in"}, description = {Description.encrypted})
    private String encrypted;

    @Option(names = {"-p", "--public"}, required = true, description = {Description.publicKey})
    private String pubkey;

    @Option(names = {"-s", "--secret"}, required = true, description = {Description.secretKey})
    private String secret;

    @Option(names = {"-o", "--out"})
    private String decfile;


    private InputStream getInputStream() throws FileNotFoundException {
        return encrypted == null
                ? System.in
                : new FileInputStream(encrypted);
    }

    private OutputStream getOutputStream() throws FileNotFoundException {
        return decfile == null
                ? System.out
                : new FileOutputStream(decfile);
    }

    @Override
    public Integer call() throws Exception {
        var publicKey = decode(pubkey);
        var secretKey = decode(secret);

        var encryptedBase64Binary = Util.readStream(getInputStream());
        var encryptedBase64 = new String(encryptedBase64Binary);
        var encrypted = decode(encryptedBase64);
        var plain = Cpabe2.decrypt(publicKey, secretKey, encrypted);
        Util.writeStream(getOutputStream(), plain);

        return ExitCode.OK;
    }
}