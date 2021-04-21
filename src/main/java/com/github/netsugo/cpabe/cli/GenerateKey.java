package com.github.netsugo.cpabe.cli;

import java.io.*;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.concurrent.Callable;

import com.github.netsugo.cpabe.Cpabe2;
import picocli.CommandLine.*;

@Command(name = "keygen", mixinStandardHelpOptions = true, description = {Description.Command.keygen})
public class GenerateKey extends Base64EncDec implements Callable<Integer> {
    private static final Base64.Decoder decoder = Base64.getDecoder();
    private static final Base64.Encoder encoder = Base64.getEncoder();

    @Option(names = {"-i", "--input"}, description = {Description.masterKeyPath})
    private String mskfile;

    @Option(names = {"-a", "--attr"}, required = true, description = {Description.attr, Description.attrExample})
    private String attribute;

    @Option(names = {"-o", "--out"})
    private String secfile;

    private InputStream getInputStream() throws FileNotFoundException {
        return mskfile == null
                ? System.in
                : new FileInputStream(mskfile);
    }

    private OutputStream getOutputStream() throws FileNotFoundException {
        return secfile == null
                ? System.out
                : new FileOutputStream(secfile);
    }

    @Override
    public Integer call() throws IOException, NoSuchAlgorithmException {
        var keyPairBinary = Util.readStream(getInputStream());
        var keyPair = new String(keyPairBinary).split("\n");

        var masterKey = decode(keyPair[0]);
        var publicKey = decode(keyPair[1]);
        var privateKey = Cpabe2.keygen(publicKey, masterKey, attribute);

        Util.writeStream(getOutputStream(), encode(privateKey));

        return ExitCode.OK;
    }
}