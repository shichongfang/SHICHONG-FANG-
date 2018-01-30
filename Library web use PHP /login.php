<?php
// Logic
include 'core/init.php';
if (empty($_POST) === false) {
	$username = $_POST['username'];
	$password = $_POST['password'];
	// error checking
	if (empty($username) === true || empty($password) === true) 
	{
		$errors[] = 'You need to enter a username and password';
	} 
	else 
	{
		// more error checking
		if (strlen($password) > 6) {
			$errors[] = 'Password too long';
		}
		// call login function with submitted data
		$login = login($username, $password);
		if ($login === false) {
			$errors[] = 'Username and password combination is incorrect';
		} else {
			// set the user session and login
			$_SESSION['username'] = $login;
			header('Location: index.php');
			exit();
		}
	}
} else {
	$errors[] = 'No data recieved';
}
// Output
include 'includes/overall/overall_header.php';
if (empty($errors) === false) {
?>
	<h2>Login failed:</h2>
<?php	
	echo output_errors($errors);
}
include 'includes/overall/overall_footer.php';
?>