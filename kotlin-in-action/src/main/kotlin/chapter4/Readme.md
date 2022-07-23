# 클래스 정의하기
기본적으로 클래스 선언은 참조 타입(referential type)을 정의한다. 코틀린 1.3부터는 인라인 클래스(inline class)라는 개념이 도입되어 참조 타입이 아닌 타입을 정의할 수 있다.

## 클래스 내부 구조
```kotlin
class Person {
    var firstName: String = ""
    var familyName: String = ""
    var age: Int = 0

    fun fullName() = "$firstName $familyName"
    
    fun showMe() {
        println("${fullName()} : $age")
    }
}
```
자바 필드와 달리, 코틀린에서는 클라이언트 코드를 바꾸지 않아도 원하는 대로 프로퍼티의 구현을 바꿀 수 있기 때문에 코틀린 프로퍼티는 캡슐화에 위배되지 않는다.
예를 들어 커스텀 게터나 세터를 추가해도 클라이언트 소스코드를 바꿀 필요가 없다.
즉, `firstName`이라는 참조는 프로퍼티가 구현되는 방법과 무관하게 항상 올바른 참조로 남을 수 있다.

기본적으로 코틀린 클래스는 공개(`public`) 가시성이다.

## 생성자
생성자는 클래스 인스턴스를 초기화해주고 인스턴스 생성 시 호출되는 특별한 함수다.
```kotlin
class Person(firstName :String, familyName :String) {
    val fullName = "$firstName $familyName"
}

fun main() {
    val person = Person("John", "Doe")
    println(person.fullName)
}
```
클래스 헤더의 파라미터 목록을 주생성자(primary constructor) 선언이라고 부른다. 
주생성자는 함수와 달리 본문이 하나가 아니다.

### 초기화 블록
초기화 블록이란 `init`이라는 키워드가 앞에 붙은 블록이다. 이 블록 안에서 클래스 초기화 시 필요한 간단하지 않은 초기화 로직을 수행할 수 있다.
```kotlin
class Person(firstName :String, familyName :String) {
    val fullName = "$firstName $familyName"

    init {
        println("Created new Person instance : $fullName")
    }
}
```
클래스 안에 `init` 블록이 여럿 들어갈 수 있다. 이런 경우 각 블록은 프로퍼티 초기화와 함께 순서대로 실행된다.
그리고 초기화 블록에는 `return`문이 들어가지 못한다.

### 멤버 프로퍼티 정의
주생성자 파라미터를 프로퍼티 초기화나 init 블록 밖에서 사용할 수는 없다.
예를 들어, 멤버 함수 내부에서는 `firstName`을 사용할 수 없다.
```kotlin
class Person(firstName :String, familyName :String) {
    val fullName = "$firstName $familyName"

    fun printFirstName() {
        println(firstName)  // Error: firstName is not available
    }
}
```
이에 대한 해법은 생성자 파라미터의 값을 저장할 멤버 프로퍼티를 정의하는 것이다.
```kotlin
class Person(val firstName :String, familyName :String) {
    val fullName = "$firstName $familyName"

    fun printFirstName() {
        println(firstName)
    }
}

fun main() {
    val person = Person("John", "Doe")
    println(person.firstName)
}
```
생성자 파라미터 앞에 `val`이나 `var` 키워드를 덧붙이면, 자동으로 해당 생성자 파라미터로 초기화되는 (생성자 파라미터와 이름이 같은) 프로퍼티를 정의한다.<br>
이 때 파라미터 이름을 프로퍼티 초기화나 `init` 블록 안에서 참조하면 생성자 파라미터를 가리키고, 다른 위치에서 참조하면 프로퍼티를 가리키게 된다.

함수와 마찬가지로 디폴트 값과 `vararg`를 생성자 파라미터에 사용할 수 있다.
```kotlin
class Person(val firstName: String, val familyName: String = "") {
    fun fullName() = "$firstName $familyName"
}

class Room(vararg val persons :Person) {
    fun showNames() {
        for (person in persons) println(person.fullName())
    }
}

fun main() {
    val room = Room(Person("John"), Person("Jame", "Smith"))
    room.showNames()
}
```

