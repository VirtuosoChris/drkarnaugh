<?php


$name = "ChrisPugh";
$score = 0;
$rand = 200;

$ascii  = ord( $name{0} ); 
$ascii2 = ord( $name{strlen($name)-1} );
$ascii3 = ord( $name{ $score%strlen($name) } );
$ascii4 = $ascii + $ascii2 + $ascii3;
$ascii4 = $ascii4 %94;
$ascii4 +=33;

$hash[0] = chr($ascii4);

echo "".$hash[0];

  for($i = 1; $i < 50; $i++){
		
     $val = 0;
		

    if($i % 2 == 0){

	$val1 = ($score*$score);
	
	//echo "val1 ".$val1."<br>";

	$val2 = ord($hash[$i-1]);


	//echo "val2 ".$val2."<br>";	

	$val3 = $val2 * $i;
	$val3 = $val3/11;
	

	//echo "val3 ".$val3."<br>";

	$val = $val3 + $val1;
	
	//echo "i = ".$i." ".$val1." ".$val2."<br>";
	
	$val = $val3; 
	
	//$val = (($score*$score) + ord($hash[($i-1)]) * $i / 11 );
	
     }
		
    else{
        
        $valA = ord($hash[($rand % $i)]); 
	$valB = ord($hash[$i-1]);
	$valC = strlen($name);
	$val = $valA*$valB*7;
//	$val = $val*7;
	$val= $val+$valC;

	//echo "".$valA." ".$valB." ".$valC." ANS ".$val."<br>";

	//* ord($hash[$i-1])*7 + strlen($name);
    
    }
		

     //echo "".$val."<br>";
     $val = (($val%94)+33);
     $hash[$i] =  chr($val);
      
     echo "".chr($val);
		
  }	










?>