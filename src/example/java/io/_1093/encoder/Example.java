package io._1093.encoder;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Example {
    public static void main(String[] args) throws IOException {
        final List<String> words;

        try (Stream<String> stream = Files.lines(Paths.get("/usr/share/dict/words"), StandardCharsets.UTF_8)) {
            words = stream
                    .map(String::trim)
                    .filter(line -> !line.isEmpty())
                    .collect(Collectors.toList());
        }

        final Dictionary<String> dictionary = SimpleDictionary.of(words);
        final Bit64Coder<WordCombination<String>> coder = new WordCoder<>(dictionary);

        // encode
        System.out.println(coder.encode(123456L));// neophyte
        System.out.println(coder.encode(-1L));// oolly-Bridger-proreservationist-reflagellate
        System.out.println(coder.encode(Long.MAX_VALUE));// oolly-Bridger-proreservationist-quagmire
        System.out.println(coder.encode(Long.MIN_VALUE));// A-A-A-agoniada

        // decode
        System.out.println(coder.decode(new StringWordCombination("neophyte")));// 123456
        System.out.println(coder.decode(new StringWordCombination("oolly-Bridger-proreservationist-reflagellate")));// -1
        System.out.println(coder.decode(new StringWordCombination("oolly-Bridger-proreservationist-quagmire")));// 9223372036854775807
        System.out.println(coder.decode(new StringWordCombination("A-A-A-agoniada")));// -9223372036854775808
    }
}
