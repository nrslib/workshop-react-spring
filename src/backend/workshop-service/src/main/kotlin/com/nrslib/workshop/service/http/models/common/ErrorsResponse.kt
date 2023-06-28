package com.nrslib.workshop.service.http.models.common

import io.swagger.v3.oas.annotations.media.Schema

@Schema(
    description = "エラー時共通モデル"
)
data class ErrorsResponse(val errors: List<ErrorModel>) {
    companion object {
        fun new(code: String, message: String?): ErrorsResponse {
            val errors = listOf(ErrorModel(code, message))
            return ErrorsResponse(errors)
        }

        fun new(code: String): ErrorsResponse {
            val errors = listOf(ErrorModel(code, null))
            return ErrorsResponse(errors)
        }
    }
}

@Schema(
    description = "エラーモデル"
)
data class ErrorModel(val code: String, val message: String?)