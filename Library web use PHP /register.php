<?php 
include 'core/init.php';
include 'includes/overall/overall_header.php'; 
// error reporting
if (empty($_POST) === false) 
{
	// check all fields contain data
	$required_fields = array('username', 'password', 'password_again', 'firstname', 'surname', "AddressLine1", 'AddressLine2', 'city', 'telephone', 'mobile');
	
	foreach ($_POST as $key => $value) { // loop through each value and check each one
		if (empty($value) && in_array($key, $required_fields) === true) { // if a field is empty AND is in array generate error
			$errors[] = "All fields are required!"; 
			break 1;
		}
	}
	// check conditions for fields
	if (empty($errors) === true) {
		if (user_exists($_POST['username']) === true) {
			$errors[] = 'Sorry, the username \'' . $_POST['username'] . '\' is already taken.';
		}
		if (preg_match("/\\s/", $_POST['username']) == true) {
			$errors[] = "Your username must not contain any spaces";
		}
		if (strlen($_POST['password']) != 6) {
			$errors[] = 'Your password must be 6 characters';
		}
		if ($_POST['password'] !== $_POST['password_again']) {
			$errors[] = 'Your passwords do not match';
		}
		if (strlen($_POST['mobile']) != 10) {
			$errors[] = 'Mobile number must be 10 digits!';
		}
		if (is_numeric($_POST['mobile']) == false) {
			$errors[] = 'Mobile number must be a number';
		}
	}
}
?>

<h1>Register</h1>

<?php
// recieves success passed by header
if (isset($_GET['success']) && empty($_GET['success'])) {
	echo 'You have been registered!';
} 
else 
{
	// post data for register user or output errors
	if (empty($_POST) === false && empty($errors) === true) {
		$register_data = array (
			'username' 		=> $_POST['username'] ,
			'password' 		=> $_POST['password'] ,
			'firstname' 	=> $_POST['firstname'] ,
			'surname'		=> $_POST['surname'] ,
			'AddressLine1'	=> $_POST['AddressLine1'] ,
			'AddressLine2'	=> $_POST['AddressLine2'] ,
			'city'			=> $_POST['city'] ,
			'telephone'		=> $_POST['telephone'] ,
			'mobile'		=> $_POST['mobile'] 
		);
		// function to register data with query
		register_user($register_data);
		// send success and break
		header('Location: register.php?success');
		exit();
	} else if (empty($errors) === false) {
		echo output_errors($errors);
}
?>

<form action="" method="post">
	<ul> 
		<li>
			Username:<br>
			<input type="text" name="username">
		</li>
		<li>
			Password:<br>
			<input type="password" name="password">
		</li>
		<li>
			Password again:<br>
			<input type="password" name="password_again">
		</li>
		<li>
			First name:<br>
			<input type="text" name="firstname">
		</li>
		<li>
			Sur name:<br>
			<input type="text" name="surname">
		</li>
		<li>
			Address Line 1:<br>
			<input type="text" name="AddressLine1">
		</li>
		<li>
			Address Line 2:<br>
			<input type="text" name="AddressLine2">
		</li>
		<li>
			City:<br>
			<input type="text" name="city">
		</li>
		<li>
			Telephone:<br>
			<input type="text" name="telephone">
		</li>
		<li>
			Mobile:<br>
			<input type="text" name="mobile">
		</li>
		<li>
			<input type="submit" value="Register">
		</li>
	</ul>
</form>

<?php 
}
include 'includes/overall/overall_footer.php'; 