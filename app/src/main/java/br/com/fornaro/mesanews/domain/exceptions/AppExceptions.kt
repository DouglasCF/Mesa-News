package br.com.fornaro.mesanews.domain.exceptions

class InvalidNameException : Exception("Name is invalid")

class InvalidEmailException : Exception("Email is invalid")

class InvalidPasswordException : Exception("Password is invalid")

class InvalidConfirmPasswordException : Exception("Confirm password is invalid")