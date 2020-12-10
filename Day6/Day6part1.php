<?php

$input = file("day6_input.txt", FILE_IGNORE_NEW_LINES);

$groups = [];

$i = 0;
foreach($input as $line) {
    if(!isset($groups[$i]))
        $groups[$i] = [];
    if($line != '')
        $groups[$i] = array_merge($groups[$i], array_flip(str_split($line)));
    else
        $i++;
}

$total = 0;
foreach($groups as $g)
    $total += count($g);

echo "Total: $total\n";
//var_dump($groups);