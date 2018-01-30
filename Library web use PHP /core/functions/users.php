<?php
function ISBN_exists($ISBN) {
	return (mysql_result(mysql_query("SELECT COUNT(ISBN) FROM reservations WHERE ISBN = '$ISBN'"),0) == 1)? true : false;
}
function register_user($register_data) {
	array_walk($register_data, 'array_sanitize');
	$fields = '`' . implode('`, `', array_keys($register_data)) . '`'; // fields to insert data
	$data = '\'' . implode('\', \'', $register_data) . '\''; // values 
	mysql_query("INSERT INTO users ($fields) VALUES ($data) ");
}
function user_data($username) {
	$data = array();
	$func_num_args = func_num_args();
	$func_get_args = func_get_args();
	if ($func_num_args >= 1) {
		unset($func_get_args[0]); // removes actual username which is first element in array
		$fields = implode(', ', $func_get_args) . ''; // implode returns a string from array with seperators
		$data = mysql_fetch_assoc(mysql_query("SELECT $fields FROM users WHERE username = '$username' ")); // returns an array that corresponds to the fetched rows
		return($data);
	}
}
function logged_in() {
	return (isset($_SESSION['username']) ? true : false); // return
}
function user_exists($username) {
	$sql = "SELECT COUNT(username) FROM users WHERE username = '$username'";
	return (mysql_result(mysql_query($sql),0) == 1)? true : false;
}
function login($username, $password) {
	// return username or false
	$sql = "SELECT COUNT(`username`) FROM `users` WHERE `username`='$username' AND `password`='$password' ";
	echo mysql_query($sql);
	return (mysql_result(mysql_query($sql), 0)==1) ? $username : false;
}
?>