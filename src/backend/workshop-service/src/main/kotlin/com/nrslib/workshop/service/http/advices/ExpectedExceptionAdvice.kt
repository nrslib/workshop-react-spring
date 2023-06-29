package com.nrslib.workshop.service.http.advices

import com.nrslib.workshop.service.http.exceptions.NotFoundException
import com.nrslib.workshop.service.http.models.common.ErrorsResponse
import io.swagger.v3.oas.annotations.Hidden
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@Hidden
@RestControllerAdvice
class ExpectedExceptionAdvice : ResponseEntityExceptionHandler() {
    @ExceptionHandler(NotFoundException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun handle(exception: NotFoundException): ErrorsResponse {
        return ErrorsResponse.new("404", exception.message ?: "Not Found")
    }
}