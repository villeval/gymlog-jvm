package gymlog.services

object UsersService {
    // todo: register service
        // todo: validate input => 400 with reason if not
        // todo: check if email already exists => 400 if already exists
        // todo: if both passed, encrypt password and store user data to database

    // todo: login
        // todo: validate input
        // todo: find user, if not found 404
        // todo: if email found, compare password (remember take encryption into account)
        // todo: create jwt payload, sign token with secret key
        // todo: return "Bearer + token" else 400 incorrect password

    // todo: get current user
        // todo: extract current user from jwt payload and return?
}