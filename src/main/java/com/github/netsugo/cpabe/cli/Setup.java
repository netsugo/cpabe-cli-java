package com.github.netsugo.cpabe.cli;

import java.io.IOException;
import java.util.concurrent.Callable;

import co.junwei.cpabe.Cpabe;

import picocli.CommandLine.*;

@Command(name = "setup", mixinStandardHelpOptions = true, description = { Description.Command.setup })
public class Setup implements Callable<Integer> {
    @Option(names = { "-P", "--public" }, required = true, description = { "output of " + Description.publicKey })
    private String pubfile;

    @Option(names = { "-m", "--master" }, required = true, description = { "output of" + Description.master })
    private String mskfile;

    @Override
    public Integer call() throws IOException, ClassNotFoundException {
        var cpabe = new Cpabe();
        cpabe.setup(pubfile, mskfile);
        return ExitCode.OK;
    }
}