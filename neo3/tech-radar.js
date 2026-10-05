export const TECH_RADAR=[
 {capability:"speech-to-text",current:"browser",target:"whisper.cpp",status:"planned"},
 {capability:"text-to-speech",current:"browser",target:"Piper/libpiper",status:"planned"},
 {capability:"local-brain",current:"rules",target:"llama.cpp compatible model",status:"planned"},
 {capability:"online-brain",current:"adapter",target:"best configured cloud model",status:"adapter-ready"},
 {capability:"research",current:"static feed",target:"multi-source search + provenance",status:"adapter-ready"},
 {capability:"avatar",current:"CSS animation",target:"real-time lip sync / 3D",status:"planned"}
];
export function upgrades(){return TECH_RADAR.filter(x=>x.status!=="active")}