### 부생성자
여러 생성자를 사용해 클래스 인스턴스를 서로 다른 방법으로 초기화하고 싶을 때도 있다. 이런 경우 코틀린에서는 부생성자(secondary constructor)를 사용해 해결할 수 있다.
```kotlin
class Person {
    val fistName :String
    val familyName :String
    
    constructor(firstName :String, familyName :String) {
        this.firstName = firstName
        this.familyName = familyName
    }
    
    constructor(fullName :String) {
        val names = fullName.split(" ")
        if (names.size != 2) {
            throw IllegalArgumentException("Invalid name : $fullName")
        }
        this.firstName = names[0]
        this.familyName = names[1]
    }
}
```
클래스에 주생성자를 선언하지 않은 경우, 모든 부생성자는 자신의 본문을 실행하기 전에 프로퍼티 초기화와 `init` 블록을 실행한다.
이렇게 하면 어떤 부생성자를 호출하든지 공통적인 초기화 코드가 정확히 한 번만 실행되게 보장할 수 있다.

다른 방법으로는 부생성자가 생성자 위임 호출을 사용해 다른 부생성자를 호출하는 것이 있다.
생성자 파라미터 목록 뒤에 콜론(`:`)을 넣고 그 뒤에 일반 함수를 호출하는 것처럼 코드를 작성하되, 함수 이름 대신 `this`를 사용하면 생성자 위임 호출이 된다.
```kotlin
class Person {
    val fullName :String
    
    constructor(firstName :String, familyName :String) : this("$firstName $familyName")
    
    constructor(fullName :String) {
        this.fullName = fullName
    }
}
```

클래스에 주생성자가 있다면, 모든 부생성자는 주생성자에게 위임을 하거나 다른 부생성자에게 위임을 해야한다.
```kotlin
class Person(val fullName :String) {
    constructor(firstName :String, familyName :String) : this("$firstName $familyName")
}
```

부생성자의 파라미터 목록에는 `val`, `var` 키워드를 사용할 수 없다.

### 정리
코틀린 클래스에는 주 생성자, 부 생성자, 초기화 블록이 있는데, 아래 순서로 동작한다.
1. 주 생성자
2. 초기화 블록
3. 부 생성자

## 멤버 가시성
* `public`(공개)
  * 멤버를 어디서나 볼 수 있다.
  * 디폴트 가시성
* `internal`(모듈 내부)
  * 멤버를 멤버가 속한 클래스가 포함된 컴파일 모듈 내부에서만 볼 수 있다.
* `protected`(보호)
  * 멤버를 멤버가 속한 클래스와 멤버가 속한 클래스의 모든 하위 클래스 안에서 볼 수 있다.
* `private`(비공개)
  * 멤버를 멤버가 속한 클래스 내부에서만 볼 수 있다.


## 내포된 클래스
```kotlin
class Person(val id: Id, val age :Int) {
    class Id(val firstName :String, val familyName :String)
    fun showMe() = println("${id.firstName} ${id.familyName}, $age")
}

fun main() {
    val id = Person.Id("John", "Doe")
    val person = Person(id, 25)
    person.showMe()
}
```
자바와 달리 바깥쪽 클래스는 자신에게 내포된 클래스의 비공개 멤버에 접근할 수 없다.
내포된 클래스에 `inner`를 붙이면 자신을 둘러싼 외부 클래스의 현재 인스턴스에 접근할 수 있다.

일반적으로 `this`는 항상 가장 내부의 클래스 인스턴스를 가리킨다. 따라서 내부 클래스 본문에서 `this`는 내부 클래스 자신을 가리킨다.
내부 클래스 본문에서 외부 클래스 인스턴스를 가리켜야한다면 한정시킨(qualified) `this`식을 사용해야 한다.
```kotlin
class Person(val firstName: String, val familyName: String) {
    inner class Possession(val description :String) {
        fun getOwner() = this@Person
    }
} 
```


