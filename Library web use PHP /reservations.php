<?php 
include 'core/init.php';
include 'includes/overall/overall_header.php'; 
?>

<h1>Reservations</h1>

<?php 
if (isset($_SESSION["username"])) {
	$username = $_SESSION['username'];
	$sql = ("	SELECT reservations.*, books.*
				FROM reservations
				LEFT JOIN books
				ON books.ISBN = reservations.ISBN 
				WHERE username = '$username' 
			");
	$result = mysql_query($sql);
	$row = mysql_fetch_assoc($result);
	$total_results = mysql_num_rows($result);
	echo "<table>";
	echo "<tr> <th>Title</th> <th>Authour</th> <th>Date Reserved</th> </tr>";
	for ($i = 0; $i < mysql_num_rows($result); $i++)
	{
		// make sure that PHP doesn't try to show results that don't exist
		if ($i == $total_results) { break; }
			echo "<tr>";
			echo '<td>' . mysql_result($result, $i, 'bookTitle') . '</td>';
			echo '<td>' . mysql_result($result, $i, 'authour') . '</td>';
			echo '<td>' . mysql_result($result, $i, 'reservedDate') . '</td>';
			echo '<td>';
	  		$ISBN = mysql_result($result, $i, 'ISBN');
	  		?>
	    	<form method="post" action="">
	    		<button type="submit" name="ISBN" value=" <?php echo "$ISBN" ?> ">Unreserve</button>
	    	</form><?php
	      	echo '</td>';
			echo "</tr>"; 
		}
	// close table>
	echo "</table>"; 
}
else {
	echo "Login to view this page";
}
?>

<?php
if (isset($_POST['ISBN'])) {
	$string = preg_replace('/\s+/', '', $_POST['ISBN']);
	mysql_query ("	UPDATE books
					SET reserved = 'N'
					WHERE ISBN = '$string'
				") ;
	mysql_query ("	DELETE FROM reservations 
					WHERE ISBN = '$string'
				");
	header('Location: '.$_SERVER['REQUEST_URI']);
}
?>

<?php include 'includes/overall/overall_footer.php'; ?>