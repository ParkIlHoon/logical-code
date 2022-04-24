fun main() {
    var foodCourt = FoodCourt();
    foodCourt.searchPrice(FoodCourt.FOOD_PIZZA)
    foodCourt.searchPrice(FoodCourt.FOOD_STEAK)
    foodCourt.searchPrice(FoodCourt.FOOD_CREAM_PASTA)

    println("-----------------")
    checkInit()

    println("-----------------")
    print(b)
}

/**
 * 상수
 * 컴파일 시점에 결정되어 절대 바꿀 수 없는 값. 기본 자료형만 가능.
 * 클래스 속성이나 지역 변수로는 선언 불가.
 */
class FoodCourt {
    companion object {
        const val FOOD_CREAM_PASTA = "크림 파스타"
        const val FOOD_STEAK = "스테이크"
        const val FOOD_PIZZA = "피자"
    }

    fun searchPrice(foodName: String) {
        val price = when(foodName) {
            FOOD_CREAM_PASTA -> 10000
            FOOD_STEAK -> 15000
            FOOD_PIZZA -> 8000
            else -> 0
        }

        println("${foodName} 의 가격은 ${price} 원 입니다.")
    }
}

/**
 * lateinit var 변수
 * 초기값 할당 전까지 변수를 사용할 수 없음
 * 기본 자료형에는 사용할 수 없음
 */

lateinit var late: FoodCourt

fun checkInit() {
    println(::late.isInitialized)
}

/**
 * lazy delegate 속성
 * 변수를 사용하는 시점까지 초기화를 늦춰주는 속성
 * 코드에서는 선언시 즉시 객체를 생성 및 할당하여 변수를 초기화하는 형태를 갖고 있지만 런타임에서는 사용 시점에 초기화됨
 */
val b: Int by lazy {
    print("초기화합니다")
    9
}