## 지역 클래스
코틀린에서도 함수 본문에서 클래스를 정의할 수 있다. 이런 지역 클래스는 자신을 둘러싼 코드 블록 안에서만 쓰일 수 있다.
```kotlin
fun main() {
    class Point(val x :Int, val y :Int) {
        fun shift(dx :Int, dy :Int) :Point = Point(x + dx, y + dy)
        override fun toString() = "($x, $y)"
    }
  
    val p = Point(10, 10)
    println(p.shift(-1, 3))
}
```
지역 클래스는 클래스 본문 안에서 자신이 접근할 수 있는 값을 포획(capture)할 수 있고, 심지어는 변경할 수도 있다.
```kotlin
fun main() {
    var x = 1
  
    class Counter {
        fun increment() {
            x++
        }
    }
  
    Counter().increment()
    println(x)  // 2
}
```
하지만 코틀린이 제공하는 **포획 변수를 변경하는 기능은 그에 따른 비용을 수반**한다.<br>
익명 객체와 이 객체를 둘러싸고 있는 코드 사이에 변수를 공유하기 위해 코틀린 컴파일러는 값을 특별한 래퍼 객체로 둘러싼다.<br>
위 예제를 컴파일한 바이트코드에 해당하는 자바 코드는 아래와 같다.
```java
import kotlin.jvm.internal.Ref.IntRef;

class MainKt {
  public static void main(String[] args) {
    final IntRef x = new IntRef();  // 래퍼 생성
    x.element = 1;
    
    final class Counter {
        public final void increment() {
            x.element++;
        }
    }
    
    (new Counter()).increment();
    System.out.println(x.element);
  }
}
```
내포된 클래스와 달리 지역 클래스에는 가시성 변경자를 붙일 수 없으며, 지역 클래스의 영역은 항상 자기를 둘러싼 블록으로 제한된다.<br>
지역 클래스도 함수, 프로퍼티, 생성자, 내포된 클래스 등 다른 클래스가 포함할 수 있는 모든 멤버를 포함할 수 있따. 하지만 내포된 클래스는 반드시 `inner` 클래스여야만 한다.

---

# 널 가능성
코틀린 타입 시스템에서는 널 값이 될 수 있는 참조 타입과 널 값이 될 수 없는 참조 타입을 확실히 구분해주는 큰 장점이 있다. 
이 기능은 널 발생 여부를 컴파일 시점으로 옮겨주기 때문에 악명 높은 `NullPointerExeption` 예외를 상당 부분 막을 수 있다.


## 널이 될 수 있는 타입
코틀린에서 기본적으로 모든 참조 타입은 **널이 될 수 없는 타입**이다.<br>
코틀린에서 널이 될 수도 있는 값을 지정하려면 물음표(`?`)를 붙여야한다.
```kotlin
fun isBooleanString(s :String?) = s == "false" || s == "true"
```
타입 시스템 용어에서 모든 널이 될 수 있는 타입은 원래 타입의 상위 타입이며, 원래 타입에 속하는 모든 값으로 이뤄진 집합을 `null`로 확장한 집합의 값의 집합이 된다.
이 말은 특히 널이 될 수 있는 타입의 변수에 항상 널이 될 수 없는 타입의 값을 대입할 수 있다는 뜻이다. 하지만 반대의 경우는 불가능하다.
```kotlin
fun main() {
    println(isBooleanString(null))  // 가능
    val s :String? = "abc"          // 가능
    val ss :String = s              // 불가능
}
```
런타입에 널이 될 수 없는 값은 실제로 널이 될 수 있는 값과 차이가 없다. 둘 사이 구분은 컴파일 수준에서만 존재한다.
코틀린 컴파일러는 널이 될 수 없는 값을 표현하기 위해 어떤 래퍼(예: `Optional`)도 사용하지 않기 때문에 런타임에 어떠한 부가 비용도 들지 않는다.

널이 될 수 있는 타입은 원래 타입에 들어있는 어떤 프로퍼티나 메서드도 제공하지 않는다. 멤버 함수를 호출하거나 프로퍼티를 읽는 등의 일반적인 연산이 `null`에서는 의미가 없기 때문이다.


## 널 가능성과 스마트 캐스트
널이 될 수 있는 값을 처리하는 가장 직접적인 방법은 해당 값을 조건문을 사용해 `null`과 비교하는 것인데, **스마트 캐스트**(smart cast)라고 불리는 코틀린 기능이 이런 일을 가능하게 해준다.

