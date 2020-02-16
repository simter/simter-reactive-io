package tech.simter.reactive.io

import reactor.core.publisher.Flux
import java.nio.charset.Charset
import java.nio.charset.StandardCharsets
import java.nio.file.Files
import java.nio.file.Path

/**
 * Some reactive ways to deal with file resource.
 *
 * @author RJ
 */
object FileUtils {
  /**
   * Read line by line from a file as [Flux<String>].
   *
   * This method use [Files.lines] indeed. It will lazily read the lines from the specific [path] file,
   * without ever having to hold the whole content of the file in memory.
   * Bytes from the file are decoded into characters using the specified [charset].
   * After done reading, auto close the file stream.
   *
   * @return a new [Flux] with [String] element type built around a auto close resource
   */
  @JvmOverloads
  fun readLineToString(path: Path, charset: Charset = StandardCharsets.UTF_8): Flux<String> {
    return Flux.using({ Files.lines(path, charset) }, { Flux.fromStream(it) }, { it.close() })
  }
}