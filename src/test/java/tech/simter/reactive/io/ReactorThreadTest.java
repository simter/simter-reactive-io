package tech.simter.reactive.io;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

/**
 * @author RJ
 */
class ReactorThreadTest {
  private void printThreadName(String t) {
    System.out.println(Thread.currentThread().getName() + " : " + t);
  }

  @Test
  void main() {
    printThreadName("in main start");

    Flux.just("a", "b")
      .map(it -> {
        printThreadName("in map " + it);
        return it;
      })
      .doOnNext(it -> printThreadName("in doOnNext " + it))
      .subscribe(it -> printThreadName("in subscribe " + it));

    printThreadName("in main end");
  }

  @Test
  void publishOn() {
    printThreadName("in main start");

    Flux.just("c", "d")
      .map(it -> {
        printThreadName("in map " + it);
        return it;
      })
      .publishOn(Schedulers.elastic())
      .doOnNext(it -> printThreadName("in doOnNext " + it))
      .subscribe(it -> printThreadName("in subscribe " + it));

    printThreadName("in main end");
  }

  @Test
  void subscribeOn() {
    printThreadName("in main start");

    Flux.just(1, 2)
      .map(it -> {
        printThreadName("in map " + it);
        return it;
      })
      .subscribeOn(Schedulers.elastic())
      .doOnNext(it -> printThreadName("in doOnNext " + it))
      .subscribe(it -> printThreadName("in subscribe " + it));

    printThreadName("in main end");
  }
}