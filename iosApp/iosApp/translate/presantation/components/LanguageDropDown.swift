//
//  LanguageDropDown.swift
//  iosApp
//
//  Created by Tharuka Gamage on 2023-02-12.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI
import shared

struct LanguageDropDown: View {
    
    var language: UiLanguage
    var isOpen:Bool
    var selectedLanguage: (UiLanguage) -> Void
    
    var body: some View {
        Menu{
            VStack{
                ForEach(UiLanguage.Companion().allLanguages, id: \.self.language.langCode){language in
                    LanguageDropDownItem(
                        language: language, onClick: {selectedLanguage(language)}
                    )
                }
            }
        }label: {
            HStack{
                SmallLanguageIcon(language: language)
                Text(language.language.langName).foregroundColor(.lightBlue)
                Image(systemName: isOpen ? "chevron.up":"chevron.down").foregroundColor(.lightBlue)
            }
        }
    }
}

struct LanguageDropDown_Previews: PreviewProvider {
    static var previews: some View {
        LanguageDropDown(language: UiLanguage(language: .german, imageName: "german"), isOpen: false, selectedLanguage: {Language in})
    }
}
