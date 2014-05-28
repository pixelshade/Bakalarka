<h1>Welcome!</h1>


<div class="row">
</div>
	<style type="text/css" media="screen">

		.menu:hover{
			text-decoration: none;
			background-color: #000: 
		}

		.menu .glyphicon{
			font-size: 72px;
			/*margin: 0 auto;*/
			text-align: center;			
		}	
		.menu{
			float: left;			
			height: 115px;
			padding: 10px;
			font-size: 18px;
			line-height: 1.4;
			text-align: center;
			border: 1px solid #fff;
			background-color: #f9f9f9;
			text-decoration: none;
		}
	
		.menu .caption{
			display: block;
			text-align: center;
			word-wrap: break-word;
			text-decoration: none;
		}


	</style>
		
	<div class="row">
	<a href="<?php echo base_url("admin/page"); ?>" class="menu col-md-2">
		<span  class="glyphicon glyphicon-book"></span>
		<div class="caption">Pages</div>
	</a>

	<a href="<?php echo base_url("admin/article"); ?>" class="menu col-md-2">
		<span  class="glyphicon glyphicon-pencil"></span>
		<div class="caption">Articles</div>
	</a>

	<a href="<?php echo base_url("admin/"); ?>" class="menu col-md-2">
		<span  class="glyphicon glyphicon-cog"></span>
		<div class="caption">Game settings</div>
	</a>
	</div>
<div class="row">
<div class="page-header">
  		<h2>Game creation <small>upload game images, create and manage regions, quests,..</small></h2>
	</div>
	<a href="<?php echo base_url("admin/region"); ?>" class="menu col-md-2">
		<span  class="glyphicon glyphicon-globe"></span>
		<div class="caption">Regions</div>
	</a>

	<a href="<?php echo base_url("admin/quest"); ?>" class="menu col-md-2">
		<span  class="glyphicon glyphicon-list-alt"></span>
		<div class="caption">Quests</div>
	</a>

	<a href="<?php echo base_url("admin/reward"); ?>" class="menu col-md-2">
		<span  class="glyphicon glyphicon-gift"></span>
		<div class="caption">Rewards</div>
	</a>

	<a href="<?php echo base_url("admin/item_definition"); ?>" class="menu col-md-2">
		<span  class="glyphicon glyphicon-wrench"></span>
		<div class="caption">Items</div>
	</a>

	<a href="<?php echo base_url("admin/attribute"); ?>" class="menu col-md-2">
		<span  class="glyphicon glyphicon-certificate"></span>
		<div class="caption">Attributes</div>
	</a>
	<a href="<?php echo base_url("admin/content_file"); ?>" class="menu col-md-2">
		<span  class="glyphicon glyphicon-picture"></span>
		<div class="caption">Game pictures</div>
	</a>
	</div>
	<div class="row">

	<div class="page-header">
  		<h2>User managment <small>inventories, users quests and attributes </small></h2>
	</div>
	<a href="<?php echo base_url("admin/user"); ?>" class="menu col-md-2">
		<span  class="glyphicon glyphicon-user"></span>
		<div class="caption">Users</div>
	</a>

	<a href="<?php echo base_url("admin/user_quest"); ?>" class="menu col-md-2">
		<span  class="glyphicon glyphicon-tasks"></span>
		<div class="caption">User quests</div>
	</a>

	<a href="<?php echo base_url("admin/user_item"); ?>" class="menu col-md-2">
		<span  class="glyphicon glyphicon-briefcase"></span>
		<div class="caption">Inventories</div>
	</a>

	<a href="<?php echo base_url("admin/user_attribute"); ?>" class="menu col-md-2">
		<span  class="glyphicon glyphicon-stats"></span>
		<div class="caption">User attributes</div>
	</a>	
	<a href="<?php echo base_url("admin/user_qrscanned"); ?>" class="menu col-md-2">
		<span  class="glyphicon glyphicon-qrcode"></span>
		<div class="caption">Scanned QR codes</div>
	</a>
	</div>

