#!/usr/bin/env sh

JAR_FILENAME="$1"
MAX_HEAP_SIZE="$2"

native-image \
  -R:MaxHeapSize=${MAX_HEAP_SIZE} \
  -jar ${JAR_FILENAME}
