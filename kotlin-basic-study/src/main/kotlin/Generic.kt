fun main() {
    UsingGeneric(A()).doShouting()
    UsingGeneric(B()).doShouting()
    UsingGeneric(C()).doShouting()

    doShouting(B())
}

/**
 * generic
 */
open class A {
    open fun shout() {
        println("A 가 소리칩니다")
    }
}
class B: A() {
    override fun shout() {
        println("B 가 소리칩니다")
    }
}
class C: A() {
    override fun shout() {
        println("C 가 소리칩니다")
    }
}
class UsingGeneric<T: A> (val t: T) {
    fun doShouting() {
        t.shout()
    }
}
fun <T: A> doShouting(t: T) {
    t.shout()
}