### 스마트 캐스트
`null`에 대한 동등성 검사를 수행하면, 컴파일러가 코드 흐름의 가지 중 한쪽에서는 대상 값이 확실히 `null`이고 다른 가지에서는 `null`이 아니라는 사실을 알게되고 
값 타입을 세분화해 널이 될 수 있는 값을 널이 될 수 없는 값으로 타입 변환한다.


## 널 아님 단언 연산자
`!!` 연산자는 널 아님 단언(not-null assertion)이라고도 부르는데, `KotlinNullPointerException` 예외를 발생시킬 수 있는 연산자다.
이 연산자가 붙은 식의 타입은 원래 타입의 널이 될 수 없는 버전이다.
기본적으로 널 아님 단언은 자바 프로그램이 널 값을 역참조하려 할 때 예외를 던지는 동작을 부활시킨다.
```kotlin
val n = readLine()!!.toInt()
```
일반적으로 널이 될 수 있는 값을 사용하려면 그냥 예외를 던지는 방식보다 더 타당한 응답을 제공해야 하기 때문에 이 연산자를 사용하지 말아야 한다.


## 안전한 호출 연산자
```kotlin
fun readInt() = readLine()!!.toInt()
```
위 코드는 `KotlinNullPointerException` 예외를 발생시키면서 실패할 수 있으며, 안전한 호출 연산자를 사용하면 아래 형태로 작성할 수 있다.
```kotlin
fun readInt() = readLine()?.toInt()
```
이 코드는 다음 함수와 같다.
```kotlin
fun readInt() :Int? {
    val tml = readLine()
    return if (tmp != null) tmp.toInt() else null
}
```
안전한 호출 연산자는 수신 객체(왼쪽 피연산자)가 널이 아닌 경우 일반적인 함수 호출처럼 작동한다. 하지만 수신 객체가 널이면 안전한 호출 연산자는 호출을 수행하지 않고 그냥 널을 돌려준다.

`||`나 `&&`와 비슷하게 안전한 호출 연산도 지연 연산의 의미를 따른다. 다시 말해 수신 객체가 널이면 안전한 호출 연산자는 함수의 인자를 계산하지 않는다.


## 엘비스 연산자
널 복합 연산자(`?:`)를 사용하면 널을 대신할 디폴트 값을 지정할 수 있으며, 이를 엘비스 연산자라고 부른다.

```kotlin
fun sayHello(name :String) {
    println("Hello, " + (name ?: "Unknown"))
}
fun main() {
    sayHello("John")    // Hello, John
    sayHello(null)      // Hello, Unknown
}
```

---

# 단순한 변수 이상인 프로퍼티
일반적으로 코틀린 프로퍼티는 일반 변수를 넘어서, 프로퍼티 값을 읽거나 쓰는 법을 제어할 수 있는 훨씬 더 다양한 기능을 제공한다.


## 최상위 프로퍼티
클래스나 함수와 마찬가지로 최상위 수준에 프로퍼티를 정의할 수도 있다. 이런 경우 프로퍼티는 전역 변수나 상수와 비슷한 역할을 한다.
```kotlin
val prefixx = "Hello, " // 최상위 불변 프로퍼티

fun main() {
    val name = readLine() ?: return 
    println("$prefix$name")
}
```
이런 프로퍼티에 최상위 가시성을 지정할 수 있으며, 임포트 디렉티브에서 최상위 프로퍼티를 임포트할 수도 있다.
```kotlin
// util.kt
package util

val prefix = "Hello, "
```
```kotlin
// main.kt
package main

fun main() {
    val name = readLine() ?: return 
    println("$prefix$name")
}
```


## 늦은 초기화
어떤 프로퍼티는 클래스 인스턴스가 생성된 뒤에, 그러나 해당 프로퍼티가 사용되는 시점보다는 이전에 초기화돼야 할 수도 있다.<br>
예를 들어 단위 테스트를 준비하는 코드나 의존 관계 주입에 의해 대입돼야 하는 프로퍼티가 이런 종류에 속한다.

