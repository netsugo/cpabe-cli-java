package com.github.netsugo.cpabe.cli;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.Callable;

import com.github.netsugo.cpabe.Cpabe2;
import picocli.CommandLine.*;

@Command(name = "keygen", mixinStandardHelpOptions = true, description = {Description.Command.keygen})
public class GenerateKey implements Callable<Integer> {
    @Option(names = {"-p", "--public"}, required = true, description = {Description.publicKeyPath})
    private String pubfile;

    @Option(names = {"-m", "--master"}, required = true, description = {Description.masterKeyPath})
    private String mskfile;

    @Option(names = {"-a", "--attr"}, required = true, description = {Description.attr, Description.attrExample})
    private String attribute;

    @Option(names = {"-o", "--out"})
    private String secfile;

    private OutputStream getOutputStream() throws FileNotFoundException {
        return secfile == null
                ? System.out
                : new FileOutputStream(secfile);
    }

    @Override
    public Integer call() throws IOException, NoSuchAlgorithmException {
        var publicKey = Util.readFile(pubfile);
        var masterKey = Util.readFile(mskfile);
        var privateKey = Cpabe2.keygen(publicKey, masterKey, attribute);
        Util.writeStream(getOutputStream(), privateKey);

        return ExitCode.OK;
    }
}