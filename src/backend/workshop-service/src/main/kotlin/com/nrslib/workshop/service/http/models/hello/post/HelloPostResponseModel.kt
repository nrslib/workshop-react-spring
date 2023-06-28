package com.nrslib.workshop.service.http.models.hello.post

import java.util.UUID

data class HelloPostResponseModel(val helloId: UUID, val data: String)
