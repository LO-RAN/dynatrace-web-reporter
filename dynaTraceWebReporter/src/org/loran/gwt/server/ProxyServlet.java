package org.loran.gwt.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;

public class ProxyServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// private final String USER_AGENT = "Mozilla/5.0";

	String user;
	String pwd;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException {

		String strUrl = request.getParameter("url");
		user = request.getParameter("user");
		pwd = request.getParameter("pwd");

		System.out.println("Requested URL is : " + strUrl);

		PrintWriter out;

		try {
			out = response.getWriter();
			URL url;
			url = new URL(strUrl.replaceAll(" ", "%20"));
			String authString = user + ":" + pwd;
			byte[] authEncBytes = Base64.encodeBase64(authString.getBytes());
			String authStringEnc = new String(authEncBytes);
			HttpURLConnection connection;
			connection = (HttpURLConnection) url.openConnection();
			// connection.setRequestMethod("GET");
			connection.setUseCaches(false);

			// connection.setDoOutput(true);
			connection.setRequestProperty("Authorization", "Basic "
					+ authStringEnc);
			// act like a browser
			// connection.setRequestProperty("User-Agent", USER_AGENT);
			// connection.setRequestProperty("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
			// connection.setRequestProperty("Accept-Language","en-US,en;q=0.5");

			// wait max 30s (depending on time range, data can take a while to come back)
			connection.setConnectTimeout(30000);
			
			connection.connect();
			if (connection.getResponseCode() == 200) {
				InputStream content = (InputStream) connection.getInputStream();
				BufferedReader in = new BufferedReader(new InputStreamReader(
						content));
				String line;
				while ((line = in.readLine()) != null) {
					out.println(line);
					// System.out.println(line);
				}
			} else {
				System.out.println("Error status : "
						+ connection.getResponseCode() + ", Message : "
						+ connection.getResponseMessage());
				response.sendError(connection.getResponseCode(),
						connection.getResponseMessage());
			}

		} catch (Exception e) {
			try {
				response.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
}
