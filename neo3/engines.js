export const engines={
  speechToText:{active:"browser",candidates:["whisper.cpp","browser"]},
  textToSpeech:{active:"browser",candidates:["Piper","browser"]},
  localBrain:{active:"rules",candidates:["llama.cpp","MediaPipe LLM","rules"]},
  avatar:{active:"css-lite",candidates:["MuseTalk","WebGL","css-lite"]},
  search:{active:"pending",candidates:["multi-source-web","rss","pending"]}
};
export function capabilityReport(){return Object.entries(engines).map(([k,v])=>({capability:k,active:v.active,upgrade:v.candidates.find(x=>x!==v.active)}));}
