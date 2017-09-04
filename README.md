# weather-app-microservice

##### Please find the steps to build and deploy the microservice using Serverless.
###### It is assumed that nodeJS is installed and npm commands can be executed via command-line

#### Install serverless
>npm install -g serverless

#### configure AWS profile (An IAM user linked to my personal AWS account)
>serverless config credentials --provider aws --key XXXXXXXX --secret XXXXXXXXXX

#### Build the project
>mvn package

#### Deploy to AWS Lambda (Will create an API link vi API Gateway)
>serverless deploy
```sh


```
#### Test by opening the below URL on any browser
>

#### Test the created lambda function on local
>serverless invoke --function suburb --log {"pathParameters":{"id":"7839805"}}