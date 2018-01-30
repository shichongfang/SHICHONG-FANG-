<?php
include 'core/init.php';
include 'includes/overall/overall_header.php'; 
?>
<h1>Search</h1>

<form action="" method="get">
	<label>
		Search:
		<input type="text" name="keywords">
	</label>
	<input type="submit" value="Search">
	<br><br>
		<input type="radio" name="criteria" value="TitleAuthor">Title/Author
		<input type="radio" name="criteria" value="Category">Category
</form>

<br>

<?php 
if (isset($_GET['keywords']) && isset($_GET['keywords'])) {
	$keywords = $_GET['keywords'];
	$criteria = $_GET['criteria'];
	if ($criteria == "TitleAuthor") {
		$sql = 	("	SELECT books.*, categories.categoryDesc
					FROM books
					LEFT JOIN categories
					ON books.category = categories.categoryID
					WHERE bookTitle LIKE '%{$keywords}%' OR author LIKE '%{$keywords}%'
				");
	}
	else if ($criteria == "Category") {
		$sql = 	("	SELECT books.*, categories.categoryDesc
					FROM books
					LEFT JOIN categories
					ON books.category = categories.categoryID
					WHERE categories.categoryDesc LIKE '%{$keywords}%'
				");
	}
	else {
		$sql = 	("	SELECT books.*, categories.categoryDesc
					FROM books
					LEFT JOIN categories
					ON books.category = categories.categoryID
					WHERE bookTitle LIKE '%{$keywords}%' OR author LIKE '%{$keywords}%' OR categories.categoryDesc LIKE '%{$keywords}%'
				");
	}
	$result = mysql_query($sql);
	$row = mysql_fetch_assoc($result);
	// number of results to show per page
	$per_page = 5;
	// figure out the total pages in the database
	$total_results = mysql_num_rows($result);
	$total_pages = ceil($total_results / $per_page);
	// check if the 'page' variable is set in the URL 
	if (isset($_GET['page']) && is_numeric($_GET['page']))
	{
		$show_page = $_GET['page'];
		
		// make sure the $show_page value is valid
		if ($show_page > 0 && $show_page <= $total_pages)
		{
			$start = ($show_page -1) * $per_page;
			$end = $start + $per_page; 
		}
		else
		{
			// error - show first set of results
			$start = 0;
			$end = $per_page; 
		}		
	}
	else
	{
		// if page isn't set, show first set of results
		$start = 0;
		$end = $per_page; 
	}
	
	// display pagination
	for ($i = 1; $i <= $total_pages; $i++)
	{
		echo "<a href='search.php?&keywords=$keywords&criteria=$criteria&page=$i'>$i</a> ";
	}
	echo "</p>";
		
	// display data in table
	echo "<table>";
	echo "<tr> <th>Title</th> <th>Author</th> <th>ISBN</th> <th>Category</th> </tr>";
	// loop through results of database query, displaying them in the table	
	for ($i = $start; $i < $end; $i++)
	{
		// make sure that PHP doesn't try to show results that don't exist
		if ($i == $total_results) { break; }
			echo "<tr>";
			echo '<td>' . mysql_result($result, $i, 'bookTitle') . '</td>';
			echo '<td>' . mysql_result($result, $i, 'author') . '</td>';
			echo '<td>' . mysql_result($result, $i, 'ISBN') . '</td>';
			echo '<td>' . mysql_result($result, $i, 'categoryDesc') . '</td>';
			echo '<td>';
			if(mysql_result($result, $i, 'reserved') == 'N')
	      	{
	      		$ISBN = mysql_result($result, $i, 'ISBN');
	      		?>
	        	<form method="post" action="">
	        		<button type="submit" name="ISBN" value=" <?php echo "$ISBN" ?> ">Reserve</button>
	        	</form><?php
	      	}
	      	else
	      	{
	        	echo "Unavailable";
	      	}	
	      	echo '</td>';
			echo "</tr>"; 
		}
	// close table>
	echo "</table>"; 
	// pagination
}
?>

<?php
if (isset($_POST['ISBN']) && isset($_SESSION["username"])) {
	$string = preg_replace('/\s+/', '', $_POST['ISBN']);
	$username = $_SESSION['username'];
	mysql_query ("	UPDATE books
					SET reserved = 'Y'
					WHERE ISBN = '$string'
				");
	mysql_query ("	INSERT INTO reservations 
					VALUES ('$string', '$username', CURDATE())
				");
	header('Location: '.$_SERVER['REQUEST_URI']);
}
?>

<?php include 'includes/overall/overall_footer.php'; ?>