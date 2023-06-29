package com.nrslib.workshop.service.app.app.infrastructure.jpa.hello

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface HelloRepository : JpaRepository<HelloDataModel, UUID>