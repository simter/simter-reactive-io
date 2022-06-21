package tech.simter.reactive.io

import org.junit.jupiter.api.Test
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import reactor.core.publisher.Flux
import reactor.core.scheduler.Schedulers

/**
 * @author RJ
 */
internal class ReactorThreadTest {
  private val logger: Logger = LoggerFactory.getLogger(ReactorThreadTest::class.java)

  private fun printThreadName(t: String) {
    //println(Thread.currentThread().name + " : " + t)
    logger.info(Thread.currentThread().name + " : " + t)
  }

  @Test
  fun main() {
    printThreadName("in main start")
    Flux.just("a", "b")
      .map {
        printThreadName("in map $it")
        it
      }
      .doOnNext { printThreadName("in doOnNext $it") }
      .subscribe { printThreadName("in subscribe $it") }
    printThreadName("in main end")
  }

  @Test
  fun publishOn() {
    printThreadName("in main start")
    Flux.just("c", "d")
      .map {
        printThreadName("in map $it")
        it
      }
      .publishOn(Schedulers.boundedElastic())
      .doOnNext { printThreadName("in doOnNext $it") }
      .subscribe { printThreadName("in subscribe $it") }
    printThreadName("in main end")
  }

  @Test
  fun subscribeOn() {
    printThreadName("in main start")
    Flux.just(1, 2)
      .map {
        printThreadName("in map $it")
        it
      }
      .subscribeOn(Schedulers.boundedElastic())
      .doOnNext { printThreadName("in doOnNext $it") }
      .subscribe { printThreadName("in subscribe $it") }
    printThreadName("in main end")
  }
}