# MP4Cutter (Java)

MP4Cutter is a basic Java tool built to parse `.mp4` file atoms/boxes like `ftyp`, `moov`, `mdhd`, and more. It was primarily created as a learning project to better understand the MP4 file structure and binary file handling in Java.

## Project Status

This project is currently inactive. Development was last active around 4–5 months ago. The tool is not abandoned, but it is not being actively worked on at the moment. Editing or modifying MP4 content is out of scope for now, i yet havent figured out a way to parse and store nested atoms.

## Features

- Parses basic MP4 atoms including:
  - `ftyp`
  - `moov`
  - `mvhd`
  - `tkhd`
- Uses standard Java file I/O (no external libraries)

## Limitations

- Does not support editing or modifying MP4 files
- Does not cut or trim videos
- Does not parse all atom types
- No graphical interface
- Not production-ready

## Technologies Used

- Java 17+
- Plain Java I/O with `FileInputStream`, `DataInputStream`, etc.

