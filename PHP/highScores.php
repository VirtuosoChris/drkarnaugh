<?php

//php script that retrieves the top ten scores from the mysql database
//if html = no, generates in plain text, for use from within the KarnaughGame application
//otherwise it is shown as a table for use from within a web browser

//see rankedgame for more details of how this works.

    //****needs username and url****
    $dbc = mysql_connect('localhost', 'soapforg_chrisp', 'asdfzxcv') or die("could not connect to database"); 

mysql_select_db('soapforg_karnaugh')  or die("ERROR: There was a problem accessing the database, please try again later"); 

$request = "select * from userscores order by score desc limit 10";

$result = mysql_query($request) or die("ERROR: There was a problem accessing the database, please try again later"); 

$result2 = mysql_query("select * from userscores");

if($_GET['html']!="no")
{
echo "Here are the top scores";

$tmp = 0;
if($result2 && ($tmp = mysql_num_rows($result2)) > 0){
echo " out of $tmp users";
}echo "!";

echo "<br>";

echo '<table border = "1">';
echo '<tr>';
}else echo "SUCCESS: ";

if($result && mysql_num_rows($result) > 0){

 while($row = mysql_fetch_assoc($result)){
  
  if($_GET['html']!="no")
  {
     echo '<td>';
     echo $row['name']; 
     echo '</td>';
     echo '<td>'; 
     echo $row['score'];
     echo '</td>';
     echo '</tr>';
   }
   else
   {//that really long ugly string is a seperator so that the program knows that the next string read in is the score and not part of a name
   //its longer than the maximum name string, so this works.  check rankedgame for details.  
    echo ($row['name'])." "."abcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyz"." ".$row['score']." ";
   }

 }
}

if($_GET['html']!="no")
echo "</table>";

mysql_close();

?>

