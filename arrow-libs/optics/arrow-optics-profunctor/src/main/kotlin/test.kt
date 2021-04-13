import arrow.core.Either
import arrow.optics.Optic
import arrow.optics.combinators.backwards
import arrow.optics.combinators.filter
import arrow.optics.combinators.get
import arrow.optics.combinators.id
import arrow.optics.combinators.only
import arrow.optics.compose
import arrow.optics.get
import arrow.optics.ixCollectOf
import arrow.optics.ixCompose
import arrow.optics.ixGet
import arrow.optics.ixView
import arrow.optics.modify
import arrow.optics.predef.notNull
import arrow.optics.predef.pairFirst
import arrow.optics.predef.traversedList
import arrow.optics.predef.traversedMap
import arrow.optics.review
import arrow.optics.un

fun main() {

  val xs = listOf(2, null, 3)

  val o = Optic.traversedList<Int?, Int?>().notNull().also {

  }
  xs.modify(o) { it * 3 }
    .also(::println) // [6, null, 9]

  Optic.pairFirst<Int?, Int?, String>().notNull().filter { it % 2 == 0 }.let {
    (2 to "").modify(it) { it * 3 }
      .also(::println) // (6, "")
  }

  val g = Optic.ixGet { i: Int -> 1 to i }.get { it * 2 }.let {
    it.ixCompose(Optic.ixGet { it -> "Hello" to it })
  }

  100.ixView(g)
    .also(::println)

  val f = Optic.traversedList<String, String>()
    .backwards()
    .also {

    }

  listOf("Hello", "World", "!")
    .ixCollectOf(f)
    .also(::println)

  val h = Optic.traversedMap<String, Int, Int>()
    .compose(Optic.id())
    .backwards()

  mapOf("Hello" to 3, "World" to 5)
    .ixCollectOf(h)
    .also(::println)

  "20".review(Optic.get<String, Either<String, Int>> { Either.Left(it) }.un())
    .also(::println)
}
