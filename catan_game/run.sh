#!/bin/bash

echo "=== Compiling Catan Game ==="
javac *.java

if [ $? -eq 0 ]; then
    echo "Compilation successful!"
    echo ""
    echo "=== Running Demonstrator ==="
    java Demonstrator
else
    echo "Compilation failed!"
    exit 1
fi
