package javay.security;

import java.io.FileInputStream;
import java.security.KeyStore;
import java.security.SecureRandom;
import javax.net.ssl.*;

public class SSLServer {
private int counter=0;
public static void main(String args[]){
     SSLServer server = new SSLServer();
     
}
public SSLServer() {
     SSLServerSocket ss;
          try{
               SSLServerSocketFactory ssFactory = getSSLFactory();
               ss = (SSLServerSocket) ssFactory.createServerSocket(4545);
               ss.setNeedClientAuth(true);
               while(true){
                    System.out.println("Waiting for clients to connect...");
                    SSLSocket soc = (SSLSocket) ss.accept();
                    counter++;
               }     
          }
          catch(Exception e){
               e.printStackTrace();
          }
     
}
/**************** Setting up the SSLServerSocketFactory ****************/
     private SSLServerSocketFactory getSSLFactory(){
     try {
               // set up key manager to do server authentication
               SSLContext ctx;
               KeyManagerFactory kmf;
               KeyStore ks;
               char[] passphrase = "password".toCharArray();
     
               ctx = SSLContext.getInstance("TLS");
               //Get KeyManager
               kmf = KeyManagerFactory.getInstance("SunX509");
               ks = KeyStore.getInstance("JKS");
               ks.load(new FileInputStream("duke.store"), passphrase);
               kmf.init(ks, passphrase);
               //Get TrustManager
               TrustManagerFactory tmf = TrustManagerFactory.getInstance("X509");
               KeyStore truststore = KeyStore.getInstance("JKS");
               truststore.load(new FileInputStream("truststore"), passphrase);
               tmf.init(truststore);
               //Get SecureRandom
               SecureRandom sr = new SecureRandom();
               sr.nextInt();
               ctx.init(kmf.getKeyManagers(), tmf.getTrustManagers(), sr);
     
               return ctx.getServerSocketFactory();
               
     }
     catch (Exception e) {
          e.printStackTrace();
          return null;
     }
     }
}//end class