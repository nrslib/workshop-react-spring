package com.nrslib.workshop.service.http.controllers.hello

import com.amazonaws.xray.spring.aop.XRayEnabled
import com.nrslib.workshop.service.app.app.infrastructure.jpa.hello.HelloDataModel
import com.nrslib.workshop.service.app.app.infrastructure.jpa.hello.HelloRepository
import com.nrslib.workshop.service.http.exceptions.NotFoundException
import com.nrslib.workshop.service.http.models.hello.get.HelloGetResponseModel
import com.nrslib.workshop.service.http.models.hello.post.HelloPostRequestModel
import com.nrslib.workshop.service.http.models.hello.post.HelloPostResponseModel
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

@Tag(description = "Hello API", name = "Hello API")
@Validated
@XRayEnabled
@RestController
@RequestMapping("/api/hello")
class HelloController(
    private val helloRepository: HelloRepository
) {
    @GetMapping("{helloId}")
    fun get(@Schema(example = "9daf2971-bc66-426d-75e1-db9018ee4ce1") @PathVariable("helloId") helloId: UUID): HelloGetResponseModel {
        return helloRepository.findById(helloId)
            .map {
                HelloGetResponseModel(
                    it.helloId
                )
            }.orElseThrow{
                NotFoundException()
            }
    }

    @PostMapping
    fun post(@RequestBody request: HelloPostRequestModel): HelloPostResponseModel {
        val id = UUID.randomUUID()

        val data = HelloDataModel(
            id,
            request.data
        )

        helloRepository.save(data)

        return HelloPostResponseModel(
            UUID.randomUUID(),
            request.data
        )
    }
}