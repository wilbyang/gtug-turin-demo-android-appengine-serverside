<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Upload photo</title>
</head>
<body>
	<script type="text/javascript">
		$(document).ready(function() {
			$.get("/uploadUrlFactory", null, function(data) {
				$('#uploadForm').attr("action", data);
			});
		});
	</script>
	<form id='uploadForm' action='' method='post'
		enctype='multipart/form-data' class='well pull-left' style='margin-top: 40px;'>
		<fieldset>
			<legend>Upload an image and write a caption</legend>
			<div>
				<input name='uploadFormElement' type='file'  />
				<span class="help-block">Choose a picture to upload.</span>
			</div>
			<div>
				<input type='text' name='caption' placeholder="caption" style="height: 30px;"/>
				<span class="help-block">Choose a caption for the picture.</span>
			</div>
			<div class="control-group">
				<div class="controls">
					<a href="/" class="btn">Cancel</a> <input type='submit'
						class='btn btn-primary ' value="ok" />
				</div>
			</div>
		</fieldset>
	</form>
</body>
</html>