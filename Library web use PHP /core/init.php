<?php
session_start();
error_reporting(0);
require 'database/connect.php';
require 'functions/general.php';
require 'functions/users.php';
if (logged_in() === true) {
	$session_username = $_SESSION['username'];
	$user_data = user_data($session_username, 'username', 'password', 'firstname', 'lastname');
}
$errors = array();
?>