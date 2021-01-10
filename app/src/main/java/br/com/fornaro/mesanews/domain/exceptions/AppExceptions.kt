package br.com.fornaro.mesanews.domain.exceptions

class InvalidNameException : Exception("Name is invalid")

class InvalidEmailException : Exception("Email is invalid")

class InvalidPasswordException : Exception("Password is invalid")

class InvalidConfirmPasswordException : Exception("Confirm password is invalid")

class InvalidDateFormatException : Exception("Date format is invalid")

class UserNotLoggedInException : Exception("User is not logged in")