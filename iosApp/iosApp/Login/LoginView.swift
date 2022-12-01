//
//  LoginView.swift
//  iosApp
//
//  Created by michael on 01.12.2022.
//  Copyright © 2022 Cilestal.com. All rights reserved.
//

import shared
import SwiftUI

typealias LoginObservableObject = BaseObservableObject<LoginViewModel, LoginEvent, LoginState, LoginEffect>

struct LoginView: View {
    
    enum Field: Hashable {
        case username
        case password
    }
    
    @Environment(\.presentationMode) var mode: Binding<PresentationMode>
    
    @StateObject var vm: LoginObservableObject
    
    @SwiftUI.State var emailInput: String = ""
    @SwiftUI.State var passwordInput: String = ""
    
    @FocusState private var focusedField: Field?
    
    init() {
        let kotlinVM = TmpInjector.shared.vmInstance
        _vm = StateObject(wrappedValue: LoginObservableObject(vm: kotlinVM))
    }
    
    var body: some View {
        ZStack(alignment: .top) {
            Color(.mainBackground).ignoresSafeArea(.all)
            
            VStack(alignment: .leading, spacing: 16.0) {
                ToolbarSimple(text: "Sign In", enabled: vm.state.enabled) {
                    vm.dispatchEvent(event: .OnBackClicked())
                }
                self.loginField
                self.passwordField
                self.buttons
                
                if(vm.state.progress) {
                    ProgressView()
                        .progressViewStyle(.circular)
                        .frame(maxWidth: .infinity, alignment: .center)
                }
            }
        }.onAppear {
            vm.dispatchEvent(event: .OnInit())
        }
        .onTapGesture {
            self.hideKeyboard()
        }
        .onDisappear {
            vm.clear()
        }.onReceive(vm.$effect) { effect in
            switch(effect) {
            case is LoginEffect.ClearAllData:
                self.emailInput = ""
                self.passwordInput = ""
                break;
            case is LoginEffect.Close:
                self.mode.wrappedValue.dismiss()
                break;
            default:
                break;
            }
        }.onChange(of: emailInput) { newValue in
            self.vm.dispatchEvent(event: .OnEmailChanged(email: newValue))
        }.onChange(of: passwordInput) { newValue in
            self.vm.dispatchEvent(event: .OnPasswordChanged(password: newValue))
        }
    }
 
    @ViewBuilder
    private var loginField: some View {
        VStack(alignment: .leading, spacing: 16.0) {
            Text("Email")
                .font(.themeFont(ofSize: 15.0))
                .foregroundColor(Color(.gray90))
            RoundedTextField(
                "Email",
                text: $emailInput,
                errorMsg: vm.state.emailError,
                type: .emailAddress,
                keyboardType: .emailAddress,
                submitLabel: .next
            )
            .focused($focusedField, equals: .username)
            .onSubmit {
                self.focusedField = .password
            }
            
        }.padding(.horizontal, 16.0)
    }
    
    @ViewBuilder
    private var passwordField: some View {
        VStack(alignment: .leading, spacing: 16.0) {
            Text("Password")
                .font(.themeFont(ofSize: 15.0))
                .foregroundColor(Color(.gray90))
            
            RoundedTextField(
                "Password",
                text: $passwordInput,
                errorMsg: vm.state.passwordError,
                type: .password,
                keyboardType: .default,
                submitLabel: .done
            ).onSubmit {
                hideKeyboard()
                nextClicked()
            }
            .focused($focusedField, equals: .password)
        }.padding(.horizontal, 16.0)
    }
    
    @ViewBuilder
    private var buttons: some View {
        HStack(alignment: .center, spacing: 0) {
            Spacer()
            
            Button {
                hideKeyboard()
                nextClicked()
            } label: {
                Text("Next")
            } .padding(.trailing, 16.0)
                .disabled(!vm.state.enabled)

        }
    }
    
    private func nextClicked() {
        vm.dispatchEvent(event: .OnNextButtonClicked(email: emailInput, password: passwordInput))
    }
}


struct LoginView_Previews: PreviewProvider {
    static var previews: some View {
        LoginView()
    }
}
