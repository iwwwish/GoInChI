GoInChI
=======

GoInChI is an ultra light-weight HTTP GET client that facilitates various format conversions both "to and from" InChI and InChIKey. 

Format conversion might be erraneous when novice programmers start using the chemoinformatics APIs, especially when you talk about InChIs and InChIKeys. Although existing tool kits provide such methods, InChIKey needs delicate handling and, I believe, there are no methods in open source tool kits to convert it back into reusable formats likes SMILES/InChI/MOL. This is important since InChIkeys are apparently used as identifiers to index chemical compound libraries/databases.

###### How to use:

    git clone https://github.com/vishalkpp/GoInChI.git
    cd GoInChI/
    ant
  
Execute the above commands to build the project locally and find the GoInChI.jar in the dist folder. Use the JAR file wherever required. It is also convenient to use the source files directly in your project.

###### Acknowledgment:

GoInChI, shamelessly relies on the ChemSpider's InChI [Webservice](https://www.chemspider.com/InChI.asmx).
