
import streamlit as st
import requests
import os

st.set_page_config(page_title="VotreIAPro+", layout="centered")

st.title("👩‍⚖️👨‍⚕️ VotreIAPro+ – Assistant IA Multimétier")
st.write("Posez une question juridique ou médicale et recevez une réponse claire, avec intelligence artificielle.")

metier = st.selectbox("Choisissez votre assistant :", ["Juriste", "Médecin"])
question = st.text_area("Votre question :", height=150)

prompts = {
    "Juriste": "Tu es un juriste expert francophone. Tu aides l’utilisateur à comprendre le droit français (visa, contrat, divorce, logement...). Sois clair, empathique, sans jargon.",
    "Médecin": "Tu es un médecin généraliste compétent. Tu donnes des conseils simples basés sur les symptômes, sans poser de diagnostic officiel."
}

api_key = os.getenv("openai_key")

if not api_key:
    st.error("Clé API OpenAI manquante. Veuillez configurer la variable d’environnement 'openai_key'.")
else:
    if st.button("Envoyer") and question:
        with st.spinner("Réflexion en cours..."):
            headers = {
                "Content-Type": "application/json",
                "Authorization": f"Bearer {api_key}"
            }
            data = {
                "model": "gpt-3.5-turbo",
                "messages": [{"role": "user", "content": prompts[metier] + "\n\nQuestion : " + question}]
            }
            try:
                res = requests.post("https://api.openai.com/v1/chat/completions", headers=headers, json=data)
                result = res.json()
                answer = result["choices"][0]["message"]["content"]
                st.success("Réponse :")
                st.write(answer)
            except Exception as e:
                st.error(f"Erreur lors de l'appel à l'API : {e}")
