package org.gtug.torino.bootcamp;

import static com.google.appengine.api.taskqueue.TaskOptions.Builder.withUrl;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.gtug.torino.bootcamp.dao.BaseDAO;
import org.gtug.torino.bootcamp.entity.Photo;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.appengine.api.datastore.Blob;
import com.google.appengine.api.images.Image;
import com.google.appengine.api.images.ImagesService;
import com.google.appengine.api.images.ImagesServiceFactory;
import com.google.appengine.api.images.Transform;
import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.TaskOptions;
import com.google.appengine.api.taskqueue.TaskOptions.Method;

public class UploadServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();

	private static final Logger log = Logger.getLogger(UploadServlet.class.getName());
	private static final int PHOTO_THUMB_WIDTH = 260;
	private static final int PHOTO_THUMB_HEIGHT = 180;

	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		resp.setContentType("text/plain");
		resp.getWriter().println("Hello, world");
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		log.fine("[UploadServlet::doPost] START");

		String caption = req.getParameter("caption");
		System.out.println(caption);
		log.fine("[UploadServlet::doPost] caption: " + caption);

		ImagesService imagesService = ImagesServiceFactory.getImagesService();
		try {
			Map<String, List<BlobKey>> blobs = blobstoreService.getUploads(req);
			List<BlobKey> blobKeys = blobs.get("uploadFormElement");
			Long photoId = null;
			if (blobKeys != null) {
				int counter = 0;
				for (BlobKey blobKey : blobKeys) {
					if (blobKey != null) {
						log.fine("[UploadServlet::doPost] loop: " + counter + ") imageFromBlob maked");
						Transform resizeThumb = ImagesServiceFactory.makeResize(PHOTO_THUMB_WIDTH, PHOTO_THUMB_HEIGHT);
						log.fine("[UploadServlet::doPost] loop: " + counter + ") imageFromBlob resized");

						try {
							Image thumbImage = imagesService.applyTransform(resizeThumb, ImagesServiceFactory.makeImageFromBlob(blobKey));
							log.fine("[UploadServlet::doPost] loop: " + counter + ") thumbImage trasformed");
							Photo photo = new Photo();
							photo.setBlokKey(blobKey.getKeyString());
							photo.setContentType("image/png");
							photo.setThumbData(new Blob(thumbImage.getImageData()));
							photo.setCaption(caption);
							photo.setLastupdate(new Date());
							photoId = BaseDAO.put(photo);
						} catch (Exception e) {
							log.severe("[UploadServlet::doPost] loop: " + counter + ") thumbImage error: " + e.getMessage());
							e.printStackTrace();
							throw e;
						}
						counter++;
					}
				}

				String androidId = req.getParameter("androidId");
				if (androidId != null && !androidId.trim().equals("")) {
					Queue queue = QueueFactory.getDefaultQueue();

					TaskOptions url = withUrl("/tasks/messages");
					url.param("androidId", androidId);
					url.param("photoId", ""+photoId);

					url.method(Method.POST);
					queue.add(url);

				}
			}
		} catch (Exception e) {
			log.severe("[UploadServlet::doPost] ERROR " + e.getMessage());
			e.printStackTrace();
			throw new ServletException(e.getMessage());
		} finally {
			log.fine("[UploadServlet::doPost] END");
		}
		res.sendRedirect("/");

	}
}
