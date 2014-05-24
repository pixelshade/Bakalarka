<?php
/**
*	Here you can find main settings for this game engine
*	You can specify GameName, whether to show your logo on qrcodes which are generated, and which logo - defaultly its logo.png
*/
$config['site_name'] = 'Príbehy Stredozeme';





/** 
* QR logo path. Relative to app_content/ folder
* defautly logo
*/

$config['qrlogo_filename'] = 'logo.png';

/**
* Whether to add QR logo to 
*/
$config['add_logo_to_qrcode'] = TRUE;




$config['client_download_url'] = 'https://play.google.com/store';
$config['qrcode_length'] = 6;