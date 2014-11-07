GoInChI
=======

GoInChI is an ultra light-weight HTTP GET client that facilitates various format conversion both "to and from" InChI and InChIKey. Format conversion might be erraneous when novice programmers start using the chemoinformatics APIs, especially when you talk about InChIs and InChIKeys. Although existing tool kits provide such methods, InChIKey needs delicate handling and, I believe, there are no methods in open source tool kits to convert it back into reusable formats likes SMILES/InChI/MOL. This is of great importance since InChIkeys are apparently used as identifiers to indexing chemical compound libraries.

GoInChI can be used in your Java projects directly by copying the source files or as a library (JAR). The best part is that you need no chemoinformatics tool kit or API to use GoInChI.

###### How to use:

Execute the following commands to build the project locally and find the GoInChI.jar in the dist folder. Use the JAR file wherever required.

$ git clone https://github.com/vishalkpp/GoInChI.git
$ cd GoInChI/
$ ant

It is also convenient to use the source files directly in your project.

###### Acknowledgment:

GoInChI, shamelessly relies on the ChemSpider's InChI Webservice.
