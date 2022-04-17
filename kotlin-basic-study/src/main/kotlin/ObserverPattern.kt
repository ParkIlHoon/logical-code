fun main() {
    EventPrinter().start()
    println()
}

/**
 * observer 패턴
 */
class EventPrinter: EventListener {
    override fun onEvent(count: Int) {
        print("${count}-")
    }

    fun start() {
        val counter = EventCounter(this)
        counter.count()
    }

    /**
     * EventPrinter 가 EventListener 를 구현하지 않을 경우
     */
//    fun start() {
//        var counter = EventCounter(object: EventListener {
//            override fun onEvent(count: Int) {
//                print("${count}-")
//            }
//        })
//        counter.count()
//    }
}
class EventCounter(var eventListener: EventListener) {
    fun count() {
        for (i in 1..100) {
            if (i % 5 == 0) {
                eventListener.onEvent(i)
            }
        }
    }
}
interface EventListener {
    fun onEvent(count: Int)
}