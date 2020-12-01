<?php

$input = file("day1-1_input.txt", FILE_IGNORE_NEW_LINES | FILE_SKIP_EMPTY_LINES);

arsort($input);

foreach($input as $k => $i) {
    foreach (array_slice($input, $k) as $x) {
        if(($i + $x) == 2020) {
		echo $i * $x . "\n";
        }
    }
}
