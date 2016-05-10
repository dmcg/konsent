#!/bin/bash
set -e

function write_file_contents {
    echo '```kotlin'
    sed -e '1,/README_TEXT/d' -e '/README_TEXT/,$d' $1
    echo '```'
}

echo "
Konsent
=========

An acceptance testing library for Kotlin.

[KonsentExampleTests](src/test/java/com/oneeyedmen/konsent/KonsentExampleTests.kt)
shows how to write a test.
"  > README.md

write_file_contents src/test/java/com/oneeyedmen/konsent/KonsentExampleTests.kt >> README.md

echo "
This writes an approved file

\`\`\`gherkin
"  >> README.md

cat src/test/java/com/oneeyedmen/konsent/KonsentExampleTests.approved >> README.md

echo "
\`\`\`
" >> README.md