fun main() {
    deliveryItem("짬뽕")
    deliveryItem("짜장면", 3)
    deliveryItem("탕수육", 2, "학교")
    deliveryItem("선물", destination = "친구집")
}

/**
 * default args
 */
fun deliveryItem(name: String, count: Int = 1, destination: String = "집") {
    println("${name}, ${count} 개를 ${destination} 에 배달하였습니다.")
}

/**
 * varargs
 */
fun sum(vararg number: Int):Int {
    var sum = 0;
    for (n in number) {
        sum += n
    }
    return sum
}