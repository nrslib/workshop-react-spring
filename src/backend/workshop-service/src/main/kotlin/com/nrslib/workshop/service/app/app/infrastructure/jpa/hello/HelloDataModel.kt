package com.nrslib.workshop.service.app.app.infrastructure.jpa.hello

import java.util.*
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "hello")
data class HelloDataModel(
    @Id
    val helloId: UUID = UUID.randomUUID(),

    @Column
    val data: String = ""
)