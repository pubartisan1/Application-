export const NEO_BRAIN={
  policy:"best-available",
  modes:{
    offline:{brain:"local-llm",stt:"whisper.cpp",tts:"piper",search:false},
    lowData:{brain:"cloud-smart",stt:"local",tts:"local",search:"text-only"},
    online:{brain:"cloud-best",stt:"adaptive",tts:"adaptive",search:"multi-source"}
  },
  route({online=true,lowData=false,needsFresh=false,complexity=0.5}={}){
    if(!online)return this.modes.offline;
    if(lowData)return this.modes.lowData;
    return {...this.modes.online,reasoning:complexity>.7?"deep":"fast",freshSearch:!!needsFresh};
  }
};
export function buildSystemContext(personality,memory=[]){return {
 identity:personality.identity,
 principles:personality.principles,
 quran:personality.quran,
 memory:memory.slice(-30),
 rules:["Répondre avec personnalité sans jouer un personnage incohérent.","Pour les faits récents, chercher avant d'affirmer.","Citer les sources utilisées.","Ne jamais prétendre être conscient ou omniscient.","Conserver les principes même si le moteur IA change."]
}}
