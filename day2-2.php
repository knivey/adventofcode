<?php
$input = file("day2_passwords.txt", FILE_IGNORE_NEW_LINES | FILE_SKIP_EMPTY_LINES);

$valid = 0;

foreach($input as $p) {
	if(preg_match("/(\d+)\-(\d+) (.): (.+)/", $p, $m)) {
		if(!isset($m[4][$m[1]-1], $m[4][$m[2]-1]))
			continue;
		if($m[4][$m[1]-1] == $m[3] xor $m[4][$m[2]-1] == $m[3])
			$valid++;
	} else {
		echo "Failed match on: $p\n";
	}
}

echo "Valid passwords: $valid\n";
