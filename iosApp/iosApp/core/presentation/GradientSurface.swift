//
//  GradientSurface.swift
//  iosApp
//
//  Created by Tharuka Gamage on 2023-02-15.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI

struct GradientSurface: ViewModifier{
    
    @Environment(\.colorScheme) var colorScheme
    
    func body(content: Content) -> some View {
        if colorScheme == .dark{
            let startGradient = Color(hex: 0xFF23262E)
            let endGradient = Color(hex: 0xFF212329)
            content.background(LinearGradient(colors: [startGradient, endGradient], startPoint: .top, endPoint: .bottom))
        }else{
            content.background(Color.surface)
        }
    }
}
    extension View{
        func gradientSurface() -> some View{
            return modifier(GradientSurface())
        }
    }
