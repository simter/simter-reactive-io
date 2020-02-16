package tech.simter.reactive.io.fileutils

import org.junit.jupiter.api.Test
import reactor.test.StepVerifier
import tech.simter.reactive.io.FileUtils
import tech.simter.reactive.io.FileUtils.readLineToString
import java.io.IOException
import java.nio.file.Files
import java.nio.file.NoSuchFileException
import java.nio.file.Paths
import java.util.*

/**
 * Test [FileUtils.readLineToString].
 *
 * @author RJ
 */
internal class ReadLineToStringMethodTest {
  @Test
  fun calculateMemory() {
    val runtime = Runtime.getRuntime()
    System.gc()
    println(String.format(
      "Memory in use before reading: %dMB\n",
      (runtime.totalMemory() - runtime.freeMemory()) / (1024 * 1024)
    ))
    val path = Paths.get("src/main/kotlin/tech/simter/reactive/io/FileUtils.kt")
    println(path.toString())
    readLineToString(path)
      .doOnNext { x: String? -> println(x) }
      .subscribe()
    println(String.format(
      "Memory in use after reading: %dMB\n",
      (runtime.totalMemory() - runtime.freeMemory()) / (1024 * 1024)
    ))
    System.gc()
    println(String.format(
      "Memory in use after gc: %dMB\n",
      (runtime.totalMemory() - runtime.freeMemory()) / (1024 * 1024)
    ))
  }

  @Test
  @Throws(IOException::class)
  fun fileExists() {
    val path = Paths.get("src/main/kotlin/tech/simter/reactive/io/FileUtils.kt")
    val c = Files.readAllLines(path).size
    StepVerifier.create(readLineToString(path))
      .expectNextCount(c.toLong())
      .verifyComplete()
  }

  @Test
  fun fileNotExists() {
    val path = Paths.get(UUID.randomUUID().toString())
    StepVerifier.create(readLineToString(path))
      .expectError(NoSuchFileException::class.java)
      .verify()
  }
}