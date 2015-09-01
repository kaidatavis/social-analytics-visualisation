# Introduction #

Currently there are **two sets of code** and **one dataset** available.

## Code ##

Available in the **'Source'** section.

**Prototype3**

This is the Java code for the visual front end. It is a NetBeans project and can't be (easily) used in Eclipse because of usage of NetBeans templates/libraries. Below is a screenshot:

![https://social-analytics-visualisation.googlecode.com/svn/trunk/Images/Prototype3-screenshot.png](https://social-analytics-visualisation.googlecode.com/svn/trunk/Images/Prototype3-screenshot.png)

**TextAnalysisOverTweetsV2**

This is the Java code for processing the collected tweets. It is a Eclipse project. The dataset available for download already contains the processing results, so you don't need to run this unless you want to re-process the data.

## Data ##

Available in the **'Downloads'** section.

There are more than 3 million tweets collected during the summer of 2011. Most of these are within the general London, UK area.

These are MySQL table dump. Due to the large size, the whole dataset is split into 5 files, which are compressed (using RAR format). It should just work if you import them into a MySQL database. The only change is to add the command for selecting MySQL schema.

Below is the table schema:

| **Column** | **Data type** |
|:-----------|:--------------|
| serial     | int(11)       |
| tweet\_id  | bigint(30)    |
| username   | varchar(40)   |
| userid     | int(11)       |
| touser     | varchar(40)   |
| touserid   | int(11)       |
| createdat  | datetime      |
| time       | timestamp     |
| profile\_image\_source | varchar(150)  |
| geolatitude | double        |
| geolongitude | double        |
| place      | varchar(200)  |
| geolocation | varchar(150)  |
| source     | varchar(400)  |
| tweettext  | text          |
| annotations | text          |
| sentiscore | double        |
| phrases    | varchar(200)  |