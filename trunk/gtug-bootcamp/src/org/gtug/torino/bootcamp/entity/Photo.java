package org.gtug.torino.bootcamp.entity;

import java.util.Date;

import com.google.appengine.api.datastore.Blob;
import com.google.appengine.api.datastore.Entity;

public class Photo {

	public static final String NAME = "PHOTO";

	private Long id;
	private String caption;
	private Blob thumbData;
	private String blokKey;
	private String contentType;
	private Date lastupdate;

	public Photo() {
		super();
	}

	public Photo(Entity e) {
		setId(e.getKey().getId());
		setCaption((String) e.getProperty("Caption"));
		setThumbData((Blob) e.getProperty("ThumbData"));
		setBlokKey((String) e.getProperty("BlokKey"));
		setContentType((String) e.getProperty("ContentType"));
		setLastupdate((Date) e.getProperty("lastupdate"));
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setCaption(String caption) {
		this.caption = caption;
	}

	public String getCaption() {
		return caption;
	}

	public Blob getThumbData() {
		return thumbData;
	}

	public void setThumbData(Blob thumbData) {
		this.thumbData = thumbData;
	}

	public String getBlokKey() {
		return blokKey;
	}

	public void setBlokKey(String blokKey) {
		this.blokKey = blokKey;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public void setLastupdate(Date lastupdate) {
		this.lastupdate = lastupdate;
	}

	public Date getLastupdate() {
		return lastupdate;
	}

	public Entity getEntity() {
		Entity entity =null;
		if(getId()!=null)
			entity = new Entity(Photo.NAME, getId());
		else
			entity = new Entity(Photo.NAME);

		entity.setProperty("Caption", getCaption());
		entity.setProperty("ThumbData", getThumbData());
		entity.setProperty("BlokKey", getBlokKey());
		entity.setProperty("ContentType", getContentType());
		entity.setProperty("lastupdate", getLastupdate());

		return entity;
	}


}
