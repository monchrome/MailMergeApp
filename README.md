# mailMergeApp

WORK IN PROGRESS

This is a spring boot based application , that I am building for a charity organization. Their specific need is to be handle mailmerge and social media posts in a more easy manner. They currently have many technically challenged people working for them and hence need a more intuitive way of getting their daily tasks done without much end user training. 
Their main pain point today wrt mailmerge is that they come to know erroneous entries ( names or gmail addresses after the entire mail merge task is completed via gmail. And some one has to look into "inbox" folder , to see which recipients did not get the email and then accordingl make changes to mailmerge record source. This is time consuming for them as they email tens of thousands of patrons/donors very frequently. Thye would prefer errors in record source being identified upfront, before an email send operation is attempted. 
Hence a spring boot api's are being worked upon as backend for a small ui app. The spring boot api's will serve as the backend for the ui app. 

Mail Merge API requirements  : 
  - MailMerge API should allow to add few records of recipients on demand or allow to upload in bulk via a csv file.
  - MailMerge API's should indicate errors in first, middle or last names and email addresses upfront.

List of API's :
=> POST /v1/mailMerge/recipients/create - 
  - Input - Takes JSON array of recipients 
      -     [
        {
            "firstName": "Sam123",
            "lastName": "Dsouza",
            "emailAddress": "samd@gmail.com"
        },
        {
            "firstName": "Alice",
            "lastName": "Laker",
            "emailAddress": "alaer@gmail.com"
        },
        {
            "firstName": "Alicia",
            "lastName": "Laker",
            "middleName" : "M",
            "emailAddress": "aliciaL@gmail.com"
        }
    ]
  - Output - Returns 201 on success or 400/500 on failure

    
=> POST /v1/mailMerge/recipients/upload
  - Input - Takes file of type "text/csv" as input mapped to key "csvfile".
  - Output - Returns 201 on success or 400/500 on failure
    
    
 
