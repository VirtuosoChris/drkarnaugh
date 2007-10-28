<?php

require("phpHash.php");

if(( isSet($_REQUEST['name'])  && isSet($_REQUEST['score']) )) {

 $n = $_REQUEST['name'];
 $s = $_REQUEST['score'];
 $h = $_REQUEST['hash'];
 $r = $_REQUEST['r'];
 
 $h = trim($h);
 $s = trim($s);
 $n = trim($n);  
 $r = trim($r); 

 if(ctype_digit($s) && ctype_digit($r)){

  if(strlen($n) <= 25){

    if(strlen($h) == 50 && hashData($n, $s, $r) == $h){
   
   $dbc = mysql_connect('localhost', 'root', 'asdfzxcv') or die("could not connect to database"); 

	
     if(ini_get('magic_quotes_gpc')){$n = stripslashes($n);}
     $n = mysql_real_escape_string($n);


    mysql_select_db('userscores')  or die("could not select database"); 
 
    $request = "insert into userscores (name, score) values ('{$_REQUEST['name']}', {$_REQUEST['score']})";
 
    $result = mysql_query($request) or die("could not process request"); 

    if($result == false){die("request invalid");}

    echo "Score submitted! ";

    $result3 = @mysql_query("select * from userscores where score > $s");
    $result2 = @mysql_query("select * from userscores");

    $i = @mysql_num_rows($result3);
    $j = @mysql_num_rows($result2);

    $k = $j - $i - 1;
     
   echo "Out of $j users, $i scored higher and $k scored equal or lower."; 

   mysql_close();
   
    }else echo "Invalid Hash key!";
   }else echo "Name is an invalid string!";
  }else echo "Score is not numeric!";

 

}else{echo "No inputs received!";}



?>
