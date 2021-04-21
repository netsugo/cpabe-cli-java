package com.github.netsugo.cpabe.cli;

import com.github.netsugo.cpabe.Cpabe2;
import picocli.CommandLine.Command;
import picocli.CommandLine.ExitCode;
import picocli.CommandLine.Option;

import java.io.*;
import java.util.Base64;
import java.util.concurrent.Callable;

@Command(name = "encrypt", mixinStandardHelpOptions = true, description = {Description.Command.encrypt})
public class Encrypt implements Callable<Integer> {
    private static final Base64.Decoder decoder = Base64.getDecoder();
    private static final Base64.Encoder encoder = Base64.getEncoder();

    @Option(names = {"-i", "--in"}, description = {Description.decrypted})
    private String plain;

    @Option(names = {"-p", "--public"}, description = {Description.publicKey}, required = true)
    private String pubkey;

    @Option(names = {"-c", "--policy"}, description = {Description.policy, Description.policyExample}, required = true)
    private String policy;

    @Option(names = {"-o", "--out"})
    private String encfile;

    private InputStream getInputStream() throws FileNotFoundException {
        return plain == null
                ? System.in
                : new FileInputStream(plain);
    }

    private byte[] readPlain() throws IOException {
        return plain == null
                ? Util.readStream(System.in)
                : plain.getBytes();
    }

    private OutputStream getOutputStream() throws FileNotFoundException {
        return encfile == null
                ? System.out
                : new FileOutputStream(encfile);
    }

    @Override
    public Integer call() throws Exception {
        var decoder = Base64.getDecoder();
        var publicKey = decoder.decode(pubkey.replace("\n", ""));
        var plain = readPlain();
        var encrypted = Cpabe2.encrypt(publicKey, policy, plain);

        var encoder = Base64.getEncoder();
        Util.writeStream(getOutputStream(), encoder.encode(encrypted));

        return ExitCode.OK;
    }
}
