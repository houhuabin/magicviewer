
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package test;

/**
 *
 * @author lenovo
 */
import   org.apache.commons.net.ftp.FTPClient;
  import   org.apache.commons.net.ftp.FTPReply; 

  import   java.io.*;
 
  public   class   MyFTP   {
          private   FTPClient   ftp   =   new   FTPClient();

          public   MyFTP()   {
//                  ftp.addProtocolCommandListener(new   PrintCommandListener(new   PrintWriter(System.out)));
          }

          public   boolean   connect(String   hostname,   int   port,   String   username,   String   password)   throws   IOException   {
                  ftp.connect(hostname,   port);
                  if   (FTPReply.isPositiveCompletion(ftp.getReplyCode()))   {
                          if   (ftp.login(username,   password))   {
                                  return   true;
                          }
                  }
                  disconnect();
                  return   false;
          }

          public   boolean   download(String   remote,   String   local)   throws   IOException   {
                  ftp.enterLocalPassiveMode();
                  boolean   result;
                  File   f   =   new   File(local);
                  if   (f.exists())   {
                          OutputStream   out   =   new   FileOutputStream(f,   true);
                          ftp.setRestartOffset(f.length());
                          result   =   ftp.retrieveFile(remote,   out);
                          out.close();
                  }   else   {
                          OutputStream   out   =   new   FileOutputStream(f);
                          result   =   ftp.retrieveFile(remote,   out);

                          out.close();
                  }
                  return   result;
          }

          public   void   disconnect()   throws   IOException   {
                  if   (ftp.isConnected())   {
                          ftp.disconnect();
                  }
          }
  }
