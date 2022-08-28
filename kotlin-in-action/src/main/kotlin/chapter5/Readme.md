# 코틀린을 활용한 함수형 프로그래밍
함수형 프로그래밍은 프로그램 코드를 불변 값을 반환하는 함수의 함성으로 구성할 수 있다는 아이디어를 바탕으로 한다.<br>
함수형 언어는 함수를 일급 시민(first class) 값으로 취급한다. 이 말은 함수를 다른 일반적인 타입의 값과 똑같이 취급한다는 뜻이다.

## 고차 함수
어떤 정수 배열의 원소의 합계를 계산하는 함수를 정의하고 싶다고 하자.
```kotlin
fun sum(numbers: IntArray): Int {
	var result = numbers.firstOrNull() ?: throw IllegalArgumentException("Empty Array")

	for (i in 1..numbers.lastIndex) {
		result += numbers[i]
	}

	return result
}
```
이 함수를 더 일반화해서 곱셈이나 최대/최소값 처럼 다양한 집계 함수를 사용하려면 어떻게 해야할까?<br>
함수 자체의 루프 로직은 그대로 두고 중간 값들을 함수의 파라미터로 호출해 적당한 연산을 제공하면 된다.
```kotlin
fun aggregate(numbers: IntArray, op: (Int, Int) -> Int): Int {
	var result = numbers.firstOrNull() ?: throw IllegalArgumentException("Empty Array")

	for (i in 1..numbers.lastIndex) {
		result = op(result, numbers[i])
	}

	return result
}

fun sum(numbers: IntArray) {
	aggregate(numbers, { result, op -> result + op })
}

fun max(numbers: IntArray) {
	aggregate(numbers, { result, op -> if (op > result) op else result })
}
```

## 함수 타입
함수 타입은 함수처럼 쓰일 수 있는 값들을 표시하는 타입이다. 문법적으로 이런 타입은 함수 시그니처와 비슷하며, 다음과 같이 두 가지 부분으로 구성된다.

1. 괄호로 둘러싸인 파라미터 타입 목록은 함수값에 전달될 데이터의 종류와 수를 정의한다.
2. 반환 타입은 함수 타입의 함숫값을 호출하면 돌려받게 되는 값의 타입을 정의한다.

반환값이 없는 함수라도 함수 타입에서는 반환 타입을 반드시 명시해야한다. 이러한 경우 `Unit`을 반환 타입으로 사용한다.<br>
예를 들어 `(Int, Int) -> Boolean` 이라는 타입은 정수 한 쌍을 인자로 받아 `Boolean` 값을 결과로 계산하는 함수를 뜻하며,
함수 타입 표기에서는 인자 타입 목록과 반환 타입 사이를 `:`이 아닌 `->`로 구분한다.

함수 타입의 값을 함수의 파라미터에만 사용할 수 있는 것도 아니다. 실제로는 이런 함수 타입을 다른 타입이 쓰일 수 있는 모든 장소에서 사용할 수 있다.
```kotlin
fun main() {
	val lessThan: (Int, Int) -> Boolean = { a, b -> a < b }
	println(lessThan(1, 2)) // true
}
```

변수 타입을 생략하면 컴파일러가 람다 파라미터의 타입을 추론할 수 없다.
```kotlin
var lessThan = { a, b -> a < b }	// error: cannot infer a type for this parameter. Please specify it explicitly
```
이 경우 파라미터의 타입을 명시하면 된다.
```kotlin
var lessThan = { a: Int, b: Int -> a < b }	// Ok
```

다른 타입과 마찬가지로 함수 타입도 널이 될 수 있는 타입으로 지정할 수 있으며 함수 타입 전체를 괄호로 둘러싼 다음에 물음표를 붙인다.
```kotlin
fun measureTime(action: (() -> Unit)?): Long {
	val start = System.nanoTime()

	action?.invoke()

	return System.nanoTime() - start
}

fun main() {
	println(measureTime(null))
}
```

함수 타입을 다른 함수 타입 안에 내포시켜서 고차 함수의 타입을 정의할 수 있다.
```kotlin
fun main() {
	val shifter: (Int) -> (Int) -> Int = { n -> { i -> i + n } }

	val inc = shifter(1)
	val dec = shifter(-1)

	println(inc(10))	// 11
	println(dec(10))	// 9
}
```
`->`는 오른쪽 결함이기 때문에 `(Int) -> (Int) -> Int`는 실제로 `(Int) -> ((Int) -> Int)`라고 해석된다.


## 람다와 익명 함수
### 람다
람다는 함수 정의와 달리 반환 타입을 지정할 필요가 없으며, 람다의 본문으로부터 반환 타입이 자동으로 추론된다. 그리고 람다 본문에서 맨 마지막에 있는 식이 람다의 결과값이 된다.

