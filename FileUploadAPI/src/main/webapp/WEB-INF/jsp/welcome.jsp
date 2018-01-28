<html>
<body>

	<h1> Welcome</h1>
	<h2> The sample application allows the following end points <h2> 
	<h3> <a href = "upload">1.	/upload   </a>-------- Use this to upload files </h3>  Example : localhost:8080/upload 
	<h3> <a href = "list">2.	/list   </a>---------- Use this to view all Uploaded files </h3>  Example: localhost:8080/list
	<h3> 3.	/report/"filename"/     ------- Use this to view reports of file meta data </h3> Example : localhost:8080/report/x.txt/ ---- Will display the meta data of the file x.txt
	<br>Correct Usage : /report/x.txt/ 
	<br>Incorrect Usage : /report/x.txt
	<h3> 4.	/report/"filename"/"keyword"   -------- Additionally, use this end point to view reports of file meta data after removing the word "keyword" from the file content </h3>
		Example : localhost:8080/report/x.txt/blue ---- Will display the meta data of the file x.txt after removing the words containing "blue" 

</body>
</html>