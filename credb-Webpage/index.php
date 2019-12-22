<html>
<head>
  <title>CredB - One Happy Crawler</title>
  <style>
.button {
  border-radius: 40px;
  background-color: #f4511e;
  border: none;
  color: #FFFFFF;
  text-align: center;
  font-size: 28px;
  padding: 2px;
  width: 150px;
  transition: all 0.5s;
  cursor: pointer;
  margin: 15px;
}

.button span {
  cursor: pointer;
  display: inline-block;
  position: relative;
  transition: 0.5s;

}

.button span:after {
  content: '\00bb';
  position: absolute;
  opacity: 0;
  top: 0;
  right: -20px;
  transition: 0.5s;
}

.button:hover span {
  padding-right: 25px;
}

.button:hover span:after {
  opacity: 1;
  right: 0;
}


.content{
  background-color: #F2E2CA;
  padding-left: 80px;
  margin: auto;
  max-width: 100%;
}

.aspect{
  padding-top: 50px;
}

#sbox{
  width:60%;
}
</style>
</head>

<link href="main_style.css" rel="stylesheet" type="text/css" />


<body>
  <div class=content>
    <div class="aspect">
    <h1>
      <center>
        <img src="credbp.png" alt="CredB-One Happy Crawler">
      </center>
    </h1>
    <form method="post" action="searchpage.php" name="registerform">
      <center>
      <input type=text id="sbox" name='query' placeholder="Enter the name of the File or Folder you want to search.">
      </input>
    </center>
      <center>
        <button class="button">
          <span>
            Search
          </span>
        </button>
      </center>
    </form>
  </div>
  </div>
</body>
</html>
