
# Assignment Doc: 
 https://docs.google.com/document/d/1bkOATqob7ppA0bl015S6donv_HlgN6zzUAz0pVW9YAE/edit

# Members
Sam Siggs - 16059692

Jurgen van Rooyen - 18037406

# Running the Program
### How to run
Package with maven (mvn package), and run the jar.

# Work Split
  - Sam works on front end
  - Jurgen works on back end

# Libraries
 - JavaFX
 - fxmisc
 - ODF
 - Tika
 - PDFBox

# Issue Tracking
 - Using gitlab's tracker

# Additional Features
 - Colour Schemes
 - Caret Tracker (Bottom Left hand corner of the screen)
 
### Additional Folders
#### src/main/resources/
Contains .xml config for tika so suppress optional dependency warnings

#### src/test/resources/
Contains a .txt for testing of the open function

# Significant Commits
### Sam


### Jurgen
* 15f2927b
* 50000971

# Additional Functions
### Reading File Types
The application will read most common text files (.txt, .pdf, .odt, .doc, .docx) and if the extension is missing it is able to infer the file type and load it.

