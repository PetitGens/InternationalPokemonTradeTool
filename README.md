# International Pokémon Trade Tool

A Java program for trading Pokémon in Gen 1 (and later Gen 2) games in any language using backed-up save files.
This works with both GB and 3DS Virtual Console saves.

This project is still in development and currently only offers a command-line interface.
Gen 2 support and GUI should come in the near future.

## Why would I need this?

In generations 1 and 2, the Japanese Pokémon games were only compatible with other Japanese games due to different character encodings and different string lengths. The same also applies for Gen 2 Korean games, which are only compatible with themselves.

This tool allows you to bring your Japanese Pokémon to your Western save file and vice versa. However, nicknames and trainer names cannot be translated, so these are not kept right now.

I plan on storing every nickname and trainer name so that they are put back on the Pokémon if it travels back to its original game.

## How do I use this program?

(This program requires Java SE 10 or later; you can download Java SE here if you need to: https://www.oracle.com/fr/java/technologies/java-archive-javase10-downloads.html)

- Download the latest release
- Extract the zip at the location that is most convenient for you
- Place the two save files in the same folder as the program
- Start the app:
    - in a shell or terminal: $java -jar trade-tool.jar
    - by executing the right script file (start.bat on Windows; start.sh on MacOS or Linux)

## Feature Details

### Trade

Trading is the primary feature of this application (and pretty much the only one right now).

To do a trade, please follow these steps:

1. Type the path of the first save file (if you placed it in the application's folder, just type the file's name).
2. Do the same for the second save file.
3. Input the position of the Pokémon you want to trade for the first save file:
    - one number if the Pokémon is in the party (example: 5 for the fifth Pokémon of the party)
    - two numbers if the Pokémon is in a box: first the Pokémon's position within the box, then the box number (example :  2 5 for the second Pokémon in the fifth box)
4. Your Pokémon have now been traded, and the two save files have been written (assuming no exception says it's not the case).
If you want to do another trade with these save files, repeat step 3. If you want to stop trading, just close the console or terminal.

### Automatic Backup

- The application automatically backups any opened save file in the "backups" folder located in the application's folder.
- Any backup file older than 7 days is deleted each time the application starts, except if no backup was created in the last 7 days for a particular save file (meaning if you haven't opened a save file named "green.sav" for more than one week, the most recent backup for this file won't be deleted).