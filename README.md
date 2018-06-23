# Tax Problem App
This app is to resolve the Tax Problem given by American Express Task.

### What is this repository for? 

The main purpose of this repository is to showcase my android skills and solve the Tax Problem. To solve this problem, i have used the below Android Tools and API's:

* Android Studio (IDE)

* Activity/Fragment for the Screens.

* RecyclerView for Shopping Items List

* Architectural Components (ViewModel, LiveData, Room Database)

* RX Java for Reactive Call backs.

* Expresso for UI Testing.

### How do I get set up? ###
1. In the root folder, I have provided the Android Package "tax-problem.apk"

2. You can run the command "adb install tax-problem.apk" to install the App. 

3.  Or you can open the Project in Android Studio and Run.

4. or use the given gradlew to install the app using the command "gradlew installdebug".

### How do I set up Data? ###
1. In the Main Screen, Select Options and Click on Add Shopping Item.

2. Give values to the fields "Item Name, Item Price, is Imported and is Exempted".

3. Click Save Icon on the Tool Bar.

4. Repeat these steps until you add all the shopping Items.

5. Now, In the Main Screen, click on '+' icon to give the qty.

6. Repeat the 5th step until you have all the required items for Check Out.

7. In Toolbar | Settings, Select Check out to see the Reciepts with the calculated Taxes.

8. Repeat the Steps 5 - 7 to solve all the 3 problems.

### Who do I talk to? ###
* Nagaraj Dandey, Developer -  ndandey@prokarma.com

### Assumptions ###
* I assumed, the maximum Quantity, the shopper can add is 10.