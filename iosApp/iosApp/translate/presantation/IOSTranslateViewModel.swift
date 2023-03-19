//
//  IOSTranslateViewModel.swift
//  iosApp
//
//  Created by Tharuka Gamage on 2023-02-13.
//  Copyright © 2023 orgName. All rights reserved.
//

import Foundation
import shared

extension TranslateScreen{
    @MainActor class IOSTranslateViewModel: ObservableObject{
        private var historyDatSource: HistoryDataSource
        private var translateUseCase: Translate
    
        
        private let viewModel: TranslateViewModel
        
        private var handle: DisposableHandle?
        
        init(historyDatSource: HistoryDataSource, translateUseCase: Translate) {
            self.historyDatSource = historyDatSource
            self.translateUseCase = translateUseCase
            self.viewModel = TranslateViewModel(translate: translateUseCase, historyDataSource: historyDatSource, coroutineScope: nil)
        }
        
        @Published var state: TranslateState = TranslateState(
            fromText: "",
            toText: nil,
            isTranslating: false,
            fromLanguage: UiLanguage(language: .english, imageName: "english"),
            toLanguage: UiLanguage(language: .german, imageName: "german"),
            isChoosingFromLanguage: false,
            isChoosingToLanguage: false,
            error: nil,
            history: [])
        
        func onEvent(event:TranslateEvent){
            self.viewModel.onTranslateEvent(event: event)
        }
        
        func StartObserving(){
            
            handle = viewModel.state.subscribe(onCollect:{ state in
                if let state = state{
                    self.state = state
                }
            })
        }
        
        func dispose(){
            handle?.dispose()
        }
        
    }
}
