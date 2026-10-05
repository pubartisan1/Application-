package com.amelle.neo3;
import android.app.Activity;
import android.os.Bundle;
import android.webkit.*;
import android.content.*;
import android.content.pm.PackageManager;
import android.Manifest;
import android.net.Uri;
import android.speech.*;
import android.speech.tts.*;
import java.util.*;
import java.net.*;
import java.io.*;
import java.util.concurrent.*;
import org.json.JSONObject;
public class MainActivity extends Activity {
 private WebView web; private TextToSpeech tts; private boolean ready=false;
 private SpeechRecognizer mic; private final ExecutorService pool=Executors.newFixedThreadPool(3);
 private String pendingSpeech="";
 private void js(String code){runOnUiThread(()->web.evaluateJavascript(code,null));}
 @Override public void onCreate(Bundle b){
  super.onCreate(b);web=new WebView(this);web.setBackgroundColor(0xff050912);
  WebSettings s=web.getSettings();s.setJavaScriptEnabled(true);s.setDomStorageEnabled(true);s.setAllowFileAccess(false);s.setAllowContentAccess(false);s.setMediaPlaybackRequiresUserGesture(true);
  web.addJavascriptInterface(new Bridge(),"AndroidNeo");
  web.setWebViewClient(new WebViewClient(){
   @Override public WebResourceResponse shouldInterceptRequest(WebView v,WebResourceRequest r){
    Uri u=r.getUrl();if(!"neo.local".equals(u.getHost()))return null;
    String p=u.getPath();if(p==null||p.equals("/"))p="/index.html";
    if(p.contains(".."))return new WebResourceResponse("text/plain","UTF-8",new ByteArrayInputStream(new byte[0]));
    String mime=p.endsWith(".js")?"application/javascript":p.endsWith(".css")?"text/css":p.endsWith(".json")||p.endsWith(".webmanifest")?"application/json":"text/html";
    try{return new WebResourceResponse(mime,"UTF-8",getAssets().open(p.substring(1)));}catch(IOException e){return new WebResourceResponse("text/plain","UTF-8",404,"Not found",Collections.emptyMap(),new ByteArrayInputStream(new byte[0]));}
   }
   @Override public boolean shouldOverrideUrlLoading(WebView v,WebResourceRequest r){
    Uri u=r.getUrl();if("neo.local".equals(u.getHost()))return false;
    if("https".equals(u.getScheme()))try{startActivity(new Intent(Intent.ACTION_VIEW,u));}catch(Exception ignored){}
    return true;
   }
  });
  web.setWebChromeClient(new WebChromeClient());setContentView(web);
  tts=new TextToSpeech(this,status->{ready=status==TextToSpeech.SUCCESS;if(ready){tts.setLanguage(Locale.FRENCH);tts.setSpeechRate(.94f);if(!pendingSpeech.isEmpty())speak(pendingSpeech);}});
  tts.setOnUtteranceProgressListener(new UtteranceProgressListener(){public void onStart(String id){js("window.neoSpeaking&&neoSpeaking(true)");}public void onDone(String id){js("window.neoSpeaking&&neoSpeaking(false)");}public void onError(String id){js("window.neoSpeaking&&neoSpeaking(false)");}});
  web.loadUrl("https://neo.local/index.html");
 }
 private void speak(String text){if(!ready){pendingSpeech=text;return;}pendingSpeech="";tts.stop();int max=TextToSpeech.getMaxSpeechInputLength()-100;for(int i=0;i<text.length();i+=max)tts.speak(text.substring(i,Math.min(text.length(),i+max)),i==0?TextToSpeech.QUEUE_FLUSH:TextToSpeech.QUEUE_ADD,null,"neo-"+i);}
 private void listen(){
  if(checkSelfPermission(Manifest.permission.RECORD_AUDIO)!=PackageManager.PERMISSION_GRANTED){requestPermissions(new String[]{Manifest.permission.RECORD_AUDIO},7);return;}
  if(!SpeechRecognizer.isRecognitionAvailable(this)){js("window.neoVoiceError&&neoVoiceError('Reconnaissance vocale indisponible sur ce téléphone. Écris à Néo.')");return;}
  if(mic!=null)mic.destroy();mic=SpeechRecognizer.createSpeechRecognizer(this);
  mic.setRecognitionListener(new RecognitionListener(){
   public void onReadyForSpeech(Bundle p){js("window.neoVoiceStatus&&neoVoiceStatus('Je t’écoute…')");}public void onBeginningOfSpeech(){} public void onRmsChanged(float r){}public void onBufferReceived(byte[] b){}public void onEndOfSpeech(){}
   public void onError(int e){js("window.neoVoiceError&&neoVoiceError('Le micro n’a pas reconnu de phrase. Réessaie ou écris ton message.')");}
   public void onResults(Bundle b){ArrayList<String> words=b.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);if(words!=null&&!words.isEmpty())js("window.neoTranscript&&neoTranscript("+JSONObject.quote(words.get(0))+")");}public void onPartialResults(Bundle b){}public void onEvent(int e,Bundle b){}
  });
  Intent i=new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);i.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);i.putExtra(RecognizerIntent.EXTRA_LANGUAGE,"fr-FR");i.putExtra(RecognizerIntent.EXTRA_PREFER_OFFLINE,true);mic.startListening(i);
 }
 @Override public void onRequestPermissionsResult(int r,String[] p,int[] g){super.onRequestPermissionsResult(r,p,g);if(r==7&&g.length>0&&g[0]==PackageManager.PERMISSION_GRANTED)listen();else js("window.neoVoiceError&&neoVoiceError('Accès au microphone refusé. Tu peux écrire.')");}
 public class Bridge {
  @JavascriptInterface public void speak(String text){runOnUiThread(()->MainActivity.this.speak(text));}
  @JavascriptInterface public void stop(){runOnUiThread(()->{tts.stop();js("window.neoSpeaking&&neoSpeaking(false)");});}
  @JavascriptInterface public void listen(){runOnUiThread(()->MainActivity.this.listen());}
  @JavascriptInterface public void fetch(String id,String address){pool.execute(()->{
   try{
    URL u=new URL(address);Set<String> hosts=new HashSet<>(Arrays.asList("api.open-meteo.com","feeds.bbci.co.uk","news.google.com","www.tsunami.gov"));
    if(!u.getProtocol().equals("https")||!hosts.contains(u.getHost())||u.getUserInfo()!=null||u.getPort()!=-1)throw new IOException("Source non autorisée");
    HttpURLConnection c=(HttpURLConnection)u.openConnection();c.setInstanceFollowRedirects(false);c.setConnectTimeout(12000);c.setReadTimeout(15000);c.setRequestProperty("User-Agent","Neo3/0.2");
    try{if(c.getResponseCode()!=200)throw new IOException("Source indisponible ("+c.getResponseCode()+")");ByteArrayOutputStream out=new ByteArrayOutputStream();try(InputStream in=c.getInputStream()){byte[] buf=new byte[8192];int n;while((n=in.read(buf))!=-1){out.write(buf,0,n);if(out.size()>1500000)throw new IOException("Flux trop volumineux");}}js("window.neoFetchResult("+JSONObject.quote(id)+","+JSONObject.quote(out.toString("UTF-8"))+",null)");}finally{c.disconnect();}
   }catch(Exception e){js("window.neoFetchResult("+JSONObject.quote(id)+",null,"+JSONObject.quote("Connexion impossible. Réessaie avec Internet.")+")");}
  });}
 }
 @Override public void onBackPressed(){if(web.canGoBack())web.goBack();else super.onBackPressed();}
 @Override protected void onDestroy(){if(mic!=null)mic.destroy();if(tts!=null){tts.stop();tts.shutdown();}pool.shutdownNow();web.destroy();super.onDestroy();}
}
