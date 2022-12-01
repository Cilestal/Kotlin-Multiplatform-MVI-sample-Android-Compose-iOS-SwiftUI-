import SwiftUI

struct ToolbarSimple: View {
    
    private let text: String
    private let action: () -> Void
    private let enabled: Bool
    
    public init(text: String, enabled: Bool = true, action: @escaping () -> Void) {
        self.text = text
        self.action = action
        self.enabled = enabled
    }
    
    var body: some View {
        
        HStack(alignment: .center, spacing: 0) {
            Button(action: action, label: {
                Image("arrow_left")
                    .resizable()
                    .foregroundColor(Color(.greenDark))
                    .frame(width: 24.0, height: 24.0)
                    .padding(16.0)
            }).disabled(!enabled)
            
            Text(text).fontWeight(.bold)
                .font(.themeFont(ofSize: 18.0))
                .foregroundColor(Color(.gray90))
                .lineLimit(1)
                .padding(.trailing, 16.0)
            
            Spacer()
        }.frame(
            height: 56
        )
    }
}


struct ToolbarSimple_Previews: PreviewProvider {
    static var previews: some View {
        ToolbarSimple(
            text: "Simple Toolbar") {
                
            }
    }
}
