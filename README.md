# suburb-postcode-microservice

##### Please find the steps to build and deploy the microservice using Serverless.
###### It is assumed that nodeJS is installed and npm commands can be executed via command-line

#### Install serverless
>npm install -g serverless

#### configure AWS profile (An IAM user linked to my personal AWS account)
>serverless config credentials --provider aws --key XXXXXXXX --secret XXXXXXXXXX

#### Build the project
>mvn package

#### Deploy to AWS Lambda (Will create an API link vi API Gateway)
>serverless deploy --aws-profile sktest
```sh
Serverless: Packaging service...
Serverless: Uploading CloudFormation file to S3...
Serverless: Uploading artifacts...
Serverless: Validating template...
Serverless: Updating Stack...
Serverless: Checking Stack update progress...
............................
Serverless: Stack update finished...
Service Information
service: suburb-postcode-microservice
stage: dev
region: us-east-1
stack: suburb-postcode-microservice-dev
api keys:
  None
endpoints:
  GET - https://pnvsdw28a4.execute-api.us-east-1.amazonaws.com/dev/suburbs/postcode
  GET - https://pnvsdw28a4.execute-api.us-east-1.amazonaws.com/dev/suburbs/name
  POST - https://pnvsdw28a4.execute-api.us-east-1.amazonaws.com/dev/suburb/create
functions:
  getSuburb: suburb-postcode-microservice-dev-getSuburb
  getPostcode: suburb-postcode-microservice-dev-getPostcode
  createSuburb: suburb-postcode-microservice-dev-createSuburb

```

#### Test the created lambda function on local
>serverless invoke --aws-profile sktest --function createSuburb --path data.json
>serverless invoke --aws-profile sktest --f getSuburb --log {"pathParameters":{"value":"3008"}}
>serverless invoke --aws-profile sktest --f getPostcode --log {"pathParameters":{"value":"Docklands"}}
