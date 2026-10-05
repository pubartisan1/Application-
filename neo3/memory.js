const KEY="neo3.memory.v1";
export function loadMemory(){try{return JSON.parse(localStorage.getItem(KEY)||"[]")}catch{return[]}}
export function remember(role,text){const m=loadMemory();m.push({role,text,at:Date.now()});localStorage.setItem(KEY,JSON.stringify(m.slice(-100)));return m}
export function clearMemory(){localStorage.removeItem(KEY)}
