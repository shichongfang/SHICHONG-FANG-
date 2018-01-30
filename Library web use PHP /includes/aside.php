<aside>
	<?php
	$check = logged_in();
	if ($check === true) {
		include 'includes/widgets/widget_logged_in.php';
	} else {
		include 'includes/widgets/widget_login.php';
	}
	?>
</aside>