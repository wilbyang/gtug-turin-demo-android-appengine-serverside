package org.gtug.torino.bootcamp.dao;

import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

import org.gtug.torino.bootcamp.entity.Photo;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.SortDirection;

public class BaseDAO {

	protected static DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
	private static final Logger log = Logger.getLogger(BaseDAO.class.getName());

	public static long put(Photo photo) throws EntityNotFoundException {
		Key savedKey = datastore.put(photo.getEntity());
		return savedKey.getId();
	}

	public static Photo getPhoto(String key) throws EntityNotFoundException {
		Entity entity = datastore.get(KeyFactory.stringToKey(key));
		Photo photo = new Photo(entity);
		return photo;
	}

	public static Photo getPhoto(Long photoId) throws EntityNotFoundException {
		Key key = KeyFactory.createKey(Photo.NAME, photoId);
		Entity entity = datastore.get(key);
		Photo photo = new Photo(entity);
		return photo;
	}

	public static List<Photo> getLastPhoto() {
		log.fine("[BaseDAO::getLastPhoto] - START");
		try {
			Query q = new Query(Photo.NAME);
			q.addSort("lastupdate", SortDirection.ASCENDING);
			PreparedQuery pq = datastore.prepare(q);
			log.fine("[BaseDAO::getLastPhoto] - pq: " + pq);

			List<Photo> picturesFound = new LinkedList<Photo>();
			for (Entity result : pq.asIterable()) {
				picturesFound.add(new Photo(result));
			}
			log.fine("[BaseDAO::getLastPhoto] - picturesFound.size(): " + picturesFound.size());

			return picturesFound;
		} finally {
			log.fine("[BaseDAO::getLastPhoto] - END");
		}
	}

}
