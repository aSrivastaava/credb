<html>
<head>
  <title>CredB - One Happy Crawler</title>
<style>
	.se12{    
		position: fixed;
		float: left;
	}
</style>
  </head>
<link href="main_style.css" rel="stylesheet" type="text/css" />

	<body>
	<img src = "credb-icon1.png" alt="CredB - One Happy Crawler" class="se12" />
			
		<div class=content style="background-color: #F2E2CA;
									padding-top:30px;
									padding-left:100px">
			<?php
				 
				$q=htmlspecialchars($_POST['query']);
				echo '<h2>Showing result for <i><u><font color=green>'.$q.'</font></u></i></h2>';
				$file = 'File_Index.txt';
				$contents = file_get_contents($file);
				$pattern = preg_quote($q, '/');
				$pattern = "/^.*$pattern.*\$/m";

				 
				if(preg_match_all($pattern, $contents, $matches) ){
				   echo "Found matches:\n";
				   //echo "<pre>".implode("\n", $matches[0])."</pre>";
				   echo "<font size='5' face='Arial'>";
				   echo "<pre>".implode("\n\n" , $matches[0])."</pre>";
				}
				else{
				   echo "No matches found";
				}
			?>
		</div>
	</body>
</html>
