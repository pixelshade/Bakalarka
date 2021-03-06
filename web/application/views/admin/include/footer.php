<script src="//ajax.googleapis.com/ajax/libs/jqueryui/1.10.3/jquery-ui.min.js"></script>
<script src="//cdnjs.cloudflare.com/ajax/libs/lodash.js/1.3.1/lodash.min.js"></script>
<script src="<?php echo site_url('assets/js/bootstrap.min.js') ?>"></script>
<script src="<?php echo site_url('assets/js/custom.js') ?>"></script>
<script src="<?php echo site_url('assets/js/jquery.mjs.nestedSortable.js') ?>"></script>
<script src="<?php echo site_url('assets/js/bootstrap-datepicker.js') ?>"></script>

<script src="//tinymce.cachefly.net/4.0/tinymce.min.js"></script>

<script>
	tinymce.init({
		selector:'.tinymce'
    	<?php    
    	if(isset($content_dir) && isset($imgs)){
			echo ',plugins: "image textcolor",';
			echo 'toolbar: "insertfile undo redo | styleselect | bold italic | alignleft aligncenter alignright alignjustify | bullist numlist outdent indent | link image | print preview media fullpage | forecolor backcolor",';
	    	echo 'image_list: [ ';
	    	foreach ($imgs as $img) {
	    		echo "{title: '".$img."', value: '".site_url($content_dir.$img)."'}, ";
	    	}        	
    		echo ']';
        }
        ?>
});
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

<script>
	$(function() {
		$('.datepicker').datepicker({ format : 'yyyy-mm-dd' });
	});
</script>

</body>
</html>
