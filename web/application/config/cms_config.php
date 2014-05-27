<?php
/**
*	Here you can find main settings for this game engine
*	You can specify GameName, whether to show your logo on qrcodes which are generated, and which logo - defaultly its logo.png
*/




/************************************** GAME NAME *************************/
$config['site_name'] = 'Príbehy Stredozeme';
// $config['site_name'] = 'Gotham City';


/************************************** QR code generator settings *******/

/**
* Whether to add QR logo to 
*/
$config['add_logo_to_qrcode'] = TRUE;


/** 
* QR logo path. Relative to app_content/ folder
* defautly logo
*/

$config['qrlogo_filename'] = 'logo.png';


$config['qrcode_length'] = 6;



$config['client_download_url'] = 'https://play.google.com/store';