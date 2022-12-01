import UIKit

extension UIColor {
    
    static var mainBackground = UIColor(named: "colorBackground")!
    static var bottomBarBackground = UIColor(named: "colorBottomBar")!
    static var controlButton = UIColor(named: "colorControl")!
    static var editText = UIColor(named: "colorEditText")!
    static var tagBackground = UIColor(named: "tagBackground")!
    static var grayEastBay = UIColor(named: "gray_east_bay")!
    
    static var whiteTransparent = UIColor(with: 0xffffff, alpha: 0)
    static var alertBackground = UIColor(with: 0x0B2342, alpha: 0.7)

    static var yellowStatus = UIColor(with: 0xFFC907)
    static var green = UIColor(named: "green")!
    static var greenLight = UIColor(with: 0x00da76)
    static var greenDark = UIColor(named: "green_dark")!
    static var green10 = UIColor(hexString: "1A00BF60")
    
    static var pink = UIColor(with: 0xfc438c)
    static var pinkLight = UIColor(with: 0xff5da9)
    static var pinkDark = UIColor(with: 0xf9296f)
    
    static var redDark = UIColor(named: "redDark")!
    
    static var blue = UIColor(with: 0x2b81ff)
    static var blueDeepSky = UIColor(with: 0x07a3ff)
    static var blueDark = UIColor(with: 0x294fd9)
    static var bluePersian19 = UIColor(with: 0x2f003ede)
    
    static var grayMarkerCircle = UIColor(with: 0xa9b0ba)
    
    static var gray90 = UIColor(named: "gray_90")!
    static var gray80 = UIColor(named: "gray_80")!
    static var gray70 = UIColor(named: "gray_70")!
    static var gray60 = UIColor(named: "gray_60")!
    static var gray50 = UIColor(named: "gray_50")!
    static var gray40 = UIColor(named: "gray_40")!
    static var gray30 = UIColor(named: "gray_30")!
    static var gray20 = UIColor(named: "gray_20")!
    static var gray10 = UIColor(named: "gray_10")!
    
    static var gray90_60 = UIColor(named: "gray_90_60")!
    
    // MARK: PrimaryButton
    
    static var primaryText = UIColor.white
    static var primaryGradientLeft = greenDark
    static var primaryGradientRight = greenLight
    static var primaryGradientDisabledLeft = greenDark.withAlphaComponent(0.6)
    static var primaryGradientDisabledRight = greenLight.withAlphaComponent(0.6)
    
    // MARK: SecondaryButton
    
    static var secondaryText = UIColor(with: 0x009865)
    static var secondaryTextHighlighted = UIColor(with: 0x00c874)

    static var secondaryBorder = green
    
    // MARK: OnImageButton
    
    static var onImageBackground = UIColor(with: 0x1d222b).withAlphaComponent(0.6)
    static var onImageBackgroundHighlighted = UIColor(with: 0x1d222b).withAlphaComponent(0.9)
    static var onImageBorder = UIColor(hexString: "#00e291")
    
    static var wrongAnswerGradientLeft = pinkDark
    // MARK: LoginTextField
    
    static var loginTextFieldDefaultUnderline = UIColor(with: 0x1d222b).withAlphaComponent(0.3)
    static var loginTextFieldActiveUnderline = green
    static var loginTextFieldErrorUnderline = redDark

    static var themeText = gray90
    
    // MARK: Tasklist
    
    static var taskLocked = UIColor(hexString: "e9e9e9")
    static var taskActiveBackground = UIColor(hexString: "fef8e1")
    
    // MARK: QrCode
    static var purpleStrawberry = UIColor(hexString: "FE2980")

    // MARK: -

    convenience init(with hex: UInt32, alpha: CGFloat = 1.0) {
        self.init(red: CGFloat((hex & 0xFF0000) >> 16) / CGFloat(255),
                  green: CGFloat((hex & 0x00FF00) >> 8) / CGFloat(255),
                  blue: CGFloat(hex & 0x0000FF) / CGFloat(255),
                  alpha: alpha)
    }
    
    
    convenience init(hexString: String) {
        var chars = Array(hexString.hasPrefix("#") ? hexString.dropFirst() : hexString[...])
        switch chars.count {
        case 3: chars = chars.flatMap { [$0, $0] }; fallthrough
        case 6: chars = ["F","F"] + chars
        case 8: break
        default: fatalError("Wrong hex string")
        }
        self.init(red: .init(strtoul(String(chars[2...3]), nil, 16)) / 255,
                  green: .init(strtoul(String(chars[4...5]), nil, 16)) / 255,
                  blue: .init(strtoul(String(chars[6...7]), nil, 16)) / 255,
                  alpha: .init(strtoul(String(chars[0...1]), nil, 16)) / 255)
    }
    
}
