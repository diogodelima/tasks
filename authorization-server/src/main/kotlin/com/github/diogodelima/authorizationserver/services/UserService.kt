package com.github.diogodelima.authorizationserver.services

import com.github.diogodelima.authorizationserver.domain.User
import com.github.diogodelima.authorizationserver.repositories.UserRepository
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserService(

    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder

) : UserDetailsService {

    override fun loadUserByUsername(username: String?): User =
        userRepository.findUserByUsername(username) ?: userRepository.findUserByEmail(username) ?: throw UsernameNotFoundException("User $username not found")

    fun create(username: String, email: String, password: String) {

        userRepository.save(
            User(
                username = username,
                email = email,
                password = passwordEncoder.encode(password)
            )
        )

    }

}