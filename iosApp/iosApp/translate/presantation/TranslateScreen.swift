//
//  TranslateScreen.swift
//  iosApp
//
//  Created by Tharuka Gamage on 2023-02-13.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI
import shared

struct TranslateScreen: View {
    
    private let historyDatSource: HistoryDataSource
    private let translateUseCase: Translate
    private let parser: IOSVoiceToTextParser = IOSVoiceToTextParser()
    @ObservedObject var viewModel: IOSTranslateViewModel
    
    init(historyDatSource: HistoryDataSource, translateUseCase: Translate) {
        self.historyDatSource = historyDatSource
        self.translateUseCase = translateUseCase
        self.viewModel = IOSTranslateViewModel(historyDatSource: historyDatSource, translateUseCase: translateUseCase)
    }
    
    var body: some View {
        ZStack{
            List(){
                HStack(alignment: .center){
                    LanguageDropDown(language: viewModel.state.fromLanguage, isOpen: viewModel.state.isChoosingFromLanguage){ language in
                        viewModel.onEvent(event: TranslateEvent.ChooseFromLanguage(language: language))
                    }
                    Spacer()
                    SwapLanguageButton{
                        viewModel.onEvent(event: TranslateEvent.SwapLanguages())
                    }
                    Spacer()
                    LanguageDropDown(language: viewModel.state.toLanguage, isOpen: viewModel.state.isChoosingToLanguage){language in
                        viewModel.onEvent(event: TranslateEvent.ChooseToLanguage(language: language))
                    }
                }
                .listRowSeparator(.hidden)
                .listRowBackground(Color.background)
                
                TranslateTextField(
                    fromText: Binding(
                    get: {viewModel.state.fromText},
                    set: {value in viewModel.onEvent(event: TranslateEvent.ChangeTranslationText(text: value))}
                ),
                    toText: viewModel.state.toText,
                    isTranslating: viewModel.state.isTranslating,
                    fromLanguage: viewModel.state.fromLanguage,
                    toLanguage: viewModel.state.toLanguage,
                    onTranslateEvent: { event in viewModel.onEvent(event: event)}
                )
                .listRowSeparator(.hidden)
                .listRowBackground(Color.background)
                
                if !viewModel.state.history.isEmpty{
                    
                    Text("History").font(.title.weight(.bold))
                        .listRowSeparator(.hidden)
                        .listRowBackground(Color.background)
                }
                
                ForEach(viewModel.state.history, id: \.self.id){historyItem in
                    TranslateHistoryItem(item: historyItem, onClick: {viewModel.onEvent(event: TranslateEvent.SelectHistoryItem(item: historyItem))})
                }
                .listRowSeparator(.hidden)
                .listRowBackground(Color.background)
                
            }
            .listStyle(.plain)
            .buttonStyle(.plain)
            
            VStack{
                Spacer()
                NavigationLink(destination: VoiceToTextScreen(
                    onResult: { result in
                        viewModel.onEvent(event: TranslateEvent.SubmitVoiceResult(result: result))
                    },
                    parser: parser,
                    languageCode: viewModel.state.fromLanguage.language.langCode)){
                    ZStack{
                        Circle()
                            .foregroundColor(.primaryColor)
                            .padding()
                        Image(uiImage: UIImage(named: "mic")!)
                            .foregroundColor(.onPrimary)
                    }
                    .frame(maxWidth: 100, minHeight: 100)
                }
            }
        }
        .onAppear{viewModel.StartObserving()}
        .onDisappear{viewModel.dispose()}
    }
}
        

//struct TranslateScreen_Previews: PreviewProvider {
//    static var previews: some View {
//        TranslateScreen(
//
//        )
//    }
//}