```kotlin
import java.io.File

class Content {
    var text: String? = null
  
    fun loadFile(file: File) {
        text = file.readText()
    }
}

fun getContentSize(content: Content) = content.text?.length ?: 0
```

여기서 `loadFile()`은 다른 곳에서 호출되며 어떤 파일의 내용을 모두 문자열로 읽어온다고 가정하자.<br>
이 예제의 단점은 항상 사용 전에 초기화되므로 절대 널이 될 수 없는 값이라는 사실을 알고 있음에도 불구하고 늘 널 가능성을 처기해야 한다는 점이다.

코틀린은 이런 패턴을 지원하는 `lateinit` 키워드를 제공한다.

```kotlin
import java.io.File

class Content {
  lateinit var text: String

  fun loadFile(file: File) {
    text = file.readText()
  }
}

fun getContentSize(content: Content) = content.text.length
```
`lateinit` 표시가 붙은 프로퍼티는 값을 읽으려고 시도할 때 프로그램이 프로퍼티가 초기화됐는지 검사해서 초기화되지 않은 경우 `UninitializedPropertyAccessException`을 던진다.

프로퍼티를 `lateinit`으로 만들기 위해서는 몇가지 조건을 만족해야 한다.
1. 프로퍼티가 코드에서 변경될 수 있는 지점이 여러 곳일 수 있으므로 프로퍼티를 가변 프로퍼티(`var`)로 정의해야 한다.
2. 프로퍼티의 타입은 널이 아닌 타입이어야 하고 `Int`나 `Boolean` 같은 원시 값을 표현하는 타입이 아니어야 한다.
3. `lateinit` 프로퍼티를 정의하면서 초기화 식을 지정해 값을 바로 대입할 수 없다.


## 커스텀 접근자 사용하기
지금까지 살펴본 프로퍼티는 근본적으로 어떤 코틀린 클래스의 인스턴스나 어떤 파일 안의 문맥 내에 저장된 일반 변수처럼 작동했다.
하지만 코틀린 프로퍼티의 실제 능력은 변수와 함수의 동작을 한 선언 안에 조합할 수 있는 기능에 있다.
이런 기능은 커스텀 접근자를 통해 이뤄진다. 커스텀 접근자는 프로퍼티 값을 읽거나 쓸 때 호출되는 특별한 함수다.

아래는 프로퍼티 값을 읽을 때 사용하는 커스텀 게터(getter)를 정의한다.
```kotlin
class Person(val firstName: String, val familyName: String) {
  val fullName: String
    get(): String {
      return "$firstName $familyName"
    }
}

fun main() {
    val person = Person("일훈", "박")
    println(person.fullName)    // 일훈 박
}
```
게터는 프로퍼티 정의 끝에 붙으며 프로퍼티를 읽을 때 프로그램이 자동으로 게터를 호출한다.
게터는 파라미터가 없으며 반환 타입은 프로퍼티의 타입과 같아야한다.

코틀린 1.1 부터는 프로퍼티와 게터 정의에서 프로퍼티의 타입을 생략하고 타입 추론에 의존하면 된다.
```kotlin
val fullName
    get() = "$firstName $familyName"
```
위 예제의 프로퍼티 값은 매번 `fullName` 프로퍼티를 읽을 때마다 다시 계산된다.
`fistName`, `familyName`과 달리 `fullName`에는 **뒷받침하는 필드**가 없기 때문에 클래스 인스턴스에서 전혀 메모리를 차지하지 않는다.
즉, 기본적으로 `fullName`은 프로퍼티 형태인 함수와 같다. 자바에서는 이런 경우 보통 `getFullName()`으로 게터 이름을 정한다.

뒷받침하는 필드 관련 규칙은 아래와 같다.
1. 프로퍼티에 명시적으로 `field`를 사용하는 디폴트 접근자나 커스텀 접근자가 하나라도 있으면 뒷받침하는 필드가 생성된다.
2. 뒷받침하는 필드 참조는 `field`라는 키워드를 사용하며 접근자의 본문 안에서만 유용하다.

