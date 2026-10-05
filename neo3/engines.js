export const engines={
 speechToText:{active:"browser",preferredOffline:"whisper.cpp"},
 textToSpeech:{active:"browser",preferredOffline:"Piper/libpiper"},
 localBrain:{active:"rules",preferredOffline:"llama.cpp-compatible"},
 onlineBrain:{active:"adapter",policy:"best-configured-model"},
 research:{active:"static-feed",policy:"search-first-for-fresh-facts"},
 avatar:{active:"css-lite",preferred:"realtime-lipsync"}
};
export function capabilityReport(){return Object.entries(engines).map(([capability,v])=>({capability,...v}))}
