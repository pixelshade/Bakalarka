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
				<a class="navbar-brand" href="./"><?php echo $site_name . " - " . $this->uri->segment(2); ?></a>
			</div>
			<div class="navbar-collapse collapse">
				<ul class="nav navbar-nav">

					<!-- <li <?php echo $page=="user"? 'class="active"' : ''; ?>><a href="/admin/user">Users</a></li>			
					<li <?php echo $page=="article"? 'class="active"' : ''; ?>><a href="/admin/article">Articles</a></li>			
					  
					
					<li class="<?php echo $page=="page"? 'active ' : ''; ?>dropdown">
						<a href="#" class="dropdown-toggle" data-toggle="dropdown">Pages <b class="caret"></b></a>
						<ul class="dropdown-menu">
							<li><a href="/admin/page">List</a></li>
							<li><a href="/admin/page/edit">Add</a></li>
							<li><a href="/admin/page/order">Reorder pages</a></li>						
						
						</ul>
					</li>
					 -->
				<?php echo get_menu($menu); ?>
				<li <?php echo $page=="world"? 'class="active"' : ''; ?>><a href="/world/json">World</a></li>			
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
	
	<?php //$this->load->view($subview); ?>


	</div> <!-- /.container -->
<?php $this->load->view('include/footer.php');
