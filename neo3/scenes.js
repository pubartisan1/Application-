export const SCENES=[
{id:"observatory",name:"Observatoire orbital",sky:"aurora",description:"Baies vitrées, pluie lointaine, Terre lumineuse et cartes célestes."},
{id:"zanzibar",name:"Terrasse de Zanzibar",sky:"sunset",description:"Océan Indien, palmiers dans le vent, lanternes et horizon après la pluie."},
{id:"library",name:"Bibliothèque infinie",sky:"night",description:"Rayonnages vertigineux, cartes anciennes, lumière chaude et table d'enquête."},
{id:"workshop",name:"Atelier de Néo",sky:"neon",description:"Écrans translucides, prototypes robotiques et mur technologique vivant."}
];
export function sceneForTime(d=new Date()){let h=d.getHours();return h<6?SCENES[0]:h<12?SCENES[2]:h<18?SCENES[3]:SCENES[1]}
