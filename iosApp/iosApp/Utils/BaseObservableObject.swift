//
//  LoginView.swift
//  iosApp
//
//  Created by michael on 01.12.2022.
//  Copyright © 2022 Cilestal.com. All rights reserved.
//

import shared
import SwiftUI

class BaseObservableObject<VM: BaseViewModel, Event, State, Effect>: ObservableObject {
    @Published var state: State
    @Published var effect: Effect? = nil
    private let vm: VM
    
    init(vm: VM) {
        self.vm = vm
        
        self.state = self.vm.stateFlow.value()
        self.observeState()
    }
    
    private func observeState() {
        vm.stateFlow.subscribe { state in
            self.state = state
            print("TEST: \(state)")
        }
        
        vm.effectFlow.subscribe { effect in
            self.effect = effect
            print("TEST: \(String(describing: effect))")
        }
    }
    
    func dispatchEvent(event: Event) {
        self.vm.dispatchEvent(event: event)
    }
    
    func clear() {
        vm.clear()
    }
}


//func createVM<VM: BaseViewModel, Event, State, Effect>(
//    vm: (Kodein_diDI) -> VM
//) -> BaseObservableObject<VM, Event, State, Effect> {
//    return BaseObservableObject(vm: vm(Framework.shared.baseModule))
//}
