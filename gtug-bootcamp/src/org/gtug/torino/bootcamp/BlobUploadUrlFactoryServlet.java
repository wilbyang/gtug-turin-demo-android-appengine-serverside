package org.gtug.torino.bootcamp;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;

public class BlobUploadUrlFactoryServlet 
		extends HttpServlet {
	

	private static final long serialVersionUID = 1L;
	private BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();

	private static final Logger log = Logger.getLogger(UploadServlet.class.getName());

	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		log.info("[BlobUploadUrlFactoryServlet::doGet] - START");
		try {
			String uploadUrl = blobstoreService.createUploadUrl("/upload");
			log.info("[BlobUploadUrlFactoryServlet::doGet] - uploadUrl: " + uploadUrl);
			resp.setContentType("text/plain");
			resp.getWriter().println(uploadUrl);
		} finally {
			log.info("[BlobUploadUrlFactoryServlet::doGet] - END");
		}
	}
}
