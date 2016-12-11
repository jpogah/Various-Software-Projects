Grocery List Manager Application
===================

This design document formulates the design decisions made and rationale behind them to come up with the design for the Grocery List Manager application.

-------------

Classes
-------------
#### GroceryListManager Class

The GroceryListManager is the main class in the application whose job is to initialize all objects including the DBInterface Class upon launching the application. It enables the user to manager his/her grocery lists, where the user can create, delete and edit them.

##### Attributes:
 - groceryLists (List{GroceryList}) :  This attribute is used to reference all GroceryList objects in the application.
 - DBCOnn (DBInterface) : This instance of DBInterface allows the GroceryListManager class to execute DB commands on the database linked to the application.

##### Methods:
 - void createGroceryList(name : String) : This will create and initialize a new GroceryList instance and adds it to groceryLists attribute. This will update the database accordingly by running DBConn.addNewListToDB(newList)
 - void renameGroceryList(listName : String , newName : String ) : This will change the listName attribute, of GroceryList instance that has its name attribute = listName, to newName. listName and newName are to be passed from the GUI. This will also update the database accordingly by running DBConn.renameGroceryListInDB(groceryList, newName)
 - void deleteGroceryList(groceryListName : String) : This will delete GroceryList instance, that has its name attribute = groceryListName, from groceryLists attribute and update the database accordingly by running DBConn.deleteListFromDB(groceryList)
 - GroceryList selectGroceryList(listName : String) : This will allow user to choose among list of Grocery List. listName is the name of the list User wants to choose.
 
#### GroceryList Class

The GroceryList class's job is to maintain the items on the grocery list. It enables to edit the contents of the grocery list by adding, deleting or editing items from the list. It also allows the user to add new items to the database.

##### Attributes:
 - listName (String) : This attribute represents the name of the list to be displayed to the user.
 - itemsList (List{GroceryItem}) : This attribute is used to reference all GroceryItem objects in this specific grocery list
 - DBCOnn (DBInterface) : This instance of DBInterface allows the GroceryList class to execute DB commands on the database linked to the application.
 
##### Methods:
 - List{Map{String,List{String}}} getHierarchicalList() : This method is invoked when the user chooses the option to add an item to the grocery list. Once this method is invoked it will open an interactive window that will let the user choose one of two options. The first option is to choose the item from a predefined hierarchical view of itemtypes and items. This is done by running the getUpdatedTypeItemList method ofDBInterface to get the list of item type and its items. After the user selects the item from the hierarchical view and enters the quantity required, the addItemToList method (see below) will be invoked. 
 - boolean searchItem(itemName:String) : The second option is to search for an item. The user will enter a certain text. searchItem method will be called which internally calls searchItemDB method of DBInterface to search the text in the database. If the user chooses one of the returned results, then he/she will enter the quantity and addItemToList method (see below) will be invoked. If the item the user is looking for is not in the list, then a new item will be created using the itemtype and name specified by the user and a GroceryItem class will be added with the quantity and default checkoff = false. After the creation of the item, the item will be added to attribute itemsList and also method addNewItemToDB(newItem,itemType) will be called (see below).
 - void addItemToList(name : String, itemType : String, quantityAmount : double, quantityUnit : String) : This method takes as input the itemType, itemName, quantityAmount and quantityUnit from addItemInterface() method and will instantiate a new GroceryItem and add that new GroceryItem to itemsList. This will also update the database accordingly  by running DBConn.addGroceryItemToListInDB(groceryItem)
 - void changeQuantity(itemName : String, itemType : String, newQuantityAmount : double, newQuantityUnit : String) : This method will take as input Strings itemName and itemType and a quantityAmount and quantityUnit. It will then call the setterfunction for both quantity attributes in GroceryItem instance, which extends an Item with itemName and itemType as its attributes, and update their values. Also, it will update the database accordingly by  running DBConn.changeGroceryItemQuantityInDB(groceryItem)
 -void deleteItemFromList(itemName : String, itemType : String) : This method will take as input Strings itemName and itemType. It will then delete GroceryItem instance, which extends an Item with itemName and itemType as its attributes, and remove it from attribute itemsList. Also, it will update the database accordingly by running DBConn.deleteGroceryItemFromListInDB(groceryItem)
 - void checkoffItem(itemName : String, itemType : String, checkoff : boolean) : This method will take as input Strings itemName and itemType and a boolean specifying whether to check the item off or put it back on the list.  It will then call the checkoff attribute setterfunction for GroceryItem instance, which extends an Item with itemName and itemType as its attributes. To make this data persistent, it will update the database accordingly by running DBConn.checkOffGroceryItemInDB(groceryItem, checkoff)
 - void checkoffAllItems(checkoff : boolean) : This method will take as input a boolean specifying whether to check all items on the list off or put them back on the list. Also, it will update the database accordingly by running DBConn.checkOffAllGroceryItemsInDB(groceryItemList, checkoff)
 Notes:
 - quantityUnit can be taken from a predetermined dropdown list containing most used units of measure. Also, the user is allowed to put his/her own text to specify the unit. For example, the user might choose to insert 2 for quantityAmount and "20g boxes" or "dozen" for quantityUnit for a specific Item.

#### GroceryItem Class

The GroceryItem class represents an Item that is added to a grocery list.

##### Attributes:
 - quantityUnit (String) : This attribute represents the unit that the quantityAmount is measured in. As mentioned above in GroceryList class notes that this can be a manual input by the user.
 - quantityAmount (double) : This attribute specifies the amount of quantityUnits that he/she should put on the grocery list.
 - checkoff (boolean) : This attribute specifies whether the item is checked off or still remaining on the list.
 
#### Item Class

The Item class represents an item before it is added to a grocery list.

