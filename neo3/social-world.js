const KEY="neo3.social.v1";
const seed={neo:{mood:"curieux",energy:.82,place:"Observatoire",lastSeen:Date.now()},friends:[
{id:"orion",name:"Orion",nature:"ami numérique",traits:["inventif","taquin","astronomie"],bond:0.62,createdByNeo:true},
{id:"eden",name:"Éden",nature:"ami numérique",traits:["calme","philosophe","musique"],bond:0.48,createdByNeo:true}
],moments:[]};
export function socialWorld(){try{return JSON.parse(localStorage.getItem(KEY)||"null")||seed}catch{return seed}}
export function saveWorld(w){localStorage.setItem(KEY,JSON.stringify(w));return w}
export function neoMoment(kind,text){let w=socialWorld();w.moments.push({kind,text,at:Date.now()});w.moments=w.moments.slice(-80);return saveWorld(w)}
export function createFriend({name,traits=[],nature="ami numérique"}={}){let w=socialWorld();let id=(name||"ami").toLowerCase().replace(/[^a-z0-9]+/g,"-");if(!w.friends.some(f=>f.id===id))w.friends.push({id,name,nature,traits,bond:.25,createdByNeo:true});return saveWorld(w)}
export function simulateLife(){let w=socialWorld(),hour=new Date().getHours();w.neo.place=hour<7?"Appartement de Néo":hour<12?"Atelier":hour<18?"Bibliothèque panoramique":"Observatoire";w.neo.energy=Math.max(.2,Math.min(1,w.neo.energy+(Math.random()-.48)*.08));return saveWorld(w)}
