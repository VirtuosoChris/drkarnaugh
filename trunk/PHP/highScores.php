<?php

$dbc = mysql_connect('localhost', 'root', 'asdfzxcv') or die("ERROR: There was a problem accessing the database, please try again later"); 

mysql_select_db('userscores')  or die("ERROR: There was a problem accessing the database, please try again later"); 

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
   {
    echo ($row['name'])." "."abcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyz"." ".$row['score']." ";
   }

 }
}

if($_GET['html']!="no")
echo "</table>";

mysql_close();


#</body>
#</html>

?>

