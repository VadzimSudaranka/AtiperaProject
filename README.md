
# AtiperaProject2.0

The AtiperaProject is a Java application that retrieves information about GitHub repositories and their branches for a given user.

## Installation

1. Clone the repository:

```bash
git clone https://github.com/VadzimSudaranka/AtiperaProject.git
```

2. Navigate to the project directory:

```bash
cd AtiperaProject
```

3. Build the project using Maven:

```bash
mvn clean install
```

## Usage

1. Run the application using Maven:

```bash
mvn spring-boot:run
```

2. Make a GET request to the following endpoint:

```
GET localhost:8080/repositories/{userName}
```

Replace `{userName}` with the GitHub username for which you want to retrieve repository information.

3. Include the `Accept` header in the request with the value `application/json`.

4. The response will contain the repository information in the following format:

```json
[
    {
        "repositoryName": "repository name",
        "ownerLogin": "owner login",
        "branches": [
            {
                "branchName": "branch name",
                "lastCommit": "last commit"
            },
            {
                "branchName": "branch name",
                "lastCommit": "last commit"
            }
        ]
    },
    ...
]
```

## Error Handling

- If the specified user is not found, a `404` status code will be returned with the following error message:

```json
{
    "status": 404,
    "message": "User not found"
}
```

- If an incorrect `Accept` header is provided in the request, a `406` status code will be returned with the following error message:

```json
{
    "status": 406,
    "message": "Wrong header provided"
}
```

- If any other error occurs during the GitHub API call, an appropriate status code and error message will be returned.

## Dependencies

The AtiperaProject application uses the following dependencies:


- Spring boot starter Web
- Lombok
- json
- Maven

## Contributing

Contributions are welcome! If you have any suggestions or find any issues, please create a new issue or submit a pull request.

## License

This project is licensed under the [MIT License](LICENSE).

Feel free to customize this README file based on your specific requirements and add any additional sections or information as needed.