package com.Campus.BulletinBoard;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import android.os.AsyncTask;


/**
 * Async task to handle all network tasks. Constructor takes context.
 * @author Johnil
 *
 */

public class NetworkUtility extends AsyncTask<Void, Void, String> {
	
	private URL url;
	private BufferedReader reader;
	private OutputStreamWriter wr;
	private HttpURLConnection conn;
	private String data;
	private GeneralCallback onTaskCompleted;
	private boolean UploadImage = false;
	private boolean NetworkActivity = true;
	private String[] UploadFiles;
	private Context mContext;
	
	public NetworkUtility(Context c){
		mContext = c;
	}
	
	/**
	 * Method for logging in a user
	 * @param I {@link Map<String, String>} containing a Username and Password, labeled as "username" and "password" respectively.
	 * @param o Instance of {@link GeneralCallback}
	 * @throws MalformedURLException
	 * @throws UnsupportedEncodingException
	 */
	
	public void LogIn(Map<String, String> I, GeneralCallback o){
		onTaskCompleted = o;
		try {
		url = new URL("http://www.digiqlik.binhoster.com/LogIn.php");
		data = URLEncoder.encode("username", "UTF-8")
				+ "=" + URLEncoder.encode((String)I.get("Username"), "UTF-8");
		data+= "&" + URLEncoder.encode("password", "UTF-8")
				+ "=" + URLEncoder.encode((String)I.get("Password"), "UTF-8");
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * Method for creating a new user
	 * @param I {@link Map<String, String} containing Username, Password, Email, and School of the new user
	 * @param o Instance of {@link GeneralCallback}
	 */
	
	
	
	public void UploadNewUser(Map<String, String> I, GeneralCallback o){
		onTaskCompleted = o;
		try {
		url = new URL("http://www.digiqlik.binhoster.com/SignUp.php");
		data = URLEncoder.encode("username", "UTF-8")
				+ "=" + URLEncoder.encode((String)I.get("Username"), "UTF-8");
		data+= "&" + URLEncoder.encode("password", "UTF-8")
				+ "=" + URLEncoder.encode((String)I.get("Password"), "UTF-8");
		data+= "&" + URLEncoder.encode("email", "UTF-8")
				+ "=" + URLEncoder.encode((String)I.get("Email"), "UTF-8");
		data+= "&" + URLEncoder.encode("school", "UTF-8")
				+ "=" + URLEncoder.encode((String)I.get("School"), "UTF-8");
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * Method for uploading a new BulletinItem. 
	 * @param I {@link HashMap<String, String>} containing various post information.
	 * @param o Instance of {@link GeneralCallback}
	 * @param B String[] containing paths to the selected images
	 */
	
	
	
	public void UploadNewDigiPost(HashMap<String, String> I, GeneralCallback o, String[] B){
		onTaskCompleted = o;
		try {
		url = new URL("http://www.digiqlik.binhoster.com/NewPost.php");
		data = URLEncoder.encode("title", "UTF-8")
				+ "=" + URLEncoder.encode(GetPostInfo(I, "Title"), "UTF-8");
		data+= "&" + URLEncoder.encode("price", "UTF-8")
				+ "=" + URLEncoder.encode(GetPostInfo(I, "Price"), "UTF-8");
		data+= "&" + URLEncoder.encode("description", "UTF-8")
				+ "=" + URLEncoder.encode(GetPostInfo(I, "Description"), "UTF-8");
		data+= "&" + URLEncoder.encode("date", "UTF-8")
				+ "=" + URLEncoder.encode(GetPostInfo(I, "Date"), "UTF-8");
		data+= "&" + URLEncoder.encode("time", "UTF-8")
				+ "=" + URLEncoder.encode(GetPostInfo(I, "Time"), "UTF-8");
		data+= "&" + URLEncoder.encode("maximumoccupancy", "UTF-8")
				+ "=" + URLEncoder.encode(GetPostInfo(I, "MaximumOccupancy"), "UTF-8");
		data+= "&" + URLEncoder.encode("subject", "UTF-8")
				+ "=" + URLEncoder.encode(GetPostInfo(I, "Subject"), "UTF-8");
		data+= "&" + URLEncoder.encode("startlocation", "UTF-8")
				+ "=" + URLEncoder.encode(GetPostInfo(I, "StartLocation"), "UTF-8");
		data+= "&" + URLEncoder.encode("destination", "UTF-8")
				+ "=" + URLEncoder.encode(GetPostInfo(I, "Destination"), "UTF-8");
		data+= "&" + URLEncoder.encode("type", "UTF-8")
				+ "=" + URLEncoder.encode(GetPostInfo(I, "Type"), "UTF-8");
		data+= "&" + URLEncoder.encode("user", "UTF-8")
				+ "=" + URLEncoder.encode(GetPostInfo(I, "User"), "UTF-8");
		data+= "&" + URLEncoder.encode("DateStamp", "UTF-8")
				+ "=" + URLEncoder.encode(GetPostInfo(I, "DateStamp"), "UTF-8");
		data+= "&" + URLEncoder.encode("ViewCount", "UTF-8")
				+ "=" + URLEncoder.encode("0", "UTF-8");
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		for(String S : B){
			if (S!=null){
				UploadImage = true;
			}
		}
		if (UploadImage){
			UploadFiles = B;
		}
	}
	
	public void UploadImage(String[] S, GeneralCallback o){
		UploadImage = true;
		UploadFiles = S;
		onTaskCompleted = o;
	}
	
	/**
	 * Method for adding a new User to send messages to.
	 * @param Username Username to send a message to
	 * @param o Instance of {@link GeneralCallback}
	 */
	
	
	
	public void NewMessage(String Username, GeneralCallback o){
		onTaskCompleted = o;
		try {
		url = new URL("http://www.digiqlik.binhoster.com/NewMessage.php");
		data = URLEncoder.encode("username", "UTF-8")
				+ "=" + URLEncoder.encode(Username, "UTF-8");
		data+= "&" + URLEncoder.encode("password", "UTF-8")
				+ "=" + URLEncoder.encode(Username, "UTF-8");
		System.out.println(Username + " This is the Username");
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * Method for retrieving BulletinItems
	 * @param I {@link HashMap<String, String>} containing 'Location' and 'Pattern' specifying which posts to get
	 * @param o
	 */
	
	
	
	public void DownloadNewDigiPost(HashMap<String, String> I, GeneralCallback o){
		onTaskCompleted = o;
		try {
		url = new URL("http://www.digiqlik.binhoster.com/DownloadPost.php");
		data = URLEncoder.encode("Location", "UTF-8")
				+ "=" + URLEncoder.encode(I.get("Location"), "UTF-8");
		data+= "&" + URLEncoder.encode("Pattern", "UTF-8")
				+ "=" + URLEncoder.encode(I.get("Pattern"), "UTF-8");
		System.out.println("Location is" + I.get("Location"));
		System.out.println("Pattern is" + I.get("Pattern"));
		System.out.println(data.toString());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	
	
	
	@Override
	protected String doInBackground(Void... params) {
		if (NetworkActivity) {
		ConnectivityManager CM = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
		State M = CM.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState();
		State W = CM.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState();
		if ((M == NetworkInfo.State.CONNECTED || M == NetworkInfo.State.CONNECTING) | (W == NetworkInfo.State.CONNECTED || W == NetworkInfo.State.CONNECTING)){
		try {
			conn = (HttpURLConnection) url.openConnection();
			conn.setDoInput(true);
			conn.setRequestMethod("POST");
			conn.setDoOutput(false);
			conn.setInstanceFollowRedirects(false);
			conn.setRequestProperty("User-Agent","");
			wr = new OutputStreamWriter(conn.getOutputStream());
			if (UploadImage){
				for (int x = 0; x < UploadFiles.length; x++){
					if (UploadFiles[x]!=null){
						System.out.println(new FileUploader().UploadFile(UploadFiles[x], x) + "This is the result");
					}
				}
			}
			System.out.println(data + "This is the data");
			wr.write(data);
			wr.flush();
			wr.close();
			reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return GetJSON(reader);
		}
		else{
			JSONObject jsonObject = new JSONObject();
			try {
				jsonObject.put("Response", "No Network Connection");
				jsonObject.put("Error", 3);
				jsonObject.put("Message", "No Network Connection");
			} catch (JSONException e) {
				e.printStackTrace();
			}
			return jsonObject.toString();
		}
	} else {
		if (UploadImage){
			JSONObject jsonObject = new JSONObject();
			for (int x = 0; x<UploadFiles.length; x++){
				try {
					jsonObject.put("Link", new FileUploader().UploadFile(UploadFiles[x], x));	
				} catch (IOException e) {
					e.printStackTrace();
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
			return jsonObject.toString();
		} else {
			JSONObject jsonObject = new JSONObject();
			try {
				jsonObject.put("Upload", "Image");
			} catch (JSONException e) {
				e.printStackTrace();
			}
			return jsonObject.toString();
		}
	}
	}
	
	@Override
	public void onPostExecute(String v){
		final HashMap<String, String> Response = new HashMap<String, String>();
		try {
			System.out.println(v + "This is the response");
			JSONObject jsonObject = new JSONObject(v);
			Response.put("Response", (String) jsonObject.get("Response"));
			Response.put("Error", Integer.toString((Integer)jsonObject.get("Error")));
			Response.put("Message", jsonObject.getString("Message"));
			Response.put("Username", jsonObject.getString("Username"));
			Response.put("Password", jsonObject.getString("Password"));
			Response.put("Email", jsonObject.getString("Email"));
			Response.put("School", jsonObject.getString("School"));
			Response.put("Link", jsonObject.getString("Link"));
			System.out.println(jsonObject.get("Error"));
		} catch (JSONException e) {
			System.out.println(v);
			e.printStackTrace();
		}
		onTaskCompleted.onTaskCompleted(Response);
	}
	
	
	/**
	 * Method for converting the response from the server into JSON format
	 * @param reader {@link BufferedReader} Reader retrieved from the connection to the server
	 * @return
	 */
	
	
	
	private String GetJSON(BufferedReader reader){
		String line = null;
		StringBuilder sb = new StringBuilder();
		try {
			while ( (line = reader.readLine()) != null )
			{
			    sb.append(line + "\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(sb.toString()+":" + " this is sb.tostring()");
		return sb.toString();
	}
	
	private String GetPostInfo(HashMap<String, String> H, String s){
		if (H.get(s) != null){
			return H.get(s);
		}
		else{
			return "";
		}
	}
	
	/**
	 * Class for uploading file. Used to upload the images of BulletinItems. 
	 * @author Johnil
	 *
	 */
	
	private class FileUploader{
		
		private HttpURLConnection Conn;
		private final String LineEnd = "\r\n";
		private final String TwoHyphens = "--";
		private final String Boundary = "*****";
		private int BytesRead, BytesAvailable, BufferSize;
		private byte[] Buffer;
		private final int MaxBufferSize = 1 * 1024 * 1024;
		
		public String UploadFile(String Path, int i) throws IOException{
			int Index = i + 1;
			String FileName = UUID.randomUUID().toString() + ".png";
			data += "&" + URLEncoder.encode("Image" + Index, "UTF-8") + "="
					+ URLEncoder.encode("http://www.digiqlik.binhoster.com/Images/" + FileName, "UTF-8");
			FileInputStream InputStream = new FileInputStream(new File(Path));
			URL url = new URL("http://www.digiqlik.binhoster.com/UploadFile.php");
			Conn = (HttpURLConnection) url.openConnection();
			Conn.setDoInput(true);
			Conn.setDoOutput(true);
			Conn.setUseCaches(false);
			Conn.setRequestMethod("POST");
			Conn.setRequestProperty("Connection", "Keep-Alive");
			Conn.setRequestProperty("ENCTYPE", "multipart/form-data");
			Conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + Boundary);
			Conn.setRequestProperty("uploaded_file", FileName);
			Conn.connect();
			DataOutputStream dos = new DataOutputStream(Conn.getOutputStream());
			dos.writeBytes(TwoHyphens + Boundary + LineEnd);
			dos.writeBytes("Content-Disposition: form-data; name=\"uploaded_file\";filename=\"" + FileName + "\"" + LineEnd);
			dos.writeBytes(LineEnd);
			
			//Create Buffer
			BytesAvailable = InputStream.available();
			BufferSize = Math.min(BytesAvailable, MaxBufferSize);
			Buffer = new byte[BufferSize];
			System.out.println(InputStream.available());
			System.out.println(Path);
			System.out.println(BufferSize);
			BytesRead = InputStream.read(Buffer, 0, BufferSize);
			
			while(BytesRead > 0){
				dos.write(Buffer);
				BytesAvailable = InputStream.available();
				BufferSize = Math.min(BytesAvailable, MaxBufferSize);
				BytesRead = InputStream.read(Buffer, 0, BufferSize);
			}
			dos.writeBytes(LineEnd);
			dos.writeBytes(TwoHyphens + Boundary + TwoHyphens + LineEnd);
			dos.flush();
			dos.close();
			System.out.println(Conn.getResponseCode() + " : Response Code");
			System.out.println(Conn.getResponseMessage());
			return data;
		}
		
		String toString(BufferedReader r) throws IOException{
		String line;
		StringBuilder sb = new StringBuilder();
		while((line = r.readLine())!=null){
			sb.append(line);
		}
		r.close();
		return sb.toString();
		}
	}

}
