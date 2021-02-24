# PayTM Challenge

## Moving Average datastructure
- Please find the classes and interfaces in the `src` folder
- I've created a parent interface (MovingAverage.java) for the basic methods to add, get, and check if structure is empty

- 2 possible ways to do this
    1. a datastructure with a fixed number of items
       - use a list that will pop old items once the window size is reached and only keeps n items 
       - StaticMovingAverage interface / StaticDoubleMovingAverage implementation class
        - to keep a fixed size, i remove the oldest element and add elements at the end of the list. this is because it doesn't matter which direction we traverse the list, since all `n` window elements will be accessed anyways
    2. a datastructure that keeps the past items where it's possible to find a moving average within a window of past items with an offset
       - keeps adding to end of the list
       - MovingAverageWithHistory interface / DoubleMovingAverageWithHistory implementation class
        - I add new elements to the private linkedlist at the head, ie. where the newest item has index 0
            - this allows me to work with historical elements and use an offset without having to traverse the entire linked list to access the last `n` window elements
        - with the offset index, it is possible to run into out of index exception, but i've ignored this usecase for now.
    
## Analytics system design
![](./PayTM%20Analytics%20Design.png "Logo Title Text 1")

- Decided to use Kafka as the heart of the infrastructure because it's by design able to scale and handle very well with high volume
  - consuming apps can read messsages from the past using offset indexes in kafka partitions allowing reprocessing of historical data
  - even if the consuming app is offline, messages can be read from where it was left off before shutdown, allowing loss of data to be near impossible 
- User's would use the apps which the backend springboot app would use to produce analytics metric messages to Kafka
    - DB changes can trigger and send messages to kafka using Kafka Connect
    - Backend sends messages as well using producers
- Kafka streams api is used to process stream of messages to transform and write back to kafka
- AWS s3 used for storage of data for mid-long term
- Consumers and Kafka connect would be used to process and write data to Elastic search for fast access to data
- If data is required from a long time ago, a service would be used to fetch data from S3 and produce it back into kafka which then goes into the pipeline again to be loaded into Elasticsearch
- 1 more springboot app would be used to provide an interface for the analytics dashboard apps
- for important analytics, users can setup alerts which the notification service would use to read data and decide if a notification must be sent
    - this data is stored and read from another pgSQL DB which the analytics dashboard app would use. 
