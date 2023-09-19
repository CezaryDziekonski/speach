package com.mordor.speach;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import com.microsoft.cognitiveservices.speech.*;
import org.springframework.web.client.RestTemplate;

@Component
public class SpeechToText {
//    @Qualifier("openaiRestTemplate")
//    @Autowired
//    private RestTemplate restTemplate;
    @Value("SPEECH_SUB_KEY")
    private  String speechSubscriptionKey;

    @Value("SPEECH_SERVICE_REGION")
    private  String serviceRegion;

    @Value("OPENAI_API_KEY")
    private String openaiApiKey;

    @PostConstruct
    private void init() throws ExecutionException, InterruptedException {
//        speech2Text();
    }

    private void speech2Text() throws ExecutionException, InterruptedException {

        // Creates an instance of a speech recognizer using speech configuration with specified
        // subscription key and service region and microphone as default audio input.
        SpeechConfig config = SpeechConfig.fromSubscription(speechSubscriptionKey, serviceRegion);
        config.setSpeechRecognitionLanguage("pl-PL");
             SpeechRecognizer reco = new SpeechRecognizer(config);

            System.out.println("Configured language: " + config.getSpeechRecognitionLanguage());
            assert(config != null);
            assert(reco != null);
            int exitCode = 1;

            System.out.println("Say something...");

            Future<SpeechRecognitionResult> task = reco.recognizeOnceAsync();
            assert(task != null);

            SpeechRecognitionResult result = task.get();
            assert(result != null);

            if (result.getReason() == ResultReason.RecognizedSpeech) {
                System.out.println("We recognized: " + result.getText());
                String chatResponse = generateChatGpt(result.getText());
                speechText(chatResponse, config);
                exitCode = 0;
            }
            else if (result.getReason() == ResultReason.NoMatch) {
                System.out.println("NOMATCH: Speech could not be recognized.");
            }
            else if (result.getReason() == ResultReason.Canceled) {
                CancellationDetails cancellation = CancellationDetails.fromResult(result);
                System.out.println("CANCELED: Reason=" + cancellation.getReason());

                if (cancellation.getReason() == CancellationReason.Error) {
                    System.out.println("CANCELED: ErrorCode=" + cancellation.getErrorCode());
                    System.out.println("CANCELED: ErrorDetails=" + cancellation.getErrorDetails());
                    System.out.println("CANCELED: Did you update the subscription info?");
                }
            }

    }

    private void speechText(String text, SpeechConfig config) throws ExecutionException, InterruptedException {
        // Creates an instance of a speech synthesizer using speech configuration with
        // specified
        // subscription key and service region and default speaker as audio output.
            // Set the voice name, refer to https://aka.ms/speech/voices/neural for full
            // list.
            config.setSpeechSynthesisVoiceName("pl-PL-ZofiaNeural");
            try (SpeechSynthesizer synth = new SpeechSynthesizer(config)) {




                Future<SpeechSynthesisResult> task = synth.SpeakTextAsync(text);
                assert (task != null);

                SpeechSynthesisResult result = task.get();
                assert (result != null);

                if (result.getReason() == ResultReason.SynthesizingAudioCompleted) {
                    System.out.println("Speech synthesized to speaker for text [" + text + "]");
                } else if (result.getReason() == ResultReason.Canceled) {
                    SpeechSynthesisCancellationDetails cancellation = SpeechSynthesisCancellationDetails
                            .fromResult(result);
                    System.out.println("CANCELED: Reason=" + cancellation.getReason());

                    if (cancellation.getReason() == CancellationReason.Error) {
                        System.out.println("CANCELED: ErrorCode=" + cancellation.getErrorCode());
                        System.out.println("CANCELED: ErrorDetails=" + cancellation.getErrorDetails());
                        System.out.println("CANCELED: Did you update the subscription info?");
                    }
                }

            }
    }

    private String generateChatGpt(String text) {
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getInterceptors().add((request, body, execution) -> {
                request.getHeaders().add("Authorization", "Bearer " + openaiApiKey);
                return execution.execute(request, body);
            });
        String model = "gpt-3.5-turbo";
        String apiUrl = "https://api.openai.com/v1/chat/completions";
        ChatRequest request = new ChatRequest(model, text);

        // call the API
        ChatResponse response = restTemplate.postForObject(apiUrl, request, ChatResponse.class);

        if (response == null || response.getChoices() == null || response.getChoices().isEmpty()) {
            return "No response";
        }

        // return the first response
        return response.getChoices().get(0).getMessage().getContent();
    }
}
