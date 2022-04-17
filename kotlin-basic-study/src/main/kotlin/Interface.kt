fun main() {
    var horse = Horse()
    horse.run()
    horse.eat()
}

/**
 * 인터페이스
 * 속성, 추상함수, 일반함수
 * 생성자 없음
 * 구현부가 있는 함수 -> open 함수
 * 구현부가 없는 함수 -> abstract 함수
 */
interface Runner {
    fun run()
}
interface Eater {
    fun eat() {
        println("음식을 먹습니다")
    }
}
class Horse : Runner, Eater {
    override fun run() {
        println("다그닥 다그닥")
    }

    override fun eat() {
        println("당근을 먹어요")
    }
}