package com.github.netsugo.cpabe.cli;

import com.github.netsugo.cpabe.Cpabe2;
import picocli.CommandLine.Command;
import picocli.CommandLine.ExitCode;
import picocli.CommandLine.Option;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.Callable;

@Command(name = "setup", mixinStandardHelpOptions = true, description = {Description.Command.setup})
public class Setup implements Callable<Integer> {
    @Option(names = {"-p", "--public"}, required = true, description = {"output of " + Description.publicKeyPath})
    private String pubfile;

    @Option(names = {"-m", "--master"}, required = true, description = {"output of " + Description.masterKeyPath})
    private String mskfile;

    @Override
    public Integer call() throws IOException {
        var params = Cpabe2.setup();
        Util.writeStream(new FileOutputStream(mskfile), params.master);
        Util.writeStream(new FileOutputStream(pubfile), params.pubkey);
        return ExitCode.OK;
    }
}