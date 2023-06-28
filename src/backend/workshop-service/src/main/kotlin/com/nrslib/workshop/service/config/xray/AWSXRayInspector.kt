package com.nrslib.workshop.service.config.xray

import com.amazonaws.xray.AWSXRay
import com.amazonaws.xray.entities.Subsegment
import com.amazonaws.xray.spring.aop.AbstractXRayInterceptor
import com.amazonaws.xray.spring.aop.XRayInterceptorUtils
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Pointcut
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression
import org.springframework.stereotype.Component
import java.util.*

@Aspect
@Component
@ConditionalOnExpression("\${application.enable-xray:true}")
class AWSXRayInspector : AbstractXRayInterceptor() {
    @Pointcut("@within(com.amazonaws.xray.spring.aop.XRayEnabled) && bean(*Controller)")
    override fun xrayEnabledClasses() {
    }

    override fun generateMetadata(pjp: ProceedingJoinPoint, subsegment: Subsegment): Map<String, Map<String, Any>> {
//        logger.trace("aws xray tracing method - {}.{}", pjp?.signature?.declaringTypeName, pjp?.signature?.name)

        val metadata = super.generateMetadata(pjp, subsegment)

        metadata["ClassInfo"]!!["Class"] = pjp.signature.declaringTypeName

        val argumentsInfo: MutableMap<String, Any> = HashMap()

        pjp.args
            .forEachIndexed { index, it ->
                if (it == null) {
                    argumentsInfo["NullParameter$index"] = "null"
                } else {
                    argumentsInfo[it::class.java.simpleName] = it
                }
            }

        metadata["Arguments"] = argumentsInfo
        metadata["ClassInfo"]!!["Package"] = pjp.signature.declaringType.getPackage().name
        metadata["ClassInfo"]!!["Method"] = pjp.signature.name

        return metadata
    }

    override fun processXRayTrace(pjp: ProceedingJoinPoint): Any? {
        return try {
            val subsegment: Subsegment = AWSXRay.beginSubsegment(pjp.signature.declaringTypeName + "." + pjp.signature.name)
//            logger.trace("Begin aws xray subsegment")
            Optional.ofNullable(subsegment)
                .ifPresent { s -> s.metadata = generateMetadata(pjp, subsegment) }
            val result = XRayInterceptorUtils.conditionalProceed(pjp)
            Optional.ofNullable(result)
                .ifPresent {
                    val resultMeta: MutableMap<String, Any> = HashMap()
                    resultMeta[result.javaClass.canonicalName] = result
                    subsegment.metadata["Result"] = resultMeta
                }
            result
        } catch (e: Exception) {
            AWSXRay.getCurrentSegment()!!.addException(e)
            throw e
        } finally {
//            logger.trace("Ending aws xray subsegment")
            AWSXRay.endSubsegment()
        }
    }
}