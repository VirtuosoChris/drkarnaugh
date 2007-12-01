<?php


//soapfog_karnaugh.userscores
//fields
//name
//score
//rand

//php page to which data is submitted from the KarnaughGame
//data is verified, and the hash key is compared to the serverside hash
//of the same data.
//If all the checks are passed, username, score, and rand are saved into the database.
//the output then contains a string describing the user's placement

//see rankedgame for details on how this works.

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
   
   
    //****needs username and url****
    $dbc = mysql_connect('localhost', 'soapforg_chrisp', 'asdfzxcv') or die("could not connect to database"); 

	
     if(ini_get('magic_quotes_gpc')){$n = stripslashes($n);}
     $n = mysql_real_escape_string($n);


    mysql_select_db('soapforg_karnaugh')  or die("could not select database"); 

    

    $resultRand = @mysql_query("select * from userscores where name  = $n AND rand = $r");
    $submitted = @mysql_num_rows($resultRand);
    if($submitted == 0){

 
    $request = "insert into userscores (name, score, rand) values ('{$_REQUEST['name']}', {$_REQUEST['score']}, {$_REQUEST['r']})";
 
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

	}else echo "This score and hash key have already been submitted!";
    }else echo "Invalid Hash key!";
   }else echo "Name is an invalid string!";
  }else echo "Score is not numeric!";

 

}else{echo "No inputs received!";}



?>
