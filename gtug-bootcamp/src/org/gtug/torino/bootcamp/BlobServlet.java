package org.gtug.torino.bootcamp;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;

public class BlobServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();

	private static final Logger log = Logger.getLogger(BlobServlet.class.getName());

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
		log.fine("[BlobServlet::doGet] START");
		BlobKey blobKey = new BlobKey(req.getParameter("blob-key"));
		log.fine("[BlobServlet::doGet] blobKey: " + blobKey);
		blobstoreService.serve(blobKey, res);
		String contentType = req.getParameter("contentType");
		log.fine("[BlobServlet::doGet] contentType: " + contentType);
		if (contentType != null && !contentType.trim().equals(""))
			res.setContentType(contentType);

		String filename = req.getParameter("filename");
		log.fine("[BlobServlet::doGet] filename: " + filename);
		if (filename != null && !filename.trim().equals(""))
			res.setHeader("content-disposition", "attachment;filename=\"" + filename + "\"");
	}
}
