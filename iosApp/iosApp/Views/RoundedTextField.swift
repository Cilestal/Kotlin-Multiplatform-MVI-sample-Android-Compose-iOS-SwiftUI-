import SwiftUI

struct RoundedTextField: View {
    @Binding private var text: String
    @State private var isSecured: Bool = true
    private var title: String
    
    private let keyboardType: UIKeyboardType
    private let type: UITextContentType?
    private let isPassword: Bool
    private let errorMsg: Binding<String>
    
    private let submitLabel: SubmitLabel
    
    @State private var shakeState: Int = 0
    @FocusState private var requestFocus: Bool
    
    init(_ title: String,
         text: Binding<String>,
         errorMsg: String? = nil,
         type: UITextContentType? = nil,
         keyboardType: UIKeyboardType = .default,
         submitLabel: SubmitLabel = .done
    ) {
        self.title = title
        self._text = text
        self.type = type
        self.keyboardType = keyboardType
        self.errorMsg = Binding.constant(errorMsg ?? "")
        self.submitLabel = submitLabel
        
        self.isPassword = type == .password || self.type == .newPassword
    }
    
    var body: some View {
        VStack(alignment: .leading, spacing: 6.0) {
            inputField
            if(errorMsg.wrappedValue.isEmpty == false) {
                errorLabel
            }
        }.onChange(of: errorMsg.wrappedValue) { newValue in
            if(!newValue.isEmpty) {
                withAnimation(.default) {
                    shakeState += 1
                }
            }
        }
    }
    
    private var errorLabel: some View {
        Text(errorMsg.wrappedValue)
            .font(.themeFont(ofSize: 13.0))
            .foregroundColor(Color(.redDark))
            .padding(.horizontal, 16.0)
    }
    
    private var inputField: some View {
        HStack(alignment: .center) {
            ZStack {
                if isPassword && isSecured {
                    SecureField("", text: $text)
                        .lineLimit(1)
                        .textContentType(type)
                        .keyboardType(self.keyboardType)
                        .font(.themeMediumFont(ofSize: 15.0))
                        .foregroundColor(Color(.gray90))
                        .placeHolder(
                            Text(title)
                                .font(.themeFont(ofSize: 15.0))
                                .foregroundColor(Color(.gray60))
                                .lineLimit(1),
                            show: text.isEmpty
                        )
                        .submitLabel(submitLabel)
                        .autocorrectionDisabled()
                } else {
                    TextField("", text: $text)
                        .lineLimit(1)
                        .textContentType(type)
                        .keyboardType(self.keyboardType)
                        .font(.themeMediumFont(ofSize: 15.0))
                        .foregroundColor(Color(.gray90))
                        .placeHolder(
                            Text(title)
                                .font(.themeFont(ofSize: 15.0))
                                .foregroundColor(Color(.gray60))
                                .lineLimit(1),
                            show: text.isEmpty
                        )
                        .submitLabel(submitLabel)
                        .autocorrectionDisabled()
                }
            }.padding(.horizontal, 16.0)
                .padding(.vertical, 16.0)
                .focused($requestFocus)
            
            if(self.isPassword) {
                Button(action: {
                    isSecured.toggle()
                    
                    DispatchQueue.main.asyncAfter(deadline: .now() + 0.1, execute: {
                        self.requestFocus = true
                    })
                   
                }) {
                    Image(self.isSecured ? "ic_password_hidden" : "ic_password_visible")
                        .resizable()
                        .foregroundColor(Color(.green))
                        .frame(width: 24.0, height: 24.0)
                }.padding(.trailing, 16.0)
            }
        }
        .background(Color(.editText))
        .cornerRadius(24.0)
        .overlay(
            RoundedRectangle(cornerRadius: 24.0)
                .stroke(hasError() ? Color(.redDark) : Color(.whiteTransparent), lineWidth: 1)
        )
        .modifier(ShakeEffect(animatableData: CGFloat(shakeState)))
        .onTapGesture {
            self.requestFocus = true
        }
    }
    
    private func hasError() -> Bool {
        return self.errorMsg.wrappedValue.isEmpty == false
    }
    
    
}


struct RoundedTextField_Previews: PreviewProvider {
    @State static var text = "454554646464"
    
    static var previews: some View {
        ZStack {
            Color(.white).ignoresSafeArea(.all)
            RoundedTextField(
                "Placeholder",
                text: $text
                //                errorMsg: "hello world"
                //                type: .newPassword
            ).padding(.all , 16.0)
        }
        
    }
}
