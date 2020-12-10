<?php

$input = file("day1-1_input.txt", FILE_IGNORE_NEW_LINES | FILE_SKIP_EMPTY_LINES);

arsort($input);

foreach($input as $k => $i) {
    foreach(array_slice($input, $k) as $kk => $x) {
        foreach(array_slice($input, $k + $kk) as $y) {
            if(($i + $x + $y) == 2020) {
 		die( $i * $x * $y . "\n");
            }
        }
    }
}