```kotlin
class Person(val firstName: String, val familyName: String, age: Int) {
  val fullName: String  // 뒷받침 하는 필드가 아님
    get(): String {
      return "$firstName $familyName"
    }
  
  val age: Int = age    // 뒷받침 하는 필드임
    get() {
        println("age에 접근합니다.")
        return field
    }
}
```
프로퍼티에 뒷받침하는 필드가 없다면 필드를 초기화할 수 없다. **초기화는 기본적으로 클래스를 인스턴스화할 때 값을 뒷받침하는 필드에 직접 대입하는 것**이기 때문이다. 프로퍼티를 초기화하면 값을 바로 뒷받침하는 필드에 쓰기 때문에 프로퍼티 초기화는 세터를 호출하지 않는다.

커스텀 게터가 있는 프로퍼티는 약간의 문법적인 차이에도 불구하고 파라미터가 없는 함수처럼 동작하므로, 어떤 경우 함수를 사용하고 어떤 경우 프로퍼티를 사용할지에 대한 의문이 떠오를 수 있다.

공식 코틀림 코딩 관습은 아래의 경우에서 함수보다 프로퍼티를 사용하는 쪽을 권장한다.
* 값을 계산하는 과정에서 예외가 발생할 여지가 없는 경우
* 값을 계산하는 비용이 충분히 싼 경우
* 값을 캐시해두는 경우
* 클래스 인스턴스의 상태가 바뀌기 전에는 여러번 프로퍼티를 읽는 경우
* 함수를 호출해도 항상 똑같은 결과를 내는 경우

```kotlin
class Person(val firstName: String, val familyName: String) {
    var age :Int? = null    // age가 디폴트 게터를 사용하고 커스텀 세터에서 field를 언급하므로 뒷받침하는 필드를 생성
        set(value) {
            if (value != null && value <= 0) {
                throw IllegalArgumentException("Invalid Age : $value")
            }
            field = value
        }
}

fun main() {
    val Person = Person("John", "Doe")
    person.age = 20     // 커스텀 세터 호출
    println(person.age)
}
```

프로퍼티 접근자에 별도로 가시성 변경자를 붙여 클래스 외부에서는 프로퍼티의 값을 변경하지 못하게 하고 외부에서는 객체가 실질적으로 불변인것 처럼 보이게 할 수도 있다.

```kotlin
import java.util.Date

class Person(name: String) {
    var lastChanged: Date? = null
        private set

    var name: String = name
        set(value) {
            lastChanged = Date()
            field = value
        }
}

```

`lateinit` 프로퍼티의 경우 항상 자동으로 접근자가 생성되기 때문에 프로그래머가 직접 커스텀 접근자를 정의할 수 없다. 그리고 주생성자 파라미터로 선언된 프로퍼티에 대한 접근자도 지원하지 않는다.


## 지연 계산 프로퍼티와 위임
어떤 프로퍼티를 처음 읽을 때까지 그 값에 대한 계산을 미뤄두고 싶을 때 `lazy` 프로퍼티를 통해 이를 달성할 수 있다.
```kotlin
import java.io.File

val text by lazy {
    File("data.txt").readText()
}

fun main() {
    while (true) {
        when (val command = readLine() ?: return) {
            "print data" -> println(text)
            "exit" -> return
        }
    }
}
```
`lazy` 다음에 오는 블록 안에는 해당 프로퍼티를 초기화하는 코드를 지정한다.
`main()` 함수에서 사용자가 적절한 명령으로 프로퍼티 값을 읽기 전까지, 프로그램은 `lazy` 프로퍼티의 값을 계산하지 않는다.
초기화가 된 이후 프로퍼티의 값은 필드에 저장되고, 그 이후로는 프로퍼티의 값을 읽을 때마다 저장된 값을 읽게 된다.

```kotlin
val text  = File("data.txt").readText() // 프로그램이 시작될 때 바로 파일을 읽는다.

val text
    get() File("data.txt").readText()   // 프로그램이 프로퍼티 값을 읽을 땜마다 파일을 매번 다시 읽어온다.
```

