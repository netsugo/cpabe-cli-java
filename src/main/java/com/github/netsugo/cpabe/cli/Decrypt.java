package com.github.netsugo.cpabe.cli;

import java.io.IOException;
import java.util.concurrent.Callable;

import co.junwei.cpabe.Cpabe;

import picocli.CommandLine.*;

@Command(name = "decrypt", mixinStandardHelpOptions = true, description = { Description.Command.decrypt })
public class Decrypt implements Callable<Integer> {
    @Option(names = { "-s", "--source" }, required = true, description = { Description.encrypted })
    private String encrypted;

    @Option(names = { "-P", "--public" }, required = true, description = { Description.publicKey })
    private String pubfile;

    @Option(names = { "-p", "--private" }, required = true, description = { Description.privateKey })
    private String privfile;

    @Option(names = { "-o", "--out" }, required = true)
    private String decfile;

    @Override
    public Integer call() throws IOException, Exception {
        var cpabe = new Cpabe();
        cpabe.dec(pubfile, privfile, encrypted, decfile);
        return ExitCode.OK;
    }
}