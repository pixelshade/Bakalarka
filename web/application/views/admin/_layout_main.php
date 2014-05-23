<?php $this->load->view('admin/include/header.php'); ?>

<?php  $page = $this->uri->segment(2); ?>
<div class="container">
	<div class="navbar navbar-default" role="navigation">
		<div class="navbar-header">
			<button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
				<span class="sr-only">Toggle navigation</span>
				<span class="icon-bar"></span>
				<span class="icon-bar"></span>
				<span class="icon-bar"></span>
			</button>
			<a class="navbar-brand" href="./admin/dashboard"><?php  echo $site_name; // . " - " . $this->uri->segment(2); ?></a>
		</div>
		<div class="navbar-collapse collapse">
			<ul class="nav navbar-nav">
				<li <?php echo $page=="user"? 'class="active"' : ''; ?>><a href="/admin/user">Users</a></li>			
				<li <?php echo $page=="article"? 'class="active"' : ''; ?>><a href="/admin/article">Articles</a></li>
				
				<!-- <li><a href="#">Link</a></li> -->
		
				<li class="<?php echo $page=="page"? 'active ' : ''; ?>dropdown">
					<a href="#" class="dropdown-toggle" data-toggle="dropdown">Pages <b class="caret"></b></a>
					<ul class="dropdown-menu">
						<li><a href="/admin/page">List</a></li>
						<li><a href="/admin/page/edit">Add</a></li>
						<li><a href="/admin/page/order">Reorder pages</a></li>
						
							<!-- <li><a href="#">Something else here</a></li>
							<li class="divider"></li>
							<li class="dropdown-header">Nav header</li>
							<li><a href="#">Separated link</a></li>
							<li><a href="#">One more separated link</a></li> -->
						</ul>
					</li>
					<?php 
						$links = array("content_file","region","quest","item_definition","item_instance","attribute","reward","user_quest","user_item","user_attribute","user_position","user_qrscanned");
					?>
					<li class="<?php echo in_array($page, 	$links)? 'active ' : ''; ?>dropdown">
						<a href="#" class="dropdown-toggle" data-toggle="dropdown">Game content <b class="caret"></b></a>
						<ul class="dropdown-menu">
							<li><a href="/admin/content_file">Content files</a></li>
							<li><a href="/admin/region">Regions</a></li>
							<li><a href="/admin/quest">Quests</a></li>
							<li><a href="/admin/item_definition">Item definitions</a></li>
							<!-- <li><a href="/admin/item_instance">Item instance</a></li> -->
							<li><a href="/admin/attribute">Attributes</a></li>
							<li><a href="/admin/reward">Rewards</a></li>
							<li><a href="/admin/cron_task">Timed regions movement, </a></li>
								<li class="divider"></li>
							<li><a href="/admin/user_quest">Characters active quests</a></li>
							<li><a href="/admin/user_item">Inventories</a></li>
							<li><a href="/admin/user_attribute">User Attributes</a></li>
							<li><a href="/admin/user_position">User Positions</a></li>
							<li><a href="/admin/user_qrscanned">User qr scanned</a></li>
							<!-- 	<li><a href="/admin/user_objective">Characters active objectives</a></li> -->
						</ul> 
					</li>
					<li <?php echo $page=="migration"? 'class="active"' : ''; ?>><a href="/admin/migration">Migration</a></li>			
				</ul>
				<ul class="nav navbar-nav navbar-right">
					<li><a href="/admin/user/logout">Logout</a></li>
					<!-- <li class="active"><a href="./">Default</a></li>
					<li><a href="../navbar-static-top/">Static top</a></li>
					<li><a href="../navbar-fixed-top/">Fixed top</a></li> -->
				</ul>
			</div><!--/.nav-collapse -->
		</div>
		
		<?php $this->load->view($subview); ?>


	</div> <!-- /.container -->
	<?php $this->load->view('admin/include/footer.php');
