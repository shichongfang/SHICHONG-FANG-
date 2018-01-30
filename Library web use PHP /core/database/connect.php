<?php 
		$database="labdb";
		$servername = "localhost";
		$username = "root";
		$password = "";
	//CREAMOS LA CONEXION CON EL SERVIDOR
		$con = mysql_connect($servername, $username, $password);
		//Comprobamos la conexion
		if (!$con) {
			die("Cannot connect to the database " . mysqli_connect_error());
		}//Final del if
		mysql_query('use '.$database);
 ?>