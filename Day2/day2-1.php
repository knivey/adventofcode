<?php
$input = file("day2_passwords.txt", FILE_IGNORE_NEW_LINES | FILE_SKIP_EMPTY_LINES);

$valid = 0;

foreach($input as $p) {
	if(preg_match("/(\d+)\-(\d+) (.): (.+)/", $p, $m)) {
		$c = substr_count($m[4], $m[3]);
		if($m[1] <= $c && $c <= $m[2])
			$valid++;
	} else {
		echo "Failed match on: $p\n";
	}
}

echo "Valid passwords: $valid\n";
