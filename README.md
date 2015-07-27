# SAM3 Tagger
This **S**emi-**A**utomatic **M**P**3** **Tagger** is a pretty simple and useful tool with the goal of fix ID3 tags on mp3 files. Many downloaded music packs have mp3 files with incorrect ID3 data, but most of the time the filenames are preserved. An example of these filenames can be seen below:

- "01. R.E.M. - Everybody Hurts.mp3"
- "16. Roger Waters - Every Strangers Eyes.mp3"
- "28. Dio - All The Fools Sailed Away.mp3"

If we take a closer look we can see a straightforward pattern:
- "**[TRACK NUMBER]**. **[ARTIST / BAND]** - **[MUSIC NAME]**.mp3"

Take into account all this information SAM3 asks two inputs to accomplish its task: FILENAME PATTERN and a FOLDER. When it runs all mp3 files inside the specified FOLDER will have the ID3 tag automatic fixed. The FILENAME PATTERN is a combination of three supported patterns: [TRACK NUMBER], [ARTIST / BAND] and [MUSIC NAME]. Below are some examples of filenames and the FILENAME PATTERN that must be provided to SAM3 Tagger. To simplify FILENAME PATTERN input SAM3 has the following convention:

[TRACK NUMBER] -> [1]
[ARTIST / BAND] -> [2]
[MUSIC NAME] -> [3]

Examples:
- "#01__Bon Jovi__Always.mp3" -> "#[1]__[2]__[3]"
- "Gamma Ray - Pray.mp3" -> "[2] - [3]"
- "The Unforgiven. Metallica__01.mp3" -> "[3]. [2]__[1]"
