fun main() {
    var tree = Tree()
    tree.germination()
    tree.photosynthesis()
}

/**
 * 추상클래스
 */
abstract class Plant {
    abstract fun germination()
    fun photosynthesis() {
        println("광합성을 합니다")
    }
}
class Tree: Plant() {
    override fun germination() {
        println("나무가 발아합니다.")
    }
}