package com.github.netsugo.cpabe.cli;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class Util {
    public static byte[] readFile(String path) throws IOException {
        try (var inputStream = new FileInputStream(path)) {
            return inputStream.readAllBytes();
        }
    }

    public static byte[] readStream(InputStream stream) throws IOException {
        try (stream) {
            return stream.readAllBytes();
        }
    }

    public static void writeStream(OutputStream stream, byte[] data) throws IOException {
        try (stream) {
            stream.write(data);
        }
    }
}
