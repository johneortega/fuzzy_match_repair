#!/usr/bin/perl

use warnings;
use strict;
use utf8;

binmode STDIN, ":utf8";
binmode STDOUT, ":utf8";
binmode STDERR, ":utf8";

my ($lm, $out, $in);

while (<>)
{
	if (m!<e lm="([^"]*)" a="eleka"><i>([^>]*)</i><par n="abát/ir__vblex"/></e>!) {
		$lm=$1;
		$in=$2;
		$out = $2; 
		$out =~ tr/áéíóú/aeiou/; 
		print "<e lm=\"$lm\" a=\"eleka\"><p><l>$in</l><r>$out</r></p><par n=\"abát/ir__vblex\"/></e>\n"; 
		print STDERR "$1:$2\n";
	} else {
		print;
	}
}
