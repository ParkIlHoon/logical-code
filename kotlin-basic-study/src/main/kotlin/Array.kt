val arr = arrayOf(1, 2, 3)
val nullArr = arrayOfNulls<Int>(5)

fun main() {
    println(arr.asList().toString())
    println(nullArr.asList().toString())
    nullArr[3] = 456
    println(nullArr.asList().toString())
}