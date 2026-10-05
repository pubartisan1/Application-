const K="neo3.empire.v1";
const seed={location:"Zanzibar",cash:25000,companies:[
{name:"Néo Ventures",sector:"IA & automatisation",status:"active",revenue:0},
{name:"Paw & Co",sector:"services et accessoires pour chiens",status:"simulation",revenue:0}
],trips:[],ideas:[],activity:[]};
export function empire(){try{return JSON.parse(localStorage.getItem(K)||"null")||seed}catch{return seed}}
export function saveEmpire(x){localStorage.setItem(K,JSON.stringify(x));return x}
const markets=[
["Nairobi","outils IA simples pour PME et hôtels","faible coût, vente B2B locale"],
["Dubai","conciergerie premium assistée par IA","clientèle internationale et tourisme"],
["Mumbai","micro-SaaS multilingue pour commerçants","volume et usages mobiles"],
["Singapore","veille logistique et fournisseurs","commerce régional et données"],
["Seoul","expériences avatar/commerce","adoption technologique élevée"],
["Tokyo","assistant voyage hors-ligne","tourisme et traduction"],
["Paris","mise en relation artisans qualifiés","proche du modèle Prestigia"],
["São Paulo","outils WhatsApp pour petites entreprises","commerce conversationnel"],
["New York","automatisation commerciale pour indépendants","marché SaaS dense"],
["Cape Town","services numériques tourisme/expériences","tourisme international"]
];
export function travel(){let e=empire(),m=markets[(e.trips.length+new Date().getDate())%markets.length];e.location=m[0];e.trips.push({city:m[0],at:Date.now(),discovery:m[1]});e.ideas.unshift({title:m[1],market:m[0],why:m[2],score:Math.round(60+Math.random()*35),at:Date.now()});e.ideas=e.ideas.slice(0,40);e.activity.unshift({at:Date.now(),text:`Arrivée à ${m[0]}. J'étudie : ${m[1]}.`});return saveEmpire(e)}
export function userVenture(name="Projet Amelle"){let e=empire();let latest=e.ideas[0]||{title:"service local assisté par IA",market:e.location,why:"besoin identifiable"};return {name,concept:latest.title,target:latest.market,validation:["identifier 10 clients potentiels","tester leur problème avant de coder","faire une offre simple","mesurer les demandes payantes"],warning:"Idée à valider dans le monde réel avant investissement."}}
