package com.github.netsugo.cpabe.cli;

import java.io.IOException;
import java.util.concurrent.Callable;

import co.junwei.cpabe.Cpabe;

import picocli.CommandLine.*;

@Command(name = "encrypt", mixinStandardHelpOptions = true, description = { Description.Command.encrypt })
public class Encrypt implements Callable<Integer> {
    @Option(names = { "-s", "--source" }, description = { Description.decrypted }, required = true)
    private String plain;

    @Option(names = { "-P", "--public" }, description = { Description.publicKey }, required = true)
    private String pubfile;

    @Option(names = { "-p", "--policy" }, description = { Description.policy, Description.policyExample }, required = true)
    private String policy;

    @Option(names = { "-o", "--out" }, required = true)
    private String encfile;

    @Override
    public Integer call() throws IOException, Exception {
        var cpabe = new Cpabe();
        cpabe.enc(pubfile, policy, plain, encfile);
        return ExitCode.OK;
    }
}
