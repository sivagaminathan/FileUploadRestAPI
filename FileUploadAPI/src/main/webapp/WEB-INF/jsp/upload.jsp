<html>
<body>

	<h1>File Upload</h1>
	<h3> Processes the Input File into a meta data with "filename", "filesize", "wordcount" and "frequency of words" and returns that "</h3>
	<form method="POST" action="/upload" enctype="multipart/form-data">
		<input type="file" name="file" /><br />
		<br /> <input type="submit" value="Submit" />
	</form>

</body>
</html>