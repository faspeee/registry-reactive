# Registry Reactive

This repository contains a reactive-based application to manage a people registry system. It leverages reactive programming principles to handle large datasets and asynchronous operations efficiently.

## Features

- **Reactive People Registry**: A system to manage and query people data reactively.
- **Quarkus**: Used for reactive HTTP handling.
- **Postgresql Integration**: Stores people data in a NoSQL database.
- **Real-time Data Handling**: Optimized for high concurrency and asynchronous requests.

## Technologies Used

- **Quarkus** for backend services 
- **Mutiny** for reactive programming
- **Postgresql** for database management
- **Maven** for project management

## Setup

1. Clone the repository:
   ```bash
   git clone https://github.com/faspeee/registry-reactive.git
## Install Dependencies
cd registry-reactive
mvn clean install
## Run Application

```bash
./mvnw quarkus:dev
```

# Quarkus + Testcontainers + Docker

To work with docker, is only necessary that docker engine is running, by default Testcontainers use docker engine.

# Quarkus + Testcontainers + Podman Setup on macOS
This README explains how to run a Quarkus application using Testcontainers while using Podman instead of Docker on macOS.

### Control podman socket
if you want, you can enter inside the podman machine and check if podman socket is active, to make it, you can run the next command.
```shell
podman machine ssh
```
inside the podman machine you can run the next command:
```shell
systemctl status podman.socket
```

If status is disabled, you can run the next command to activate the podman socket, after run this command you can try again the previous command to verify the status.
```shell
sudo systemctl enable --now podman.socket
```

---

## 1. Configure Testcontainers

Create the global Testcontainers configuration file:

```bash
nano ~/.testcontainers.properties
```

Set the Docker host using your Podman machine’s socket. To find the correct path, run:

```bash
podman machine inspect --format '{{.ConnectionInfo.PodmanSocket.Path}}'
```

Then, in the `~/.testcontainers.properties` file, add:

```properties
docker.host=unix:///path/to/podman.sock
```

> Replace `/path/to/podman.sock` with the actual path returned by the `podman machine inspect` command.

---

## 2. Override the Docker socket for Testcontainers

Run the following command in your terminal:

```bash
export TESTCONTAINERS_DOCKER_SOCKET_OVERRIDE=/var/run/docker.sock
```

This ensures Testcontainers can communicate with Podman’s socket.

---

## 3. Start Quarkus

Once the configuration is done, you can run your Quarkus application:

```bash
./mvnw quarkus:dev
```

All commands above are executed in the macOS terminal.

---

> ⚠️ **Note:** This setup is specific to macOS and assumes you are using Podman with a Podman machine. Other operating systems may require different configuration.

---

## Optional: Skip Tests and DevServices

If you want to skip running tests (and avoid Testcontainers starting containers automatically), run Quarkus with:

```bash
./mvnw quarkus:dev -DskipTests
```

Or disable DevServices in `application.properties`:

```properties
quarkus.datasource.devservices.enabled=false
```

This is useful when running Quarkus locally with Podman and avoiding potential container startup issues.


## Contributing

1. Fork the repository.
2. Create a new branch (git checkout -b feature-branch).
3. Commit your changes (git commit -am 'Add new feature').
4. Push to the branch (git push origin feature-branch).
5. Open a pull request.
