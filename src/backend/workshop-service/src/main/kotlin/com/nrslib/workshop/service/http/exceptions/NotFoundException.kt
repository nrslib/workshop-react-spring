package com.nrslib.workshop.service.http.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.client.HttpStatusCodeException

class NotFoundException(
    override val message: String? = null
) : HttpStatusCodeException(HttpStatus.NOT_FOUND)