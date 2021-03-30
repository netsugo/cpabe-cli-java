package com.github.netsugo.cpabe.cli;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.Callable;

import co.junwei.cpabe.Cpabe;

import picocli.CommandLine.*;

@Command(name = "keygen", mixinStandardHelpOptions = true, description = { Description.Command.keygen })
public class GenerateKey implements Callable<Integer> {
    @Option(names = { "-P", "--public" }, required = true, description = { Description.publicKey })
    private String pubfile;

    @Option(names = { "-m", "--master" }, required = true, description = { Description.master })
    private String mskfile;

    @Option(names = { "-a", "--attr" }, required = true, description = { Description.attr })
    private String attribute;

    @Option(names = { "-o", "--out" }, required = true)
    private String privfile;

    @Override
    public Integer call() throws IOException, NoSuchAlgorithmException {
        var cpabe = new Cpabe();
        cpabe.keygen(pubfile, privfile, mskfile, attribute);
        return ExitCode.OK;
    }
}