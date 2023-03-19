//
//  TextToSpeech.swift
//  iosApp
//
//  Created by Tharuka Gamage on 2023-03-05.
//  Copyright © 2023 orgName. All rights reserved.
//


import Foundation
import AVFoundation

struct TextToSpeech {
    
    private let synthesizer = AVSpeechSynthesizer()
    
    func speak(text: String, language: String) {
        let utterance = AVSpeechUtterance(string: text)
        utterance.voice = AVSpeechSynthesisVoice(language: language)
        utterance.volume = 1
        synthesizer.speak(utterance)
    }
}