`lazy` 구문은 사실 프로퍼티 처리에 필요한 데이터를 모아 유지하면서 읽기와 쓰기를 처리하는 위임 객체를 통해 프로퍼티를 구현하게 해주는 위임 프로퍼티라는 기능의 특별한 경우다.
위임 객체는 `by` 라는 키워드 다음에 위치하며, 코틀린이 정한 규약을 만족하는 객체를 반환할 수 있는 임의의 식이 될 수 있다.
때문에 `lazy {}` 는 코틀린의 내장 구성 요소가 아니라 표준 라이브러리 함수에 람다를 넘기는 식일 뿐이다.

디폴트로 `lazy` 프로퍼티는 스레드 안전하다. 즉, 다중 스레드 환경에서도 값을 한 스레드 안에서만 계산하기 때문에 `lazy` 프로퍼티에 접근하려는 모든 스레드는 궁극적으로 같은 값을 얻게 된다.

---

# 객체
## 객체 선언
코틀린은 어떤 클래스에 인스턴스가 오직 하나만 존재하게 보장하는 싱글턴 패턴을 내장하고 있다. `class` 대신 `object`라는 키워드를 사용한다.
```kotlin
object Application {
    val name = "My Application"

    override fun toString() = name

    fun exit() { }
}
```
객체 정의는 스레드 안전하다. 컴파일러는 실행되는 여러 스레드에서 싱글턴에 접근하더라도 오직 한 인스턴스만 공유되고 초기화 코드도 단 한 번만 실행되도록 보장한다.
초기화는 싱글턴 클래스가 실제 로딩되는 시점까지 지연된다. 보통은 프로그램이 객체 인스턴스에 처음 접근할 때 초기화가 이뤄진다.

클래스와 마찬가지로 객체 선언도 멤버 함수와 프로퍼티를 포함할 수 있고, 초기화 블록도 포함할 수 있다.
하지만 객체에는 주생성자나 부생성자가 없다. 객체 인스턴스는 항상 암시적으로 만들어지기 때문에 객체의 경우 생성자 호출이 아무런 의미가 없다.

객체의 본문에 들어있는 클래스에는 `inner`가 붙을 수 없다. 
내부 클래스의 인스턴스는 항상 바깥쪽 클래스의 인스턴스와 연관되는데, 객체 선언은 항상 인스턴스가 하나뿐이므로 `inner` 변경자가 불필요해진다. 
그래서 객체 안에 정의된 클래스에 대해서는 `inner` 사용을 금지한다.

최상위 선언들과 마찬가지로, 객체의 멤버를 임포트해서 간단한 이름만 사용해 참조할 수 있다.
```kotlin
import Application.exit

fun main() {
    println(Application.name)   // 전체 이름 사용
    exit()                      // 간단한 이름 사용
}
```
하지만 객체의 모든 멤버가 필요할 때 임포트 문으로 임포트할 수는 없다.
```kotlin
import Application.*    // Error
```
이런 제약은 객체 정의 안에 다른 클래스 정의와 같이 `toString()`이나 `equals()`와 같은 공통 메서드 정의가 들어있기 때문이다.

클래스와 마찬가지로 객체도 다른 클래스 안에 내포될 수 있고 다른 객체 안에 내포될 수 있다. 하지만 객체를 함수 내부에 넣거나 지역 클래스 또는 내부 크래스 안에 넣을 수 없다. 이런 정의들은 어떤 외부 문맥에 의존하므로 싱글턴이 될 수 없기 때문이다.

자바에서는 유틸리티 클래스가 종종 존재한다. 이런 패턴은 자바에서는 유용하나 코틀린에서는 일반적으로 권장하지 않는 패턴이다.
무엇보다 코틀린 클래스에서는 정적 메서드를 정의할 수 없기 때문에 일반 클래스를 통해 자바의 유틸리티 클래스를 정의할 방법이 없다.
하지만 코틀린은 자바와 달리 최상위 선언을 패키지 안에 함께 모아둘 수 있으므로 불필요하게 유틸리티 클래스를 선언해야 할 필요가 없다.


## 동반 객체
내포된 클래스와 마찬가지로 내포 객체도 인스턴스가 생기면 자신을 둘러싼 클래스의 비공개 멤버에 접근할 수 있다.
이런 특성은 팩토리 디자인 패턴을 쉽게 구현하는 경우 유용하게 활용할 수 있다.

