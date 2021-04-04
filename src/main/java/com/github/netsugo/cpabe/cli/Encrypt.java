package com.github.netsugo.cpabe.cli;

import java.io.*;
import java.util.concurrent.Callable;

import co.junwei.cpabe.Cpabe;

import com.github.netsugo.cpabe.Cpabe2;
import picocli.CommandLine.*;

@Command(name = "encrypt", mixinStandardHelpOptions = true, description = { Description.Command.encrypt })
public class Encrypt implements Callable<Integer> {
    @Option(names = { "-i", "--in" }, description = { Description.decrypted })
    private String plain;

    @Option(names = { "-P", "--public" }, description = { Description.publicKey }, required = true)
    private String pubfile;

    @Option(names = { "-p", "--policy" }, description = { Description.policy, Description.policyExample }, required = true)
    private String policy;

    @Option(names = { "-o", "--out" })
    private String encfile;

    private InputStream getInputStream() throws FileNotFoundException {
        return plain == null
                ? System.in
                : new FileInputStream(plain);
    }

    private OutputStream getOutputStream() throws FileNotFoundException {
        return encfile == null
                ? System.out
                : new FileOutputStream(encfile);
    }

    @Override
    public Integer call() throws Exception {
        var publicKey = Util.readFile(pubfile);
        var plain = Util.readStream(getInputStream());
        var encrypted = Cpabe2.encrypt(publicKey, policy, plain);
        Util.writeStream(getOutputStream(), encrypted);

        return ExitCode.OK;
    }
}
