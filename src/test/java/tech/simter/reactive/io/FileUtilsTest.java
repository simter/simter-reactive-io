package tech.simter.reactive.io;

import org.junit.jupiter.api.Test;
import reactor.test.StepVerifier;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

/**
 * Test [DynamicBean].
 *
 * @author RJ
 */
class FileUtilsTest {
  @Test
  void calculateMemory() {
    final Runtime runtime = Runtime.getRuntime();
    System.gc();
    System.out.println(String.format(
      "Memory in use before reading: %dMB\n",
      (runtime.totalMemory() - runtime.freeMemory()) / (1024 * 1024)
    ));

    Path path = Paths.get("src/main/java/tech/simter/reactive/io/Utils.java");
    System.out.println(path.toString());
    FileUtils.readLineToString(path)
      .doOnNext(System.out::println)
      .subscribe();

    System.out.println(String.format(
      "Memory in use after reading: %dMB\n",
      (runtime.totalMemory() - runtime.freeMemory()) / (1024 * 1024)
    ));
    System.gc();
    System.out.println(String.format(
      "Memory in use after gc: %dMB\n",
      (runtime.totalMemory() - runtime.freeMemory()) / (1024 * 1024)
    ));
  }

  @Test
  void fileExists() throws IOException {
    Path path = Paths.get("src/main/java/tech/simter/reactive/io/Utils.java");
    int c = Files.readAllLines(path).size();
    StepVerifier.create(FileUtils.readLineToString(path))
      .expectNextCount(c)
      .verifyComplete();
  }

  @Test
  void fileNotExists() {
    Path path = Paths.get(UUID.randomUUID().toString());
    StepVerifier.create(FileUtils.readLineToString(path))
      .expectError(NoSuchFileException.class)
      .verify();
  }
}