<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="org.gtug.torino.bootcamp.dao.BaseDAO" %>
<%@ page import="org.gtug.torino.bootcamp.entity.Photo" %>
<%@ page import="java.util.List" %>
<%@ page import="com.google.appengine.api.datastore.Key" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Upload ok</title>
</head>
	<body>
		<%
			String photoUrl1 =  "http://placehold.it/360x268";
			String photoUrl2 =  "http://placehold.it/360x268";
			String[] thumbsUrl =  new String [12];
			String[] captions = new String [12];
			for(int i= 0; i<thumbsUrl.length; i++){
				thumbsUrl[i] = "http://placehold.it/160x120";
				captions [i] = "Empty caption";
			} 
			List<Photo> pictures = BaseDAO.getLastPhoto();
			if(pictures!=null){
				int counter = 0;
				for(Photo photo: pictures){
					if(counter==0)
						photoUrl1 = "/blob?blob-key="+photo.getBlokKey();
					else if(counter==1)
						photoUrl2 = "/blob?blob-key="+photo.getBlokKey();
					thumbsUrl[counter] = "/thumbs?id=" + photo.getId().toString();
					captions [counter] = photo.getCaption();
					counter++;
				}
			}
		%>
		<div class="row" style='margin-top: 40px'>
			<div class='span12'>
				<ul class="thumbnails">
					<li class="span4">
						<div class="thumbnail">
								<img alt="<%out.println(captions[0]);%>"
									title="<%out.println(captions[0]);%>" 
							 	src="<%out.println(photoUrl1);%>" />
						</div>
					</li>
					<li class="span4">
						<div class="thumbnail">
								<img alt="<%out.println(captions[1]);%>"
									title="<%out.println(captions[1]);%>" 
							 		src="<%out.println(photoUrl2);%>" />
						</div>
					</li>
					<%
						int counter = 2;
						for(int i= 2; i<thumbsUrl.length; i++){ 
					%>
						<li class="span2">
							<div class="thumbnail" >
								<img alt="<%out.println(captions[counter]);%>"
									title="<%out.println(captions[counter]);%>" 
									src="<%out.println(thumbsUrl[counter]);%>">
							</div>
						</li>
					<%
						counter++;
						} 
					%>
				</ul>
			</div>		
		</div>
	</body>
</html>