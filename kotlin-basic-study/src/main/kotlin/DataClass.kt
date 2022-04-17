fun main() {
    println("---------------------")
    val ge = General("파이리", 1)
    println(ge == General("파이리", 1))
    println(ge.hashCode())
    println(ge)

    val da = Data("꼬부기", 1)
    println(da == Data("파이리", 1))
    println(da.hashCode())
    println(da)
    println(da.copy())
    println(da.copy(name = "이상해씨"))
    println(da.copy(id = 3))
    println("---------------------")

    println("---------------------")
    val list = listOf(
        Data("꼬부기", 1),
        Data("파이리", 2),
        Data("이상해씨", 3)
    )

    for ((a, b) in list) {
        println("${a}, ${b}")
    }
    println("---------------------")
}

/**
 * Data Class
 * 1. equals() 자동 구현
 * 2. hashcode() 자동 구현
 * 3. toString() 자동 구현
 * 4. copy() 자동 구현
 * 5. componentX() 자동 구현
 */
class General(val name: String, val id: Int)
data class Data(val name: String, val id: Int)