##### Attributes:
 - itemType (String) : This attribute is used to distinguish the itemType related to this Item. These itemtypes are set in the databases and can only be selected (when adding a new item to the database) via a dropdown list.
 - itemName (String) : This attribute represents the name of the Item to be displayed to the user.

#### DBInterface

The class is meant to be the single point of contact with the database. It is a helper class that allows other classes to manipulate the database and perform operations.

##### Attributes:
 - DBUrl (String) : This attribute specifies the location of the DB, including the port number to communicate with it.
 - DBUsername (String) : This attribute specifies the username that is used to authenticate with the DB.
 - DBPassword (String) : This attribute specifies the password that is used to authenticate with the DB.
 - typeItemList:List<Map<String,List<String>>> : This attribute specifies the list of item types and list of items for each item type.

##### Methods:
 -  getUpdatedTypeItemList() : This will return the updated list of item types and list of items for exh item type.
 - addNewListToDB(newListName: String) : This method will add newList to the database.
 - renameListInDB(currentListName : String, newListName : String) : This method will rename groceryList's name in the database.
 - deleteListFromDB(listName : String) : This method will delete groceryList from the database
 - addGroceryItemToListInDB(listName:String, itemName:String, checkoff:boolean, quantityAmount:double, quantityUnit:String) : This methos is called to add the grocery item to list in saved in the database.
 - addNewItemToDB(newItem : String, itemType: String) : This method will add a new Item to the database and also add it to typeItemList attribute of DBInterface.
 - changeGroceryItemQuantityInDB(listName:String, itemName:String, newQuantityAmount:double) : This will update the quantity values for the groceryItem itemName in the listName grocery list in the database.
 - deleteGroceryItemFromListInDB(listName:String, itemName:String) : This will delete this groceryItem record in the database from the listName grocery list.
 - checkoffGroceryItemInDB(listName:String, itemName:String, checkOff:boolean) : This method will update the checkoff value for the itemName groceryItem in the database for the listName grocery list.
 - checkoffAllGroceryItemsInDB(listName:String, checkoff : boolean) : This method will update the checkoff values for all groceryItem records in the  listNamr grocery list in the database.
 - searchItemDB(itemName : String) : boolean : This method is used to search for an item in the database.

-------------

Requirement Realization
-------------
#### Requirement #1
 >A grocery list consists of items the users want to buy at a grocery store. The application must allow users to add items to a list, delete items from a list, and change the quantity of items in the list (e.g., change from one to two pounds of apples).

##### Realization
 This requirement is realized by creating the GroceryList class and adding addItemToList, deleteItemFromList and changeQuantity methods to the class.

#### Requirement #2

 >The application must contain a database (DB) of items and corresponding item types.

##### Realization
 This requirement is realized by having a database infrastructure that is linked to the application. Furthermore, from the design point of view, classes have a link to a helper class that integrates with the DB for ease of use.
 
#### Requirement #3
   >Users must be able to add items to a list by picking them from a hierarchical list, where the first level is the item type (e.g., cereal), and the second level is the name of the actual item (e.g., shredded wheat). After adding an item, users must be able to specify a quantity for that item.

##### Realization
 This requirement is realized in the design by creating getHierarchicalList method and addITemToList methods in GroceryList class. (see explanation of method above for more information)

#### Requirement #4
 >Users must also be able to specify an item by typing its name. In this case, the application must look in its DB for items with similar names and ask the users, for each of them, whether that is the item they intended to add. If a match cannot be found, the application must ask the user to select a type for the item and then save the new item, together with its type.

##### Realization
 This requirement is realized in the design by creating getHierarchicalList, searchItem and addItemToList methods in GroceryList class and addNewListToDB method in DBInterface class. This allows the user to choose between two methods for adding the item and perform operations according to the users input and interaction with the UI.

#### Requirement #5
 >Lists must be saved automatically and immediately after they are modified.

##### Realization
 This requirement is realized by updating the DB after every operation done on the objects of any class. Also, all classes are mapped accordingly in the database.

#### Requirement #6
 >Users must be able to check off items in a list (without deleting them).

##### Realization
 This requirement is realized in the design by creating checkoffItem method  in GroceryList class, and also adding the checkoff boolean in the GroceryItem class. Furthermore, an implicit requirement is met by allowing the user to put back an item on the list after he/she checked it off.

#### Requirement #7
 >Users must also be able to clear all the check-off marks in a list at once.

##### Realization
  This requirement is realized in the design by creating checkoffAllItems method  in GroceryList class which takes a boolean as input (to specify whether to checkoff or clear all checkoffs), and also adding the checkoff boolean in the GroceryItem class. Furthermore, an implicit requirement is met by allowing the user to check off all items on the list at once

#### Requirement #8
 >Check-off marks for a list are persistent and must also be saved immediately.

##### Realization
 This requirement is realized by adding the checkoff boolean in the GroceryItem class. Also, by updating the DB after running checkoffItem or checkoffAllITems in GroceryList class

#### Requirement #9
 >The application must present the items in a list grouped by type, so as to allow users to shop for a specific type of products at once (i.e., without having to go back and forth between aisles).

##### Realization
 This requirement is a pure UI requirement and does not affect the design.
 
#### Requirement #10
 >The application must support multiple lists at a time (e.g., <93>weekly grocery list<94>, <93>monthly farmer<92>s market list<94>). Therefore, the application must provide the users with the ability to create, (re)name, select, and delete lists.

##### Realization
 This requirement is realized by creating the GroceryListManager class and createNewList, renameList, selectList and deleteList methods.

#### Requirement #11
 >The User Interface (UI) must be intuitive and responsive.

##### Realization
 This requirement is a pure UI, optimization and performance requirement which does not affect the design. Further efforts will be exerted at a later stage in the project to realize this requirement