```kotlin
class Application private constructor(val name: String) {
    object Factory {
        fun create(args: Array<String>): Application? {
            val name = args.firstOrNull() ?: return null
            return Application(name)
        }
    }
}

fun main(args: Array<String>) {
    val app = Application.Factory.create(args) ?: return
    println("Application started : ${app.name}")
}
```
이런 경우 별도로 `import Application.Factory.create`로 팩토리 메서드를 임포트하지 않는 한 매번 내포된 이름을 지정해야한다.
코틀린에서는 동반 객체(companion object)를 통해 이를 해결할 수 있다. 동반 객체는 `companion` 키워드를 덧붙인 내포된 객체이다.
이 객체는 다른 내포된 객체와 마찬가지로 동작하나 이 동반 객체의 멤버에 접근할 때는 동반 객체의 이름을 사용하지 않고 동반 객체가 들어있는 외부 클래스의 이름을 사용할 수 있다.

```kotlin
class Application private constructor(val name: String) {
    companion object Factory {
        fun create(args: Array<String>): Application? {
            val name = args.firstOrNull() ?: return null
            return Application(name)
        }
    }
}

fun main(args: Array<String>) {
    val app = Application.create(args) ?: return
    println("Application started : ${app.name}")
}
```

동반 객체의 경우 정의에서 이름을 아예 생략할 수 있으며 이러한 방식을 더 권장한다. 
이 때 컴파일러는 동반 객체의 디폴트 이름을 `Companion`으로 가정한다. 하지만 동반 객체의 멤버를 임포트하고 싶을 때는 객체 이름을 명시해야한다.
```kotlin
class Application private constructor(val name: String) {
    companion object {
        fun create(args: Array<String>): Application? {
            val name = args.firstOrNull() ?: return null
            return Application(name)
        }
    }
}
```

클래스에 동반 객체가 둘 이상 있을 수는 없다.
`companion` 키워드를 최상위 객체 앞에 붙이거나 다른 객체에 내포된 객체 앞에 붙이는 것은 금지된다.
전자의 경우 동반 객체를 연결할 클래스 정의가 없기 때문이고, 후자의 경우 `companion`을 붙이는 것이 불필요한 중복이기 때문이다.


## 객체 식
코틀린은 명시적인 선언 없이 객체를 바로 생성할 수 있는 객체 식을 제공하는데, 이는 자바 익명 클래스와 아주 유사하다.

```kotlin
fun main() {
    fun midPoint(xRange :IntRange, yRange: IntRange) = object {
        val x = (xRange.first + xRange.last) / 2
        val y = (yRange.first + yRange.last) / 2
    }

    val midPoint = midPoint(1..5, 2..6)
    println("${midPoint.x}, ${midPoint.y}") // 3, 4
}
```

위 코드에서 `midPoint()` 함수가 반환하는 타입은 **객체 식 안에 정의된 모든 멤버가 들어있는 클래스를 표현하는 익명 객체 타입**이다.
이런 타입은 단 하나만 존재하며 멤버가 모두 완전히 똑같은 두 객체 식은 서로 타입이 다르다.

익명 객체 타입은 지역 선언이나 비공개 선언에만 전달될 수 있다.
아래 `minPoint()` 함수의 타입은 객체 식에 해당하는 익명 객체 타입이 아니라 객체 식에 지정된 상위 타입이 된다.
하지만 상위 타입을 명시하지 않았으므로 `Any`를 상위 타입으로 가정한다. 때문에 `midPoint.x` 참조에서 `x`를 찾을 수 없다.

```kotlin
fun midPoint(xRange :IntRange, yRange: IntRange) = object {
    val x = (xRange.first + xRange.last) / 2
    val y = (yRange.first + yRange.last) / 2
}

fun main() {
    val midPoint = midPoint(1..5, 2..6)
    // error: unresolved reference: x
    // error: unresolved reference: y
    println("${midPoint.x}, ${midPoint.y}")
}
```

객체 식은 기존 클래스의 하위 클래스를 선언하지 않고도 기존 클래스를 약간만 변경해 기술하는 간결한 방법을 제공한다.