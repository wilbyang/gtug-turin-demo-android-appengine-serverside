package org.gtug.torino.bootcamp.task;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MessagesTask extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static final Logger log = Logger.getLogger(MessagesTask.class.getName());

	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		log.info("[MessagesTask::doPost]  - START");
		try {

			
			String androidId = req.getParameter("androidId");
			String photoId = req.getParameter("photoId");
			log.info("[MessagesTask::doPost] - androidId: " + androidId);
			log.info("[MessagesTask::doPost] - photoId: " + photoId);

			
			// build the notificaiton message
			String postString = "";
			postString += "registration_id=" + androidId;
			
			// collapse_key is the id of the messages
			postString += "&collapse_key=0";
			
			String message = "Photo uploaded successfully - photoId: " + photoId;
			postString +="&data.payload="+URLEncoder.encode(message, "UTF-8");
			
			log.info("[MessagesTask::doPost] - postString: " + postString);

			byte[] postData = postString.getBytes("UTF-8");
			 
			// Connect to c2dm server
			URL c2dmURL = new URL("https://android.clients.google.com/c2dm/send");

			HttpURLConnection conn = (HttpURLConnection) c2dmURL.openConnection();
			log.info("[MessagesTask::doPost] - connection open");
			conn.setDoOutput(true);
			conn.setUseCaches(false);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
			conn.setRequestProperty("Content-Length", Integer.toString(postData.length));
			conn.setRequestProperty("Authorization", "GoogleLogin auth=" + getAuthToken());

			OutputStream out = conn.getOutputStream();
			log.info("[MessagesTask::doPost] - outputStream open");
			
			out.write(postData);
			log.info("[MessagesTask::doPost] - data posted");

			out.close();
			log.info("[MessagesTask::doPost] - outputStream close");

			int responseCode = conn.getResponseCode();
			log.fine("responseCode: " + responseCode);
		} catch (Exception e) {
			log.severe("[MessagesTask::doPost]  - ERROR " + e.getMessage());
			e.printStackTrace();
		} finally {
			log.info("[MessagesTask::doPost]  - END");

		}
	}

	private String getAuthToken() throws MalformedURLException, IOException {
		String authToken = null;

		String user = "email@gmail.com";
		String pwd = "****";
		String accountType = "GOOGLE";
		String source = "appName";
		String service = "ac2dm";

		String url = "https://www.google.com/accounts/ClientLogin?";
		url += "user=" + user;
		url += "pwd=" + pwd;
		url += "accountType=" + accountType;
		url += "source=" + source;
		url += "service=" + service;

		BufferedReader reader = new BufferedReader(new InputStreamReader((new URL(url)).openStream()));
		String line;

		while ((line = reader.readLine()) != null) {
			if (line.startsWith("Auth=")) {
				authToken = line.substring("Auth=".length());
				break;
			}
		}
		reader.close();
		return authToken;
	}
}
