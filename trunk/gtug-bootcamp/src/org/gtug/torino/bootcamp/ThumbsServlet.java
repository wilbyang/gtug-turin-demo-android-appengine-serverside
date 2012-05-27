package org.gtug.torino.bootcamp;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.gtug.torino.bootcamp.dao.BaseDAO;
import org.gtug.torino.bootcamp.entity.Photo;

public class ThumbsServlet
		extends HttpServlet {
	
	private static final long serialVersionUID = 1L;


	private static final Logger log = Logger.getLogger(ThumbsServlet.class.getName());

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		try {
			String key = req.getParameter("key");
			log.info("[ServeThumbnailsServlet::doGet] - Start - key: " + key);
			Photo photo ; 
			if(key!=null)
				photo = BaseDAO.getPhoto(key);
			else{
				log.info("[ServeThumbnailsServlet::doGet] - Start - id: " + req.getParameter("id"));
				photo =  BaseDAO.getPhoto(new Long(req.getParameter("id")));
			}
			
			res.setContentType(photo.getContentType());
			res.getOutputStream().write(photo.getThumbData().getBytes());
		    res.flushBuffer();
		}
		catch (Exception e) {
			e.printStackTrace();
			log.severe("[ServeThumbnailsServlet::doGet] - ERROR id: " + req.getParameter("id")+ " - msg: " + e.getMessage());
		}
		finally{
			log.info("[ServeThumbnailsServlet::doGet] - End id: " + req.getParameter("id"));
		}
	}}
