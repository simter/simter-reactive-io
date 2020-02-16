package tech.simter.reactive.io

import org.junit.jupiter.api.Test
import reactor.core.publisher.Flux
import reactor.core.scheduler.Schedulers

/**
 * @author RJ
 */
internal class ReactorThreadTest {
  private fun printThreadName(t: String) {
    println(Thread.currentThread().name + " : " + t)
  }

  @Test
  fun main() {
    printThreadName("in main start")
    Flux.just("a", "b")
      .map { it: String ->
        printThreadName("in map $it")
        it
      }
      .doOnNext { it: String -> printThreadName("in doOnNext $it") }
      .subscribe { it: String -> printThreadName("in subscribe $it") }
    printThreadName("in main end")
  }

  @Test
  fun publishOn() {
    printThreadName("in main start")
    Flux.just("c", "d")
      .map { it: String ->
        printThreadName("in map $it")
        it
      }
      .publishOn(Schedulers.elastic())
      .doOnNext { it: String -> printThreadName("in doOnNext $it") }
      .subscribe { it: String -> printThreadName("in subscribe $it") }
    printThreadName("in main end")
  }

  @Test
  fun subscribeOn() {
    printThreadName("in main start")
    Flux.just(1, 2)
      .map { it: Int ->
        printThreadName("in map $it")
        it
      }
      .subscribeOn(Schedulers.elastic())
      .doOnNext { it: Int -> printThreadName("in doOnNext $it") }
      .subscribe { it: Int -> printThreadName("in subscribe $it") }
    printThreadName("in main end")
  }
}