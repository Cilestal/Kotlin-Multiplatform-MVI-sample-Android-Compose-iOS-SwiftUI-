//
//  KeyboardExt.swift
//  iosApp
//
//  Created by michael on 01.12.2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI

extension View {
    func hideKeyboard() {
        UIApplication.shared.sendAction(#selector(UIResponder.resignFirstResponder), to: nil, from: nil, for: nil)
    }
}
