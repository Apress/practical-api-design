#!/bin/sh
hg log --template '0' . | wc -c
