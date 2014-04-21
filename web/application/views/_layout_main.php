 <?php $this->load->view('include/header.php'); ?>

<?php  $page = $this->uri->segment(1); ?>
	<div class="container">
		<div class="navbar navbar-default" role="navigation">
			<div class="navbar-header">
				<button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
					<span class="sr-only">Toggle navigation</span>
					<span class="icon-bar"></span>
					<span class="icon-bar"></span>
					<span class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href="./"><?php echo $site_name;?></a> <?php // " " . $this->uri->segment(2);  ?>
			</div>
			<div class="navbar-collapse collapse">
				<ul class="nav navbar-nav">							
				<?php echo get_menu($menu); ?>
				<li <?php echo $page=="api"? 'class="active"' : ''; ?>><a href="/api">Client Api</a></li>			
				<li <?php echo $page=="admin"? 'class="active"' : ''; ?>><a href="/admin/">Admin</a></li>			
				</ul>
				<ul class="nav navbar-nav navbar-right">
				<li><a href="/user/logout">Logout</a></li>
					<!-- <li class="active"><a href="./">Default</a></li>
					<li><a href="../navbar-static-top/">Static top</a></li>
					<li><a href="../navbar-fixed-top/">Fixed top</a></li> -->
				</ul>
			</div><!--/.nav-collapse -->
		</div>
	
	<?php $this->load->view($subview); ?>


	</div> <!-- /.container -->
<?php $this->load->view('include/footer.php');
