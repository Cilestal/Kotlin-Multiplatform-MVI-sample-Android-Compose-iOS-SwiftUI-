import SwiftUI

extension Font {


    static var themeSize: CGFloat = 15.0
    static var headingSize: CGFloat =  22.0
    
    static func themeFont(ofSize fontSize: CGFloat = Font.themeSize) -> Font {
        return Font.system(size: fontSize)
    }
    
    static func themeBoldFont(ofSize fontSize: CGFloat = Font.themeSize) -> Font {
        return Font.system(size: fontSize)
    }
    
    static func themeMediumFont(ofSize fontSize: CGFloat = Font.themeSize) -> Font {
        return Font.system(size: fontSize)
    }
    
    static func themeBlackFont(ofSize fontSize: CGFloat = Font.themeSize) -> Font {
        return Font.system(size: fontSize)
    }
}


