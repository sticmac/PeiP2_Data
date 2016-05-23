#!/bin/sh
jar cvfe lemaire-novac-2.jar sticmacpiernov.spreadsheet.Spreadsheet `find . -not -path "*/.git/*" -not -type d -not -path "*/.gitignore"`
