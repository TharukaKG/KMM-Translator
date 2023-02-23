//
//  IOSVoiceToTextViewModel.swift
//  iosApp
//
//  Created by Tharuka Gamage on 2023-02-20.
//  Copyright © 2023 orgName. All rights reserved.
//

import Foundation
import shared
import Combine

@MainActor class IOSVoiceToTextViewModel: ObservableObject{
    private var parser: any VoiceToTextParser
    private let languageCode: String
    
    private let viewModel: VoiceToTextViewModel
    @Published var state = VoiceToTextState(powerRatios: [], spokenText: "", canRecord: false, recordError: nil, displayState: nil)
    private var handle: DisposableHandle?
    
    init(parser: VoiceToTextParser, languageCode: String){
        self.parser = parser
        self.languageCode = languageCode
        self.viewModel = VoiceToTextViewModel(parser: self.parser, coroutineScope: nil)
        self.viewModel.onEvent(viceToTextEvent: VoiceToTextEvent.PermissionResult(isGranted: true, isPermanentlyDeclined: false))
    }
    
    func onEvent(event: VoiceToTextEvent){
        viewModel.onEvent(viceToTextEvent: event)
    }
    
    func startObserving(){
        handle = viewModel.state.subscribe{ [weak self] state in
            if let state{
                self?.state = state
            }
        }
    }
    
    func dispose(){
        handle?.dispose()
        onEvent(event: VoiceToTextEvent.Reset())
    }
    
}
