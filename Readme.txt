
Description:

The sample demonstrates how SAPLumira can obtain data from MongoDB . Data from three different blog posts is retrieved
and inserted into mongodb after removing stopwords. You should be able to find the database used in the sample inside "WebpageDB" folder 
of this project .

The sample application also demonstrates how one can use SAPUI5 to build any UI Elements which 
would be required for enabling user to enter information about mongoDatabase.


Folder structure :

"src" : Contains source code to retrieve data from MongoDB using java-mongo driver.

"webcontent": Contains the sapui5 resources, and also the htmlpage which enables endusers to input db specific information.

"help" : Contains this "readme.txt"

"libs" : The libs required.

"WebpageDB" : Which contains sample JSON file that should be imported into a mongodb.                


Database for the sample :

Steps to create sample database ..

1) go the webpagedb folder in the project . You should be able to see "webpage.json" file there.

2)To import the json file into mongodb , execute the below command.
   a) mongoimport --db MongoWebpageDB --collection webpage --file webpage.json

3) The above step shall import the data into mongodb which will be used
   for the sample application to function.

How to run :

1) You need to export this project as runnable jar.
2) You need to make this jar a win32 executable . So one can use Launch4j to generate exe after giving this jar as input
3) Put the executable inside daextension folder (something like :"C:\Program Files\SAP Lumira\Desktop\daextensions)
4) Choose external datasources in lumira while creating new dataset and point to the "exe" generated in the step 2.
 