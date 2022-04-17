fun main() {

}

/**
 * List<out T> : 생성시에 넣은 객체를 대체, 추가, 삭제할 수 없음
 * MutableList<T> : 생성시에 넣은 객체를 대체, 추가, 삭제할 수 있음
 */
var list = listOf("사과", "딸기", "배")
var mutableList = mutableListOf(6, 3, 1)