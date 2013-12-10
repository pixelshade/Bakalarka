
	<script src="//ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
	<script src="//ajax.googleapis.com/ajax/libs/jqueryui/1.10.3/jquery-ui.min.js"></script>
	<script src="//cdnjs.cloudflare.com/ajax/libs/lodash.js/1.3.1/lodash.min.js"></script>
	<script src="<?php echo site_url('assets/js/bootstrap.min.js') ?>"></script>
	<script src="<?php echo site_url('assets/js/custom.js') ?>"></script>
	<script src="<?php echo site_url('assets/js/jquery.mjs.nestedSortable.js') ?>"></script>

	<script src="//tinymce.cachefly.net/4.0/tinymce.min.js"></script>

	<script>
		tinymce.init({selector:'.tinymce'});
	</script>





	<script>
		$(function() {
			$.post('<?php echo site_url('admin/page/order_ajax'); ?>', {}, function(data){
				$('#orderResult').html(data);
			});

			$('#save').click(function(){
				oSortable = $('.sortable').nestedSortable('toArray');

				$('#orderResult').slideUp(function(){
					$.post('<?php echo site_url('admin/page/order_ajax'); ?>', { sortable: oSortable }, function(data){
						$('#orderResult').html(data);
						$('#orderResult').slideDown();
					});
				});

			});
		});


	</script>



</body>
</html>
