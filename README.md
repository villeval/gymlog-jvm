# Gymlog

### Starting the app
The application can be ran locally with Docker compose by running the following command:
```
bash deploy-local.sh
```

### Notes for real world deployment
- The application expects that there are three environment variables present upon deployment: ENV_CONFIG, ENC_ALGORITHM and ENC_PASSWORD. These can be given when running the Docker image.
- The app outputs logs to stdout. Depending on your platform, you can utilize stdout directly or enable logging to file, mount log directory from container to host and configure logging to needed level

### TODO:
- new front-end (for example Vue.js-based) or modifying and using the implementation from gymlog-js-project