람다가 함수의 마지막 파라미터인 경우, 함수를 호출할 때 인자를 둘러싸는 괄호 밖에 이 람다를 위치시킬 수 있다. 코틀린에서는 이런 스타일의 코드를 권장한다.
```kotlin
fun sum (numbers: IntArrat) = 
	aggregate(numbers) { result, op -> result + op }

fun max (numbers: IntArray) =
	aggregate(numbers) { result, op -> if (op > result) op else result }
```

코틀린은 인자가 하나밖에 없는 람다를 특별히 단순화해 사용할 수 있는 문법을 제공한다.<br>
람다 인자가 하나인 경우에는 파라미터 목록과 화살표 기호를 생략하고, 유일한 파라미터는 미리 정해진 `it`이라는 이름을 사용해 가리킬 수 있다.
```kotlin
fun check (s: String, condition: (Char) -> Boolean): Boolean {
	for (c in s) {
		if (!condition(c)) return false
	}
	return true
}

fun main() {
	println(check("Hello") { c -> c.isLetter() })	// true
	println(check("Hello") { it.isLowerCase() })	// false
}
```

코틀린 1.1부터는 람다의 파라미터 목로에서 사용하지 않는 람다 파라미터를 밑줄 기호(`_`)로 지정할 수 있다.
```kotlin
fun check (s: String, condition: (Int, Char) -> Boolean): Boolean {
	for (i in s.indices) {
		if (!condition(i, s[i])) return false
	}
	return true
}

fun main() {
	println(check("Hello") { _, c -> c.isLetter() })	// true
	println(check("Hello") { i, c -> i == 0 || c.isLowerCase() })	// true
}
```

### 익명 함수
```kotlin
fun sum (numbers: IntArray) = aggregate(numbers, fun(result, op) = result + op)
```
익명 함수의 문법은 일반 함수의 문법과 거의 똑같지만, 아래와 같은 차이점이 있다.
* 익명 함수에는 이름을 지정하지 않는다. 따라서 `fun` 키워드 다음에 바로 파라미터 목록이 온다.
* 람다와 마찬가지로 문맥에서 파라미터 타입을 추론할 수 있으면 파라미터 타입을 지정하지 않아도 된다.
* 함수 정의와 달리, 익명 함수는 식이기 때문에 인자로 함수에 넘기거나 변수에 대입하는 등 일반 값처럼 쓸 수 있다.

람다와 달리 익명 함수에서는 반환 타입을 적을 수 있으며, 익명 함수를 인자 목록의 밖으로 내보낼 수는 없다.


## 호출 가능 참조
람다나 익명 함수와 달리 이미 함수가 정의되어 있고 이 함수 정의를 함숫값처럼 고차 함수에 넘기고 싶다면 호출 가능 참조를 사용하면 된다.
```kotlin
fun check(s: String, condition: (Char) -> Boolean): Boolean {
	for (c in s) {
		if (!condition(c)) return false
	}
}

fun isCapitalLetter(c: Char) = c.isUpperCase() && c.isLetter()

fun main() {
	println(check("Hello", ::isCapitalLetter))
}
```
가장 간단한 형태의 호출 가능 참조는 최상위나 지역 함수를 가리키는 참조다. 이런 함수를 가리키는 참조를 만드려면 함수 이름 앞에 `::`을 붙이면 된다.<br>
호출 가능 참조를 만들 때는 함수 이름을 간단한 형태로만 써야한다. 따라서 다른 패키지에 들어있는 함수의 호출 가능 참조를 만드려면 먼저 함수를 임포트해야한다.<br>
`::`을 클래스 이름 앞에 붙이면 클래스의 생성자에 대한 호출 가능 참조를 얻는다.

코틀린 1.1부터는 **바인딩된 호출 가능 참조**라는 `::` 사용법이 도입됐다. 주어진 클래스 인스턴스의 문맥 안에서 멤버 함수를 호출하고 싶을 때는 바인딩된 호출 가능 참조를 사용한다.
```kotlin
class Person (val firstName: String, val familyName :String) {
	fun hasNameOf(name: String) = name.equals(firstName, ignoreCase = true)
}

fun main () {
	val isJohn = Person("John", "Doe")::hasNameOf

	println(isJohn("JOHN"))	// true
	println(isJohn("Jake"))	// false
}
```

호출 가능 참조 자체는 오버로딩된 함수를 구분할 수 없는데, 오버로딩된 함수 중 어떤 함수를 참조할지 명확히 하려면 컴파일러에게 타입을 지정해줘야 한다.
```kotlin
fun max(a: Int, b: Int) = if (a > b) a else b
fun max(a: Double, b: Double) = if (a > b) a else b

val f: (Int, Int) -> Int = ::max	// ok
val g = ::max	// error: overload resolution ambiguity
```

코틀린 프로퍼티에 대한 호출 가능 참조를 만들 수도 있다.<br>
이런 참조 자체는 실제로는 함숫값이 아니고 프로퍼티 정보를 담고있는 리플랙션 객체다.












