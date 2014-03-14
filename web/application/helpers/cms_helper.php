<?php
function btn_edit($uri){
 	return anchor($uri, '<i class="icon-edit"></i>');
}

function btn_delete($uri){
	return anchor($uri, '<i class="icon-remove"></i>', array(
		'onclick' => "return confirm('You are about to delete a record. This cannot be undone.');"
		));	
}


function e($string){
    return htmlentities($string);
}


function get_menu ($array, $child = FALSE)
{
    $CI =& get_instance();
    $str = '';
    
    if (count($array)) {
        $str .= $child == FALSE ? '<li>' . PHP_EOL : '<li class="dropdown">' . PHP_EOL;
        
        foreach ($array as $item) {
            
            $active = $CI->uri->segment(1) == $item['slug'] ? TRUE : FALSE;
            if (isset($item['children']) && count($item['children'])) {                
                $str .= $active ? '<li class="dropdown active">' : '<li class="dropdown">';
                $str .= '<a  class="dropdown-toggle" data-toggle="dropdown" href="' . site_url(e($item['slug'])) . '">' . e($item['title']);
                $str .= '<b class="caret"></b></a>' . PHP_EOL;
                $str .= '<ul class="dropdown-menu">';
                $str .= get_menu($item['children'], TRUE);
                $str .= '</ul>';
            }
            else {
                $str .= $active ? '<li class="active">' : '<li>';
                $str .= '<a href="' . site_url($item['slug']) . '">' . e($item['title']) . '</a>';
            }
            $str .= '</li>' . PHP_EOL;
        }
        
        $str .= '</li>' . PHP_EOL;
    }
    
    return $str;
}


/**
 * Dump helper. Functions to dump variables to the screen, in a nicley formatted manner.
 * @author Joost van Veen
 * @version 1.0
 */
if (!function_exists('dump')) {
    function dump ($var, $label = 'Dump', $echo = TRUE)
    {
        // Store dump in variable 
        ob_start();
        var_dump($var);
        $output = ob_get_clean();
        
        // Add formatting
        $output = preg_replace("/\]\=\>\n(\s+)/m", "] => ", $output);
        $output = '<pre style="background: #FFFEEF; color: #000; border: 1px dotted #000; padding: 10px; margin: 10px 0; text-align: left;">' . $label . ' => ' . $output . '</pre>';
        
        // Output
        if ($echo == TRUE) {
            echo $output;
        }
        else {
            return $output;
        }
    }
}


if (!function_exists('dump_exit')) {
    function dump_exit($var, $label = 'Dump', $echo = TRUE) {
        dump ($var, $label, $echo);
        exit;
    }
}


if (!function_exists('form_boolean_select')) {
    function form_boolean_select($name, $default_true = FALSE) {
        ?>
        <select name="<?php echo $name; ?>">
            <option value="1" <?php echo set_select($name, '1', $default_true); ?> >TRUE</option>
            <option value="0" <?php echo set_select($name, '0', !$default_true); ?> >FALSE</option>           
        </select>
        <?
    }
}


