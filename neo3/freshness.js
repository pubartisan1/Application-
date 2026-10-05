export const TTL={breaking:6,weather:12,news:48,local:72,investigation:720,story:8760};
export function isFresh(item,now=Date.now()){const h=TTL[item.kind]??48;return now-new Date(item.publishedAt).getTime()<h*3600000}
export function confidenceLabel(score){if(score>=.9)return"FAIT ÉTABLI";if(score>=.7)return"FORTEMENT ÉTAYÉ";if(score>=.45)return"INDICE";if(score>=.2)return"HYPOTHÈSE";return"INCONNU"}
