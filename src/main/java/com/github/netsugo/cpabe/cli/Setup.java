package com.github.netsugo.cpabe.cli;

import com.github.netsugo.cpabe.Cpabe2;
import picocli.CommandLine.Command;
import picocli.CommandLine.ExitCode;
import picocli.CommandLine.Option;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Base64;
import java.util.concurrent.Callable;

@Command(name = "setup", mixinStandardHelpOptions = true, description = {Description.Command.setup})
public class Setup extends Base64EncDec implements Callable<Integer> {
    @Option(names = {"-o", "--out"})
    private String output;

    private OutputStream getOutputStream() throws FileNotFoundException {
        return output == null
                ? System.out
                : new FileOutputStream(output);
    }

    @Override
    public Integer call() throws IOException {
        var params = Cpabe2.setup();
        var master = encode(params.master);
        var pubkey = encode(params.pubkey);
        var pairKey = new String(master) + '\n' + new String(pubkey) + '\n';
        Util.writeStream(getOutputStream(), pairKey.getBytes());
        return ExitCode.OK;
    }
}