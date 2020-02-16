package tech.simter.reactive.io;

import reactor.core.publisher.Flux;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.BaseStream;

/**
 * @author RJ
 */
public class FileUtils {
  /**
   * Read line by line from a file as a {@link Flux Flux&lt;String&gt;}.
   *
   * <p> This method use {@link Files#lines(Path, Charset)} indeed. It will lazily read the lines from the file,
   * without ever having to hold the whole content of the file in memory.
   *
   * <p> Bytes from the file are decoded into characters using the specified charset.
   *
   * <p> After done reading, auto close the file stream.
   *
   * @param path    the path to the file
   * @param charset the charset to use for decoding
   * @return a new {@link Flux Flux&lt;String&gt;} built around a auto close resource
   */
  public static Flux<String> readLineToString(Path path, Charset charset) {
    return Flux.using(() -> Files.lines(path, charset), Flux::fromStream, BaseStream::close);
  }

  /**
   * Read line by line from a file as a {@link Flux Flux&lt;String&gt;}.
   *
   * <p> This method works as if invoking it were equivalent to evaluating the expression:
   * <pre>{@code
   * readLineToString(path, StandardCharsets.UTF_8)
   * }</pre>
   *
   * @param path the path to the file
   * @return a new {@link Flux Flux&lt;String&gt;} built around a auto close resource
   */
  public static Flux<String> readLineToString(Path path) {
    return readLineToString(path, StandardCharsets.UTF_8);
  }
}
