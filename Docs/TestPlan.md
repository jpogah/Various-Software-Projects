# Test Plan

**Author**: Team69

| Version   | Description                         | 
| ----------|------------------------------------ |
| V1        | Initial draft of the document       |
| V2        | More comprehensive test suite added |
| V3        | Changes made for Alpha Release      |

## 1 Testing Strategy

### 1.1 Overall strategy
The first activity that our team will perform is unit-testing. During the unit-testing we will test individual components separately of the application. After unit-testing we will perform integration-testing which will help us to examine how multiple components interact with each other. For the third step we will perform system-testing. This test will help us to verify the functionality provided by the application and it will also help us to measure non-functional qualities of the system. The last step is regression-testing. This action will take place every time when there's an update in the code. This way we will make sure that the application delivers the desirable results and runs smoothly. Our plan is to make the entire testing process automated so every time we update the code we will be able to run the entire testing process with a simple click on the button.


### 1.2 Test Selection

Since we don't have the actual code of the application yet we will use the black-box technique to select our test cases. We already have the system specifications expressed in UML class diagram. Black-box testing will help us the test the logic of the application and it also can be performed during all granularity levels.

### 1.3 Adequacy Criterion

In order to assess the quality of our testing cases we will start with analyzing by function specifications of the application. The next step will be to identify independently testable features followed by identifying relevant inputs. For the relevant inputs we will use the boundary values (spaces, empty string, special characters, negative values ) to test each specification from every corner. The next step will be to identify constraints among the input values and derive with quality test cases.

### 1.4 Bug Tracking

As mentioned in section 1.1 we are going to make regression-testing automated. During the regression-testing we will make sure that any enhancement in the code syncs perfectly with the entire system and doesn't cause any issues. Regression-testing will also detect bugs and we can backtrack form there (system => integration => unit levels).

### 1.5 Technology

Manual testing will be the methodology used to test the application

## 2 Test Cases


<table>
<tr><th>Test Case</th><th>Purpose</th><th>Steps</th><th>Expected Result</th><th>Actual Result</th></tr>

<body>

<tr><td>Create a new List</td><td>The user shall be able to create a new list</td><td>1.Open the app 2.Click on "Create New List" 3.Enter the name of the new list</td><td>New Grocery list should be created</td><td> Pass</td></tr>

<tr><td>List name must be alphanumeric and not empty</td><td>The must enter a list name, list name must not be empty</td><td>1.Open the app 2.Click on "Create New List" 3.Leave the text field empty 4.Click on Add List</td><td> Message should be display saying "List name must not be empty"</td></tr>


<tr><td>Create multiple lists</td><td>The user shall be able to create more than one list</td><td>1.Open the app 2.Click on "Create New List" 3.Enter the name of the new list 4.continue step 2 and 3 to create one more list</td><td>Multiple grocery lists should be created</td><td> Functionality to be delivered with next release</td></tr>

<tr><td>Create List with already existing list name</td><td>The user shall not be able to create new list with existing list name</td><td>1.Open the app 2.Click on "Create New List" 3.Enter the name of the new list same as already existing list name</td><td>New Grocery list should not be created with error saying "Create new list failed as list name already exist"</td><td>Functionality to be delivered with next release</td></tr>

<tr><td>Rename List</td><td>The user shall be able to rename the list which is already created</td><td>1.Click on the list 2. select pencil mark to edit the list 3. Enter new name for the list 4.Click OK</td><td>List should be displayed with the new name</td><td>Functionality to be delivered with next release</td></tr>

<tr><td>Rename List with the same as already existing list</td><td>The user shall not be able to rename the list to already existing list name</td><td>1.Click on the list 2. select pencil icon to edit the list 3. Enter new name for the list which is already used 4.Click OK </td><td>Error message pops up saying "This list name already exist.Please provide new name"</td><td>Functionality to be delivered with next release</td></tr>

<tr><td>Select List</td><td>The user shall be able to choose a list among multiple grocery lists</td><td>1.Click on the list displyed among multiple lists</td><td>Chosen list should be the current list</td><td>Functionality to be delivered with next release</td></tr>

<tr><td>Delete List</td><td>The user shall be able to delete a list among multiple grocery lists</td><td>1.Click on list to delete 2.Click on trash can icon 3.Click "OK" on confirm wimdow</td><td>Choosen list should be deleted and removed from list display</td><td>Functionality to be delivered with next release</td></tr>

<tr><td>Add item to list</td><td>The user shall be able to add item to the current list</td><td>1.Click on the list to add item 2.Click on Add Item button 3.New window opens with list of items in the database with item type 4.Choose item by clicking on it</td><td>Choosen item should be displyed as part of the current list under its item type</td><td>Pass</td></tr>

<tr><td>Items in Grocery List must be unique</td><td>The user should not be allowed to add the same item to the same list twice</td><td>1.Click on the list to add item 2.Click on Add Item button 3.Select an Item that already exist in the list</td><td>Display message that says"Item already exist in the list"</td><td>Pass</td></tr>


<tr><td>Delete item from the list</td><td>The user shall be able to delete item from the list</td><td>1.Click on the list to delete an item 2.Click on trash can icon against the item to be deleted</td><td>Choosen item should be removed from the current list under its item type. If there is no items under that item type, item type should be removed from the current list</td><td>Functionality to be delivered with next release</td></tr>

<tr><td>Change quantity of the item from the list</td><td>The user shall be able to change the quantity of the item added to the current list</td><td>1.Click on the list to add item 2.Enter quantity and its unit </td><td>Entered quantity along with the unit of measurement should be displayed for the list and this information is saved in database</td><td>Functionality to be delivered with next release</td></tr>

<tr><td>Search for an item which exist in the database</td><td>User shall be able to search for an item by typing its name in the search bar</td><td>1.Enter the item in the search box at the top 2.A drop down menu displayes the existing match for the entered string 3.Choose one of the match 4.click add button yo add it to the current list</td><td>Searched item should be added to the current list</td><td>Functionality to be delivered with next release</td></tr>

<tr><td>Search for an item which does not exist in the database</td><td>User shall be able to search for an item by typing its name in the search bar and add new item to the database if it doesn't exist</td><td>1.Enter item in the search box 2.Message displayed that there is no match found 3.Click on save button to add it to the database 4.Drop down window opens with list of item types to choose from 5.Choose item type and click OK</td><td>Searched item should be added to the current list under chosen item type and to the database</td><td>Functionality to be delivered with next release</td></tr>



<tr><td>Close and open the app</td><td>Changes to the list should be available after close and opening the app</td><td>1.Add item to the list 2.Close the app 3.Open the app again 4.Look for the item in the list</td><td>Newly added item along with other deatils should be available</td><td>Functionality to be delivered with next release</td></tr>

<tr><td>Check off items in a list</td><td>User should be able to check off an item in the list</td><td>1.Click on the list to display 2.Click on check box against the item</td><td>Check off should be visible against the item</td><td>Functionality to be delivered with next release</td></tr>

<tr><td>Clear all check off marks in a list at once</td><td>User should be able to clear all check off at once from the current list</td><td>1.Click on the list to display 2.Un Click on check box at the top againt the list name</td><td>Check off should be removed from all the items in the current list</td><td>Functionality to be delivered with next release</td></tr>


<tr><td>Check off marks should be saved</td><td>User should be able to check off an item in the list and should not worry about loosing that information even after closing the app</td><td>1.Click on the list to display 2.Click on check box against the item 3.Close the app. 4.Open the app. </td><td>Check off should be visible against the item</td><td>Functionality to be delivered with next release</td></tr>




</tbody>
</table>


