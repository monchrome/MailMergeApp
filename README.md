# mailMergeApp

WORK IN PROGRESS

This is a spring boot based application, that I am building for a charity organization.  They currently have many employees who aren't very tech savvy and also do not have resources for employee traiing. They need a more intuitive way of getting their daily tasks done without much end user training.

Their main pain point today is performing mail merges to send emails across vast set of donors/patron emails. Crafting an email draft is easy for them, doing mail merge is not. Their pain point is that they come to know of  erroneous entries (names or gmail addresses) after the entire mail merge task is completed via gmail. And some one has to look into "inbox" folder , to see which recipients did not get the email and then accordingly make changes to mailmerge record source file. This is time consuming for them because they have to email tens of thousands of patrons/donors very frequently. They would prefer errors in record source ( e.g. spreadsheets in csv format), being identified upfront, before an email send operation is attempted. 

Hence this spring boot app is being worked upon as backend. It will be used via a custom frontend. This spring boot app provide api's that would capture recipient data and store in memory and report errors upfront before an email send operation is attempted via the custom frontend (being worked upon separately).

Mail Merge API requirements : 
  - MailMerge API should allow to add few records of recipients on demand or allow to upload in bulk via a csv file.
  - MailMerge API's should indicate errors in first, middle or last names and email addresses upfront.

LIST OF API's :
=> POST /v1/mailMerge/recipients/create - 
  - Input - Takes JSON array of recipients 
      -     [
        {
            "firstName": "Sam",
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
    
   

USAGE EXAMPLES :

 ![Example of error handling with create api](https://github.com/monchrome/mailMergeApp/blob/main/images/Example%20of%20successful%20create%20api%20usage.png?raw=true)

 
 ![Example of error handling with update api](https://github.com/monchrome/mailMergeApp/blob/main/images/Example%20of%20successful%20update%20api%20usage.png?raw=true)


 ![Example of error handling with create api](https://github.com/monchrome/mailMergeApp/blob/main/images/Example%20of%20error%20handling%20with%20create%20api.png?raw=true)


![Example of error handling with update api](https://github.com/monchrome/mailMergeApp/blob/main/images/Example%20of%20successful%20update%20api%20usage.png?raw=true)


![Example of exception shown in create api response when show exception stack is enabled](https://github.com/monchrome/mailMergeApp/blob/main/images/Example%20of%20exception%20shown%20in%20create%20api%20response%20when%20show%20exception%20stack%20is%20enabled.png?raw=true)


![Example of exception shown in upload api response when show exception stack is enabled](https://github.com/monchrome/mailMergeApp/blob/main/images/Example%20of%20exception%20shown%20in%20upload%20api%20response%20when%20show%20exception%20stack%20is%20enabled%20.png?raw=true)

