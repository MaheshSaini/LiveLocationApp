# APP developer Machine coding question
 Description:
 Implement a trip management app.
 Feature:
 Application should allow to start and end a trip
 Location will be tracked for the duration of the trip.
 Location should be tracked at a regular time interval of 5 seconds
 Locations should be described as an JSON output.
 Sample Output:
 {
  "trip_id": "1",
  "start_time":"2021-04-06T10:10:10Z",
  "end_time": "2021-04-06T11:11:11Z",
  "locations":[
  {
  "latitude": 1.235,
  "longitide": 8.984,
  "timestamp": "2021-04-06T10:10:10Z",
  "accuracy": 10.9
  },
  {
  "latitude": 1.235,
  "longitide": 8.984,
  "timestamp": "2021-04-06T10:15:10Z",
  "accuracy": 10.0
  }
  ]
 }
 Expectation:
 Location should be tracked at any state of app - foreground, background.
 Location data should have coordinates, timestamp of recording, accuracy.
 Use any form of data storage to save the data locally within the app. (eg: embedded database, local file, sharedpreferences,
 nsuserdefaults).
 Basic UI for trip management and data export.
 Work on the expected output first and then add good-to-have features of your own.