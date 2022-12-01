import shared

class Collector<T> : Kotlinx_coroutines_coreFlowCollector {
    let block:(T) -> Void
    
    init(block: @escaping (T) -> Void) {
        self.block = block
    }
    
    func emit(value: Any?) async throws {
        if let parsed = value as? T {
            DispatchQueue.main.async {
                self.block(parsed)
            }
        }
    }
}

extension Kotlinx_coroutines_coreFlow {
    func subscribe<T>(block: @escaping (T) -> Void, onError: ((Error?) -> Void)? = nil) {
        self.collect(
            collector: Collector<T> { value in
                block(value)
            }
        ) { error in
            print("Error ocurred during state collection")
            onError?(error)
        }
    }
}

extension Kotlinx_coroutines_coreStateFlow {
    func subscribe<T>(block: @escaping (T) -> Void, onError: ((Error?) -> Void)? = nil) {
        self.collect(
            collector: Collector<T> { value in
                block(value)
            }
        ) { error in
            print("Error ocurred during state collection")
            onError?(error)
        }
    }
    
    func value<T>() -> T {
        return value as! T
    }
}
