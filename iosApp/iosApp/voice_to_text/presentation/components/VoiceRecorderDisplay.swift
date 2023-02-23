//
//  VoiceRecorderDisplay.swift
//  iosApp
//
//  Created by Tharuka Gamage on 2023-02-18.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI

struct VoiceRecorderDisplay: View {
    
    var powerRatios: [Double]
    
    var body: some View {
        Canvas{ context, size in
            context.clip(to: Path(CGRect(origin: .zero, size: size)))
            
            let barWidth = 3.0
            let barCount = Int(size.width/Double(barWidth*2))
            let defaultLevel = 0.05
            let reversedRatios = powerRatios.map{ ratio in
                min(max(defaultLevel, ratio), 1.0)
            }
                .suffix(barCount)
                .reversed()
            
            for(i, powerRatio) in reversedRatios.enumerated(){
                let centerY = CGFloat(size.height / 2.0)
                let yTopStart = CGFloat(centerY - centerY*powerRatio)
                var path = Path()
                path.addRoundedRect(in: CGRect(
                    x: CGFloat(size.width) - CGFloat(i) * barWidth * 2,
                    y: yTopStart,
                    width: barWidth,
                    height: (centerY - yTopStart) * 2.0
                ), cornerSize: CGSize(width: 10.0, height: 10.0))
                context.fill(path, with: .color(.primaryColor))
            }
        }
        .gradientSurface()
        .cornerRadius(20)
        .padding(.horizontal, 15)
        .padding(.horizontal, 15)
        .padding(.vertical, 5)
        .shadow(radius: 4)
    }
}

struct VoiceRecorderDisplay_Previews: PreviewProvider {
    static var previews: some View {
        VoiceRecorderDisplay(powerRatios: [])
    }
}
