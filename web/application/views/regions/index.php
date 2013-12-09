
<?php foreach ($regions as $regions_item): ?>

    <h2><?php echo $regions_item['name'] ?></h2>
    <div id="main">
        <?php echo $regions_item['info'] ?>
    </div>
    <p><a href="regions/<?php echo $regions_item['id'] ?>">View region</a></p>

<?php endforeach ?>