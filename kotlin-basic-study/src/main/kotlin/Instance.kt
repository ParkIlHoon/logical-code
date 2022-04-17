fun main() {
    var prodA = Product("콜라", 1000)
    var prodB = Product("콜라", 1000)
    var prodC = prodA
    var prodD = Product("사이다", 1000)

    println(prodA == prodB)
    println(prodA === prodB)

    println(prodA == prodC)
    println(prodA === prodC)

    println(prodA == prodD)
    println(prodA === prodD)
}

/**
 * 객체의 동일성 : ===
 */
class Product(val name: String, val price: Int) {
    override fun equals(other: Any?): Boolean {
        if (other is Product) {
            return other.name == name && other.price == price
        }
        return false
    }
}