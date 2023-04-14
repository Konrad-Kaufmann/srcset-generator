# Semi automated srcset-generator
## Scope
A srcset is an html attribute that allows the browser to load and deliver an appropriately sized image. This repo provides a tool that parses a html file that doesn't have srcsets yet and adds them automaticly together with an size attribute.
This is achieved by parsing the html and searching for all images, then accesing the image and creating it in the correct sizes.
## Limitations/Issues
- only works if the image has the same size on all screen - sizes
- currently only exports .webp images
- no GUI
## Input
## Dependencies
To parse and write into the .html this tool uses [JSOUP](https://github.com/jhy/jsoup) and [webp-imageio](https://github.com/sejda-pdf/webp-imageio) support to create .webp files.
