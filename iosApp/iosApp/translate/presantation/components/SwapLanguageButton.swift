//
//  SwapLanguageButton.swift
//  iosApp
//
//  Created by Tharuka Gamage on 2023-02-12.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI

struct SwapLanguageButton: View {
    
    var onClick:()->Void
    
    var body: some View {
        Button(action: onClick){
            Image(uiImage: UIImage(named: "swap_languages")!).padding().background(Color.primaryColor).clipShape(Circle())
        }
    }
}

struct SwapLanguageButton_Previews: PreviewProvider {
    static var previews: some View {
        SwapLanguageButton(onClick: {})
    }
}
