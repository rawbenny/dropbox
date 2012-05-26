package com.ryan.dropbox.upload;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;

import com.dropbox.client2.DropboxAPI;
import com.dropbox.client2.DropboxAPI.Entry;
import com.dropbox.client2.session.AccessTokenPair;
import com.dropbox.client2.session.AppKeyPair;
import com.dropbox.client2.session.Session;
import com.dropbox.client2.session.WebAuthSession;

 
/**
 * 
 * @author Ryan
 */
public class Client {

	public Client(String fileName) {
		String key = "iwmvas6dycmcqmb";
		String secret = "9dpwmx7tyy1jki2";
		AppKeyPair pair = new AppKeyPair(key,secret);
		AccessTokenPair sourceAccess = new AccessTokenPair("2uw3urp32fvaohn","tro5gf2yjrm9op3");
		try {
			WebAuthSession session = new WebAuthSession(pair,Session.AccessType.DROPBOX,sourceAccess);
			DropboxAPI<?> sourceClient = new DropboxAPI<WebAuthSession>(session);
			
			File file = new File(fileName);
			FileInputStream fis = new FileInputStream(file);
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			byte [] buf = new byte[1024];
			int times = 1;
			for(int readNum; (readNum = fis.read(buf)) != -1;) {
			    bos.write(buf, 0, readNum);
			    System.out.println("read "+ readNum + "bytes * "+(times++) +",");
			}
			ByteArrayInputStream inputStream2 = new ByteArrayInputStream(bos.toByteArray());

			Entry newEntry = sourceClient.putFile("/Ryan/"+file.getName(), inputStream2,bos.size(), null, null);
			
			System.out.println("Done. \nSize: "+newEntry.size+"\nRevision of file: " + newEntry.rev + " " + newEntry.mimeType);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		if(args.length != 1){
			usage();
		}else{
			new Client(args[0]);	
		}
		
	}
	public static void usage(){
		System.out.println("Usage: java -jar ***.jar filename1");
	}
}
