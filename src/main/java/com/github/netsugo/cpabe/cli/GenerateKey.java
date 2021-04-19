package com.github.netsugo.cpabe.cli;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.Callable;

import co.junwei.cpabe.Cpabe;

import com.github.netsugo.cpabe.Cpabe2;
import picocli.CommandLine.*;

@Command(name = "keygen", mixinStandardHelpOptions = true, description = { Description.Command.keygen })
public class GenerateKey implements Callable<Integer> {
    @Option(names = { "-P", "--public" }, required = true, description = { Description.publicKey })
    private String pubfile;

    @Option(names = { "-m", "--master" }, required = true, description = { Description.master })
    private String mskfile;

    @Option(names = { "-a", "--attr" }, required = true, description = { Description.attr, Description.attrExample })
    private String attribute;

    @Option(names = { "-o", "--out" })
    private String privfile;

    private OutputStream getOutputStream() throws FileNotFoundException {
        return privfile == null
                ? System.out
                : new FileOutputStream(privfile);
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