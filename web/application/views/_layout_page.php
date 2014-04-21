<?php
if(empty($page)){
	$page = new stdClass();
	$page->title = "Welcome!";
	$page->body = "No page selected";
}
?>

<div class="page-header">
  <h1><?php
echo $page->title;
?></h1>
</div>
<?php
echo $page->body;

?>








