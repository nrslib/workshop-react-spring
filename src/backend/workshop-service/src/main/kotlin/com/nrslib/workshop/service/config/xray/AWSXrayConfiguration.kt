package com.nrslib.workshop.service.config.xray

import com.amazonaws.xray.AWSXRay
import com.amazonaws.xray.AWSXRayRecorderBuilder
import com.amazonaws.xray.javax.servlet.AWSXRayServletFilter
import com.amazonaws.xray.slf4j.SLF4JSegmentListener
import com.amazonaws.xray.strategy.sampling.CentralizedSamplingStrategy
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.util.ResourceUtils
import java.io.FileNotFoundException
import java.net.URL
import javax.servlet.Filter


@Configuration
@ConditionalOnExpression("\${application.enable-xray:true}")
class AWSXrayConfiguration {
    companion object {
        private const val samplingRuleJsonPath = "classpath:xray/sampling-rules.json"
    }

    init {
        val ruleFile: URL? = try {
            ResourceUtils.getURL(samplingRuleJsonPath)
        } catch (e: FileNotFoundException) {
//            logger.error("sampling rule cannot load for aws xray - {}", e.message)
            null
        }

        if (ruleFile != null) {
//            logger.debug("sampling rule load from {} for aws xray", ruleFile.path)

            val builder = AWSXRayRecorderBuilder.standard()
                .withDefaultPlugins()
                .withSamplingStrategy(CentralizedSamplingStrategy(ruleFile))
                .withSegmentListener(SLF4JSegmentListener())

            AWSXRay.setGlobalRecorder(builder.build())
//            logger.info("aws xray recorder was set globally.")
        }

//        logger.info("aws xray configuration is initialized.")
    }

    @Value("\${spring.application.name:}")
    private lateinit var awsXraySegmentName: String

    @Value("\${application.prefix:}")
    private lateinit var applicationPrefix: String

    @Bean
    fun tracingFilter(): Filter {
        val segmentName = "$applicationPrefix-$awsXraySegmentName"
//        logger.info("The segment name for aws xray tracking has been set to {}.", segmentName)

        return AWSXRayServletFilter(segmentName)
    }
}