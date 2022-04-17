fun main() {
    println("---------------------")
    println(Counter.count)
    Counter.countUp()
    Counter.countUp()
    println(Counter.count)
    Counter.clear()
    println(Counter.count)
    println("---------------------")

    println("---------------------")
    var jjajang = FoodPoll("짜장")
    var jjampong = FoodPoll("짬뽕")

    jjajang.vote()
    jjajang.vote()

    jjampong.vote()
    jjampong.vote()
    jjampong.vote()

    println(jjajang.count)
    println(jjampong.count)
    println(FoodPoll.total)
    println("---------------------")
}

/**
 * Object
 * 싱글턴 패턴을 언어 차원에서 지원
 */
object Counter {
    var count = 0

    fun countUp() {
        count++
    }

    fun clear() {
        count = 0
    }
}

/**
 * companion object
 */
class FoodPoll (val name: String) {
    companion object {
        var total = 0
    }

    var count = 0

    fun vote() {
        total++
        count++
    }
}